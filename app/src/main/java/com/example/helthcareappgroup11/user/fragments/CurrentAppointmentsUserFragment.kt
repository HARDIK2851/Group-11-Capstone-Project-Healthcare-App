package com.example.helthcareappgroup11.user.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.user.adapters.UserAppointmentAdapter
import com.example.helthcareappgroup11.user.models.AppointmentUser

class CurrentAppointmentsUserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_current_appointments_user, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_current)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val currentAppointments = generateCurrentAppointments() // Replace with your data generation function

        val adapter = UserAppointmentAdapter(currentAppointments)
        recyclerView.adapter = adapter

        return view
    }

    private fun generateCurrentAppointments(): List<AppointmentUser> {
        // Replace with your actual data retrieval or generation logic
        return listOf(
            AppointmentUser(
                doctorImage = R.drawable.baseline_account_circle_24,
                doctorName = "Dr. John Doe",
                doctorExpertise = "Cardiologist",
                appointmentDateTime = "Monday, July 5, 2024 at 10:00 AM"
            ),
            AppointmentUser(
                doctorImage = R.drawable.baseline_account_circle_24,
                doctorName = "Dr. Jane Smith",
                doctorExpertise = "Dermatologist",
                appointmentDateTime = "Wednesday, July 7, 2024 at 3:30 PM"
            ),
            // Add more appointments as needed
        )
    }
}