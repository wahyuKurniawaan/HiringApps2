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

class OffersFragment : Fragment() {

    private lateinit var binding: FragmentOffersBinding
    private lateinit var viewModel: OffersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_offers, container, false)

        val service = ApiClient.getApiClient(this.requireContext())?.create(OffersApiService::class.java)
        viewModel = ViewModelProvider(this).get(OffersViewModel::class.java)
        if (service != null) {
            viewModel.setOffersService(service)
        }

        binding.recycleView.adapter = OffersAdapter()
        binding.recycleView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        viewModel.callOffersApi()
        subscribeLiveData()
        return  binding.root
    }

    private fun subscribeLiveData() {
        viewModel.listOffersLiveData.observe(viewLifecycleOwner, {
            (binding.recycleView.adapter as OffersAdapter).addList(it)
        })

        viewModel.isLoadingLiveData.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

    }

}