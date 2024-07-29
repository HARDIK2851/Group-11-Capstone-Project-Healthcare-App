package com.example.helthcareappgroup11.doctor.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.doctor.fragments.SettingFragment
import com.example.helthcareappgroup11.models.Doctors
import com.example.helthcareappgroup11.user.fragments.AccountFragmentUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener



class Profile_Info_DoctorActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private lateinit var fullName: TextView
    private lateinit var email: TextView
    private lateinit var phone: TextView
    private lateinit var education: TextView
    private lateinit var certifications: TextView
    private lateinit var specialization: TextView
    private lateinit var bio: TextView
    private lateinit var photoUrl: ImageView
    private lateinit var goBackBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_info_doctor)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Initialize views
        fullName = findViewById(R.id.fullName_PID)
        email = findViewById(R.id.email_PID)
        phone = findViewById(R.id.phone_PID)
        education = findViewById(R.id.education_PID)
        certifications = findViewById(R.id.certifications_PID)
        specialization = findViewById(R.id.speciality_PID)
        bio = findViewById(R.id.bio_PID)
        photoUrl = findViewById(R.id.photoUrl_PID)
        goBackBtn = findViewById(R.id.goBackBtn_PID)


        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            Log.d("Profile_Info_DoctorActivity", "Retrieving profile for userId: $userId")

            // Retrieve user data from the database
            database.reference.child("doctors").child(userId).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val pFullName = snapshot.child("fullName").getValue(String::class.java) ?: "N/A"
                    val pEmail = snapshot.child("email").getValue(String::class.java) ?: currentUser.email ?: "N/A"
                    val pPhone = snapshot.child("phone").getValue(String::class.java) ?: "N/A"
                    val pSpecialization = snapshot.child("specialization").getValue(String::class.java) ?: "N/A"
                    val pEducation = snapshot.child("education").getValue(String::class.java) ?: "N/A"
                    val pCertifications = snapshot.child("certifications").getValue(String::class.java) ?: "N/A"
                    val pBio = snapshot.child("bio").getValue(String::class.java) ?: "N/A"
                    val pPhotoUrl = snapshot.child("photoUrl").getValue(String::class.java) ?: ""

                    Log.d("Profile_Info_DoctorActivity", "Full Name: $pFullName")
                    Log.d("Profile_Info_DoctorActivity", "Email: $pEmail")
                    Log.d("Profile_Info_DoctorActivity", "Phone: $pPhone")
                    Log.d("Profile_Info_DoctorActivity", "Specialization: $pSpecialization")
                    Log.d("Profile_Info_DoctorActivity", "Education: $pEducation")
                    Log.d("Profile_Info_DoctorActivity", "Certifications: $pCertifications")
                    Log.d("Profile_Info_DoctorActivity", "Bio: $pBio")
                    Log.d("Profile_Info_DoctorActivity", "Photo URL: $pPhotoUrl")

                    fullName.text = pFullName
                    email.text = pEmail
                    phone.text = pPhone
                    specialization.text = pSpecialization
                    education.text = pEducation
                    certifications.text = pCertifications
                    bio.text = pBio

                    if (pPhotoUrl.isNotEmpty()) {
                        Glide.with(this@Profile_Info_DoctorActivity)
                            .load(pPhotoUrl)
                            .into(photoUrl)
                    } else {
                        photoUrl.setImageResource(android.R.color.transparent) // Set a transparent placeholder if needed
                    }

                    Log.d("Profile_Info_DoctorActivity", "Profile data retrieved successfully.")
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Profile_Info_DoctorActivity, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
                    Log.e("Profile_Info_DoctorActivity", "Failed to retrieve data for userId: $userId", error.toException())
                }
            })
        } else {
            Toast.makeText(this, "No authenticated user", Toast.LENGTH_SHORT).show()
            Log.e("Profile_Info_DoctorActivity", "No authenticated user found")
        }

        // Handle the back button click
        goBackBtn.setOnClickListener {
            startActivity(Intent(this, SettingFragment::class.java))
            Toast.makeText(this, "Going back", Toast.LENGTH_SHORT).show()
        }
    }
}