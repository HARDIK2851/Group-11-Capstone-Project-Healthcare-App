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

    private lateinit var userFullName: TextView
    private lateinit var userEmail: TextView
    private lateinit var userPhone: TextView
    private lateinit var userGender: TextView
    private lateinit var userDateOfBirth: TextView
    private lateinit var backBtn: Button

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_account_info_for_user)

        userFullName = findViewById(R.id.user_full_name)
        userEmail = findViewById(R.id.user_email)
        userPhone = findViewById(R.id.user_phone)
        userGender = findViewById(R.id.user_gender)
        userDateOfBirth = findViewById(R.id.user_date_of_birth)
        backBtn = findViewById(R.id.backBtnAtAccountInfo)

        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            Log.d("Account_InfoForUserActivity", "Retrieving profile for userId: $userId")

            // Retrieve user data from the database
            database.child("clients").child(userId).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val pFullName = snapshot.child("firstName").getValue(String::class.java) + " " +
                            snapshot.child("lastName").getValue(String::class.java)
                    val pEmail = currentUser.email
                    val pPhone = snapshot.child("phone").getValue(String::class.java)
                    val pGender = snapshot.child("gender").getValue(String::class.java)
                    val pDateOfBirth = snapshot.child("dateOfBirth").getValue(String::class.java)

                    if (pFullName != null) {
                        userFullName.text = pFullName
                        Log.d("Account_InfoForUserActivity", "Full Name retrieved: $pFullName")
                    } else {
                        Log.e("Account_InfoForUserActivity", "Full Name is null for userId: $userId")
                    }

                    if (pEmail != null) {
                        userEmail.text = pEmail
                        Log.d("Account_InfoForUserActivity", "Email retrieved: $pEmail")
                    } else {
                        Log.e("Account_InfoForUserActivity", "Email is null for userId: $userId")
                    }

                    if (pPhone != null) {
                        userPhone.text = pPhone
                        Log.d("Account_InfoForUserActivity", "Phone retrieved: $pPhone")
                    } else {
                        Log.e("Account_InfoForUserActivity", "Phone is null for userId: $userId")
                    }

                    if (pGender != null) {
                        userGender.text = pGender
                        Log.d("Account_InfoForUserActivity", "Gender retrieved: $pGender")
                    } else {
                        Log.e("Account_InfoForUserActivity", "Gender is null for userId: $userId")
                    }

                    if (pDateOfBirth != null) {
                        userDateOfBirth.text = pDateOfBirth
                        Log.d("Account_InfoForUserActivity", "Date of Birth retrieved: $pDateOfBirth")
                    } else {
                        Log.e("Account_InfoForUserActivity", "Date of Birth is null for userId: $userId")
                    }
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

        // Handle the back button click
        backBtn.setOnClickListener {
            startActivity(Intent(this, AccountFragmentUser::class.java))
            Toast.makeText(this, "going back", Toast.LENGTH_SHORT).show()
        }
    }
}