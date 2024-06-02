package com.example.helthcareappgroup11.models

data class Doctors(
    val name: String,
    val gender: String,
    val speciality: String,
    val treatment: String,
    val contactNumber: String,
    val profileImageUrl: String,
    val languages: String,
    val email: String,
    val qualification: String,
    val experience: Int,
    val workingHours: String,
    val rating: Double,
    val certifications: String,
    val biography: String,
    val specialInterests: String
)  {
    constructor() : this("", "", "",
        "", "", "",
        "", "", "", 0,
        "", 0.0, "", "",
        "")
}
