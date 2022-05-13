package com.example.lessstress

import android.app.ActionBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class Login : DialogFragment() {
    lateinit var users: String
    lateinit var password: String
    lateinit var homeScreen: HomeScreen

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewDialog = inflater.inflate(R.layout.activity_login, container, false)
        val bottomLogin = viewDialog.findViewById<Button>(R.id.buttonEntrar)
        bottomLogin.setOnClickListener { onLogin() }
        return viewDialog
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.9).toInt()
        dialog!!.window?.setLayout(width, ActionBar.LayoutParams.WRAP_CONTENT)
    }

    private fun onLogin() {
        val et_user = dialog!!.findViewById<EditText>(R.id.editTextTextName)
        val et_pwd = dialog!!.findViewById<EditText>(R.id.editTextPwd)
        val user = et_user.text.toString()
        val pwd = et_pwd.text.toString()
        if ((user == users) and pwd.equals(password, ignoreCase = true)) {
            val user1  = Users()
            user1.user = user
            homeScreen.onActivateInformation()
            dismiss()
        } else {
            Toast.makeText(this.activity, "НЕПРАВИЛЬНОЕ ИМЯ ПОЛЬЗОВАТЕЛЯ/ПАРОЛЬ", Toast.LENGTH_LONG).show()
        }
    }
}