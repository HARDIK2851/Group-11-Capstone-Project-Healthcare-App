package com.example.helthcareappgroup11.user.activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.models.AppointmentSlots
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SlotDetailActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private  lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_slot_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val customerId = currentUser?.uid.toString()
        val slotId = intent.getStringExtra("slot_id").toString()
        val doctorId = intent.getStringExtra("doctorId").toString()
        Log.v("ids",slotId+"/"+doctorId);
        databaseReference = FirebaseDatabase.getInstance().getReference("appointmentSlots/"+doctorId+"/"+slotId)

        val tvDate: TextView = findViewById(R.id.tvDateDetail)
        val tvDuration: TextView = findViewById(R.id.tvDurationDetail)
        val tvAvailability: TextView = findViewById(R.id.tvAvailabilityDetail)
        val tvMeetingOption: TextView = findViewById(R.id.tvMeetingOptionDetail)
        val etTitle: EditText = findViewById(R.id.etTitleDetail)
        val etDescription: EditText = findViewById(R.id.etDescriptionDetail)
        val btnBookSlot: Button = findViewById(R.id.btnBookSlot)

        // Fetch slot details
        databaseReference.get().addOnSuccessListener { snapshot ->
            val slot = snapshot.getValue(AppointmentSlots::class.java)
            slot?.let {
                tvDate.setText(it.date)
                tvDuration.setText(it.duration)
                tvAvailability.setText(it.availability)
                tvMeetingOption.setText(it.meetingOption)
            }
        }

        btnBookSlot.setOnClickListener {
            val title = etTitle.text.toString()
            val description = etDescription.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                databaseReference.updateChildren(
                    mapOf(
                        "title" to title,
                        "description" to description,
                        "booked" to true,
                        "customerId" to customerId
                    )
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Slot booked successfully!", Toast.LENGTH_SHORT).show()
                        finish() // Close the activity and go back
                    }
                    else {
                        // Show error message
                        Toast.makeText(this, "Failed to book slot. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Failed to book slot. Please try again.", Toast.LENGTH_SHORT).show()            }
        }

    }
}