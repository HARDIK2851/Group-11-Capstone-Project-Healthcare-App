package com.example.helthcareappgroup11.objectClasses

data class Doctors(
    var fullName: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var clinicId: String? = null,
    var specialization: String? = null,
    var education: String? = null,
    var certifications: String? = null,
    var bio: String? = null,
    var photoUrl: String? = null
)