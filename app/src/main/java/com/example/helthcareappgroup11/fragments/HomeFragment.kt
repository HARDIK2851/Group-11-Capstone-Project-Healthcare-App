package com.example.helthcareappgroup11.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.adapters.PatientHistoryAdapter
import com.example.helthcareappgroup11.models.DoctorProfile
import com.example.helthcareappgroup11.models.PatientHistory
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class HomeFragment : Fragment() {
    private lateinit var photoUrl: ImageView
    private lateinit var fullName: TextView

    private lateinit var storage: FirebaseStorage
    private lateinit var doctorRef: DatabaseReference

    private var adapter: PatientHistoryAdapter? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)


        photoUrl = view.findViewById(R.id.doctor_profile_img)

        fullName = view.findViewById(R.id.doctor_name)


        recyclerView = view.findViewById(R.id.patientHistoryRview)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)



        // Initialize Firebase Storage and Database references
        storage = FirebaseStorage.getInstance()
        doctorRef = FirebaseDatabase.getInstance().reference.child("doctors")
        val options = FirebaseRecyclerOptions.Builder<DoctorProfile>()
            .setQuery(doctorRef, DoctorProfile::class.java)
            .build()

        fetchDoctorData()





        // fpr mpw we are using hard codes list to display the list of users
        val patientHistories = listOf(
            PatientHistory(R.drawable.patient1, "Alex Johnson"),
            PatientHistory(R.drawable.patient2, "Kella Morgan"),
            PatientHistory(R.drawable.patient3, "Riya Patel"),
            PatientHistory(R.drawable.patient4, "Aditya Desai")
        )

//        and for that part we also need to update the options in adapter
        adapter = PatientHistoryAdapter(patientHistories)
        recyclerView.adapter = adapter

        return  view
    }
    private fun fetchDoctorData() {
        //...
        doctorRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val doctorData = snapshot.children.firstOrNull()?.getValue(DoctorProfile::class.java)
                    if (doctorData != null) {
                        fullName.text = "Welcome, "
                        fullName.text = doctorData.fullName
                        val profileImageUrl = doctorData.photoUrl
                        if (profileImageUrl != null) {
                            Glide.with(requireActivity())
                                .load(profileImageUrl)
                                .into(photoUrl)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors
            }
        })
    }
}