package com.example.helthcareappgroup11.doctor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.models.PatientHistory
import de.hdodenhof.circleimageview.CircleImageView


// have tp use static list for now
//class PatientHistoryAdapter(options: FirebaseRecyclerOptions<PatientHistory>) :
//    FirebaseRecyclerAdapter<PatientHistory, PatientHistoryAdapter.PatientViewHolder>(options)

class PatientHistoryAdapter(private val patientHistories: List<PatientHistory>) :
    RecyclerView.Adapter<PatientHistoryAdapter.PatientViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PatientViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patientHistory = patientHistories[position]
        holder.patientImg.setImageResource(patientHistory.patientImg)
        holder.patientName.text = patientHistory.patientName
    }

    override fun getItemCount(): Int {
        return patientHistories.size
    }

    class PatientViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
       RecyclerView.ViewHolder(inflater.inflate(R.layout.patient_hisory_layout, parent, false))
    {
           val patientName : TextView = itemView.findViewById(R.id.patientName);
            val patientImg : CircleImageView = itemView.findViewById(R.id.patientImg);
    }

}

