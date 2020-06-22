package com.example.codechallenge8.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {

    @Insert
    fun insert(history: History)

    @Query("SELECT * FROM history ORDER BY id DESC")
    fun getAllHistory(): MutableList<History>

    @Delete
    fun delete(history: History)
}