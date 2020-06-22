package com.example.codechallenge8.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [History::class], version = 1)
abstract class HistoryDB: RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}