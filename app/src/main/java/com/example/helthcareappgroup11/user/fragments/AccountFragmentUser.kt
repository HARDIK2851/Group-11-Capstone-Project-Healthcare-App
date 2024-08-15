package com.example.helthcareappgroup11.user.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.helthcareappgroup11.ActivityLogin
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.user.activities.Account_InfoForUserActivity
import com.example.helthcareappgroup11.user.activities.SettingUserActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AccountFragmentUser : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var btnLogOut: Button

    private lateinit var database: DatabaseReference
    private lateinit var username: TextView
    private lateinit var useremail: TextView

    private lateinit var account_txt : TextView
    private lateinit var setting_txt : TextView

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

        username = view.findViewById(R.id.user_name)
        useremail = view.findViewById(R.id.user_email)

        database = FirebaseDatabase.getInstance().reference

        account_txt = view.findViewById(R.id.account_info_text)
        setting_txt = view.findViewById(R.id.settings_text)


        account_txt.setOnClickListener{
            val intent = Intent(requireContext(), Account_InfoForUserActivity::class.java)
            startActivity(intent)
            Toast.makeText(requireContext(), "showing client information", Toast.LENGTH_SHORT).show()
        }

        setting_txt.setOnClickListener {
            val intent = Intent(requireContext(), SettingUserActivity::class.java)
            startActivity(intent)
            Toast.makeText(requireContext(), "moving to user setting", Toast.LENGTH_SHORT).show()
        }

        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid
            Log.d("AccountFragmentUser", "Retrieving profile for userId: $userId")

            database.child("Customers").child(userId).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val pUsername = snapshot.child("name").getValue(String::class.java)
                    val pEmail = currentUser.email

                    if (pUsername != null) {
                        username.text = pUsername
                        Log.d("AccountFragmentUser", "Username retrieved: $pUsername")
                    } else {
                        Toast.makeText(requireContext(), "Failed to retrieve username", Toast.LENGTH_SHORT).show()
                        Log.e("AccountFragmentUser", "Username is null for userId: $userId")
                    }

                    if (pEmail != null) {
                        useremail.text = pEmail
                        Log.d("AccountFragmentUser", "Email retrieved: $pEmail")
                    } else {
                        Toast.makeText(requireContext(), "Failed to retrieve email", Toast.LENGTH_SHORT).show()
                        Log.e("AccountFragmentUser", "Email is null for userId: $userId")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Failed to retrieve data", Toast.LENGTH_SHORT).show()
                    Log.e("AccountFragmentUser", "Failed to retrieve data for userId: $userId", error.toException())
                }
            })
        } else {
            Toast.makeText(requireContext(), "No authenticated user", Toast.LENGTH_SHORT).show()
            Log.e("AccountFragmentUser", "No authenticated user found")
        }

        return view
    }
}