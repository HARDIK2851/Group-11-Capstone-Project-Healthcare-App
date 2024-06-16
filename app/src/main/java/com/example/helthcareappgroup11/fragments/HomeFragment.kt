package com.example.helthcareappgroup11.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.helthcareappgroup11.ActivityDocotr_AddClinic
import com.example.helthcareappgroup11.Activity_Doctor_CompleteProfile
import com.example.helthcareappgroup11.R

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val goToCompleteProfile: Button = view.findViewById(R.id.goToCompleteProfile)
        val goToAddClinic: Button = view.findViewById(R.id.goToAddClinic)


        goToCompleteProfile.setOnClickListener{
            val intent = Intent(requireActivity(), Activity_Doctor_CompleteProfile::class.java)
            startActivity(intent)
        }


        goToAddClinic.setOnClickListener{
            val intent = Intent(requireActivity(), ActivityDocotr_AddClinic::class.java)
            startActivity(intent)
        }

        return view;
    }
}