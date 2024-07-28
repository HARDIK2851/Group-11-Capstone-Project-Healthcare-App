package com.example.helthcareappgroup11.user.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.models.Customers
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserClinicFragment : Fragment() {


    private lateinit var name: EditText
    private lateinit var age: EditText
    private lateinit var height: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var medicalHistory: EditText
    private lateinit var allergies: EditText
    private lateinit var completeProfile: Button

    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView =  inflater.inflate(R.layout.fragment_user_clinic, container, false)

        name = rootView.findViewById(R.id.uc_name)
        age = rootView.findViewById(R.id.uc_age)
        height = rootView.findViewById(R.id.uc_height)
        email = rootView.findViewById(R.id.uc_email)
        phone = rootView.findViewById(R.id.uc_phone)
        medicalHistory = rootView.findViewById(R.id.uc_medical_history)
        allergies = rootView.findViewById(R.id.uc_allergies)
        completeProfile = rootView.findViewById(R.id.uc__btn_complete_profile)

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Customers")

        completeProfile.setOnClickListener {
            saveCustomerData()
        }

        return rootView
    }

    private fun saveCustomerData() {
        val nameStr = name.text.toString().trim()
        val ageStr = age.text.toString().trim()
        val heightStr = height.text.toString().trim()
        val emailStr = email.text.toString().trim()
        val phoneStr = phone.text.toString().trim()
        val medicalHistoryStr = medicalHistory.text.toString().trim().takeIf { it.isNotEmpty() }
        val allergiesStr = allergies.text.toString().trim().takeIf { it.isNotEmpty() }

        if (nameStr.isEmpty() || ageStr.isEmpty() || heightStr.isEmpty() || emailStr.isEmpty() || phoneStr.isEmpty()) {
            Toast.makeText(context, "Please fill out all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        val age = ageStr.toInt()
        val height = heightStr.toFloat()

        val customer = Customers(nameStr, age, height, emailStr, phoneStr, medicalHistoryStr, allergiesStr, null)
        val userId = auth.currentUser?.uid

        if (userId != null) {
            databaseReference.child(userId).setValue(customer).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Profile saved successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed to save profile", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(context, "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }
}