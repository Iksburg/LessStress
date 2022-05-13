package com.example.lessstress

import android.os.Bundle

class Users {
    var user: String = ""
    var password: String = ""
    var name: String = ""
    var lastName: String = ""

    fun getBundle(): Bundle {
        val bundle = Bundle()
        bundle.putCharSequence("user", user)
        bundle.putCharSequence("password", password)
        bundle.putCharSequence("name", name)
        bundle.putCharSequence("lastName", lastName)
        return bundle
    }

    fun setBundle(bundle: Bundle) {
        user = bundle.getString("user", "")
        password = bundle.getString("password", "")
        name = bundle.getString("name", "")
        lastName = bundle.getString("lastName", "")
    }
}