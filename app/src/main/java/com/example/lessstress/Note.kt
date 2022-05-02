package com.example.lessstress

import io.realm.RealmObject

open class Note : RealmObject() {
    var title: String? = null
    var description: String? = null
    var createdTime: Long = 0
}