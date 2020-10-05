package com.wahyu.hiringapps2.dashboard.profile.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.databinding.ItemRecycleViewRoomBinding
import java.util.*

class NoteListAdapter : RecyclerView.Adapter<NoteListAdapter.NoteListViewHolder>() {

    private val items = mutableListOf<NoteRoomEntity>()

    fun addItems(data: List<NoteRoomEntity>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListViewHolder {
        return NoteListViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_recycle_view_room, parent, false))
    }

    class NoteListViewHolder(val binding: ItemRecycleViewRoomBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) {
        val note = items[position]
        holder.binding.tvId.text = note.id.toString()
        holder.binding.tvNoteTitle.text = note.name
        holder.binding.tvCreatedAt.text = formatDate(note.createdAt)
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