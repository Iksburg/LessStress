package com.example.lessstress.network

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

private const val BASE_URL = "http://10.0.2.2:8080"

@Serializable
data class AuthRequest(val username: String, val password: String)

@Serializable
data class AuthResponse(val message: String)

suspend fun registerUser(username: String, password: String): AuthResponse {
    val response: HttpResponse = ktorClient.post("$BASE_URL/register") {
        contentType(ContentType.Application.Json)
        setBody(AuthRequest(username, password))
    }
    return response.body()
}

suspend fun loginUser(username: String, password: String): AuthResponse {
    val response: HttpResponse = ktorClient.post("$BASE_URL/login") {
        contentType(ContentType.Application.Json)
        setBody(AuthRequest(username, password))
    }
    return response.body()
}