package com.example.helthcareappgroup11.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.adapters.ClinicsAdapter
import com.example.helthcareappgroup11.objectClasses.Clinics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class doctorClinicUserFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var clinicsAdapter: ClinicsAdapter
    private var clinicsList: MutableList<Clinics> = mutableListOf() // List to hold clinics data

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_doctor_clinic_user, container, false)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        recyclerView = rootView.findViewById(R.id.recyclerViewClinics)
        recyclerView.layoutManager = LinearLayoutManager(context)
        clinicsAdapter = ClinicsAdapter(clinicsList)
        recyclerView.adapter = clinicsAdapter

        loadClinicsData()
        Log.v("data", clinicsList.toString())

        return rootView
    }

    private fun loadClinicsData() {
        val userId = auth.currentUser?.uid
        val clinicsRef = database.reference.child("clinics")

        clinicsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                clinicsList.clear()
                for (clinicSnapshot in snapshot.children) {
                    val clinic = clinicSnapshot.getValue(Clinics::class.java)
                    if (clinic != null) {
                        clinicsList.add(clinic)
                    }

                }
                clinicsAdapter.notifyDataSetChanged()
                Log.v("DataSize", "Clinics List Size: ${clinicsList.size}")
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }
}
