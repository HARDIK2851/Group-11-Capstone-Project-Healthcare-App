package com.example.helthcareappgroup11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.example.helthcareappgroup11.models.UserRole
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ActivityRegister : AppCompatActivity() {
    private lateinit var role: String
    private lateinit var loginTxt : TextView

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupRole)
        val userRole = findViewById<RadioButton>(R.id.user_role)
        val docterRole = findViewById<RadioButton>(R.id.docter_role)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val confirmPassword = findViewById<EditText>(R.id.confirmPassword)

        val btnRegister = findViewById<Button>(R.id.btnRegister)

        loginTxt = findViewById(R.id.login_txt)
        loginTxt.setOnClickListener{
            startActivity(Intent(this, ActivityLogin::class.java))
            Toast.makeText(this, "processing", Toast.LENGTH_SHORT).show()
        }


        auth = FirebaseAuth.getInstance()

        role = if (userRole.isChecked) "user" else "doctor"

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            role = when (checkedId) {
                R.id.user_role -> "user"
                R.id.docter_role -> "doctor"
                else -> ""
            }
        }

        btnRegister.setOnClickListener {
            val emailText = email.text.toString()
            val passwordText = password.text.toString()
            val confirmPasswordText = confirmPassword.text.toString()

            Log.d("RegistrationDetails", "Role: $role")
            Log.d("RegistrationDetails", "Email: $emailText")
            Log.d("RegistrationDetails", "Password: $passwordText")

            if(passwordText == confirmPasswordText) {
                auth.createUserWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            val user = auth.currentUser
                            user?.sendEmailVerification()?.addOnCompleteListener { verificationTask ->
                                if (verificationTask.isSuccessful) {
                                    val userId = auth.currentUser?.uid
                                    val userRoleRef =
                                        FirebaseDatabase.getInstance().reference.child("users")
                                            .child(userId!!)
                                    userRoleRef.setValue(UserRole(role))

                                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT)
                                        .show()
                                    Toast.makeText(this, "Verification email sent", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(this, "Failed to send verification email", Toast.LENGTH_SHORT).show()
                                }
                            }

                        } else {
                            Toast.makeText(
                                this,
                                "Registration failed: ${task.exception?.message.toString()}",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d("ERROR", task.exception.toString())
                        }
                    }
            }else{
                Toast.makeText(this, "Password must match with confirm password", Toast.LENGTH_SHORT)
                    .show()
            }

        }

    }
}