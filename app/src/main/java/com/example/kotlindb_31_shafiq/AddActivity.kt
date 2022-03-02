package com.example.kotlindb_31_shafiq

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlindb_31_shafiq.room.Absen
import com.example.kotlindb_31_shafiq.room.AbsenDb
import com.example.kotlindb_31_shafiq.room.Constant
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    val db by lazy { AbsenDb(this) }
    private var absenId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setupView()
        setupListener()
        absenId = intent.getIntExtra("intent_id", 0)

        //Toast.makeText(this, animeId.toString(), Toast.LENGTH_SHORT).show()
    }

    fun setupView() {
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                btn_update.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btn_save.visibility = View.GONE
                btn_update.visibility = View.GONE
                getMovie()
            }
            Constant.TYPE_UPDATE -> {
                btn_save.visibility = View.GONE
                getMovie()
            }
        }
    }

    fun setupListener() {
        btn_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.AbsenDao().addAbsen(
                    Absen(0, et_siswa.text.toString(), et_kelas.text.toString())
                )
                finish()
            }
        }
        btn_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.AbsenDao().updateAbsen(
                    Absen(
                        absenId, et_siswa.text.toString(),
                        et_kelas.text.toString()
                    )
                )
                finish()
            }
        }
    }

    fun getMovie() {
        absenId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val movies = db.AbsenDao().getAbsen(absenId)[0]
            et_siswa.setText(movies.siswa)
            et_kelas.setText(movies.kelas)
        }
    }

//override fun onSupportNavigateUp(): Boolean {
//onBackPressed()
//return super.onSupportNavigateUp()}
}