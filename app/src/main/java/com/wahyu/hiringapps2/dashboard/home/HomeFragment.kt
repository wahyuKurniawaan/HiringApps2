package com.wahyu.hiringapps2.dashboard.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.databinding.FragmentHomeBinding
import com.wahyu.hiringapps2.util.ApiClient
import com.wahyu.hiringapps2.util.SharedPreferencesUtil

class HomeFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var sharedPref: SharedPreferencesUtil


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sharedPref = SharedPreferencesUtil(requireContext())
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        val service = ApiClient.getApiClient(this.requireContext())?.create(HomeApiService::class.java)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        if (service != null) {
            viewModel.setHomeService(service)
        }

        binding.recycleView.adapter = HomeAdapter()
        binding.recycleView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        val adapter = ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.job_titles,
            R.layout.spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = this

        (requireActivity() as AppCompatActivity)
            .setSupportActionBar(binding.topToolbar)

        subscribeLiveData()
        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val text = parent?.getItemAtPosition(position).toString()
        Log.d("spinner-home", "get text = $text")
        when (text) {
            "Android Developer" -> {
                viewModel.callHomeApi(text)
                Log.d("spinner-home", "android developer selected = $text")
            }
            else -> {
                viewModel.callHomeApi(text)
                Log.d("spinner-home", "android developer selected = $text")
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    private fun subscribeLiveData() {
        viewModel.listLiveData.observe(viewLifecycleOwner, {
            (binding.recycleView.adapter as HomeAdapter).addList(it)
        })
        viewModel.isLoadingLiveData.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
    }
}