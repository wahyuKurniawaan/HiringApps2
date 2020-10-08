package com.wahyu.hiringapps2.dashboard.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.dashboard.profile.room.NoteListActivity
import com.wahyu.hiringapps2.databinding.FragmentProfileBinding
import com.wahyu.hiringapps2.login.SignInActivity
import com.wahyu.hiringapps2.util.ApiClient
import com.wahyu.hiringapps2.util.SharedPreferencesUtil
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferencesUtil
    private lateinit var binding: FragmentProfileBinding
    private lateinit var coroutineScope: CoroutineScope
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

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.topToolbar)
        callSignApi()
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

    private fun callSignApi() {
        val service = ApiClient.getApiClient(this.requireContext())?.create(ProfileApiService::class.java)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service?.getProfileRecruiterByEmailRequest("wahyukurniawaan@gmail.com")?.enqueue(object :
                        retrofit2.Callback<ProfileResponse> {
                        override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                            Log.d("onFailure", t.message.toString())
                            Log.d("profile", call.toString())
                        }

                        override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                            Log.d("onResponse", response.toString())
                            Log.d("profile", response.body()?.data.toString())
                            Log.d("profile", response.body()?.data!![0].name!!)
                            binding.tvName.text = response.body()?.data!![0].name!!
                            Picasso.get().load(getPhotoImage(response.body()?.data!![0].profileImage!!)).into(binding.imageProfile)
                            binding.tvJobTitle.text = response.body()?.data!![0].roleJob!!
                            binding.tvCity.text = response.body()?.data!![0].city!!
                            binding.tvDescription.text = response.body()?.data!![0].description!!
                            binding.tvEmail.text = response.body()?.data!![0].email!!
                            binding.tvInstagram.text = response.body()?.data!![0].instagramLink!!
                            binding.tvLinkedin.text = response.body()?.data!![0].linkedinLink!!

                        }
                    })
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }


}