package com.example.lessstress

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmResults
import java.text.DateFormat


class MyAdapter(var context: Context, var notesList: RealmResults<Note>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = notesList[position]
        holder.titleOutput.text = note!!.title
        holder.descriptionOutput.text = note.description
        val formatedTime = DateFormat.getDateTimeInstance().format(note.createdTime)
        holder.timeOutput.text = formatedTime
        holder.itemView.setOnLongClickListener { v ->
            val menu = PopupMenu(context, v)
            menu.menu.add("Удалить")
            menu.setOnMenuItemClickListener { item ->
                if (item.title == "Удалить") {
                    val realm = Realm.getDefaultInstance()
                    realm.beginTransaction()
                    note.deleteFromRealm()
                    realm.commitTransaction()
                    Toast.makeText(context, "Сон удалён", Toast.LENGTH_SHORT).show()
                }
                true
            }
            menu.show()
            true
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleOutput: TextView = itemView.findViewById(R.id.titleoutput)
        var descriptionOutput: TextView = itemView.findViewById(R.id.descriptionoutput)
        var timeOutput: TextView = itemView.findViewById(R.id.timeoutput)

    }
}