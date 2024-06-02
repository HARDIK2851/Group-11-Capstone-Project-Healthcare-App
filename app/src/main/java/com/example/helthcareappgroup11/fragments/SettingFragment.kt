package com.example.helthcareappgroup11.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.helthcareappgroup11.ActivityLogin
import com.example.helthcareappgroup11.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SettingFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var tvUserRole: TextView
    private lateinit var btnLogout: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        tvUserRole = view.findViewById(R.id.tvUserRole)
        btnLogout = view.findViewById(R.id.btnLogout)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        loadUserRole()

        btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(activity, ActivityLogin::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return view
    }

    private fun loadUserRole() {
        val userId = auth.currentUser?.uid ?: return
        val userRoleRef = database.child("users").child(userId)

        userRoleRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userRole = snapshot.child("role").getValue(String::class.java) ?: "N/A"
                tvUserRole.text = "Role: $userRole"
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity, "Error retrieving user role", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
