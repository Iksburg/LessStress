package com.example.lessstress.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lessstress.R
import io.realm.kotlin.Realm
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import io.ktor.client.request.*
import io.ktor.http.*
import com.example.lessstress.network.ktorClient
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddSleepActivity : AppCompatActivity(), CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sleep)

        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, SleepDiary::class.java)
            startActivity(intent)
        }

        val titleInput = findViewById<EditText>(R.id.titleInput)
        val descriptionInput = findViewById<EditText>(R.id.descriptionInput)
        val saveButton = findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {
            val title = titleInput.text.toString()
            val description = descriptionInput.text.toString()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
            val date = dateFormat.format(Date())
            val iso8601Date = date.substring(0, date.length - 2) + ":" + date.substring(date.length - 2)

            val prefs = getSharedPreferences("auth", MODE_PRIVATE)
            val token = prefs.getString("token", null)
            val userId = prefs.getInt("userId", -1)

            if (token == null || userId == -1) {
                Toast.makeText(this, "Ошибка авторизации", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            launch {
                val request = SleepRequest(userId, title, description, iso8601Date)
                try {
                    val response = ktorClient.post("http://192.168.161.109:8080/sleep") {
                        contentType(ContentType.Application.Json)
                        setBody(request)
                        headers {
                            append(HttpHeaders.Authorization, "Bearer $token")
                        }
                    }

                    if (response.status.value == 200) {
                        Toast.makeText(applicationContext, "Сон добавлен", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@AddSleepActivity, SleepDiary::class.java))
                    } else {
                        Toast.makeText(applicationContext, "Ошибка: ${response.status}", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, "Ошибка соединения: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        realm.close()
    }
}