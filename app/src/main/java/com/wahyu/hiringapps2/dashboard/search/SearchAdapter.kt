package com.wahyu.hiringapps2.dashboard.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.databinding.ItemRecycleViewSearchBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchHolder>() {

    private val items = mutableListOf<SearchModel>()
    private fun getPhotoImage(file: String) : String = "http://34.234.66.114:8080/uploads/$file"
    fun addList(list: List<SearchModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class SearchHolder(val binding: ItemRecycleViewSearchBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        return SearchHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_recycle_view_search, parent, false))
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        val item = items[position]
        holder.binding.tvName.text = item.name
        holder.binding.tvJobTitle.text = item.jobTitle
        Picasso.get().load(getPhotoImage(item.image.toString())).
        into(holder.binding.ivImage)

    }

    override fun getItemCount(): Int = items.size
}