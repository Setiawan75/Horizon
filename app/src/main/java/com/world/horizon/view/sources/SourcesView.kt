package com.world.horizon.view.sources

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.world.horizon.R
import com.world.horizon.databinding.ActivitySourcesBinding
import com.world.horizon.model.Category
import com.world.horizon.model.Sources
import com.world.horizon.network.Resource
import com.world.horizon.shared.extensions.showDialogError
import com.world.horizon.shared.supportclasses.PaginationScrollListener
import com.world.horizon.shared.utils.getParcelableIntent
import com.world.horizon.shared.utils.setVisible
import com.world.horizon.view.articles.ArticlesView
import com.world.horizon.viewmodel.SourcesViewModel

class SourcesView : AppCompatActivity() {

    companion object{
        private const val CATEGORY = "category"
        fun newIntent(context: Context, category: Category) = Intent(context, SourcesView::class.java).apply {
            putExtra(CATEGORY, category)
        }
    }

    private lateinit var binding: ActivitySourcesBinding
    private lateinit var sourcesAdapter: SourcesAdapter
    private lateinit var viewModel: SourcesViewModel
    private lateinit var scrollListener: PaginationScrollListener
    private var page = 1
    private var category = Category()
    private var isLoadMore = false
    private var nextPage = false
    private var search = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySourcesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[SourcesViewModel::class.java]
        initData()
        getListSources()
        initView()
    }

    private fun initData(){
        category = intent.getParcelableIntent(CATEGORY) ?: Category()
    }

    private fun initView(){
        binding.modifyToolbar.setTitle(getString(R.string.sources))

        binding.txtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                search = binding.txtSearch.text.toString()
                // update UI
                when (search.isNotEmpty()) {
                    true -> {
                        binding.txtSearch.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, 0, 0
                        )
                        binding.imgClear.visibility = View.VISIBLE
                    }
                    false -> {
                        binding.txtSearch.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_search, 0
                        )
                        binding.imgClear.visibility = View.GONE
                    }
                }
            }
        })

        binding.txtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) getListSources()
            true
        }

        binding.imgClear.setOnClickListener {
            binding.txtSearch.setText("")
            search = binding.txtSearch.text.toString()
            getListSources()
        }

        viewModel.listSources.observe(this) {
            if (isLoadMore) binding.progressLoadMore.setVisible(false)
            else binding.progressbar.setVisible(false)
            when(it) {
                is Resource.Success -> {
                    if (it.data.isNotEmpty()) {
                        nextPage = it.data.size > 99
                        if (isLoadMore) {
                            sourcesAdapter.addItem(it.data)
                            isLoadMore = false
                        } else {
                            sourcesAdapter = SourcesAdapter(it.data)
                            binding.rvSources.layoutManager = LinearLayoutManager(this@SourcesView)
                            binding.rvSources.adapter = sourcesAdapter

                            sourcesAdapter.setOnItemClickCallback(object :
                                SourcesAdapter.OnItemClickCallback {
                                override fun onItemClicked(sources: Sources) {
                                    startActivity(ArticlesView.newIntent(this@SourcesView, sources))
                                }

                            })

                            scrollListener = object : PaginationScrollListener() {
                                override fun onReachBottom() {
                                    if (!nextPage || viewModel.loadingPage) return
                                    page += 1
                                    isLoadMore = true
                                    getListSources()
                                }
                            }

                            scrollListener.visibleThresholdPercentage = 0.7

                            binding.wrapContent.scrollTo(0, 0)
                            binding.wrapContent.setOnScrollChangeListener(scrollListener)
                        }
                        binding.wrapContent.setVisible(true)
                        binding.lblNotFound.setVisible(false)
                    }else{
                        binding.wrapContent.setVisible(false)
                        binding.lblNotFound.setVisible(true)
                    }
                }
                is Resource.Failure -> showDialogError("Failed", it.throwable)
                else -> {}
            }
        }
    }

    private fun getListSources(){
        if (isLoadMore) binding.progressLoadMore.setVisible(true)
        else binding.progressbar.setVisible(true)
        viewModel.getListSources(category.category, page.toString(), search)
    }
}