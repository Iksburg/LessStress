package com.example.lessstress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Angry : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_angry)
        val back = findViewById<Button>(R.id.buttonBack)
        val start = findViewById<Button>(R.id.buttonStart)
        val text = findViewById<TextView>(R.id.text)
        var i = 0
        val motion = listOf<Pair<String, Long>>(
            Pair("Вдохните через нос", 1000), Pair("Задержите дыхание", 1000),
            Pair("Выдохните через нос", 1000), Pair("Задержите дыхание", 1000),
            Pair("Вдохните через нос", 1000), Pair("Задержите дыхание", 1000),
            Pair("Выдохните через нос", 1000), Pair("Задержите дыхание", 1000),
            Pair("Вдохните через нос", 1000), Pair("Задержите дыхание", 1000),
            Pair("Выдохните через нос", 1000), Pair("Задержите дыхание", 1000),
            Pair("Вдохните через нос", 1000), Pair("Задержите дыхание", 1000),
            Pair("Выдохните через нос", 1000), Pair("Задержите дыхание", 1000)
        )

        val thread: Thread = object : Thread() {
            override fun run() {
                try {
                    while (!this.isInterrupted) {
                        if (i > 15) {
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
                    this.interrupt()
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
                    this@Angry,
                    Breath::class.java
                )
            )
        }
    }
}