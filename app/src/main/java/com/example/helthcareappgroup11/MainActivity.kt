package com.example.helthcareappgroup11


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.helthcareappgroup11.ActivityLogin
import com.example.helthcareappgroup11.DoctorFirstStepActivity
import com.example.helthcareappgroup11.HomeActivityUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var currentUser: FirebaseUser
    private lateinit var userRoleRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        currentUser = auth.currentUser!!

        userRoleRef = database.reference.child("users").child(currentUser.uid).child("role")

        val delay: Long = 2000

        Handler().postDelayed({
            checkUserAndNavigate()
        }, delay)
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
