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
import com.example.helthcareappgroup11.models.Clinics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID

class ActivityDocotr_AddClinic : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_docotr_add_clinic)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val clinicName = findViewById<EditText>(R.id.addClinic_clinicName)
        val streetAddress = findViewById<EditText>(R.id.addClinic_address)
        val city = findViewById<EditText>(R.id.addClinic_city)
        val zipcode = findViewById<EditText>(R.id.addClinic_postalCode)
        val contactNumber = findViewById<EditText>(R.id.addClinic_contactNumber)

        val btnAddClinic = findViewById<Button>(R.id.btnSubmit_addClinic)

        btnAddClinic.setOnClickListener {
            if (validateClinic(clinicName, streetAddress, city, zipcode, contactNumber)) {
                val clinicId = UUID.randomUUID().toString()
                val currentUser = auth.currentUser
                if (currentUser != null) {
                    val doctorId = currentUser.uid
                    val clinic = Clinics(
                        clinicName = clinicName.text.toString(),
                        contactNumber = contactNumber.text.toString(),
                        doctorId = doctorId,
                        streetAddress = streetAddress.text.toString(),
                        city = city.text.toString(),
                        zipcode = zipcode.text.toString(),
                        photoUrls = ArrayList(listOf(""))
                    )
                    val clinicsRef = database.getReference("clinics")
                    clinicsRef.child(clinicId).setValue(clinic)
                        .addOnSuccessListener {
                            val doctorsRef = database.getReference("doctors").child(doctorId)
                            doctorsRef.child("clinicId").setValue(clinicId)
                                .addOnSuccessListener {
                                    updateClinicAddedStatus()
                                    Toast.makeText(this, "Clinic added successfully", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, DoctorSecondStepRegisterActivity::class.java)
                                    startActivity(intent)
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(this, "Error updating doctor: $exception", Toast.LENGTH_SHORT).show()
                                }
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(this, "Error adding clinic: $exception", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all clinic fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateClinic(
        clinicName: EditText,
        streetAddress: EditText,
        city: EditText,
        zipcode: EditText,
        contactNumber: EditText
    ): Boolean {
        return clinicName.text.isNotEmpty() &&
                streetAddress.text.isNotEmpty() &&
                city.text.isNotEmpty() &&
                zipcode.text.isNotEmpty() &&
                contactNumber.text.isNotEmpty()
    }

    private fun updateClinicAddedStatus() {
        val userId = auth.currentUser?.uid ?: return
        val doctorRef = FirebaseDatabase.getInstance().reference.child("doctors").child(userId)
        doctorRef.child("clinicAdded").setValue(true)
    }
}
