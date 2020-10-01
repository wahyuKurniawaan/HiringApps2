package com.wahyu.hiringapps2.room

import androidx.room.*

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: WordRoomEntity)

    @Query("SELECT * FROM word_table")
    fun getAllWordData(): List<WordRoomEntity>

    @Query("SELECT * FROM word_table WHERE id = :id")
    fun getWordDataById(id: Int): WordRoomEntity

    @Update
    fun updateWordData(data: WordRoomEntity)

    @Delete
    fun deleteWordData(data: WordRoomEntity)

    @Query("DELETE FROM word_table")
    fun deleteAllWordData()
}