package com.example.kotlindb_31_shafiq.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Absen (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val siswa: String,
    val kelas: String,

    )