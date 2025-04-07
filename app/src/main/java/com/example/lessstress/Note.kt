package com.example.lessstress

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.*

class Note : RealmObject {
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()

    var title: String = ""
    var description: String = ""
    var createdTime: Long = 0
}