package com.example.helthcareappgroup11.admin

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.models.Customers
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminCustomerList : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var customerAdapter: CustomerAdapter
    private var customerList: MutableList<Customers> = mutableListOf()

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_customer_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        recyclerView = findViewById(R.id.customerList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        customerAdapter = CustomerAdapter(this,customerList)
        recyclerView.adapter = customerAdapter
        loadCustomerData()
        Log.v("data", customerList.toString())

    }

    private fun loadCustomerData() {
        val userId = auth.currentUser?.uid
        val customersRef = database.reference.child("Customers")

        customersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                customerList.clear()
                for (customerSnapshot in snapshot.children) {
                    val customer = customerSnapshot.getValue(Customers::class.java)
                    if (customer != null) {
                        customerList.add(customer)
                    }
                }
                customerAdapter.notifyDataSetChanged()
                Log.v("DataSize", "Clinics List Size: ${customerList.size}")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.v("DataSize", "Some error while fetching data")
            }
        })
    }
}