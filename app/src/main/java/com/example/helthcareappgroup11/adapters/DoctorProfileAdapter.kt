package com.example.helthcareappgroup11.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.models.Doctors
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


// creating adapter class using firebase recycleroptions and firebaserecycleradapter
class DoctorProfileAdapter(options: FirebaseRecyclerOptions<Doctors>) :
    FirebaseRecyclerAdapter<Doctors, DoctorProfileAdapter.DoctorViewHolder>(options)

{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DoctorViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: DoctorProfileAdapter.DoctorViewHolder, position: Int, model: Doctors) {
        // use for holding the storage
        val storageRef : StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(model.profileImageUrl)
        // use for displaying the images
        Glide.with(holder.doctorProfileImage.context)
            .load(storageRef).into(holder.doctorProfileImage)

        // doctorName val is initialized with modek and name.
        holder.doctorName.text = model.name
        holder.doctorSpeciality.text = model.speciality

        // adding the clickable logic for viewMore button
        holder.viewMore.setOnClickListener{
            Toast.makeText(holder.itemView.context, "View more button clicked", Toast.LENGTH_SHORT).show()
        }

    }

    // creating the DoctorViewHolder class
    class DoctorViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.doctor_profile_item, parent, false))
    {
            // initializing the variabls and finding with id

        val doctorProfileImage : ImageView = itemView.findViewById(R.id.profileImageUrl)
        val doctorName : TextView = itemView.findViewById(R.id.name);
        val doctorSpeciality : TextView = itemView.findViewById(R.id.speciality);
        val viewMore : AppCompatButton = itemView.findViewById(R.id.viewMore)
    }
}