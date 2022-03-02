package com.example.kotlindb_31_shafiq

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlindb_31_shafiq.room.Absen
import com.example.kotlindb_31_shafiq.room.AbsenDb
import com.example.kotlindb_31_shafiq.room.Constant
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val db by lazy { AbsenDb(this) }
    lateinit var absenAdapter: AbsenAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupLisener()
        setupRecyclerView()

    }

    override fun onStart() {
        super.onStart()
        loadAbsen()
    }

    fun loadAbsen(){
        CoroutineScope(Dispatchers.IO).launch {
            val absen = db.AbsenDao().getAbsen()
            Log.d("MainActivity", "dbresponse $absen")
            withContext(Dispatchers.Main) {
                absenAdapter.setData(absen)
            }
        }
    }


    fun setupLisener() {
        add_absen.setOnClickListener {
            intentAdd(0, Constant.TYPE_CREATE)
        }
    }

    fun intentAdd(absen_Id: Int, intentType: Int){
        startActivity(
            Intent(applicationContext, AddActivity::class.java)
                .putExtra("intent_id", absen_Id)
                .putExtra("intent_type", intentType)
        )
    }

    private fun setupRecyclerView(){
        absenAdapter = AbsenAdapter(arrayListOf(), object : AbsenAdapter.OnAdapterListener{
            override fun onClick(absen: Absen) {
                //Toast.makeText(applicationContext, anime.title, Toast.LENGTH_SHORT).show()
                //for read
                intentAdd(absen.id,Constant.TYPE_READ)
            }

            override fun onUpdate(absen: Absen) {
                intentAdd(absen.id,Constant.TYPE_UPDATE)
            }

            override fun onDelete(absen: Absen) {
                deleteDialog(absen)
            }

        })
        rv_movie.apply{
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = absenAdapter
        }
    }

    private fun deleteDialog(absen: Absen) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Serius mau hapus absen ${absen.siswa}?")
            setNegativeButton("Cancel") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Delete") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.AbsenDao().deleteAbsen(absen)
                    loadAbsen()
                }
            }

        }
        alertDialog.show()
    }
}