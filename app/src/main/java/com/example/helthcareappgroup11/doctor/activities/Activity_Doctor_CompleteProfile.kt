package com.example.helthcareappgroup11.doctor.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.models.Doctors
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

        val fullName = findViewById<EditText>(R.id.completeProfileFullName)
        val email = findViewById<EditText>(R.id.completeProfiee_email)
        val phone = findViewById<EditText>(R.id.completeProfilePhone)
        val education = findViewById<EditText>(R.id.completeProfileEduccation)
        val certifications = findViewById<EditText>(R.id.completeProfileCertifications)
        val specialization = findViewById<EditText>(R.id.completeProfileSpecialization)
        val bio = findViewById<EditText>(R.id.completeProfileBio)

        val updateDoctorData = findViewById<Button>(R.id.btnSubmit)

        updateDoctorData.setOnClickListener {
            if (validateProfile(fullName, email, phone, education, certifications, specialization, bio)) {
                val currentUser = auth.currentUser
                if (currentUser != null) {
                    val doctorId = currentUser.uid
                    val doctor = Doctors(
                        fullName = fullName.text.toString(),
                        email = email.text.toString(),
                        phone = phone.text.toString(),
                        clinicId = "",
                        education = education.text.toString(),
                        certifications = certifications.text.toString(),
                        specialization = specialization.text.toString(),
                        bio = bio.text.toString(),
                        photoUrl = ""
                    )
                    val doctorsRef = database.getReference("doctors")
                    doctorsRef.child(doctorId).setValue(doctor)
                        .addOnSuccessListener {
                            updateProfileCompletionStatus()
                            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, DoctorFirstStepActivity::class.java)
                            startActivity(intent)
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(this, "Error updating profile: $exception", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all profile fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateProfile(
        fullName: EditText,
        email: EditText,
        phone: EditText,
        education: EditText,
        certifications: EditText,
        specialization: EditText,
        bio: EditText
    ): Boolean {
        return fullName.text.isNotEmpty() &&
                email.text.isNotEmpty() &&
                phone.text.isNotEmpty() &&
                education.text.isNotEmpty() &&
                certifications.text.isNotEmpty() &&
                specialization.text.isNotEmpty() &&
                bio.text.isNotEmpty()
    }

    private fun updateProfileCompletionStatus() {
        val userId = auth.currentUser?.uid ?: return
        val doctorRef = FirebaseDatabase.getInstance().reference.child("doctors").child(userId)
        doctorRef.child("profileCompleted").setValue(true)
    }
}
