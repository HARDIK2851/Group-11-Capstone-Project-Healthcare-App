package com.example.helthcareappgroup11.doctor.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.doctor.fragments.AddAppointmentFragment
import com.example.helthcareappgroup11.doctor.fragments.DoctorProfileFragment
import com.example.helthcareappgroup11.doctor.fragments.HomeFragment
import com.example.helthcareappgroup11.doctor.fragments.SettingFragment
import com.example.helthcareappgroup11.doctor.fragments.UpcomingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivityDoctor : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_doctor)

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.bottom_home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.bottom_doctorProfile -> {
                    replaceFragment(DoctorProfileFragment())
                    true
                }

                R.id.bottom_settings -> {
                    replaceFragment(SettingFragment())
                    true
                }

                R.id.bottom_adding_appointment -> {
                    replaceFragment(AddAppointmentFragment())
                    true
                }

                R.id.bottom_upcoming -> {
                    replaceFragment(UpcomingFragment())
                    true
                }

                else -> false
            }
        }

        replaceFragment(HomeFragment())

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.doctor_container, fragment).commit()
    }
}