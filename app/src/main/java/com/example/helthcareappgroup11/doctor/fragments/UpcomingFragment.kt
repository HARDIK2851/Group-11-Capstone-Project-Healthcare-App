package com.example.helthcareappgroup11.doctor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.doctor.adapter.CreatedAppointmentAdapter
import com.example.helthcareappgroup11.models.AppointmentSlots
import com.example.helthcareappgroup11.models.Doctors
import com.example.helthcareappgroup11.user.adapters.DoctorItemAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpcomingFragment : Fragment() {


    private lateinit var createdAppointmentAdapter: CreatedAppointmentAdapter
    private lateinit var rViewApts: RecyclerView
    private lateinit var db : DatabaseReference
    private lateinit var userId : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_upcoming, container, false)

        rViewApts = view.findViewById(R.id.rView_apts)
        rViewApts.layoutManager = LinearLayoutManager(context)

        val auth = FirebaseAuth.getInstance()
        userId = auth.currentUser?.uid ?: ""

        if (userId.isEmpty()) {
            Toast.makeText(context, "User not authenticated", Toast.LENGTH_SHORT).show()
            return view
        }

        db = FirebaseDatabase.getInstance().reference.child("appointmentSlots").child(userId)

        val options = FirebaseRecyclerOptions.Builder<AppointmentSlots>()
            .setQuery(db, AppointmentSlots::class.java)
            .build()

        createdAppointmentAdapter = CreatedAppointmentAdapter(options)
        rViewApts.adapter = createdAppointmentAdapter


        return view
    }

    override fun onStart() {
        super.onStart()
        createdAppointmentAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        createdAppointmentAdapter.stopListening()
    }
}