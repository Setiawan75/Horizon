package com.world.horizon.view.articles

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.world.horizon.R
import com.world.horizon.databinding.ItemArticleBinding
import com.world.horizon.model.Article
import com.world.horizon.shared.extensions.loadImage

class ArticlesAdapter(data: ArrayList<Article>): RecyclerView.Adapter<ArticlesAdapter.ArticlesHolder>() {
    var dataToShow = ArrayList<Article>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    init {
        dataToShow.addAll(data)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(article: Article)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticlesHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article, parent, false)
        return ArticlesHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ArticlesHolder, position: Int) {
        holder.bind(dataToShow[position])
    }

    inner class ArticlesHolder(view: View, private val context: Context) :
        RecyclerView.ViewHolder(view) {
        val binding = ItemArticleBinding.bind(view)
        fun bind(article: Article) {
            if (!article.urlToImage.isNullOrEmpty())
                loadImage(context, article.urlToImage, binding.image, R.drawable.ic_launcher_foreground)
            binding.title.text = article.title
            binding.desc.text = article.description
            binding.publishDate.text = article.publishedAt
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(article)
            }
        }
    }

    override fun getItemCount(): Int = dataToShow.size

    fun addItem(listArticles: ArrayList<Article>){
        dataToShow.addAll(listArticles)
        notifyItemInserted(dataToShow.size - 1)
    }
}