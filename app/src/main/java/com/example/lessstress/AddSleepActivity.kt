package com.example.lessstress

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm

class AddSleepActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sleep)

        val backButton = findViewById<ImageButton>(R.id.backButton)

        backButton.setOnClickListener {
            val intent = Intent(this, SleepDiary::class.java)
            startActivity(intent)
        }

        val titleInput = findViewById<EditText>(R.id.titleInput)
        val descriptionInput = findViewById<EditText>(R.id.descriptionInput)
        val saveButton = findViewById<Button>(R.id.saveButton)

        Realm.init(applicationContext)
        val realm = Realm.getDefaultInstance()

        saveButton.setOnClickListener {
            val title = titleInput.text.toString()
            val description = descriptionInput.text.toString()
            val createdTime = System.currentTimeMillis()

            realm.beginTransaction()
            val sleep: Note = realm.createObject(Note::class.java)
            sleep.title = title
            sleep.description = description
            sleep.createdTime = createdTime
            realm.commitTransaction()
            Toast.makeText(applicationContext, "Сон сохранён", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}