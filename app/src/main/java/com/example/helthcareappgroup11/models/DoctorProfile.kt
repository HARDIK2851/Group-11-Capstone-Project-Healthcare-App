package com.example.helthcareappgroup11.models

data class DoctorProfile(
    val fullName : String,
    val photoUrl : String
)
{
    constructor() : this("", "")
}
