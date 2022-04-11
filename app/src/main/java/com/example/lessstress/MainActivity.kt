package com.example.lessstress

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nature = findViewById<Button>(R.id.buttonNature)
        val bonfire = findViewById<Button>(R.id.buttonBonfire)
        val birds = findViewById<Button>(R.id.buttonBirds)
        val snowSteps = findViewById<Button>(R.id.buttonSnowSteps)
        val thunder = findViewById<Button>(R.id.buttonThunder)
        val mp = MediaPlayer()
        var pause = false
        var isPrepared = false

        nature.setOnClickListener {
            if (!mp.isPlaying) {
                if (!pause) {
                    mp.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+R.raw.nature))
                    mp.prepare()
                    mp.start()
                } else {
                    mp.start()
                }
            } else {
                mp.pause()
                pause = true
            }
        }

        bonfire.setOnClickListener {
            if (!mp.isPlaying) {
                if (!pause) {
                    mp.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+R.raw.bonfire))
                    mp.prepare()
                    mp.start()
                } else {
                    mp.start()
                }
            } else {
                mp.pause()
                pause = true
            }
        }

        birds.setOnClickListener {
            if (!mp.isPlaying) {
                if (!pause) {
                    mp.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+R.raw.birds))
                    mp.prepare()
                    mp.start()
                } else {
                    mp.start()
                }
            } else {
                mp.pause()
                pause = true
            }
        }

        snowSteps.setOnClickListener {
            if (!mp.isPlaying) {
                if (!pause) {
                    mp.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+R.raw.snow_steps))
                    mp.prepare()
                    mp.start()
                } else {
                    mp.start()
                }
            } else {
                mp.pause()
                pause = true
            }
        }

        thunder.setOnClickListener {
            if (!mp.isPlaying) {
                if (!pause) {
                    mp.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+R.raw.thunder))
                    mp.prepare()
                    mp.start()
                } else {
                    mp.start()
                }
            } else {
                mp.pause()
                pause = true
            }
        }

    }
}

