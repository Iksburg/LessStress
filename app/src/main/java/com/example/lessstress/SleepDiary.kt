package com.example.lessstress

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmResults
import io.realm.Sort


class SleepDiary : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sleep_diary)

        val addNoteBtn = findViewById<MaterialButton>(R.id.addnewnotebtn)
        addNoteBtn.setOnClickListener {
            startActivity(
                Intent(
                    this@SleepDiary,
                    AddNoteActivity::class.java
                )
            )
        }

        Realm.init(applicationContext)
        val realm = Realm.getDefaultInstance()

        val notesList: RealmResults<Note> = realm.where(
            Note::class.java
        ).sort("createdTime", Sort.DESCENDING).findAll()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val myAdapter = MyAdapter(applicationContext, notesList)
        recyclerView.adapter = myAdapter
        notesList.addChangeListener(RealmChangeListener { myAdapter.notifyDataSetChanged() })
    }
}