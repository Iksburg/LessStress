package com.example.lessstress

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.launch


class SleepDiary : AppCompatActivity() {

    private lateinit var realm: Realm
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sleep_diary)

        val music = findViewById<ImageButton>(R.id.musicButton)
        val moon = findViewById<ImageButton>(R.id.moonButton)
        val elephant = findViewById<ImageButton>(R.id.elephantButton)

        moon.setOnClickListener {
            startActivity(Intent(this, SleepDiary::class.java))
        }

        music.setOnClickListener {
            startActivity(Intent(this, Music::class.java))
        }

        elephant.setOnClickListener {
            startActivity(Intent(this, Breath::class.java))
        }

        val addMusicButton = findViewById<MaterialButton>(R.id.addMusicButton)
        addMusicButton.setOnClickListener {
            startActivity(Intent(this@SleepDiary, AddSleepActivity::class.java))
        }

        val config = RealmConfiguration.create(schema = setOf(Note::class))
        realm = Realm.open(config)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        observeData()
    }

    private fun observeData() {
        val sleepQuery = realm.query<Note>().sort("createdTime")

        lifecycleScope.launch {
            sleepQuery.asFlow().collect { resultsChange ->
                val notes = resultsChange.list.toList()
                myAdapter = MyAdapter(applicationContext, notes)
                recyclerView.adapter = myAdapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}