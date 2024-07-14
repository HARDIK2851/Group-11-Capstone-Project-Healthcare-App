import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.helthcareappgroup11.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.nav_home -> selectedFragment = HomeFragmentUser()
                R.id.nav_appointment -> selectedFragment = AppointmentFragment()
                R.id.nav_notification -> selectedFragment = NotificationFragment()
                R.id.nav_account -> selectedFragment = AccountFragmentUser()
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.container, selectedFragment).commit()
            }
            true
        }

        // Set default selection
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.nav_home
        }
    }
}
