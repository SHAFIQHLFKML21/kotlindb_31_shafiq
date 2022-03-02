package com.example.kotlindb_31_shafiq.room

import androidx.room.*

@Dao
interface AbsenDao  {

    @Insert
    suspend fun addAbsen(absen : Absen)

    @Update
    suspend fun updateAbsen(absen : Absen)

    @Delete
    suspend fun deleteAbsen(absen : Absen)

    @Query("SELECT * FROM absen")
    suspend fun getAbsen(): List<Absen>

    @Query("SELECT * FROM absen WHERE id=:absen_id")
    suspend fun getAbsen(absen_id: Int): List<Absen>

}