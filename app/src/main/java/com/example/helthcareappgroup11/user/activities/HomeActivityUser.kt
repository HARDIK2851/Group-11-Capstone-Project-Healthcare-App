package com.example.helthcareappgroup11.user.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.user.fragments.AccountFragmentUser
import com.example.helthcareappgroup11.user.fragments.AppointmentFragment
import com.example.helthcareappgroup11.user.fragments.NotificationFragmentUser
import com.example.helthcareappgroup11.user.fragments.UserHomeFragment
import com.example.helthcareappgroup11.user.fragments.doctorClinicUserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivityUser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_user)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.nav_user_home -> selectedFragment = UserHomeFragment()
                R.id.nav_user_clinic -> selectedFragment = doctorClinicUserFragment()
                R.id.nav_user_appointment -> selectedFragment = AppointmentFragment()
                R.id.nav_user_notification -> selectedFragment = NotificationFragmentUser()
                R.id.nav_user_account -> selectedFragment = AccountFragmentUser()
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.container, selectedFragment).commit()
            }
            true
        }

        // Set default selection
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.nav_user_home
        }
    }
}