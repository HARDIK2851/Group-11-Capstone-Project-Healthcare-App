package com.example.helthcareappgroup11.user.models

data class Clinics(
    var clinicName: String? = "",
    var streetAddress: String? = "",
    var city: String? = "",
    var zipcode: String? = "",
    var contactNumber: String? = "",
    var doctorId: String = "",
    var photoUrls: ArrayList<String> = ArrayList()
) {
    // Default constructor
    constructor() : this("", "", "", "", "", "", ArrayList())
}