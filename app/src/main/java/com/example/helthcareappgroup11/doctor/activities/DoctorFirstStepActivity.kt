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

class DoctorFirstStepActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_first_step)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val goToCompleteProfile = findViewById<Button>(R.id.goToCompleteProfile)
        val nextScreen: Button = findViewById(R.id.goToSecondScreen)

        goToCompleteProfile.setOnClickListener {
            val intent = Intent(this, Activity_Doctor_CompleteProfile::class.java)
            startActivity(intent)
        }

        nextScreen.setOnClickListener {
            val userId = auth.currentUser?.uid ?: return@setOnClickListener
            val doctorRef = database.getReference("doctors").child(userId)
            doctorRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val profileCompleted = snapshot.child("profileCompleted").getValue(Boolean::class.java) ?: false
                    if (profileCompleted) {
                        val intent = Intent(this@DoctorFirstStepActivity, DoctorSecondStepRegisterActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@DoctorFirstStepActivity, "Please complete your profile first", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@DoctorFirstStepActivity, "Error checking profile status", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
