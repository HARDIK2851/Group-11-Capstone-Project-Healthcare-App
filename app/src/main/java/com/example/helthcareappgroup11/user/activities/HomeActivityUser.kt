package com.example.helthcareappgroup11.user.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.user.fragments.DoctorListUserFragment
import com.example.helthcareappgroup11.user.fragments.UserHomeFragment
import com.example.helthcareappgroup11.user.fragments.doctorClinicUserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivityUser : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation_user);

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.bottom_home -> {
                    replaceFragment(UserHomeFragment())
                    true
                }

                R.id.bottom_doctorList_user -> {
                    replaceFragment(DoctorListUserFragment())
                    true
                }

                R.id.bottom_clinic_user -> {
                    replaceFragment(doctorClinicUserFragment())
                    true
                }


                else -> false
            }
        }

        replaceFragment(UserHomeFragment())

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.user_container, fragment).commit()
    }

}