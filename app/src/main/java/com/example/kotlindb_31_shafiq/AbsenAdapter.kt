package com.example.kotlindb_31_shafiq

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindb_31_shafiq.room.Absen
import kotlinx.android.synthetic.main.list_absen.view.*

class AbsenAdapter(private val absen: ArrayList<Absen>, private val listener: OnAdapterListener) : RecyclerView.Adapter<AbsenAdapter.AbsenViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbsenViewHolder {
        return AbsenViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_absen, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AbsenViewHolder, position: Int) {
        val absen = absen[position]
        holder.view.text_siswa.text = absen.siswa
        holder.view.text_siswa.setOnClickListener {
            listener.onClick( absen )
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate( absen )
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete( absen )
        }
    }

    override fun getItemCount() = absen.size

    class AbsenViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Absen>) {
        absen.clear()
        absen.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(absen: Absen)
        fun onUpdate(absen: Absen)
        fun onDelete(absen: Absen)

    }

}