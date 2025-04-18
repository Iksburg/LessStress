package com.example.lessstress.home

@kotlinx.serialization.Serializable
data class LoginRequest(
    val username: String,
    val password: String
)