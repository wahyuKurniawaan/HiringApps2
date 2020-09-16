package com.wahyu.hiringapps2.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.wahyu.hiringapps2.R

class RecycleViewAdapter : RecyclerView.Adapter<RecycleViewAdapter.ProjectViewHolder>() {

    private val listProjectName = listOf(
        "Project Camera",
        "Realtime CCTV 24 hour",
        "My Gallery Theme",
        "Game project = Merdeka atau mati!",
        "Cleaner data and cache",
        "Battle royal game = Penguin arena",
        "Logic game = The connect",
        "Avatar maker",
        "Adzan Reminder based on Location ",
        "Dating Apps = TenTen",
        "Project Weather forecast Synchronized with Google Weather",
        "Project Jadwal penerbangan nasional",
        "E-Learning Project = Ruang KepSek",
        "Tower Defence game = Dungeon Siege 2",
        "Video player for android with night mode features",
        "My music player with random algorithm",
        "Advance wifi signal catcher with extra security",
        "Message Application Using google cloud firebase",
        "Simulation Game = Roller Coaster Tycoon 2",
        "Backup data application",
        "Backup data application"
    )
    private val listProjectPictureUrl = listOf(
        "https://icons.iconarchive.com/icons/treetog/junior/256/folder-blue-pictures-icon.png",
        "https://icons.iconarchive.com/icons/aha-soft/free-global-security/128/CCTV-Camera-icon.png",
        "https://icons.iconarchive.com/icons/gakuseisean/aire/128/Images-icon.png",
        "https://icons.iconarchive.com/icons/neokratos/metal-gear-solid-4/128/snake-2-icon.png",
        "https://icons.iconarchive.com/icons/papirus-team/papirus-apps/128/preferences-system-login-icon.png",
        "https://icons.iconarchive.com/icons/3xhumed/mega-games-pack-27/128/Penguins-Arena-Sedna-s-World-overSTEAM-2-icon.png",
        "https://icons.iconarchive.com/icons/cornmanthe3rd/plex-android/96/flow-free-icon.png",
        "https://icons.iconarchive.com/icons/google/noto-emoji-people-expressions/128/10986-woman-pouting-light-skin-tone-icon.png",
        "https://icons.iconarchive.com/icons/icons8/ios7/128/Travel-Taj-Mahal-icon.png",
        "https://icons.iconarchive.com/icons/saki/nuoveXT/128/Actions-kontact-contacts-icon.png",
        "https://icons.iconarchive.com/icons/icons8/ios7/128/Ecommerce-Keep-Dry-icon.png",
        "https://icons.iconarchive.com/icons/google/noto-emoji-people-profession/128/10371-man-pilot-medium-dark-skin-tone-icon.png",
        "https://icons.iconarchive.com/icons/google/noto-emoji-people-profession/128/10218-man-teacher-icon.png",
        "https://icons.iconarchive.com/icons/3xhumed/mega-games-pack-31/128/Dungeon-Siege-2-new-1-icon.png",
        "https://icons.iconarchive.com/icons/capital18/ethereal/128/Misc-Last-icon.png",
        "https://icons.iconarchive.com/icons/raindropmemory/down-to-earth/128/G12-Music-3-icon.png",
        "https://icons.iconarchive.com/icons/oxygen-icons.org/oxygen/128/Devices-network-wireless-icon.png",
        "https://icons.iconarchive.com/icons/papirus-team/papirus-apps/128/whatsie-icon.png",
        "https://icons.iconarchive.com/icons/3xhumed/mega-games-pack-18/128/Roller-Coaster-Tycoon-2-1-icon.png",
        "https://icons.iconarchive.com/icons/whyred/dsquared-bin/128/trash-yellow-full-icon.png",
        "https://icons.iconarchive.com/icons/whyred/dsquared-bin/128/trash-yellow-full-icon.png"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        return ProjectViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycle_view_project, parent, false)
        )
    }

    override fun getItemCount(): Int = listProjectName.size

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.tvName.text = listProjectName[position]
        holder.tvPosition.text = (position + 1).toString()
        val context = holder.imageView.context
        Picasso.with(context).load(listProjectPictureUrl[position]).resize(100, 100).centerCrop()
            .into(holder.imageView)
    }

    class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tvProjectNameItem)
        var tvPosition: TextView = itemView.findViewById(R.id.tvPosition)
        var imageView: ImageView = itemView.findViewById(R.id.imageViewProject)
    }
}