package com.example.helthcareappgroup11.doctor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.doctor.adapter.PatientHistoryAdapter
import com.example.helthcareappgroup11.models.PatientHistory
import com.example.helthcareappgroup11.doctor.objectClasses.Doctors
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var doctor: Doctors

    private lateinit var fullName: TextView



    private var adapter: PatientHistoryAdapter? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        fullName = view.findViewById(R.id.doctor_name)

        val doctorId = auth.currentUser?.uid
        val doctorsRef = database.getReference("doctors").child(doctorId!!)



        recyclerView = view.findViewById(R.id.patientHistoryRview)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)




        doctorsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                doctor = dataSnapshot.getValue(Doctors::class.java)!!

                fullName.text = doctor.fullName


                // Set profile photo
                val photoUrl = doctor.photoUrl
                if (photoUrl != "") {
                    Glide.with(this@HomeFragment)
                        .load(photoUrl)
                        .into(view.findViewById(R.id.photoUrl))
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })





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
}