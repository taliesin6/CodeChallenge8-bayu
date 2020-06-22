package com.example.codechallenge8.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
class History(
    @PrimaryKey(autoGenerate = true) val idn: Int? = null,
    @ColumnInfo(name = "id") val id: String? = null,
    @ColumnInfo(name = "result") val result: String? = null,
    @ColumnInfo(name = "mode") val mode: String? = null,
    @ColumnInfo(name = "date") val date: String? = null
)