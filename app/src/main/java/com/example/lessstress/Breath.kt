package com.example.lessstress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class Breath : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breath)

        val panicAttack = findViewById<Button>(R.id.buttonPanicAttack)
        val worried = findViewById<Button>(R.id.buttonWorried)
        val sleepy = findViewById<Button>(R.id.buttonSleepy)
        val stressed = findViewById<Button>(R.id.buttonStress)
        val angry = findViewById<Button>(R.id.buttonAngry)
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
}