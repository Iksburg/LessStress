package com.example.lessstress.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.lessstress.R

class Sleepy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sleepy)
        val back = findViewById<Button>(R.id.buttonBack)
        val start = findViewById<Button>(R.id.buttonStart)
        val text = findViewById<TextView>(R.id.text)
        var i = 0
        val motion = listOf<Pair<String, Long>>(
            Pair("Вдохните через нос", 2000), Pair("Резко выдохните", 1000),
            Pair("Вдохните через нос", 2000), Pair("Резко выдохните", 1000),
            Pair("Вдохните через нос", 2000), Pair("Резко выдохните", 1000),
            Pair("Вдохните через нос", 2000), Pair("Резко выдохните", 1000),
            Pair("Вдохните через нос", 2000), Pair("Резко выдохните", 1000),
            Pair("Вдохните через нос", 2000), Pair("Резко выдохните", 1000)
        )

        val thread: Thread = object : Thread() {
            override fun run() {
                try {
                    while (!this.isInterrupted) {
                        if (i > 11) {
                            runOnUiThread {
                                text.text = "Дыхательная гимнастика закончена"
                            }
                            i = 0
                            break
                        }
                        runOnUiThread {
                            text.text = motion[i].first
                        }
                        sleep(motion[i].second)
                        i++
                    }
                } catch (e: InterruptedException) {
                }
            }
        }

        start.setOnClickListener {
            if (!thread.isAlive) thread.start()
        }

        back.setOnClickListener {
            startActivity(
                Intent(
                    this@Sleepy,
                    Breath::class.java
                )
            )
        }
    }
}