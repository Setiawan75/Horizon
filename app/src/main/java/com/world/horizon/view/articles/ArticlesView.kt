package com.world.horizon.view.articles

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
import com.world.horizon.databinding.ActivityArticlesBinding
import com.world.horizon.model.Article
import com.world.horizon.model.Sources
import com.world.horizon.network.Resource
import com.world.horizon.shared.extensions.showDialogError
import com.world.horizon.shared.supportclasses.PaginationScrollListener
import com.world.horizon.shared.utils.getParcelableIntent
import com.world.horizon.shared.utils.setVisible
import com.world.horizon.viewmodel.ArticlesViewModel

class ArticlesView : AppCompatActivity() {
    companion object{
        private const val SOURCES = "sources"
        fun newIntent(context: Context, sources: Sources) = Intent(context, ArticlesView::class.java).apply {
            putExtra(SOURCES, sources)
        }
    }

    private lateinit var binding: ActivityArticlesBinding
    private lateinit var articlesAdapter: ArticlesAdapter
    private lateinit var viewModel: ArticlesViewModel
    private lateinit var scrollListener: PaginationScrollListener
    private var page = 1
    private var sources = Sources()
    private var isLoadMore = false
    private var nextPage = false
    private var search = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticlesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ArticlesViewModel::class.java]
        initData()
        getListArticles()
        initView()
    }

    private fun initData(){
        sources = intent.getParcelableIntent(SOURCES) ?: Sources()
    }

    private fun initView(){
        binding.modifyToolbar.setTitle(getString(R.string.articles) + " of " + sources.name)

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
            if (actionId == EditorInfo.IME_ACTION_SEARCH) getListArticles()
            true
        }

        binding.imgClear.setOnClickListener {
            binding.txtSearch.setText("")
            search = binding.txtSearch.text.toString()
            getListArticles()
        }

        viewModel.listArticles.observe(this) {
            if (isLoadMore) binding.progressLoadMore.setVisible(false)
            else binding.progressbar.setVisible(false)
            when(it) {
                is Resource.Success -> {
                    nextPage = it.data.size > 99
                    if (it.data.isNotEmpty()) {
                        if (isLoadMore) {
                            articlesAdapter.addItem(it.data)
                            isLoadMore = false
                        } else {
                            scrollListener = object : PaginationScrollListener() {
                                override fun onReachBottom() {
                                    if (!nextPage || viewModel.loadingPage) return
                                    page += 1
                                    isLoadMore = true
                                    getListArticles()
                                }
                            }

                            scrollListener.visibleThresholdPercentage = 0.7

                            binding.wrapContent.scrollTo(0, 0)
                            binding.wrapContent.setOnScrollChangeListener(scrollListener)
                            articlesAdapter = ArticlesAdapter(it.data)
                            binding.rvArticles.layoutManager =
                                LinearLayoutManager(this@ArticlesView)
                            binding.rvArticles.adapter = articlesAdapter


                        }
                        binding.wrapContent.setVisible(true)
                        binding.lblNotFound.setVisible(false)
                        articlesAdapter.setOnItemClickCallback(object :
                            ArticlesAdapter.OnItemClickCallback {
                            override fun onItemClicked(articles: Article) {
    //                                startActivity()
                            }

                        })
                    } else {
                        binding.wrapContent.setVisible(false)
                        binding.lblNotFound.setVisible(true)
                    }
                }
                is Resource.Failure -> showDialogError("Failed", it.throwable)
                else -> {}
            }
        }
    }

    private fun getListArticles(){
        if (isLoadMore) binding.progressLoadMore.setVisible(true)
        else binding.progressbar.setVisible(true)
        viewModel.getListArticles(sources.url, page.toString(), search)
    }
}