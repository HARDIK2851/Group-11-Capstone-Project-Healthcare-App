package com.example.helthcareappgroup11.models

data class AppointmentSlots(
    val id: String,
    val date: String,
    val duration: String,
    val availability: String,
    val title: String,
    val description: String,
    val meetingOption: String,
    val booked: Boolean,
    val customerId: String,
    val doctorId: String
){
    constructor() : this("", "", "", "", "", "", "",false,"","")

}
