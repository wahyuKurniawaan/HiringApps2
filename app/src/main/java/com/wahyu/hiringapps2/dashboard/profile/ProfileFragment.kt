package com.wahyu.hiringapps2.dashboard.profile

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.dashboard.profile.editProfile.EditProfileActivity
import com.wahyu.hiringapps2.dashboard.profile.room.NoteListActivity
import com.wahyu.hiringapps2.databinding.FragmentProfileBinding
import com.wahyu.hiringapps2.login.SignInActivity
import com.wahyu.hiringapps2.util.ApiClient
import com.wahyu.hiringapps2.util.SharedPreferencesUtil

class ProfileFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferencesUtil
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private fun getPhotoImage(file: String) : String = "http://34.234.66.114:8080/uploads/$file"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        sharedPref = SharedPreferencesUtil(requireContext())

        val service = ApiClient.getApiClient(this.requireContext())?.create(ProfileApiService::class.java)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.setSharedPref(sharedPref)
        if (service != null) {
            viewModel.setProfileService(service)
        }

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.topToolbar)
        viewModel.callProfileApi()
        subscribeLiveData()

        binding.btnEditProfile.setOnClickListener {
            val intent = Intent(this.requireContext(), EditProfileActivity::class.java)
            startActivity(intent)
        }
        return binding.root
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

            R.id.nav_test_room -> {
                val intent = Intent(activity, NoteListActivity::class.java)
                startActivity(intent)
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

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.isProfileLiveData.observe(viewLifecycleOwner, {
            if (it) {
                val data = viewModel.listLiveData.value?.firstOrNull()

                if (data?.profileImage.isNullOrEmpty()) binding.imageProfile.setImageResource(R.drawable.ic_person)
                else Picasso.get().load(getPhotoImage(data?.profileImage!!)).into(binding.imageProfile)

                binding.tvName.text = data?.name ?: "Full Name"
                binding.tvJobTitle.text = data?.roleJob ?: "Role Job"
                binding.tvCity.text = data?.city ?: "City"
                binding.tvDescription.text = data?.description ?: "Description"
                binding.tvEmail.text = data?.email ?: "Email"
                binding.tvInstagram.text = data?.instagramLink ?: "Instagram Link"
                binding.tvLinkedin.text = data?.linkedinLink ?: "Linkedin Link"

                binding.tvLinkedin.setOnClickListener {
                    val intent = Intent(this.requireContext(), WebViewActivity()::class.java)
                    startActivity(intent)
                }
                binding.tvInstagram.setOnClickListener {
                    val intent = Intent(this.requireContext(), WebViewActivity()::class.java)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(this.requireContext(), "error", Toast.LENGTH_SHORT).show()
            }
        })
    }

}