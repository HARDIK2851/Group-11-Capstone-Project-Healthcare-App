package com.example.helthcareappgroup11

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.helthcareappgroup11.fragments.AddAppointmentFragment
import com.example.helthcareappgroup11.fragments.DoctorListUserFragment
import com.example.helthcareappgroup11.fragments.DoctorProfileFragment
import com.example.helthcareappgroup11.fragments.HomeFragment
import com.example.helthcareappgroup11.fragments.SettingFragment
import com.example.helthcareappgroup11.fragments.UpcomingFragment
import com.example.helthcareappgroup11.fragments.UserHomeFragment
import com.example.helthcareappgroup11.fragments.doctorClinicUserFragment
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