package com.example.helthcareappgroup11.user.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.helthcareappgroup11.user.fragments.CurrentAppointmentsUserFragment
import com.example.helthcareappgroup11.user.fragments.UserHistoryAppointmentsFragment

class UserAppointmentsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> CurrentAppointmentsUserFragment()
            1 -> UserHistoryAppointmentsFragment()
            else -> throw IllegalStateException("Unexpected position: $position")
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Current"
            1 -> "History"
            else -> null
        }
    }
}