package com.wahyu.hiringapps2.dashboard.search

import android.app.AlertDialog
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
import com.wahyu.hiringapps2.databinding.FragmentSearchBinding
import com.wahyu.hiringapps2.util.ApiClient
import com.wahyu.hiringapps2.util.SharedPreferencesUtil

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var sharedPref: SharedPreferencesUtil

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        sharedPref = SharedPreferencesUtil(this.requireContext())

        val service = ApiClient.getApiClient(this.requireContext())?.create(SearchApiService::class.java)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        if (service != null) {
            viewModel.setSearchService(service)
        }

        binding.recycleView.adapter = SearchAdapter()
        binding.recycleView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        binding.btnSortMenu.setOnClickListener {
            setListDialog()

        }
        subscribeLiveData()
        return binding.root
    }

    private fun setListDialog() {
        val builder = AlertDialog.Builder(this.requireContext())
        builder.setTitle(R.string.choose_a_sort_method)
            .setItems(R.array.sort_methods) { dialog, which ->
                    viewModel.callSearchApi(which, binding.etSearch.text.toString())
            }
        builder.create()
        builder.show()
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.listLiveData.observe(viewLifecycleOwner, {
            (binding.recycleView.adapter as SearchAdapter).addList(it)
        })

        viewModel.isSearchLiveData.observe(viewLifecycleOwner, {
            if (it) {
                binding.linearLayoutNoResult.visibility = View.GONE
            } else {
                binding.linearLayoutNoResult.visibility = View.VISIBLE
            }
        })
    }
}