package com.example.helthcareappgroup11.user.models

data class AppointmentUser(
    val doctorImage: Int,  // Use Int if using drawable resource IDs
    val doctorName: String,
    val doctorExpertise: String,
    val appointmentDateTime: String
)
