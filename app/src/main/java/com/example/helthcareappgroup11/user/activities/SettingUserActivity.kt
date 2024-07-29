package com.example.helthcareappgroup11.user.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.helthcareappgroup11.ActivityLogin
import com.example.helthcareappgroup11.R
import com.google.firebase.auth.FirebaseAuth


class SettingUserActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var logoutBtnUser : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setting_user)

        logoutBtnUser = findViewById(R.id.btnLogout_forUser)


        auth = FirebaseAuth.getInstance()

        logoutBtnUser.setOnClickListener {
            showLogoutConfirmationDialog()
        }

    }

    private fun showLogoutConfirmationDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_logout_confirmation, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)

        val alertDialog = builder.create()
        alertDialog.show()

        val btnCancel: Button = dialogView.findViewById(R.id.btnCancel)
        val btnConfirm: Button = dialogView.findViewById(R.id.btnConfirm)

        btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        btnConfirm.setOnClickListener {
            logoutUser()
            alertDialog.dismiss()
        }
    }

    private fun logoutUser() {
        auth.signOut()
        val intent = Intent(this, ActivityLogin::class.java)
        startActivity(intent)
        finish()
        Toast.makeText(this, "User logout", Toast.LENGTH_SHORT).show()
    }
}