package com.example.helthcareappgroup11.admin

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.models.Clinics
import com.example.helthcareappgroup11.user.adapters.ClinicsAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminClinicList : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var clinicsAdapter: ClinicsAdapter
    private var clinicsList: MutableList<Clinics> = mutableListOf()

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private  lateinit var totalClinics: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_clinic_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        totalClinics = findViewById(R.id.totalClinics)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        recyclerView = findViewById(R.id.adminClinicList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        clinicsAdapter = ClinicsAdapter(clinicsList)
        recyclerView.adapter = clinicsAdapter
        loadClinicsData()
        Log.v("data", clinicsList.toString())

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
                totalClinics.setText("Total Clinics - ${clinicsList.size}")
                clinicsAdapter.notifyDataSetChanged()
                Log.v("DataSize", "Clinics List Size: ${clinicsList.size}")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.v("DataSize", "Some error while fetching data")
            }
        })
    }
}