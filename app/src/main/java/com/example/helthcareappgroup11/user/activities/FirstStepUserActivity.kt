package com.example.helthcareappgroup11.user.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.helthcareappgroup11.R

class FirstStepUserActivity : AppCompatActivity() {
    private lateinit var firstName : EditText
    private lateinit var lastname : EditText
    private lateinit var gender : EditText
    private lateinit var date_of_birth : EditText
    private lateinit var BtnSubmit : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_step_user)

        firstName = findViewById(R.id.firstName_fsa)
        lastname = findViewById(R.id.lastName_fsa)
        gender = findViewById(R.id.gender_fsa)
        date_of_birth = findViewById(R.id.dateObirth_fsa)
        BtnSubmit = findViewById(R.id.btnRegister_fsa)

        BtnSubmit.setOnClickListener{
            startActivity(Intent(this, HomeActivityUser::class.java))
        }
    }
}