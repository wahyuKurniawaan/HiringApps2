package com.wahyu.hiringapps2.dashboard.offers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.databinding.FragmentOffersBinding
import com.wahyu.hiringapps2.util.ApiClient
import com.wahyu.hiringapps2.util.SharedPreferencesUtil

class OffersFragment : Fragment() {

    private lateinit var binding: FragmentOffersBinding
    private lateinit var viewModel: OffersViewModel
    private lateinit var sharedPref: SharedPreferencesUtil

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_offers, container, false)
        sharedPref = SharedPreferencesUtil(this.requireContext())

        val service = ApiClient.getApiClient(this.requireContext())?.create(OffersApiService::class.java)
        viewModel = ViewModelProvider(this).get(OffersViewModel::class.java)
        if (service != null) {
            viewModel.setOffersService(service)
        }
        viewModel.setSharedPref(sharedPref)

        binding.recycleView.adapter = OffersAdapter()
        binding.recycleView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        viewModel.callOffersApi()
        subscribeLiveData()
        return binding.root
    }

    private fun subscribeLiveData() {
        viewModel.listOffersLiveData.observe(viewLifecycleOwner, {
            (binding.recycleView.adapter as OffersAdapter).addList(it)
        })
        viewModel.isLoadingLiveData.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
        viewModel.isOffersLiveData.observe(viewLifecycleOwner, {
            binding.linearLayoutNoResult.visibility = if (it) View.GONE else View.VISIBLE
        })
    }

}