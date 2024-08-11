package com.example.helthcareappgroup11.user.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.models.Doctors
import com.example.helthcareappgroup11.user.activities.ViewMoreForUserActivity
import com.example.helthcareappgroup11.user.fragments.BookAppointmentForUserFragment
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class DoctorItemAdapter(options: FirebaseRecyclerOptions<Doctors>) :
    FirebaseRecyclerAdapter<Doctors, DoctorItemAdapter.MyViewHolder>(options)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater  = LayoutInflater.from(parent.context)
        return MyViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: Doctors) {
        val storageRef: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(model.photoUrl ?: "")
//        Glide.with(holder.doctorImage.context)
//            .load(storageRef).into(holder.doctorImage)

        val photoUrl = model.photoUrl
        if (!photoUrl.isNullOrEmpty()) {
            val storageRef: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(photoUrl)
            Glide.with(holder.doctorImage.context)
                .load(storageRef)
                .placeholder(R.drawable.patient1)
                .into(holder.doctorImage)
        } else {
            // Handle the case where the photoUrl is null or empty
            holder.doctorImage.setImageResource(R.drawable.patient1) // A default image resource
        }

        holder.doctorName.text = model.fullName
        holder.doctorExpertise.text = model.specialization

        holder.viewMoreForUser.setOnClickListener{
            Toast.makeText(holder.itemView.context, "Displaying all details", Toast.LENGTH_SHORT).show()
            val Intent = Intent(holder.itemView.context, ViewMoreForUserActivity::class.java).apply {
                putExtra("fullName", model.fullName)
                putExtra("email", model.email)
                putExtra("phone", model.phone)
                putExtra("clinicId", model.clinicId)
                putExtra("specialization", model.specialization)
                putExtra("education", model.education)
                putExtra("certifications", model.certifications)
                putExtra("bio", model.bio)
                putExtra("photoUrl", model.photoUrl)
            }

            holder.itemView.context.startActivity(Intent)
        }

        holder.itemView.setOnClickListener {
        }

        holder.bookAppointment.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Book Appointment clicked for ${model.fullName}", Toast.LENGTH_SHORT).show()
            val Intent = Intent(holder.itemView.context, BookAppointmentForUserFragment::class.java)
            holder.itemView.context.startActivity(Intent)
        }
    }

    class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_doctor_card_user, parent, false)) {

        var doctorImage: ImageView = itemView.findViewById(R.id.doctor_image)
        var doctorName: TextView = itemView.findViewById(R.id.doctor_name)
        var doctorExpertise: TextView = itemView.findViewById(R.id.doctor_expertise)
        var viewMoreForUser: Button = itemView.findViewById(R.id.view_more_forUser)
        var bookAppointment: Button = itemView.findViewById(R.id.book_appointment_forUser)
    }
}