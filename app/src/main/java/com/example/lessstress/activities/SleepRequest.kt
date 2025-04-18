package com.example.lessstress.activities

import kotlinx.serialization.Serializable

@Serializable
data class SleepRequest(
    val userId: Int,
    val title: String,
    val description: String,
    val date: String
)