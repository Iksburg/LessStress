package com.example.lessstress

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.sort
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.Updated
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SleepDiary : AppCompatActivity() {

    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sleep_diary)

        val music = findViewById<ImageButton>(R.id.musicButton)
        val moon = findViewById<ImageButton>(R.id.moonButton)
        val elephant = findViewById<ImageButton>(R.id.elephantButton)

        moon.setOnClickListener {
            val intent = Intent(this, SleepDiary::class.java)
            startActivity(intent)
        }

        music.setOnClickListener {
            val intent = Intent(this, Music::class.java)
            startActivity(intent)
        }

        elephant.setOnClickListener {
            val intent = Intent(this, Breath::class.java)
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

        val config = RealmConfiguration.create(schema = setOf(Note::class))
        realm = Realm.open(config)

        loadData()

    }

    private fun loadData() {
        val sleepList = realm.query<Note>().sort("createdTime").find()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val myAdapter = MyAdapter(applicationContext, sleepList)
        recyclerView.adapter = myAdapter

        sleepList.addListener { results: ResultsChange<Note> ->
            when (results) {
                is Updated -> myAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}