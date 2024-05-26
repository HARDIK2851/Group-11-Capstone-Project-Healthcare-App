package com.example.helthcareappgroup11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val delay: Long = 2000

        Handler().postDelayed({
            // Start the new activity
            val intent = Intent(this, ActivityLogin::class.java)
            startActivity(intent)
            finish() // Optional: Close the current activity
        }, delay)
    }
}