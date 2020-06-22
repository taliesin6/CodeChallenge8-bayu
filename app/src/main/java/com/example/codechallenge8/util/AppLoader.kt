package com.example.codechallenge8.util

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.codechallenge8.api.DataRepository
import com.example.codechallenge8.db.HistoryDB
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class AppLoader: Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        startKoin {
            androidContext(this@AppLoader)
            modules(module {
                single {
                    Room.databaseBuilder(applicationContext, HistoryDB::class.java, "HistoryDB")
                        .allowMainThreadQueries()
                        .build()
                }
                single { DataRepository.created() }
            })
            modules(myModule)
        }
    }
}