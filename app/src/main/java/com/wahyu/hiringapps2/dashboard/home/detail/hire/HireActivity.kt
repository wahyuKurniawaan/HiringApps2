package com.wahyu.hiringapps2.dashboard.home.detail.hire

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.databinding.ActivityHireBinding
import com.wahyu.hiringapps2.util.ApiClient
import com.wahyu.hiringapps2.util.BaseActivity
import com.wahyu.hiringapps2.util.SharedPreferencesUtil

class HireActivity : BaseActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var sharedPref: SharedPreferencesUtil
    private lateinit var binding: ActivityHireBinding
    private lateinit var viewModel: HireViewModel

    private fun setTitle(name: String): String = "Contact $name"
    private fun setDescription(name: String): String =
        "select your project then give your contact so that $name can reach you"

    override fun initView() {
        binding.tvTitle.text = setTitle(intent.getStringExtra("name")!!)
        binding.tvDescription.text = setDescription(intent.getStringExtra("name")!!)
    }

    override fun initListener() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hire)
        sharedPref = SharedPreferencesUtil(this)

        val service = ApiClient.getApiClient(this)?.create(HireApiService::class.java)
        viewModel = ViewModelProvider(this).get(HireViewModel::class.java)
        viewModel.setSharedPref(sharedPref)
        if (service != null) {
            viewModel.setHireService(service)
        }

        viewModel.callHireApi()

        initView()
        initListener()
        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        viewModel.listProjectLiveData.observe(this, {
            val data = it?.map { it.name }
            val adapter = ArrayAdapter(
                this,
                R.layout.spinner_name_project, data!!.toMutableList()
            )
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
            binding.spinner.onItemSelectedListener = this
        })
        viewModel.isLoadingLiveData.observe(this, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val projectName = parent?.getItemAtPosition(position).toString()

        binding.btnHire.setOnClickListener {
            val list = viewModel.listProjectLiveData.value
            val idProject = list?.get(list.indexOf(list.find { it.name == projectName }))?.id ?: 0
            if (idProject != 0) {
                viewModel.callOffersApi(intent.getIntExtra("id", 0), idProject)
                Toast.makeText(this,
                    "successfully offered the project to ${intent.getStringExtra("name")}",
                    Toast.LENGTH_LONG).show()
                finish()
            } else Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}