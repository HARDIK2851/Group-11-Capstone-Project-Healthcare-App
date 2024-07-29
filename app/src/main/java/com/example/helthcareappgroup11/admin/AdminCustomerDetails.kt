package com.example.helthcareappgroup11.admin

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.helthcareappgroup11.R

class AdminCustomerDetails : AppCompatActivity() {

    private lateinit var nameTextView: TextView
    private lateinit var ageTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var heightTextView: TextView
    private lateinit var photoImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_customer_details)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        nameTextView = findViewById(R.id.customerName)
        ageTextView = findViewById(R.id.customerAge)
        emailTextView = findViewById(R.id.customerEmail)
        phoneTextView = findViewById(R.id.customerPhone)
        heightTextView = findViewById(R.id.customerHeight)

//        photoImageView = findViewById(R.id.cu)

        // Retrieve data from intent
        val name = intent.getStringExtra("NAME")
        val age = intent.getIntExtra("AGE", 0)
        val email = intent.getStringExtra("EMAIL")
        val phone = intent.getStringExtra("PHONE")
        val height = intent.getFloatExtra("HEIGHT",0f)

        val photoUrl = intent.getStringExtra("PHOTO_URL")

        // Set data to views
        nameTextView.text = name
        ageTextView.text = "Age: $age"
        emailTextView.text = email
        phoneTextView.text = phone
        heightTextView.text = "Hieght: $height"

//        // Load photo using Glide or Picasso
//        Glide.with(this)
//            .load(photoUrl)
//            .into(photoImageView)
    }
}
