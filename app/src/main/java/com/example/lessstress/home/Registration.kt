package com.example.lessstress.home

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.lessstress.R
import com.example.lessstress.network.RegistrationRequest
import com.example.lessstress.network.ktorClient
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.launch

class Registration : AppCompatActivity() {
    private lateinit var et_usr: EditText
    private lateinit var et_pwd: EditText
    private lateinit var et_nam: EditText
    private lateinit var et_lastN: EditText
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
        val users = et_usr.text.toString()
        val passwords = et_pwd.text.toString()
        val names = et_nam.text.toString()
        val lastName = et_lastN.text.toString()

        val nameRegex = Regex("^[A-Za-zА-Яа-яЁё]+$")

        if (!nameRegex.matches(names)) {
            Toast.makeText(this, "Имя должно содержать только буквы", Toast.LENGTH_SHORT).show()
            return
        }

        if (!nameRegex.matches(lastName)) {
            Toast.makeText(this, "Фамилия должна содержать только буквы", Toast.LENGTH_SHORT).show()
            return
        }

        if (register) {
            val userRequest = RegistrationRequest(users, passwords, names, lastName)

            lifecycleScope.launch {
                try {
                    val response: HttpResponse = ktorClient.post("http://10.0.2.2:8080/register") {
                        contentType(ContentType.Application.Json)
                        setBody(userRequest)
                    }

                    if (response.status == HttpStatusCode.OK) {
                        Toast.makeText(this@Registration, "Успешная регистрация", Toast.LENGTH_SHORT).show()
                        val user1 = Users().apply {
                            user = users
                            password = passwords
                            name = names
                            this.lastName = lastName
                        }
                        val resultIntent = Intent(this@Registration, HomeScreen::class.java)
                        resultIntent.putExtra("user", user1.getBundle())
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                    } else {
                        Toast.makeText(this@Registration, "Ошибка регистрации: ${response.status}", Toast.LENGTH_LONG).show()
                    }

                } catch (e: Exception) {
                    Log.e("Registration", "Ошибка при отправке данных", e)
                    Toast.makeText(this@Registration, "Ошибка соединения с сервером", Toast.LENGTH_LONG).show()
                }
            }

        } else {
            Toast.makeText(this, "Обновление пока не реализовано", Toast.LENGTH_SHORT).show()
        }
    }

    fun onCancel(view: View) {
        onBackPressed()
    }
}