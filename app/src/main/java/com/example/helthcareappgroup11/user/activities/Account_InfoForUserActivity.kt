package com.example.helthcareappgroup11.user.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.user.fragments.AccountFragmentUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Account_InfoForUserActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    // UI elements
    private lateinit var textViewEmail: TextView
    private lateinit var textViewAge: TextView
    private lateinit var textViewBloodGroup: TextView
    private lateinit var textViewLocation: TextView
    private lateinit var textViewGender: TextView
    private lateinit var textViewDetails: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info_for_user)

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance().getReference("patients")

        // Initialize UI elements
        textViewEmail = findViewById(R.id.textViewEmail)
        textViewAge = findViewById(R.id.textViewAge)
        textViewBloodGroup = findViewById(R.id.textViewBloodGroup)
        textViewLocation = findViewById(R.id.textViewLocation)
        textViewGender = findViewById(R.id.textViewGender)
        textViewDetails = findViewById(R.id.textViewDetails)

        // Retrieve data from Firebase
        loadPatientData()
    }

    private fun loadPatientData() {

        val patientId = intent.getStringExtra("PATIENT_ID") ?: return

        Log.d("Account_InfoForUser", "Loading data for patientId: $patientId")
        database.child(patientId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val email = snapshot.child("email").getValue(String::class.java)
                    val age = snapshot.child("age").getValue(Int::class.java)
                    val bloodGroup = snapshot.child("bloodGroup").getValue(String::class.java)
                    val location = snapshot.child("location").getValue(String::class.java)
                    val gender = snapshot.child("gender").getValue(String::class.java)
                    val details = snapshot.child("details").getValue(String::class.java)

                    // Update UI with retrieved data
                    textViewEmail.text = "Email: $email"
                    textViewAge.text = "Age: $age"
                    textViewBloodGroup.text = "Blood Group: $bloodGroup"
                    textViewLocation.text = "Location: $location"
                    textViewGender.text = "Gender: $gender"
                    textViewDetails.text = "Details: $details"
                } else {
                    Toast.makeText(this@Account_InfoForUserActivity, "No data found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Account_InfoForUser", "Failed to load patient data", error.toException())
            }
        })
    }
}