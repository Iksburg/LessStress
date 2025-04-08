package com.example.lessstress.network

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequest(
    val username: String,
    val password: String,
    val name: String,
    val lastName: String
)