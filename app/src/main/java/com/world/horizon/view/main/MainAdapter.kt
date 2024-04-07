package com.world.horizon.view.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.world.horizon.R
import com.world.horizon.databinding.ItemCategoryBinding
import com.world.horizon.model.Category

class MainAdapter (data: ArrayList<Category>): RecyclerView.Adapter<MainAdapter.CategoryHolder>() {
    private var dataToShow = ArrayList<Category>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    init {
        dataToShow.addAll(data)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(category: Category)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(dataToShow[position])
    }

    inner class CategoryHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view){
        val binding = ItemCategoryBinding.bind(view)
        fun bind(category: Category){
            binding.title.text = category.title
            binding.cvContent.setCardBackgroundColor(context.getColor(category.color))
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(category)
            }
        }
    }

    override fun getItemCount(): Int = dataToShow.size

}