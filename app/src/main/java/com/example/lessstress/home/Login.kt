package com.example.lessstress.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lessstress.R
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject
import androidx.core.content.edit
import com.example.lessstress.activities.SleepDiary

class Login : AppCompatActivity() {

    private lateinit var etLogin: EditText
    private lateinit var etPassword: EditText
    private lateinit var buttonLogin: Button
    private val client = HttpClient() {
        install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
            json()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etLogin = findViewById(R.id.editTextTextName)
        etPassword = findViewById(R.id.editTextPwd)
        buttonLogin = findViewById(R.id.buttonEntrar)

        buttonLogin.setOnClickListener {
            onLoginClicked()
        }
    }

    private fun onLoginClicked() {
        val inputLogin = etLogin.text.toString()
        val inputPassword = etPassword.text.toString()

        MainScope().launch(Dispatchers.IO) {
            try {
                val response: HttpResponse = client.post("http://192.168.161.109:8080/login") {
                    contentType(ContentType.Application.Json)
                    setBody(LoginRequest(inputLogin, inputPassword))
                }

                val responseBody = response.bodyAsText()

                if (response.status == HttpStatusCode.OK) {
                    val jsonResponse = JSONObject(responseBody)
                    val token = jsonResponse.getString("token")
                    val userId = jsonResponse.getInt("userId")

                    if (token.isNotEmpty()) {
                        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
                        prefs.edit {
                            putString("token", token)
                                .putInt("userId", userId)
                        }

                        val intent = Intent(this@Login, SleepDiary::class.java).apply {
                            putExtra("token", token)
                            putExtra("userId", userId)
                        }
                        startActivity(intent)
                        finish()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@Login, "Неверные данные для входа", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@Login, "Неверные данные для входа", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun onCancel(view: View) {
        onBackPressed()
    }
}
