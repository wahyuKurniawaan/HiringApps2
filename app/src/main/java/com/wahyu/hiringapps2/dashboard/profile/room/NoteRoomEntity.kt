package com.wahyu.hiringapps2.dashboard.profile.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class NoteRoomEntity(var name: String = "",
                          @ColumnInfo(name = "created_at") var createdAt: Long = 0 ) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}