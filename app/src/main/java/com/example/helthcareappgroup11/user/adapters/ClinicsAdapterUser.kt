package com.example.helthcareappgroup11.user.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.admin.AdminClinicDetails
import com.example.helthcareappgroup11.models.Clinics
import com.example.helthcareappgroup11.user.activities.UserClinicDetails

class ClinicsAdapterUser(private val clinicsList: List<Clinics>) :
    RecyclerView.Adapter<ClinicsAdapterUser.ClinicViewHolder>() {

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
                val intent = Intent(itemView.context, UserClinicDetails::class.java)
                intent.putExtra("CLINIC_NAME", clinic.clinicName)
                intent.putExtra("STREET_ADDRESS", clinic.streetAddress)
                intent.putExtra("CITY", clinic.city)
                intent.putExtra("ZIPCODE", clinic.zipcode)
                intent.putExtra("CONTACT_NUMBER", clinic.contactNumber)
                intent.putExtra("CLINIC_IMAGE_URL", clinic.photoUrls)
                intent.putExtra("DOCTOR_ID", clinic.doctorId)

                itemView.context.startActivity(intent)
            }
        }

        fun bind(clinic: Clinics) {
            textClinicName.text = clinic.clinicName ?: "Unknown"
            textAddress.text = "${clinic.streetAddress ?: ""}, ${clinic.city ?: ""} ${clinic.zipcode ?: ""}"
            textContactNumber.text = clinic.contactNumber ?: "Contact Number not available"
        }
    }
}
