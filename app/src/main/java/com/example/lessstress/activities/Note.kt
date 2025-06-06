package com.example.lessstress.activities

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val title: String,
    val description: String,
    val date: String
)
