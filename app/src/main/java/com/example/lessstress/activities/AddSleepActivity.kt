package com.example.lessstress.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lessstress.R
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AddSleepActivity : AppCompatActivity(), CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sleep)

        val config = RealmConfiguration.create(schema = setOf(Note::class))
        realm = Realm.open(config)

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
            val createdTime = System.currentTimeMillis()

            launch {
                realm.write {
                    this.copyToRealm(
                        Note().apply {
                            this.title = title
                            this.description = description
                            this.createdTime = createdTime
                        }
                    )
                }
                Toast.makeText(applicationContext, "Сон сохранён", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        realm.close()
    }
}