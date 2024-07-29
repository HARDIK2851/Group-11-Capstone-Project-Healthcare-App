package com.example.helthcareappgroup11.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.helthcareappgroup11.ActivityLogin
import com.example.helthcareappgroup11.R

class AdminHomeActivity : AppCompatActivity() {
    private lateinit var btnLogOut: Button
    private lateinit var btnActivityClinic: Button
    private lateinit var btnActivityCustomers: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnLogOut = findViewById(R.id.btnLogOutA)
        btnActivityCustomers = findViewById(R.id.btnActiveCustomers)
        btnActivityClinic = findViewById(R.id.btnActiveClinics)

        btnLogOut.setOnClickListener {
            val intent = Intent(this, ActivityLogin::class.java)
            startActivity(intent)
        }

        btnActivityCustomers.setOnClickListener {
            val intent = Intent(this, AdminCustomerList::class.java)
            startActivity(intent)
        }

        btnActivityClinic.setOnClickListener {
            val intent = Intent(this, AdminClinicList::class.java)
            startActivity(intent)
        }
    }
}