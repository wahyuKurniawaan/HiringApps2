package com.wahyu.hiringapps2.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wahyu.hiringapps2.R
import java.util.*

class WordListAdapter : RecyclerView.Adapter<WordListAdapter.WordListViewHolder>() {

    private val items = mutableListOf<WordRoomEntity>()

    fun addItems(data: List<WordRoomEntity>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordListViewHolder {
        return WordListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recycle_view_room, parent, false))
    }

    class WordListViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val tvWordNo = itemview.findViewById<TextView>(R.id.tv_number_position)
        val tvWordName = itemview.findViewById<TextView>(R.id.tv_name_item)
        val tvCreateDate = itemview.findViewById<TextView>(R.id.tv_create_at)
    }

    override fun onBindViewHolder(holder: WordListViewHolder, position: Int) {
        val word = items[position]
        holder.tvWordNo.text = word.id.toString()
        holder.tvWordName.text = word.name
        holder.tvCreateDate.text = formatDate(word.createdAt)
    }

    override fun getItemCount(): Int = items.size
    
    private fun formatDate(date: Long) : String {
        val formattedDate: String
        val c = Calendar.getInstance()
        c.timeInMillis = date
        formattedDate = "${c.get(Calendar.DAY_OF_MONTH)}-${c.get(Calendar.MONTH)}-${c.get(Calendar.YEAR)} ${c.get(Calendar.HOUR_OF_DAY)}:${c.get(Calendar.MINUTE)}"
        return formattedDate
    }
}