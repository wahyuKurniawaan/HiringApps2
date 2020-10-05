package com.wahyu.hiringapps2.dashboard.search

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.databinding.FragmentSearchBinding
import com.wahyu.hiringapps2.util.HeaderInterceptor
import com.wahyu.hiringapps2.util.SharedPreferencesUtil
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var sharedPref: SharedPreferencesUtil

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        sharedPref = SharedPreferencesUtil(this.requireContext())

        binding.recycleView.adapter = SearchAdapter()
        binding.recycleView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        binding.btnSortMenu.setOnClickListener {
            setListDialog()

        }
        return binding.root
    }

    private fun useRetrofitToCallAPI(field: Int, value: String) {
        binding.progressBar.visibility = View.VISIBLE
        val okHttpClient = OkHttpClient.Builder().addInterceptor(HeaderInterceptor(this.requireContext())).build()
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://34.234.66.114:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(SearchApiService::class.java)
        fun getResponse(response: Call<SearchResponse>) {
            response.enqueue(object :
                Callback<SearchResponse> {
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    Log.d("test, on failure = ", t.message ?: "error")
                }

                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    binding.progressBar.visibility = View.GONE
                    val list = response.body()?.data?.map {
                        SearchModel(
                            it.id,
                            it.idAccount,
                            it.idPortofolio,
                            it.idSkill,
                            it.email,
                            it.name,
                            it.jobTitle,
                            it.statusJob,
                            it.address,
                            it.city,
                            it.workplace,
                            it.image,
                            it.description,
                            it.createdAt,
                            it.updatedAt
                        )
                    } ?: listOf()
                    (binding.recycleView.adapter as SearchAdapter).addList(list)
                }
            })
        }

        when (field) {
            0 -> getResponse(service.getProfileJobSeekerByNameRequest(value))
            1 -> getResponse(service.getProfileJobSeekerBySkillRequest(value))
            2 -> getResponse(service.getProfileJobSeekerByLocationRequest(value))
            3 -> getResponse(service.getProfileJobSeekerByJobTitleRequest(value))
            4 -> getResponse(service.getProfileJobSeekerByStatusJobRequest(value))
        }
    }

    private fun setListDialog() {
        val builder = AlertDialog.Builder(this.requireContext())
        builder.setTitle(R.string.choose_a_sort_method)
            .setItems(R.array.sort_methods) { dialog, which ->
                when (which) {
                    0 -> {
                        binding.progressBar.visibility = View.VISIBLE
                        useRetrofitToCallAPI(0, binding.etSearch.text.toString())
                        binding.linearLayoutDefault.visibility = View.GONE
                    }
                    1 -> {
                        binding.progressBar.visibility = View.VISIBLE
                        useRetrofitToCallAPI(1, binding.etSearch.text.toString())
                        binding.linearLayoutDefault.visibility = View.GONE
                    }
                    2 -> {
                        binding.progressBar.visibility = View.VISIBLE
                        useRetrofitToCallAPI(2, binding.etSearch.text.toString())
                        binding.linearLayoutDefault.visibility = View.GONE
                    }
                    3 -> {
                        binding.progressBar.visibility = View.VISIBLE
                        useRetrofitToCallAPI(3, binding.etSearch.text.toString())
                        binding.linearLayoutDefault.visibility = View.GONE
                    }
                    4 -> {
                        binding.progressBar.visibility = View.VISIBLE
                        useRetrofitToCallAPI(4, binding.etSearch.text.toString())
                        binding.linearLayoutDefault.visibility = View.GONE
                    }
                }
            }
        builder.create()
        builder.show()
    }
}