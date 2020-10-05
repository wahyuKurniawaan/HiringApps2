package com.wahyu.hiringapps2.dashboard.profile.room

import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: NoteRoomEntity)

    @Query("SELECT * FROM word_table")
    fun getAllNoteData(): List<NoteRoomEntity>

    @Query("SELECT * FROM word_table WHERE id = :id")
    fun getNoteDataById(id: Int): NoteRoomEntity

    @Update
    fun updateNoteData(data: NoteRoomEntity)

    @Delete
    fun deleteNoteData(data: NoteRoomEntity)

    @Query("DELETE FROM word_table")
    fun deleteAllNoteData()
}