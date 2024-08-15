import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.helthcareappgroup11.R
import com.example.helthcareappgroup11.models.AppointmentSlots
import com.example.helthcareappgroup11.user.activities.SlotDetailActivity

class AppointmentSlotsAdapter(private val slotsList: List<AppointmentSlots>) :
    RecyclerView.Adapter<AppointmentSlotsAdapter.SlotViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_appointment_slot, parent, false)
        return SlotViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlotViewHolder, position: Int) {
        val slot = slotsList[position]
        holder.date.text = slot.date
        holder.availability.text = slot.availability

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, SlotDetailActivity::class.java)
            intent.putExtra("slot_id", slot.id)
            intent.putExtra("doctorId",slot.doctorId)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return slotsList.size
    }

    class SlotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.tvDate)
        val availability: TextView = itemView.findViewById(R.id.tvAvailability)
    }
}
