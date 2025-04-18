package com.example.lessstress.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lessstress.R
import com.google.android.material.button.MaterialButton
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import io.ktor.serialization.kotlinx.json.json


class SleepDiary : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter
    private val client = HttpClient {
        install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
            json()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sleep_diary)

        setupNavigation()
        setupRecyclerView()

        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        val token = prefs.getString("token", null)
        val userId = prefs.getInt("userId", -1)

        if (token != null && userId != -1) {
            loadSleepsFromServer(userId)
        }
    }

    private fun setupNavigation() {
        findViewById<ImageButton>(R.id.musicButton).setOnClickListener {
            startActivity(Intent(this, Music::class.java))
        }
        findViewById<ImageButton>(R.id.moonButton).setOnClickListener {
        }
        findViewById<ImageButton>(R.id.elephantButton).setOnClickListener {
            startActivity(Intent(this, Breath::class.java))
        }
        findViewById<MaterialButton>(R.id.addDreamButton).setOnClickListener {
            startActivity(Intent(this, AddSleepActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyAdapter(emptyList())
        recyclerView.adapter = adapter
    }

    private fun loadSleepsFromServer(userId: Int) {
        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        val token = prefs.getString("token", null)

        if (token == null) {
            Toast.makeText(this, "Не удалось получить токен", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val response: HttpResponse = client.get("http://192.168.161.109:8080/sleeps/$userId")

                if (response.status == HttpStatusCode.OK) {
                    val sleepList: List<Note> = response.body()
                    adapter.updateData(sleepList)
                } else {
                    Toast.makeText(this@SleepDiary, "Ошибка загрузки снов", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("HTTP_ERROR", "Ошибка: ${e.localizedMessage}", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@SleepDiary, "Ошибка подключения к серверу", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}