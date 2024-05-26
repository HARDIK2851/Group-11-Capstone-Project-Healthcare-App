package com.example.helthcareappgroup11

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast

class ActivityRegister : AppCompatActivity() {
    private lateinit var role: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupRole)
        val userRole = findViewById<RadioButton>(R.id.user_role)
        val docterRole = findViewById<RadioButton>(R.id.docter_role)
        val email = findViewById<TextView>(R.id.email)
        val password = findViewById<TextView>(R.id.password)

        val btnRegister = findViewById<Button>(R.id.btnRegister)

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

            Log.d("RegistrationDetails", "Role: $role")
            Log.d("RegistrationDetails", "Email: $emailText")
            Log.d("RegistrationDetails", "Password: $passwordText")

            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
        }

    }
}