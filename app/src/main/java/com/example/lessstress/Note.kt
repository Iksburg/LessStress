package com.example.lessstress

import kotlinx.serialization.Serializable
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.*

@Serializable
open class Note : RealmObject {
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
    var title: String = ""
    var description: String = ""
    var createdTime: Long = 0
}
