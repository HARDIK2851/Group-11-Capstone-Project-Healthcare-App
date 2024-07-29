package com.example.helthcareappgroup11.user.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.models.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirstStepUserActivity : AppCompatActivity() {
    private lateinit var firstName: EditText
    private lateinit var lastname: EditText
    private lateinit var username: EditText
    private lateinit var gender: EditText
    private lateinit var date_of_birth: EditText
    private lateinit var BtnSubmit: Button

    private lateinit var database: DatabaseReference
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_step_user)

        firstName = findViewById(R.id.firstName_fsa)
        lastname = findViewById(R.id.lastName_fsa)
        username = findViewById(R.id.username_fsa)
        gender = findViewById(R.id.gender_fsa)
        date_of_birth = findViewById(R.id.dateObirth_fsa)
        BtnSubmit = findViewById(R.id.btnRegister_fsa)


        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        BtnSubmit.setOnClickListener {
            val firstNameValue = firstName.text.toString().trim()
            val lastNameValue = lastname.text.toString().trim()
            val usernameValue = username.text.toString().trim()
            val genderValue = gender.text.toString().trim()
            val dateOfBirthValue = date_of_birth.text.toString().trim()



            if (firstNameValue.isEmpty() && lastNameValue.isEmpty() && usernameValue.isEmpty()
                && genderValue.isEmpty() && dateOfBirthValue.isEmpty()
            ) {
                Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
            } else {
                saveClientProfile(
                    firstNameValue,
                    lastNameValue,
                    usernameValue,
                    genderValue,
                    dateOfBirthValue
                )

            }
        }
    }


    private fun saveClientProfile(
        firstNameValue: String, lastNameValue: String,
        usernameValue: String, genderValue: String,
        dateOfBirthValue: String) {

        val currentUser = auth.currentUser
        val userId = currentUser?.uid ?: return

//        val userId = database.push().key ?: return
        val user = UserProfile(
            firstNameValue, lastNameValue, usernameValue, genderValue, dateOfBirthValue
        )
        database.child("clients").child(userId).setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Profile saved successfully", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("FirstStepUserActivity", "Profile saved successfully for userId: $userId")
                    startActivity(Intent(this, HomeActivityUser::class.java))
                } else {
                    Toast.makeText(this, "Failed to save profile", Toast.LENGTH_SHORT).show()
                }
            }
    }
}