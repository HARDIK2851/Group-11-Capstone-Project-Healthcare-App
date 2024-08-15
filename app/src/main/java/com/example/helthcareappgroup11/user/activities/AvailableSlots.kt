package com.example.helthcareappgroup11.user.activities

import AppointmentSlotsAdapter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.models.AppointmentSlots
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AvailableSlots : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var appointmentSlotsAdapter: AppointmentSlotsAdapter
    private lateinit var databaseReference: DatabaseReference
    private var slotsList = mutableListOf<AppointmentSlots>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_available_slots)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.rView_aptSlots)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        val doctorid = intent.getStringExtra("doctorId")


        appointmentSlotsAdapter = AppointmentSlotsAdapter(slotsList)
        recyclerView.adapter = appointmentSlotsAdapter
        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("appointmentSlots/"+doctorid)

        // Fetch data
        fetchAppointmentSlots()
    }

    private fun fetchAppointmentSlots() {
        databaseReference.orderByChild("booked").equalTo(false)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    slotsList.clear()
                    for (dataSnapshot in snapshot.children) {
                        val slot = dataSnapshot.getValue(AppointmentSlots::class.java)
                        if (slot != null) {
                            slotsList.add(slot)
                        }
                    }
                    appointmentSlotsAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle database error
                }
            })
    }
}