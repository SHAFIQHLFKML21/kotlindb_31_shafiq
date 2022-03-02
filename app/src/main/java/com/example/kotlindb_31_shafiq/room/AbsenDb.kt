package com.example.kotlindb_31_shafiq.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Absen::class],
    version = 1
)
abstract class AbsenDb : RoomDatabase(){

    abstract fun AbsenDao() : AbsenDao

    companion object {

        @Volatile private var instance : AbsenDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }

        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AbsenDb::class.java,
            "Absen12345.db"
        ).build()
    }
}