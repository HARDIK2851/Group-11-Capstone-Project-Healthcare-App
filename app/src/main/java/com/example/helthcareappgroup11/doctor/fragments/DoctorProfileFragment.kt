package com.example.helthcareappgroup11.doctor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

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
    private lateinit var doctor: Doctors

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




        fullName = view.findViewById(R.id.fullName)
        email = view.findViewById(R.id.email)
        phone = view.findViewById(R.id.phone)
        education = view.findViewById(R.id.education)
        certifications = view.findViewById(R.id.certifications)
        specialization = view.findViewById(R.id.speciality)
        bio = view.findViewById(R.id.bio)


        val doctorId = auth.currentUser?.uid
        val doctorsRef = database.getReference("doctors").child(doctorId!!)


        doctorsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                doctor = dataSnapshot.getValue(Doctors::class.java)!!

                fullName.text = doctor.fullName
                specialization.text = doctor.specialization
                education.text = doctor.education
                certifications.text = doctor.certifications
                bio.text = doctor.bio
                email.text = doctor.email
                phone.text = doctor.phone


                val photoUrl = doctor.photoUrl
                if (photoUrl != "") {
                    Glide.with(this@DoctorProfileFragment)
                        .load(photoUrl)
                        .into(view.findViewById(R.id.photoUrl))
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        return view
    }
}