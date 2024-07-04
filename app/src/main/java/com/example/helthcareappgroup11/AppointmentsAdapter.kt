import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.helthcareappgroup11.R

class AppointmentsAdapter(private val appointments: List<Appointment>) :
    RecyclerView.Adapter<AppointmentsAdapter.AppointmentViewHolder>() {

    // ViewHolder inner class to hold references to views
    inner class AppointmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val doctorImage: ImageView = itemView.findViewById(R.id.doctor_image)
        val doctorName: TextView = itemView.findViewById(R.id.doctor_name)
        val doctorExpertise: TextView = itemView.findViewById(R.id.doctor_expertise)
        val appointmentDateTime: TextView = itemView.findViewById(R.id.appointment_date_time)
    }

    // Create ViewHolder instances
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_appointment_card, parent, false)
        return AppointmentViewHolder(itemView)
    }

    // Bind data to ViewHolder
    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = appointments[position]

        holder.doctorImage.setImageResource(appointment.doctorImage)
        holder.doctorName.text = appointment.doctorName
        holder.doctorExpertise.text = appointment.doctorExpertise
        holder.appointmentDateTime.text = appointment.appointmentDateTime
    }

    // Return number of items in the list
    override fun getItemCount(): Int {
        return appointments.size
    }
}
