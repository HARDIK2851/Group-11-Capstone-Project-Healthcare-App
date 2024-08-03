package com.example.helthcareappgroup11.doctor.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.helthcareappgroup11.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DoctorSecondStepRegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_second_step_register)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val goToAddClinic = findViewById<Button>(R.id.goToAddClinic)
        val goToHomeFragment: Button = findViewById(R.id.goToNextScreen)

        goToAddClinic.setOnClickListener {
            val intent = Intent(this, ActivityDocotr_AddClinic::class.java)
            startActivity(intent)
        }

        goToHomeFragment.setOnClickListener {
            val userId = auth.currentUser?.uid ?: return@setOnClickListener
            val doctorRef = database.getReference("doctors").child(userId)
            doctorRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val clinicAdded = snapshot.child("clinicAdded").getValue(Boolean::class.java) ?: false
                    if (clinicAdded) {
                        val intent = Intent(this@DoctorSecondStepRegisterActivity, HomeActivityDoctor::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@DoctorSecondStepRegisterActivity, "Please add your clinic information first", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@DoctorSecondStepRegisterActivity, "Error checking clinic status", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
