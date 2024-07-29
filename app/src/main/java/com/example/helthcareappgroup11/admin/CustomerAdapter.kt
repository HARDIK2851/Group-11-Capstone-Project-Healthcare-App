package com.example.helthcareappgroup11.admin

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.models.Customers

class CustomerAdapter(
    private val context: Context,
    private val customerList: MutableList<Customers> = mutableListOf()
) : RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photoImageView: ImageView = itemView.findViewById(R.id.photoImageView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val ageTextView: TextView = itemView.findViewById(R.id.ageTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.customer_row_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val customer = customerList[position]

        // Load photo with Glide or Picasso (example with Glide)
//        Glide.with(context)
//            .load(customer.photoUrl)
//            .into(holder.photoImageView)

        holder.nameTextView.text = customer.name
        holder.ageTextView.text = "Age: ${customer.age}"

        holder.itemView.setOnClickListener {
            val intent = Intent(context, AdminCustomerDetails::class.java).apply {
                putExtra("NAME", customer.name)
                putExtra("AGE", customer.age)
                putExtra("EMAIL", customer.email)
                putExtra("PHONE", customer.phone)
                putExtra("HEIGHT", customer.height)
                putExtra("PHOTO_URL", customer.photoUrl)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = customerList.size
}
