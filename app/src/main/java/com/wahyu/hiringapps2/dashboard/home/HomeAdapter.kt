package com.wahyu.hiringapps2.dashboard.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.dashboard.home.detail.DetailProfileActivity
import com.wahyu.hiringapps2.databinding.ItemRvHomeBinding

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeHolder>() {

    private val items = mutableListOf<HomeModel>()

    private fun getPhotoImage(file: String) : String = "http://34.234.66.114:8080/uploads/$file"

    fun addList(list: List<HomeModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class HomeHolder(val binding: ItemRvHomeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        return HomeHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_rv_home, parent, false))
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        val item = items[position]
        holder.binding.tvName.text = item.name
        holder.binding.tvJobTitle.text = item.jobTitle
        Picasso.get().load(getPhotoImage(item.image ?: "image-1601202778097.png")).
            into(holder.binding.ivImage)

        holder.binding.root.setOnClickListener {
            val intent = Intent(holder.binding.root.context, DetailProfileActivity::class.java)
            intent.putExtra("id", item.id)
            intent.putExtra("user_id", item.userId)
            holder.binding.root.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}