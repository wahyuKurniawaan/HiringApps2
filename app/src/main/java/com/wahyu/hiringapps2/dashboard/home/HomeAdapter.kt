package com.wahyu.hiringapps2.dashboard.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.databinding.ItemRecycleViewHomeBinding

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeHolder>() {

    private val items = mutableListOf<HomeModel>()

    fun addList(list: List<HomeModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class HomeHolder(val binding: ItemRecycleViewHomeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        return HomeHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_recycle_view_home, parent, false))
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        val item = items[position]
        holder.binding.tvName.text = item.name
        holder.binding.tvJobTitle.text = item.jobTitle
    }

    override fun getItemCount(): Int = items.size
}