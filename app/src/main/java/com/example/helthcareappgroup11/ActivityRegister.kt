package com.example.helthcareappgroup11

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.helthcareappgroup11.models.UserRole
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

class ActivityRegister : AppCompatActivity() {
    private lateinit var role: String
    private lateinit var loginTxt: TextView

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupRole)
        val userRole = findViewById<RadioButton>(R.id.user_role)
        val doctorRole = findViewById<RadioButton>(R.id.docter_role)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val confirmPassword = findViewById<EditText>(R.id.confirmPassword)

        val btnRegister = findViewById<Button>(R.id.btnRegister)

        loginTxt = findViewById(R.id.login_txt)
        loginTxt.setOnClickListener {
            startActivity(Intent(this, ActivityLogin::class.java))
            Toast.makeText(this, "Processing", Toast.LENGTH_SHORT).show()
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

            if (!isValidEmail(emailText)) {
                showAlertDialog("Invalid Email", "Please enter a valid email address.")
            } else if (!isValidPassword(passwordText)) {
                showAlertDialog(
                    "Invalid Password",
                    "Password must be at least 6 characters long, and include upper and lower case letters, numbers, and symbols."
                )
            } else if (passwordText != confirmPasswordText) {
                showAlertDialog("Password Mismatch", "Password and Confirm Password do not match.")
            } else {
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

                                    Toast.makeText(
                                        this,
                                        "Registration successful!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    Toast.makeText(
                                        this,
                                        "Verification email sent",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    showAlertDialog(
                                        "Verification Failed",
                                        "Failed to send verification email."
                                    )
                                }
                            }
                        } else {
                            showAlertDialog(
                                "Registration Failed",
                                "Registration failed: ${task.exception?.message.toString()}"
                            )
                            Log.d("ERROR", task.exception.toString())
                        }
                    }
            }
        }
    }

    // Email validation pattern
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)\$"
        )
        return emailPattern.matcher(email).matches()
    }

    // Password validation pattern
    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$%^&+=!])(?=\\S+\$).{6,}\$"
        )
        return passwordPattern.matcher(password).matches()
    }

    // Function to show alert dialog
    private fun showAlertDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}
