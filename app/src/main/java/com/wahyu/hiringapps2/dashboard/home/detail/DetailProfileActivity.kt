package com.wahyu.hiringapps2.dashboard.home.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.dashboard.home.detail.hire.HireActivity
import com.wahyu.hiringapps2.databinding.ActivityDetailProfileBinding
import com.wahyu.hiringapps2.util.ApiClient
import com.wahyu.hiringapps2.util.BaseActivity
import com.wahyu.hiringapps2.util.SharedPreferencesUtil

class DetailProfileActivity : BaseActivity() {

    private lateinit var sharedPref: SharedPreferencesUtil
    private lateinit var binding: ActivityDetailProfileBinding
    private lateinit var viewModel: DetailProfileViewModel

    private fun getPhotoImage(file: String) : String = "http://34.234.66.114:8080/uploads/$file"
    override fun initView() {

    }

    override fun initListener() {
        binding.btnHire.setOnClickListener {
            val intent = Intent(this, HireActivity::class.java)
            intent.putExtra("name", viewModel.detailProfileLiveData.value?.first()?.name)
            intent.putExtra("id", viewModel.detailProfileLiveData.value?.first()?.id)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_detail_profile)
        sharedPref = SharedPreferencesUtil(this)

        val service = ApiClient.getApiClient(this)?.create(DetailProfileApiService::class.java)
        viewModel = ViewModelProvider(this).get(DetailProfileViewModel::class.java)
        viewModel.setSharedPref(sharedPref)
        if (service != null) {
            viewModel.setDetailProfileService(service)
        }

        viewModel.callDetailProfileApi(intent.getIntExtra("id", 1))
        initView()
        initListener()
        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        viewModel.isDetailProfileLiveData.observe(this, {
            val data = viewModel.detailProfileLiveData.value?.firstOrNull()

            if (data?.image.isNullOrEmpty()) binding.ivProfileImage.setImageResource(R.drawable.ic_person)
            else Picasso.get().load(getPhotoImage(data?.image!!)).into(binding.ivProfileImage)

            binding.tvName.text = data?.name ?: "Full Name"
            binding.tvJobTitle.text = data?.jobTitle ?: "Job Title"
            binding.tvStatusJob.text = data?.statusJob ?: "Status Job"
            binding.tvAddress.text = data?.address ?: "Address"
            binding.tvCity.text = data?.city ?: "City"
            binding.tvDescription.text = data?.description ?: "Description"
            binding.tvSkillValue.text = data?.idSkill ?: "Skill Job Seeker"
        })

        viewModel.isLoadingLiveData.observe(this, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

    }
}