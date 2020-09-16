package com.wahyu.hiringapps2.dashboard.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.activities.SignInActivity
import com.wahyu.hiringapps2.dashboard.FormEditProfileActivity
import com.wahyu.hiringapps2.dashboard.WebViewActivity
import com.wahyu.hiringapps2.util.SharedPreferencesUtil

class ProfileFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferencesUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.top_toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)


        sharedPref = SharedPreferencesUtil(requireContext())

        val buttonGithub = view.findViewById<Button>(R.id.buttonToGithub)
        buttonGithub.setOnClickListener {
            val intent = Intent(activity, WebViewActivity::class.java)
            startActivity(intent)
        }

        val textViewEditProfile = view.findViewById<TextView>(R.id.tvEditProfile)
        textViewEditProfile.setOnClickListener {
            val intent = Intent(activity, FormEditProfileActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.drawer_profile_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_faq -> {
                Toast.makeText(activity, "FAQ", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.nav_help -> {
                Toast.makeText(activity, "Help", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.nav_about_us -> {
                Toast.makeText(activity, "About Us", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.nav_logout -> {
                Toast.makeText(activity, "Logout", Toast.LENGTH_SHORT).show()
                sharedPref.clear()
                val intent = Intent(activity, SignInActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }


}