package com.example.lessstress.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.example.lessstress.R

class Breath : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breath)

        setupNavigation()

        val panicAttack = findViewById<Button>(R.id.buttonPanicAttack)
        val worried = findViewById<Button>(R.id.buttonWorried)
        val sleepy = findViewById<Button>(R.id.buttonSleepy)
        val stressed = findViewById<Button>(R.id.buttonStress)
        val angry = findViewById<Button>(R.id.buttonAngry)

        panicAttack.setOnClickListener {
            startActivity(
                Intent(
                    this@Breath,
                    PanicAttack::class.java
                )
            )
        }

        worried.setOnClickListener {
            startActivity(
                Intent(
                    this@Breath,
                    Worried::class.java
                )
            )
        }

        sleepy.setOnClickListener {
            startActivity(
                Intent(
                    this@Breath,
                    Sleepy::class.java
                )
            )
        }

        stressed.setOnClickListener {
            startActivity(
                Intent(
                    this@Breath,
                    Stressed::class.java
                )
            )
        }

        angry.setOnClickListener {
            startActivity(
                Intent(
                    this@Breath,
                    Angry::class.java
                )
            )
        }
    }

    private fun setupNavigation() {
        findViewById<ImageButton>(R.id.musicButton).setOnClickListener {
            startActivity(Intent(this, Music::class.java))
        }
        findViewById<ImageButton>(R.id.moonButton).setOnClickListener {
            startActivity(Intent(this, SleepDiary::class.java))
        }
        findViewById<ImageButton>(R.id.elephantButton).setOnClickListener {
        }
    }
}