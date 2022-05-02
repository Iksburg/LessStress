package com.example.lessstress

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
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

        val music = findViewById<ImageButton>(R.id.musicButton)
        val puzzle = findViewById<ImageButton>(R.id.puzzleButton)
        val moon = findViewById<ImageButton>(R.id.moonButton)
        val hurricane = findViewById<ImageButton>(R.id.hurricaneButton)
        val elephant = findViewById<ImageButton>(R.id.elephantButton)

        moon.setOnClickListener {
            val intent = Intent(this, SleepDiary::class.java)
            startActivity(intent)
        }

        music.setOnClickListener {
            val intent = Intent(this, Music::class.java)
            startActivity(intent)
        }

        val addMusicButton = findViewById<MaterialButton>(R.id.addMusicButton)
        addMusicButton.setOnClickListener {
            startActivity(
                Intent(
                    this@SleepDiary,
                    AddSleepActivity::class.java
                )
            )
        }

        Realm.init(applicationContext)
        val realm = Realm.getDefaultInstance()

        val sleepList: RealmResults<Note> = realm.where(
            Note::class.java
        ).sort("createdTime", Sort.DESCENDING).findAll()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val myAdapter = MyAdapter(applicationContext, sleepList)
        recyclerView.adapter = myAdapter
        sleepList.addChangeListener(RealmChangeListener { myAdapter.notifyDataSetChanged() })
    }
}