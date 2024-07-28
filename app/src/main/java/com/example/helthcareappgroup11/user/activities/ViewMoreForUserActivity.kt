package com.example.helthcareappgroup11.user.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.user.fragments.UserHomeFragment
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ViewMoreForUserActivity : AppCompatActivity() {
    private lateinit var doctorPhoto: ImageView
    private lateinit var doctorFullName: TextView
    private lateinit var doctorSpecialization: TextView
    private lateinit var doctorEmail: TextView
    private lateinit var doctorPhone: TextView
    private lateinit var doctorEducation: TextView
    private lateinit var doctorCertifications: TextView
    private lateinit var doctorBio: TextView
    private lateinit var goBackBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_view_more_for_user)

        doctorPhoto = findViewById(R.id.doctorPhoto)
        doctorFullName = findViewById(R.id.doctorFullName)
        doctorSpecialization = findViewById(R.id.doctorSpecialization)
        doctorEmail = findViewById(R.id.doctorEmail)
        doctorPhone = findViewById(R.id.doctorPhone)
        doctorEducation = findViewById(R.id.doctorEducation)
        doctorCertifications = findViewById(R.id.doctorCertifications)
        doctorBio = findViewById(R.id.doctorBio)
        goBackBtn = findViewById(R.id.goBackBtn)

        val fullName = intent.getStringExtra("fullName")
        val email = intent.getStringExtra("email")
        val phone = intent.getStringExtra("phone")
        val clinicId = intent.getStringExtra("clinicId")
        val specialization = intent.getStringExtra("specialization")
        val education = intent.getStringExtra("education")
        val certifications = intent.getStringExtra("certifications")
        val bio = intent.getStringExtra("bio")
        val photoUrl = intent.getStringExtra("photoUrl")

        doctorFullName.text = fullName
        doctorSpecialization.text = specialization
        doctorEmail.text = email
        doctorPhone.text = phone
        doctorEducation.text = education
        doctorCertifications.text = certifications
        doctorBio.text = bio

        if (!photoUrl.isNullOrEmpty()) {
            val storageRef: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(photoUrl)
            Glide.with(this)
                .load(storageRef)
                .into(doctorPhoto)
        } else {
            doctorPhoto.setImageResource(R.drawable.patient1) // A default image resource
        }

        goBackBtn.setOnClickListener {
            startActivity(Intent(this, UserHomeFragment::class.java))
            Toast.makeText(this, "Going back", Toast.LENGTH_SHORT).show()
        }
    }
}