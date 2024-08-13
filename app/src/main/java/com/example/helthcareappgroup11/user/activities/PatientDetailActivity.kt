package com.example.helthcareappgroup11.user.activities


import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.models.Patient
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class PatientDetailActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var bloodGroupAutoCompleteTextView: AutoCompleteTextView
    private lateinit var locationEditText: EditText
    private lateinit var genderRadioGroup: RadioGroup
    private lateinit var detailsEditText: EditText
    private lateinit var submitButton: Button

    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_patient_detail)

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance().getReference("patients")

        // Initialize UI elements
        emailEditText = findViewById(R.id.email_id)
        ageEditText = findViewById(R.id.age)
        bloodGroupAutoCompleteTextView = findViewById(R.id.blood_group)
        locationEditText = findViewById(R.id.location_user)
        genderRadioGroup = findViewById(R.id.radioGroupGender)
        detailsEditText = findViewById(R.id.editTextDetails)
        submitButton = findViewById(R.id.submit_application)

        val bloodGroups = resources.getStringArray(R.array.blood_groups)
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, bloodGroups)

        bloodGroupAutoCompleteTextView.setAdapter(adapter)
        bloodGroupAutoCompleteTextView.setThreshold(1)
        bloodGroupAutoCompleteTextView.inputType = InputType.TYPE_NULL


        bloodGroupAutoCompleteTextView.setOnClickListener {
            bloodGroupAutoCompleteTextView.showDropDown()
        }

        submitButton.setOnClickListener {
            submitForm()
            val intent = Intent(this, HomeActivityUser::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this, "Application submitted successfully! Redirecting to User Home...", Toast.LENGTH_SHORT).show()
        }

    }

    private fun submitForm() {
        // Retrieve data from UI elements
        val email = emailEditText.text.toString().trim()
//        val ageText = ageEditText.text.toString().trim().toIntOrNull() ?: 0
        val bloodGroup = bloodGroupAutoCompleteTextView.text.toString().trim()
        val location = locationEditText.text.toString().trim()
        val genderId = genderRadioGroup.checkedRadioButtonId
        val gender = findViewById<RadioButton>(genderId)?.text.toString()
        val details = detailsEditText.text.toString().trim()

        val ageText = ageEditText.text.toString().trim()


        if (ageText.isEmpty() || ageText.length == 0 || ageText == "") {
            ageEditText.error = "Age is required"
            return
        }else{

        }


        var age: Int
        try {
            age = ageText.toInt()
            if (age <= 0) {
                ageEditText.error = "Age must be a positive number"
                return
            }
        } catch (e: NumberFormatException) {
            ageEditText.error = "Age must be a valid number"
            return
        }



        if (email.isEmpty()) {
            emailEditText.error = "Email is required"
            return
        }


        if (bloodGroup.isEmpty()) {
            bloodGroupAutoCompleteTextView.error = "Blood group is required"
            return
        }

        if (location.isEmpty()) {
            locationEditText.error = "Location is required"
            return
        }

        if (genderId == -1) {
            Toast.makeText(this, "Gender is required", Toast.LENGTH_SHORT).show()
            return
        }

        if (details.isEmpty()) {
            detailsEditText.error = "Details are required"
            return
        }

        // Create a Patient object
        val patient = Patient(email, age , bloodGroup, location, gender, details)

        // Add data to Firebase
        val patientId = database.push().key
        if (patientId != null) {
            database.child(patientId).setValue(patient)
                .addOnSuccessListener {
                    Toast.makeText(this, "Application submitted successfully", Toast.LENGTH_SHORT).show()
                    // Optionally clear the form or navigate away
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Failed to submit application: $exception", Toast.LENGTH_SHORT).show()
                }
        }
    }
}

