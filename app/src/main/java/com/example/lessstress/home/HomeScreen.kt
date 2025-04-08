package com.example.lessstress.home

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.lessstress.R

class HomeScreen : AppCompatActivity() {
    private val requestCodeRegister = 10
    private lateinit var buttonLog: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        buttonLog = findViewById(R.id.buttonLogin)
        buttonLog.isEnabled = true
    }

    fun onLogin(view: View) {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }

    fun onRegister(view: View) {
        val myIntent = Intent(this, Registration::class.java)
        myIntent.putExtra("register", true)
        startActivityForResult(myIntent, requestCodeRegister)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Вы действительно хотите выйти?")
        builder.setCancelable(false).setPositiveButton("Да") { dialog, id -> this@HomeScreen.finish()}
        builder.setNegativeButton("Нет") { dialog, id -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }
}