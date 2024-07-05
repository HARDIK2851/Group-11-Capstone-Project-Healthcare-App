package com.example.helthcareappgroup11.models

import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor

data class DoctorProfile(
    val fullName : String,
    val photoUrl : String
)
{
    constructor() : this("", "")
}
