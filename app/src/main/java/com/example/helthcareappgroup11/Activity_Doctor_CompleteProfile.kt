package com.example.helthcareappgroup11

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.helthcareappgroup11.objectClasses.Doctors
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Activity_Doctor_CompleteProfile : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_doctor_complete_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()


        val fullName = findViewById<EditText>(R.id.fullName)
        val email = findViewById<EditText>(R.id.email)
        val phone = findViewById<EditText>(R.id.phone)
        val education = findViewById<EditText>(R.id.education)
        val certifiations = findViewById<EditText>(R.id.certifications)
        val specialization = findViewById<EditText>(R.id.specialization)
        val bio = findViewById<EditText>(R.id.bio)

        val updateDoctorData = findViewById<Button>(R.id.updateDoctorData)

        updateDoctorData.setOnClickListener {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val doctorId = currentUser.uid
                val doctor = Doctors(
                    fullName = fullName.text.toString(),
                    email = email.text.toString(),
                    phone = phone.text.toString(),
                    clinicId = "",
                    education = education.text.toString(),
                    certifications = certifiations.text.toString(),
                    specialization = specialization.text.toString(),
                    bio = bio.text.toString(),
                    photoUrl = ""
                )
                val doctorsRef = database.getReference("doctors")
                doctorsRef.child(doctorId).setValue(doctor)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
//                        startActivity(intent)
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this, "Error updating profile: $exception", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
            }
        }




    }
}