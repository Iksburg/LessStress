package com.example.lessstress.activities

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.example.lessstress.R

class Music : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        setupNavigation()

        val nature = findViewById<ImageButton>(R.id.buttonNature)
        val bonfire = findViewById<ImageButton>(R.id.buttonBonfire)
        val birds = findViewById<ImageButton>(R.id.buttonBirds)
        val snowSteps = findViewById<ImageButton>(R.id.buttonSnowSteps)
        val thunder = findViewById<ImageButton>(R.id.buttonThunder)
        val stop = findViewById<Button>(R.id.stopButton)
        val mp = MediaPlayer()

        var pausedNature = false
        var pausedBonfire = false
        var pausedBirds = false
        var pausedSnowSteps = false
        var pausedThunder = false

        var currentResource = ""

        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        val maxVolume: Int = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val curValue: Int = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        val volumeControl = findViewById<SeekBar>(R.id.volumeControl)
        volumeControl.max = maxVolume
        volumeControl.progress = curValue
        volumeControl.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })


        nature.setOnClickListener {
            if (currentResource != "nature") {
                mp.reset()
                mp.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+ R.raw.nature))
                mp.prepare()
                mp.start()
                currentResource = "nature"
            } else {
                if (mp.isPlaying) {
                    mp.pause()
                    pausedNature = true
                } else if (pausedNature) {
                    mp.start()
                }
            }
        }

        bonfire.setOnClickListener {
            if (currentResource != "bonfire") {
                mp.reset()
                mp.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+ R.raw.bonfire))
                mp.prepare()
                mp.start()
                currentResource = "bonfire"
            } else {
                if (mp.isPlaying) {
                    mp.pause()
                    pausedBonfire = true
                } else if (pausedBonfire) {
                    mp.start()
                }
            }
        }

        birds.setOnClickListener {
            if (currentResource != "birds") {
                mp.reset()
                mp.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+ R.raw.birds))
                mp.prepare()
                mp.start()
                currentResource = "birds"
            } else {
                if (mp.isPlaying) {
                    mp.pause()
                    pausedBirds = true
                } else if (pausedBirds) {
                    mp.start()
                }
            }
        }

        snowSteps.setOnClickListener {
            if (currentResource != "snowSteps") {
                mp.reset()
                mp.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+ R.raw.snow_steps))
                mp.prepare()
                mp.start()
                currentResource = "snowSteps"
            } else {
                if (mp.isPlaying) {
                    mp.pause()
                    pausedSnowSteps = true
                } else if (pausedSnowSteps) {
                    mp.start()
                }
            }
        }

        thunder.setOnClickListener {
            if (currentResource != "thunder") {
                mp.reset()
                mp.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+ R.raw.thunder))
                mp.prepare()
                mp.start()
                currentResource = "thunder"
            } else {
                if (mp.isPlaying) {
                    mp.pause()
                    pausedThunder = true
                } else if (pausedThunder) {
                    mp.start()
                }
            }
        }

        stop.setOnClickListener {
            try {
                if (mp.isPlaying) {
                    mp.stop()
                    mp.prepare()
                    currentResource = ""
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setupNavigation() {
        findViewById<ImageButton>(R.id.musicButton).setOnClickListener {
        }
        findViewById<ImageButton>(R.id.moonButton).setOnClickListener {
            startActivity(Intent(this, SleepDiary::class.java))
        }
        findViewById<ImageButton>(R.id.elephantButton).setOnClickListener {
            startActivity(Intent(this, Breath::class.java))
        }
    }
}

