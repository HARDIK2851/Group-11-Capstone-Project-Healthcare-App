package com.example.helthcareappgroup11.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.objectClasses.Clinics

class ClinicsAdapter(private val clinicsList: List<Clinics>) :
    RecyclerView.Adapter<ClinicsAdapter.ClinicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClinicViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.clinic_list_item, parent, false)
        return ClinicViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ClinicViewHolder, position: Int) {
        val currentItem = clinicsList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return clinicsList.size
    }

    inner class ClinicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textClinicName: TextView = itemView.findViewById(R.id.textClinicName)
        private val textAddress: TextView = itemView.findViewById(R.id.textAddress)
        private val textContactNumber: TextView = itemView.findViewById(R.id.textContactNumber)

        init {
            itemView.setOnClickListener {
                val clinic = clinicsList[adapterPosition]
                // Example action: Navigate to detail page with clinic details
//                val action = doctorClinicUserFragmentDirections.navigateToClinicDetail(clinic.clinicName)
//                itemView.findNavController().navigate(action)
            }
        }

        fun bind(clinic: Clinics) {
            textClinicName.text = clinic.clinicName ?: "Unknown"
            textAddress.text = "${clinic.streetAddress ?: ""}, ${clinic.city ?: ""} ${clinic.zipcode ?: ""}"
            textContactNumber.text = clinic.contactNumber ?: "Contact Number not available"
        }
    }
}
