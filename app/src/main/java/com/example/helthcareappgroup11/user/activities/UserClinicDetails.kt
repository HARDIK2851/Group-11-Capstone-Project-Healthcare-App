package com.example.helthcareappgroup11.user.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import com.example.helthcareappgroup11.R
import com.google.firebase.database.FirebaseDatabase

class UserClinicDetails : AppCompatActivity() {
    private lateinit var clinicName: TextView
    private lateinit var clinicAddress: TextView
    private lateinit var clinicCity: TextView
    private lateinit var clinicZipcode: TextView
    private lateinit var clinicContact: TextView
    private lateinit var doctorName: TextView
    private lateinit var doctorSpecialization: TextView
    private lateinit var doctorEducation: TextView
    private lateinit var doctorCertifications: TextView
    private lateinit var doctorBio: TextView
    private lateinit var btnBookSlot: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_clinic_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get references to TextViews
        clinicName = findViewById(R.id.clinicName)
        clinicAddress = findViewById(R.id.clinicAddress)
        clinicCity = findViewById(R.id.clinicCity)
        clinicZipcode = findViewById(R.id.clinicZipcode)
        clinicContact = findViewById(R.id.clinicContact)
        doctorName = findViewById(R.id.doctorName)
        doctorSpecialization = findViewById(R.id.doctorSpecialization)
        doctorEducation = findViewById(R.id.doctorEducation)
        doctorCertifications = findViewById(R.id.doctorCertifications)
        doctorBio = findViewById(R.id.doctorBio)
        btnBookSlot = findViewById(R.id.btnBookSlot)

        // Get the clinic details from the intent
        val clinicNameText = intent.getStringExtra("CLINIC_NAME")
        val streetAddress = intent.getStringExtra("STREET_ADDRESS")
        val city = intent.getStringExtra("CITY")
        val zipcode = intent.getStringExtra("ZIPCODE")
        val contactNumber = intent.getStringExtra("CONTACT_NUMBER")
        val doctorId = intent.getStringExtra("DOCTOR_ID")
        val photoUrls = intent.getStringArrayExtra("PHOTO_URLS")

        // Populate the views with the clinic details
        clinicName.text = clinicNameText
        clinicAddress.text = streetAddress
        clinicCity.text = city
        clinicZipcode.text = zipcode
        clinicContact.text = contactNumber

        btnBookSlot.setOnClickListener {
            intent = Intent(this, AvailableSlots::class.java)
            intent.putExtra("doctorId",doctorId);
            startActivity(intent)
        }

        // Load doctor details
        doctorId?.let { loadDoctorDetails(it) }
    }

    private fun loadDoctorDetails(doctorId: String) {
        val database = FirebaseDatabase.getInstance()
        val doctorRef = database.reference.child("doctors").child(doctorId)

        doctorRef.get().addOnSuccessListener { snapshot ->
            val doctor = snapshot.getValue(com.example.helthcareappgroup11.models.Doctors::class.java)
            if (doctor != null) {
                doctorName.text = doctor.fullName
                doctorSpecialization.text = doctor.specialization
                doctorEducation.text = doctor.education
                doctorCertifications.text = doctor.certifications
                doctorBio.text = doctor.bio
            }
        }
    }
}
