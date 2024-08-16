package com.example.helthcareappgroup11.doctor.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.models.AppointmentSlots
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions




class CreatedAppointmentAdapter(options: FirebaseRecyclerOptions<AppointmentSlots>) :
    FirebaseRecyclerAdapter<AppointmentSlots, CreatedAppointmentAdapter.MyViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: AppointmentSlots) {
        holder.title.text = model.title ?: "No title"
        holder.date.text = model.date ?: "No date"
        holder.duration.text = model.duration ?: "No duration"
        holder.availability.text = model.availability ?: "No availability"
        holder.description.text = model.description ?: "No description"
        holder.meetingOption.text = model.meetingOption ?: "No meeting option"

        // Log the data to Logcat
        Log.d("CreatedAppointmentAdapter", "Binding view holder at position $position")
        Log.d("CreatedAppointmentAdapter", "Title: ${model.title}")
        Log.d("CreatedAppointmentAdapter", "Date: ${model.date}")
        Log.d("CreatedAppointmentAdapter", "Duration: ${model.duration}")
        Log.d("CreatedAppointmentAdapter", "Availability: ${model.availability}")
        Log.d("CreatedAppointmentAdapter", "Description: ${model.description}")
        Log.d("CreatedAppointmentAdapter", "Meeting Option: ${model.meetingOption}")

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Appointment clicked: ${model.title}", Toast.LENGTH_SHORT).show()
            // If you want to navigate somewhere or handle click event
        }
    }

    class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_created_apt_slots, parent, false)) {

        var title: TextView = itemView.findViewById(R.id.appointment_title)
        var date: TextView = itemView.findViewById(R.id.appointment_date)
        var duration: TextView = itemView.findViewById(R.id.appointment_duration)
        var availability: TextView = itemView.findViewById(R.id.appointment_availability)
        var description: TextView = itemView.findViewById(R.id.appointment_description)
        var meetingOption: TextView = itemView.findViewById(R.id.appointment_meeting_option)
    }
}