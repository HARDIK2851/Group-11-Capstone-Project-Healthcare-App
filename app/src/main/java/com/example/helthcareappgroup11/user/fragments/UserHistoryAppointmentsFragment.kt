import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helthcareappgroup11.R

class UserHistoryAppointmentsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history_appointments, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_history)

        // Set up the RecyclerView with a LinearLayoutManager
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Example list of history appointments (replace with your actual data)
        val historyAppointments = listOf(
            AppointmentUser(
                R.drawable.baseline_account_circle_24,
                "Dr. John Smith",
                "Cardiologist",
                "2024-07-01 10:00 AM"
            ),
            AppointmentUser(
                R.drawable.baseline_account_circle_24,
                "Dr. Emily Brown",
                "Dermatologist",
                "2024-07-03 02:30 PM"
            ),
            AppointmentUser(
                R.drawable.baseline_account_circle_24,
                "Dr. Michael Johnson",
                "Pediatrician",
                "2024-07-05 09:15 AM"
            )
            // Add more appointments as needed
        )

        // Create an instance of the AppointmentsAdapter and set it to the RecyclerView
        val adapter = AppointmentsAdapter(historyAppointments)
        recyclerView.adapter = adapter

        return view
    }
}
