package com.example.helthcareappgroup11.user.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.example.helthcareappgroup11.ActivityLogin
import com.example.helthcareappgroup11.R
import com.google.firebase.auth.FirebaseAuth

class AccountFragmentUser : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var btnLogOut: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_account_user, container, false)
        auth = FirebaseAuth.getInstance()
        btnLogOut = view.findViewById(R.id.btnLogOut)

        btnLogOut.setOnClickListener {
            auth.signOut()
            val intent = Intent(activity, ActivityLogin::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        return view
    }
}