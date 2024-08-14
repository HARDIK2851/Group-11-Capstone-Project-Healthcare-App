package com.example.helthcareappgroup11.models

data class Patient(
    val email: String = "",
    val phone : Long = 0,
    val age: Int = 0,
    val bloodGroup: String = "",
    val location: String = "",
    val gender: String = "",
    val details: String = ""
)