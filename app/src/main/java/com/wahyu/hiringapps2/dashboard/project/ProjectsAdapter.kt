package com.wahyu.hiringapps2.dashboard.project

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.databinding.ItemRecycleViewProjectFromApiBinding
import java.text.DecimalFormat

class ProjectsAdapter : RecyclerView.Adapter<ProjectsAdapter.ProjectHolder>() {

    private val items = mutableListOf<ProjectsModel>()
    private val dec = DecimalFormat("#,###")

    fun addList(list: List<ProjectsModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class ProjectHolder(val binding: ItemRecycleViewProjectFromApiBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectHolder {
        return ProjectHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_recycle_view_project_from_api, parent, false))
    }

    override fun onBindViewHolder(holder: ProjectHolder, position: Int) {
        val item = items[position]
        val getPriceFormat = "Rp ${dec.format(item.price)}"
        holder.binding.tvName.text = item.name
        holder.binding.tvDescription.text = item.description?.toEditable()
        holder.binding.tvPrice.text = getPriceFormat
        holder.binding.tvDuration.text = item.duration
    }

    override fun getItemCount(): Int = items.size

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}