package com.example.helthcareappgroup11.doctor.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import com.bumptech.glide.Glide
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.models.Doctors
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class DoctorProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private lateinit var fullName: TextView
    private lateinit var email: TextView
    private lateinit var phone: TextView
    private lateinit var education: TextView
    private lateinit var certifications: TextView
    private lateinit var specialization: TextView
    private lateinit var bio: TextView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_doctor_profile, container, false)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Initialize TextViews
        fullName = view.findViewById(R.id.fullName_DPF)
        email = view.findViewById(R.id.email_DPF)
        phone = view.findViewById(R.id.phone)
        education = view.findViewById(R.id.education_DPF)
        certifications = view.findViewById(R.id.certifications_DPF)
        specialization = view.findViewById(R.id.speciality_DPF)
        bio = view.findViewById(R.id.bio_DPF)



        val doctorId = auth.currentUser?.uid
        if (doctorId != null) {
            val doctorsRef = database.getReference("doctors").child(doctorId)

            doctorsRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("DoctorProfileFragment", "DataSnapshot: ${dataSnapshot.value}")
                    val doctorData = dataSnapshot.getValue(Doctors::class.java)
                    if (doctorData != null) {
                        fullName.text = doctorData.fullName ?: "N/A"
                        specialization.text = doctorData.specialization ?: "N/A"
                        education.text = doctorData.education ?: "N/A"
                        certifications.text = doctorData.certifications ?: "N/A"
                        bio.text = doctorData.bio ?: "N/A"
                        email.text = doctorData.email ?: "N/A"
                        phone.text = doctorData.phone ?: "N/A"

                        val photoUrl = doctorData.photoUrl
                        if (!photoUrl.isNullOrEmpty()) {
                            Glide.with(this@DoctorProfileFragment)
                                .load(photoUrl)
                                .into(view.findViewById(R.id.photoUrl_DPF))
                        }
                    } else {
                        // Handle the case where the doctor data is null
                        Toast.makeText(activity, "Doctor data is null", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle possible errors.
                    Toast.makeText(activity, "Failed to load data", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            // Handle the case where doctorId is null
            Toast.makeText(activity, "Doctor ID is null", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}