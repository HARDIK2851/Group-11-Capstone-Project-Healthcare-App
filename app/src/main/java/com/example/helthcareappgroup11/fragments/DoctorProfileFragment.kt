package com.example.helthcareappgroup11.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.adapters.DoctorProfileAdapter
import com.example.helthcareappgroup11.models.Doctors
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

class DoctorProfileFragment : Fragment() {

    // declaring the variables
    private var adapter: DoctorProfileAdapter? = null
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_doctor_profile, container, false)

        // initializng them with find /view by id
        recyclerView = view.findViewById(R.id.dPView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Creating a reference to doctors node in realtime database
        val query = FirebaseDatabase.getInstance().reference.child("doctors")

        // creating the instance of firebase recycler optionns
        val options = FirebaseRecyclerOptions.Builder<Doctors>().setQuery(query, Doctors::class.java).build()
        adapter = DoctorProfileAdapter(options)
        recyclerView.adapter = adapter

        return view

    }

    // starting the activity
    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }

    // stopping the activity
    override fun onStop() {
        super.onStop()
        adapter?.startListening()
    }
}