package com.world.horizon.view.sources

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.world.horizon.R
import com.world.horizon.databinding.ItemSourcesBinding
import com.world.horizon.model.Sources

class SourcesAdapter(data: ArrayList<Sources>): RecyclerView.Adapter<SourcesAdapter.SourcesHolder>() {
    var dataToShow = ArrayList<Sources>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    init {
        dataToShow.addAll(data)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(sources: Sources)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SourcesHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sources, parent, false)
        return SourcesHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: SourcesHolder, position: Int) {
        holder.bind(dataToShow[position])
    }

    inner class SourcesHolder(view: View, private val context: Context) :
        RecyclerView.ViewHolder(view) {
        val binding = ItemSourcesBinding.bind(view)
        fun bind(sources: Sources) {
            binding.title.text = sources.name
            binding.desc.text = sources.description
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(sources)
            }
        }
    }

    override fun getItemCount(): Int = dataToShow.size

    fun addItem(listSources: ArrayList<Sources>){
        dataToShow.addAll(listSources)
        notifyItemInserted(dataToShow.size - 1)
    }
}