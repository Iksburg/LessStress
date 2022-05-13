package com.example.lessstress

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class HomeScreen : AppCompatActivity() {
    private val requestCodeRegister = 10
    private var user = Users()
    lateinit var buttonLog: Button
    lateinit var buttonInf: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        buttonLog = findViewById(R.id.buttonLogin)
        buttonInf = findViewById(R.id.buttonInfo)
        buttonInf.isEnabled = false
    }

    fun onLogin(view: View) {
        val loginDialog = Login()
        val file = "myFile.json"
        val bufferedReader = BufferedReader(InputStreamReader(openFileInput(file)))
        val readText = bufferedReader.readLine()
        val jsonObject = JSONObject(readText)
        loginDialog.users = jsonObject.getString("user")
        loginDialog.password = jsonObject.getString("password")
        bufferedReader.close()
        loginDialog.homeScreen = this
        loginDialog.show(supportFragmentManager, "Dialog_Tag")
    }

    fun onRegister(view: View) {
        val myIntent = Intent(this, Registration::class.java)
        myIntent.putExtra("register", true)
        startActivityForResult(myIntent, requestCodeRegister)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == requestCodeRegister) {
                val bundleData = data!!.getBundleExtra("user")
                user.setBundle(bundleData!!)
                buttonLog.isEnabled = true
            }
        }
    }

    fun onInformation(view: View) {
        val myIntent = Intent(this, Registration::class.java)
        myIntent.putExtra("register", false)
        startActivity(myIntent)
    }

    fun onActivateInformation() {
        buttonInf.isEnabled = true
        val intent = Intent(this, Music::class.java)
        startActivity(intent)
    }
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Вы действительно хотите выйти?")
        builder.setCancelable(false).setPositiveButton("Да") { dialog, id -> this@HomeScreen.finish()}
        builder.setNegativeButton("Нет") { dialog, id -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }
}