package com.example.helthcareappgroup11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.helthcareappgroup11.admin.AdminHomeActivity
import com.example.helthcareappgroup11.doctor.activities.DoctorFirstStepActivity
import com.example.helthcareappgroup11.models.UserRole
import com.example.helthcareappgroup11.user.activities.FirstStepUserActivity
import com.example.helthcareappgroup11.user.activities.HomeActivityUser
import com.example.helthcareappgroup11.user.activities.PatientDetailActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ActivityLogin : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnGoToRegister = findViewById<Button>(R.id.btnGoToRegister)

        auth = FirebaseAuth.getInstance()

        btnGoToRegister.setOnClickListener {
            val intent = Intent(this, ActivityRegister::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val emailText = email.text.toString()
            val passwordText = password.text.toString()
            if (emailText == "capstoneadmin123@gmail.com" && passwordText == "admin@123") {
                val intent = Intent(this@ActivityLogin, AdminHomeActivity::class.java)
                startActivity(intent)
            } else{
                auth.signInWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            if (user?.isEmailVerified == true) {
                                val userId = auth.currentUser?.uid
                                val userRoleRef =
                                    FirebaseDatabase.getInstance().reference.child("users")
                                        .child(userId!!)

                                userRoleRef.addListenerForSingleValueEvent(object :
                                    ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        val userRole =
                                            snapshot.getValue(UserRole::class.java)?.role ?: ""

                                        if (userRole == "doctor") {
                                            Toast.makeText(this@ActivityLogin, "doctor logged in", Toast.LENGTH_SHORT).show()
                                            val intent = Intent(
                                                this@ActivityLogin,
                                                DoctorFirstStepActivity::class.java
                                            )
                                            startActivity(intent)
                                        } else if (userRole == "user") {
                                            Toast.makeText(this@ActivityLogin, "user logged in", Toast.LENGTH_SHORT).show()
                                            val intent = Intent(
                                                this@ActivityLogin,
                                                PatientDetailActivity::class.java
                                            )
                                            startActivity(intent)
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        Toast.makeText(
                                            this@ActivityLogin,
                                            "Error retrieving user role",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                })
                            } else {
                                Toast.makeText(
                                    this@ActivityLogin,
                                    "Login failed: Please verify your email first",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            val exception = task.exception
                            Toast.makeText(
                                this@ActivityLogin,
                                "Login failed - ${exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
        }
        }
    }
}
