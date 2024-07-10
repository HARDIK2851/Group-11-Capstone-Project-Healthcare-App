package com.example.helthcareappgroup11.doctor.objectClasses

data class Customers(
    var name: String? = "",
    var age: Int? = 0,
    var height: Float? = 0.0f,
    var email: String? = "",
    var phone: String? = "",
    var medicalHistory: String? = "",
    var allergies: String? = "",
    var photoUrl: String? = ""
)
