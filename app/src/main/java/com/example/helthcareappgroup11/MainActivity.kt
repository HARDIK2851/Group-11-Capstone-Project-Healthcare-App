package com.example.helthcareappgroup11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.helthcareappgroup11.doctor.activities.DoctorFirstStepActivity
import com.example.helthcareappgroup11.user.activities.HomeActivityUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Delay execution by 2 seconds
        Handler().postDelayed({
            val intent = Intent(this@MainActivity, ActivityLogin::class.java)
            startActivity(intent)
        }, 2000)
    }

}
