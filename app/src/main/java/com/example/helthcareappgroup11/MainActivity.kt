package com.example.helthcareappgroup11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.helthcareappgroup11.doctor.activities.DoctorFirstStepActivity
import com.example.helthcareappgroup11.user.activities.HomeActivityUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userRoleRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Delay execution by 2 seconds
        Handler().postDelayed({
            checkCurrentUser()
        }, 2000) // 2000 milliseconds = 2 seconds
    }

    private fun checkCurrentUser() {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            // User is signed in, fetch user role
            userRoleRef = database.reference.child("users").child(currentUser.uid).child("role")
            checkUserAndNavigate()
        } else {
            // No user is signed in, redirect to login
            goToLogin()
        }
    }

    private fun checkUserAndNavigate() {
        userRoleRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userRole = snapshot.getValue(String::class.java)

                if (userRole == "doctor") {
                    val intent = Intent(this@MainActivity, DoctorFirstStepActivity::class.java)
                    startActivity(intent)
                } else if (userRole == "user") {
                    val intent = Intent(this@MainActivity, HomeActivityUser::class.java)
                    startActivity(intent)
                } else {
                    // Handle unknown or undefined roles
                    goToLogin()
                }
                finish()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
                goToLogin()
            }
        })
    }

    private fun goToLogin() {
        val intent = Intent(this@MainActivity, ActivityLogin::class.java)
        startActivity(intent)
        finish()
    }
}
