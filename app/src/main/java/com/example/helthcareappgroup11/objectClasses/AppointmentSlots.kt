package com.example.helthcareappgroup11.objectClasses

data class AppointmentSlots(
    val id: String,
    val date: String,
    val duration: String,
    val availability: String,
    val title: String,
    val description: String,
    val meetingOption: String

)
