package com.example.helthcareappgroup11

import android.content.Intent
import android.os.Bundle
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity

class DoctorFirstStepActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_doctor_first_step)

        val goToCompleteProfile = findViewById<Button>(R.id.goToCompleteProfile)

        // mew button to take to next screen
        val nextScreen : Button = findViewById(R.id.goToSecondScreen)

        goToCompleteProfile.setOnClickListener {
            val intent = Intent(this, Activity_Doctor_CompleteProfile::class.java)
            startActivity(intent)
        }

        // adding new button that takes to next screen
        nextScreen.setOnClickListener {
            val intent = Intent(this, DoctorSecondStepRegisterActivity::class.java)
            startActivity(intent)
        }
    }
}