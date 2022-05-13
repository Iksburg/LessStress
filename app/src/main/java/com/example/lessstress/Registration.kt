package com.example.lessstress

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Registration : AppCompatActivity() {
    private lateinit var et_usr: EditText
    private lateinit var et_pwd: EditText
    private lateinit var et_nam: EditText
    private lateinit var et_lastN: EditText
    lateinit var users: String
    lateinit var passwords: String
    lateinit var names: String
    lateinit var lastName: String
    private lateinit var actualizerButton: Button
    var register = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        et_usr = findViewById(R.id.editTextTextUsr)
        et_pwd = findViewById(R.id.editTextTextPwd)
        et_nam = findViewById(R.id.editTextTextName)
        et_lastN = findViewById(R.id.editTextPwd)
        actualizerButton = findViewById(R.id.buttonOk)
        val register = intent.getBooleanExtra("register", true)
        if (!register) {
            actualizerButton.text = "Обновить"
            val file= "myFile.json"
            val bufferedReader = BufferedReader(InputStreamReader(openFileInput(file)))
            val readText = bufferedReader.readLine()
            val jsonObject = JSONObject(readText)
            et_usr.setText(jsonObject.getString("user"))
            et_pwd.setText(jsonObject.getString("password"))
            et_nam.setText(jsonObject.getString("name"))
            et_lastN.setText(jsonObject.getString("lastName"))
            bufferedReader.close()
        }
    }

    fun onEnter(view: View) {
        val file = "myFile.json"
        if (register) {
            val user1 = Users()
            users = et_usr.text.toString()
            passwords = et_pwd.text.toString()
            names = et_nam.text.toString()
            lastName = et_lastN.text.toString()
            user1.user = users
            user1.password = passwords
            user1.name = names
            user1.lastName = lastName
            val fileOutput = openFileOutput(file, Context.MODE_PRIVATE)
            val outputStreamWriter = OutputStreamWriter(fileOutput)
            val onInput = JSONObject()
            onInput.put("user", users)
            onInput.put("password", passwords)
            onInput.put("name", names)
            onInput.put("lastName", lastName)
            outputStreamWriter.write(onInput.toString())
            outputStreamWriter.close()
            fileOutput.close()
            val resultIntent = Intent(this, HomeScreen::class.java)
            resultIntent.putExtra("user", user1.getBundle())
            setResult(Activity.RESULT_OK, resultIntent)
        } else {
            val bufferedReader = BufferedReader(InputStreamReader(openFileInput(file)))
            val readText = bufferedReader.readLine()
            val jsonObject = JSONObject(readText)
            bufferedReader.close()
            val fileOutput = openFileOutput(file, Context.MODE_PRIVATE)
            val outputStreamWriter = OutputStreamWriter(fileOutput)
            users = et_usr.text.toString()
            passwords = et_pwd.text.toString()
            names = et_nam.text.toString()
            lastName = et_lastN.text.toString()
            if (users == jsonObject.getString("user")) {
                jsonObject.put("password", passwords)
                jsonObject.put("name", names)
                jsonObject.put("lastName", lastName)
            }
            outputStreamWriter.write(jsonObject.toString())
            outputStreamWriter.close()
            fileOutput.close()
        }

        finish()
    }

    fun onCancel(view: View) {
        onBackPressed()
    }
}