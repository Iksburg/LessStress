package com.example.lessstress.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lessstress.R
import java.text.SimpleDateFormat
import java.util.*

class MyAdapter(private var sleepList: List<Note>) :
    RecyclerView.Adapter<MyAdapter.SleepViewHolder>() {

    fun updateData(newList: List<Note>) {
        sleepList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return SleepViewHolder(view)
    }

    override fun onBindViewHolder(holder: SleepViewHolder, position: Int) {
        val item = sleepList[position]
        holder.titleOutput.text = item.title
        holder.descriptionOutput.text = item.description

        try {
            val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault())
            isoFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date = isoFormat.parse(item.date)
            val outputFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
            holder.timeOutput.text = date?.let { outputFormat.format(it) } ?: "Неверная дата"
        } catch (e: Exception) {
            holder.timeOutput.text = "Ошибка даты"
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int = sleepList.size

    inner class SleepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleOutput: TextView = itemView.findViewById(R.id.titleoutput)
        val descriptionOutput: TextView = itemView.findViewById(R.id.descriptionoutput)
        val timeOutput: TextView = itemView.findViewById(R.id.timeoutput)
    }
}