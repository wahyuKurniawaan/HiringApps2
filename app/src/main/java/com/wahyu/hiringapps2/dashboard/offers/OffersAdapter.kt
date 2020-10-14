package com.wahyu.hiringapps2.dashboard.offers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.databinding.ItemRvOffersBinding
import java.text.DecimalFormat

class OffersAdapter : RecyclerView.Adapter<OffersAdapter.OffersHolder>() {

    private val items = mutableListOf<OffersModel>()
    private val dec = DecimalFormat("#,###")

    fun addList(list: List<OffersModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class OffersHolder (val binding: ItemRvOffersBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OffersHolder {
        return OffersHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_rv_offers, parent, false))
    }

    override fun onBindViewHolder(holder: OffersHolder, position: Int) {
        val item = items[position]
        val getPriceFormat = "Rp ${dec.format(item.price)}"
        holder.binding.tvName.text = item.name
        holder.binding.tvDescription.text = item.description
        holder.binding.tvPrice.text = getPriceFormat
        holder.binding.tvDuration.text = item.duration
        holder.binding.tvJobSeekerName.text = item.jobSeekerName
    }

    override fun getItemCount(): Int = items.size
}