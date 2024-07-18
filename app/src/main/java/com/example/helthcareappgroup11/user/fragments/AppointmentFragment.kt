package com.example.helthcareappgroup11.user.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.user.adapters.UserAppointmentsPagerAdapter
import com.google.android.material.tabs.TabLayout

class AppointmentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_appointment, container, false)
        val tabLayout: TabLayout = rootView.findViewById(R.id.tab_layout)

//
        val viewPager: ViewPager = rootView.findViewById(R.id.view_pager)

//        val adapter = UserAppointmentsPagerAdapter(supportFragmentManager)
        val adapter = UserAppointmentsPagerAdapter(childFragmentManager)

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        return rootView
    }
}