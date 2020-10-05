package com.wahyu.hiringapps2.dashboard.profile.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteRoomEntity::class], version = 1, exportSchema = false)
abstract class NoteRoomDatabase() : RoomDatabase() {

    abstract fun wordDao() : NoteDao

    companion object {
        private var INSTANCE: NoteRoomDatabase? = null

        fun getWordDatabase(context: Context): NoteRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    NoteRoomDatabase::class.java, "Word_room.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}