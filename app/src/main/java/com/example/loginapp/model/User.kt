package com.example.loginapp.model

data class User(
    val id: Int = 0,
    val username: String,
    val password: String,
    val fullname: String,
    val email: String,
    val dob: String,
    val gender: String
)
