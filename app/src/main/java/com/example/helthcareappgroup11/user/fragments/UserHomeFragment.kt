package com.example.helthcareappgroup11.user.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.models.Doctors
import com.example.helthcareappgroup11.user.activities.ViewMoreForUserActivity
import com.example.helthcareappgroup11.user.adapters.DoctorItemAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserHomeFragment : Fragment() {

    private lateinit var database: DatabaseReference


    private lateinit var searchBar: EditText
    private lateinit var rView: RecyclerView

    private lateinit var doctorAdapterForUser: DoctorItemAdapter
    private val doctorList = mutableListOf<Doctors>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_home, container, false)


        searchBar = view.findViewById(R.id.search_bar)

        rView = view.findViewById(R.id.doctor_recycler_view)
        rView.layoutManager = LinearLayoutManager(context)

        database = FirebaseDatabase.getInstance().reference.child("doctors")

        val options = FirebaseRecyclerOptions.Builder<Doctors>()
            .setQuery(database, Doctors::class.java)
            .build()

        doctorAdapterForUser = DoctorItemAdapter(options)
        rView.adapter = doctorAdapterForUser


        setupSearch()

        return view
    }


    private fun setupSearch() {
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchQuery = s.toString()
                filterDoctors(searchQuery)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterDoctors(query: String) {
        val filteredQuery = database.orderByChild("fullName").startAt(query).endAt(query + "\uf8ff")
        val options = FirebaseRecyclerOptions.Builder<Doctors>()
            .setQuery(filteredQuery, Doctors::class.java)
            .build()

        doctorAdapterForUser.updateOptions(options)
    }

    override fun onStart() {
        super.onStart()
        doctorAdapterForUser.startListening()
    }

    override fun onStop() {
        super.onStop()
        doctorAdapterForUser.stopListening()
    }

    private fun fetchDoctors() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                doctorList.clear()
                for (doctorSnapshot in snapshot.children) {
                    val doctor = doctorSnapshot.getValue(Doctors::class.java)
                    if (doctor != null) {
                        doctorList.add(doctor)
                    }
                }
                doctorAdapterForUser.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("HomeFragmentUser", "Failed to load doctors", error.toException())
            }
        })
    }
}