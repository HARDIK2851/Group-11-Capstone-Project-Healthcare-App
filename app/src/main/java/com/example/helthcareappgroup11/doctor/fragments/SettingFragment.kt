package com.example.helthcareappgroup11.doctor.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import com.example.helthcareappgroup11.ActivityLogin
import com.example.helthcareappgroup11.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SettingFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var tvUserRole: TextView
    private lateinit var btnLogout: ImageView
    private lateinit var qaSection: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        tvUserRole = view.findViewById(R.id.tvUserRole)
        btnLogout = view.findViewById(R.id.btnLogout)
        qaSection = view.findViewById(R.id.qaSection)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        loadUserRole()
        setupQandA()

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

    private fun setupQandA() {
        val questionsAndAnswers = listOf(
            "How do I make an appointment?" to "To make an appointment, go to the 'Appointments' section in the app. Select the date and time that suits you, and confirm your booking. You will receive a confirmation notification.",
            "Can I cancel or reschedule?" to "Yes, you can cancel or reschedule an appointment. Go to 'My Appointments', select the appointment you want to change, and choose 'Cancel' or 'Reschedule'. Follow the prompts to complete the process.",
            "Is there a fee for using the appointment service?" to "Our appointment service is free of charge. However, there may be fees associated with certain medical services or consultations, which will be communicated during the booking process.",
            "How do I update my profile information?" to "To update your profile information, go to the 'Profile' section in the app. Click on 'Edit Profile', make the necessary changes, and save your updates. Your profile will be updated immediately."
        )

        questionsAndAnswers.forEach { (question, answer) ->
            val questionCardView = CardView(requireContext()).apply {
                radius = 12f
                cardElevation = 4f
                setContentPadding(16, 16, 16, 16)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(8, 8, 8, 8)
                }

                val linearLayout = LinearLayout(requireContext()).apply {
                    orientation = LinearLayout.VERTICAL
                }

                val answerTextView = TextView(requireContext()).apply {
                    text = answer
                    textSize = 16f
                    setPadding(8, 8, 8, 8)
                    visibility = View.GONE
                }

                val questionTextView = TextView(requireContext()).apply {
                    text = question
                    textSize = 18f
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
                    setPadding(8, 8, 8, 8)
                    setOnClickListener {
                        if (answerTextView.visibility == View.GONE) {
                            answerTextView.visibility = View.VISIBLE
                            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up, 0)
                        } else {
                            answerTextView.visibility = View.GONE
                            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
                        }
                    }
                }

                linearLayout.addView(questionTextView)
                linearLayout.addView(answerTextView)
                addView(linearLayout)
            }

            qaSection.addView(questionCardView)
        }
    }
}
