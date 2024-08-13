package com.example.helthcareappgroup11.user.activities


import android.os.Bundle
import android.util.Log

import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import com.example.helthcareappgroup11.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Account_InfoForUserActivity : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

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

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Initialize Firebase Database
//        database = FirebaseDatabase.getInstance().getReference("patients")

        // Initialize UI elements
        textViewEmail = findViewById(R.id.textViewEmail)
        textViewAge = findViewById(R.id.textViewAge)
        textViewBloodGroup = findViewById(R.id.textViewBloodGroup)
        textViewLocation = findViewById(R.id.textViewLocation)
        textViewGender = findViewById(R.id.textViewGender)
        textViewDetails = findViewById(R.id.textViewDetails)


        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            Log.d("Account_InfoForUserActivity", "Retrieving profile for userId: $userId")

            // Retrieve user data from the database
            database.reference.child("patients").child(userId).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val pEmail = snapshot.child("email").getValue(String::class.java) ?: currentUser.email ?: "N/A"
                    val pAge = snapshot.child("age").getValue(String::class.java) ?: "N/A"
                    val pBloodGroup = snapshot.child("bloodGroup").getValue(String::class.java) ?: "N/A"
                    val pLocation = snapshot.child("location").getValue(String::class.java) ?: "N/A"
                    val pGender = snapshot.child("gender").getValue(String::class.java) ?: "N/A"
                    val pDetails = snapshot.child("details").getValue(String::class.java) ?: "N/A"

                    Log.d("Account_InfoForUserActivity", "Email: $pEmail")
                    Log.d("Account_InfoForUserActivity", "Age: $pAge")
                    Log.d("Account_InfoForUserActivity", "Blood Group: $pBloodGroup")
                    Log.d("Account_InfoForUserActivity", "Location: $pLocation")
                    Log.d("Account_InfoForUserActivity", "Gender: $pGender")
                    Log.d("Account_InfoForUserActivity", "Details: $pDetails")

                    textViewEmail.text = pEmail
                    textViewAge.text = pAge
                    textViewBloodGroup.text = pBloodGroup
                    textViewLocation.text = pLocation
                    textViewGender.text = pGender
                    textViewDetails.text = pDetails

                    Log.d("Account_InfoForUserActivity", "Profile data retrieved successfully.")
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Account_InfoForUserActivity, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
                    Log.e("Account_InfoForUserActivity", "Failed to retrieve data for userId: $userId", error.toException())
                }
            })
        } else {
            Toast.makeText(this, "No authenticated user", Toast.LENGTH_SHORT).show()
            Log.e("Account_InfoForUserActivity", "No authenticated user found")
        }

    }

}
