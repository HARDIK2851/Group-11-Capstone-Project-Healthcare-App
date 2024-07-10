package com.example.helthcareappgroup11.doctor.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.helthcareappgroup11.R


class DoctorSecondStepRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_doctor_second_step_register)

        val goToAddClinic = findViewById<Button>(R.id.goToAddClinic)
        val goToHomeFragment : Button = findViewById(R.id.goToNextScreen)
        goToAddClinic.setOnClickListener {
            val intent = Intent(this, ActivityDocotr_AddClinic::class.java)
            startActivity(intent)
        }

        goToHomeFragment.setOnClickListener{
            val intent = Intent(this, HomeActivityDoctor::class.java)
            startActivity(intent)
        }
    }
}