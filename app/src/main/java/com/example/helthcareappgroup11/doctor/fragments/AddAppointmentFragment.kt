package com.example.helthcareappgroup11.doctor.fragments

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.models.AppointmentSlots
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.lang.Math.random
import java.sql.Date
import java.util.UUID


class AddAppointmentFragment : Fragment() {

    private lateinit var appointmentDate: CalendarView
    private lateinit var appointmentDuration: Spinner
    private lateinit var appointmentAvailability: EditText
    private lateinit var appointmentTitle: EditText
    private lateinit var appointmentDescription: EditText
    private lateinit var appointmentMeetingMode: Spinner
    private lateinit var addAppointmentButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add_appointment, container, false)

        auth = FirebaseAuth.getInstance()
        var user = auth.currentUser
        var userId = user?.uid
        database = FirebaseDatabase.getInstance()

        appointmentTitle = view.findViewById(R.id.appointment_title);
        appointmentDate = view.findViewById(R.id.appointment_date);
        appointmentDuration = view.findViewById(R.id.appointment_duration);
        appointmentAvailability = view.findViewById(R.id.appointment_availability);
        appointmentDescription = view.findViewById(R.id.appointment_description);
        appointmentMeetingMode = view.findViewById(R.id.appointment_meeting_options);
        addAppointmentButton = view.findViewById(R.id.btnAppointmentSlot);


        addAppointmentButton.setOnClickListener {
            val title = appointmentTitle.text.toString().trim()
            val selectedDate = getSelectedDate(appointmentDate)
            val duration = appointmentDuration.selectedItem.toString()
            val availability = appointmentAvailability.text.toString().trim()
            val description = appointmentDescription.text.toString().trim()
            val meetingOption = appointmentMeetingMode.selectedItem.toString()

            // Add appointment data to Firebase Realtime Database
            val appointmentRef = FirebaseDatabase.getInstance().getReference("appointmentSlots")
            val appointmentId = UUID.randomUUID().toString()
            val appointmentData = AppointmentSlots(
                appointmentId,
                selectedDate,
                duration,
                availability,
                title,
                description,
                meetingOption,
                false,
                "",

                userId.toString().trim()
            )
            if (userId != null) {
                appointmentRef.child(userId).child(appointmentId).setValue(appointmentData)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Appointment slot added successfully", Toast.LENGTH_SHORT).show()

                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(context, "Error adding appointment slot: $exception", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        return view
    }

    private fun getSelectedDate(calendarView: CalendarView): String {
        val calendar = Calendar.getInstance()
        calendar.time = Date(calendarView.date)
        return "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) +
                1}/${calendar.get(Calendar.YEAR)}"
    }
}