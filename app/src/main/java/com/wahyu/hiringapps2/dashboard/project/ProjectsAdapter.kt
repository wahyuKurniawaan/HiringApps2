package com.wahyu.hiringapps2.dashboard.project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.databinding.ItemRvProjectBinding
import java.text.DecimalFormat

class ProjectsAdapter : RecyclerView.Adapter<ProjectsAdapter.ProjectHolder>() {

    private val items = mutableListOf<ProjectsModel>()
    private val dec = DecimalFormat("#,###")

    fun addList(list: List<ProjectsModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class ProjectHolder(val binding: ItemRvProjectBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectHolder {
        return ProjectHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_rv_project, parent, false))
    }

    override fun onBindViewHolder(holder: ProjectHolder, position: Int) {
        val item = items[position]
        val getPriceFormat = "Rp ${dec.format(item.price)}"
        holder.binding.tvName.text = item.name
        holder.binding.tvDescription.text = item.description
        holder.binding.tvPrice.text = getPriceFormat
        holder.binding.tvDuration.text = item.duration
    }

    override fun getItemCount(): Int = items.size
}