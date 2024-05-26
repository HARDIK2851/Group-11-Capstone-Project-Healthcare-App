package com.example.helthcareappgroup11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnGoToRegister = findViewById<Button>(R.id.btnGoToRegister);

        btnGoToRegister.setOnClickListener {
            val intent = Intent(this, ActivityRegister::class.java)

            startActivity(intent);
        }
    }
}