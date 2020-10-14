package com.wahyu.hiringapps2.dashboard.profile.editProfile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.databinding.ActivityFormEditProfileBinding
import com.wahyu.hiringapps2.util.ApiClient
import com.wahyu.hiringapps2.util.BaseActivity
import com.wahyu.hiringapps2.util.Key
import com.wahyu.hiringapps2.util.SharedPreferencesUtil

class EditProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityFormEditProfileBinding
    private lateinit var sharedPref: SharedPreferencesUtil
    private lateinit var viewModel: EditProfileViewModel
    private fun getPhotoImage(file: String) : String = "http://34.234.66.114:8080/uploads/$file"

    override fun initView() {
        this.setSupportActionBar(binding.topToolbar)
    }

    override fun initListener() {
        binding.buttonCancel.setOnClickListener {
            onBackPressed()
        }
        binding.buttonSave.setOnClickListener {
            viewModel.callEditProfileDataApi(
                sharedPref.getInt(Key.PREF_USER_ID)!!,
                binding.etInputCompanyField.text.toString(),
                binding.etInputCity.text.toString(),
                binding.etInputDescription.text.toString(),
                binding.etInputInstagram.text.toString(),
                binding.etInputLinkedin.text.toString(),
                sharedPref.getString(Key.PREF_FULL_NAME)!!,
                sharedPref.getString(Key.PREF_EMAIL)!!,
                sharedPref.getString(Key.PREF_PASSWORD)!!,
                "Arkademy",
                "CEO",
                "089630033462"
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_form_edit_profile)
        sharedPref = SharedPreferencesUtil(this)

        val service = ApiClient.getApiClient(this)?.create(EditProfileApiService::class.java)
        viewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)
        viewModel.setSharedPref(sharedPref)
        if (service != null) viewModel.setEditProfileService(service)
        viewModel.callGetProfileDataApi()
        subscribeLiveData()

        initView()
        initListener()
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.isEditProfileLiveData.observe(this, {
            if (it) {
                val data = viewModel.listLiveData.value?.firstOrNull()
                binding.tvName.text = data?.name ?: "Full Name"
                if (data?.profileImage.isNullOrEmpty()) {
                    binding.imageProfile.setImageResource(R.drawable.ic_person)
                } else {
                    Picasso.get().load(getPhotoImage(data?.profileImage!!)).into(binding.imageProfile)
                }
                binding.tvJobTitle.text = data?.roleJob ?: "Role Job"
                binding.tvCity.text = data?.city ?: "City"
                binding.tvDescription.text = data?.description ?: "Description"
                binding.etInputInstagram.text = data?.instagramLink?.toEditable() ?: "Instagram Link".toEditable()
                binding.etInputLinkedin.text = data?.linkedinLink?.toEditable() ?: "Linkedin Link".toEditable()

            } else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}