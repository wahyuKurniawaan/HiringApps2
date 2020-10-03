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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.auth.ApiClient
import com.wahyu.hiringapps2.auth.HeaderInterceptor
import com.wahyu.hiringapps2.databinding.FragmentHomeBinding
import com.wahyu.hiringapps2.util.KeySharedPreferences
import com.wahyu.hiringapps2.util.SharedPreferencesUtil
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var sharedPref: SharedPreferencesUtil


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sharedPref = SharedPreferencesUtil(requireContext())
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
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
        return binding.root
    }

    private fun callSignApi() {
        Log.d("hometest","callSignApi() is running")
        val service = ApiClient.getApiClient(this.requireContext())?.create(HomeApiService::class.java)
        Log.d("hometest","val service")
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        coroutineScope.launch {
            Log.d("hometest","coroutine launched")
            val response = withContext(Dispatchers.IO) {
                Log.d("hometest", "callApi : ${Thread.currentThread().name}")
                try {
                    service?.getProfileJobSeekerRequest()
                    Log.d("hometest","try service")
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            Log.d("hometest","response = $response")
            if (response is HomeResponse) {
                Log.d("hometest", "data = ${response.data}")
                if (response.success) {
                    val list = response.data.map {
                        HomeModel(it.id, it.idAccount, it.idPortofolio, it.idSkill, it.email, it.name, it.jobTitle, it.statusJob,
                        it.address, it.city, it.workplace, it.image, it.description, it.createdAt, it.updatedAt)
                    } ?: listOf()
                    (binding.recycleView as HomeAdapter).addList(list)
                } else {
                    Log.d("hometest", response.message ?: "Error")
                }
            }
        }
    }

    private fun useRetrofitToCallAPI(jobTitle: String) {
        binding.progressBar.visibility = View.VISIBLE
        val okHttpClient = OkHttpClient.Builder().addInterceptor(HeaderInterceptor(this.requireContext())).build()
        val getService =
        Log.d("test", "token = ${KeySharedPreferences.PREF_TOKEN}")
        Log.d("test", "token = ${sharedPref.getString(KeySharedPreferences.PREF_TOKEN)}")
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://34.234.66.114:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(HomeApiService::class.java)

        if (jobTitle.equals("Web Developer")) {
            service.getProfileJobSeekerByJobTitleRequest(jobTitle).enqueue(object : Callback<HomeResponse> {
                override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    Log.d("test, on failure = ", t.message ?: "error")

                }

                override fun onResponse(
                    call: Call<HomeResponse>,
                    response: Response<HomeResponse>
                ) {
                    binding.progressBar.visibility = View.GONE
                    val list = response.body()?.data?.map {
                        HomeModel(it.id, it.idAccount, it.idPortofolio, it.idSkill, it.email, it.name, it.jobTitle, it.statusJob,
                            it.address, it.city, it.workplace, it.image, it.description, it.createdAt, it.updatedAt)
                    } ?: listOf()

                    (binding.recycleView.adapter as HomeAdapter).addList(list)
                    Log.d("test", "response = ${response.body()}")
                }
            })
        } else {
            service.getProfileJobSeekerByJobTitleRequest(jobTitle).enqueue(object : Callback<HomeResponse> {
                override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    Log.d("test, on failure = ", t.message ?: "error")

                }

                override fun onResponse(
                    call: Call<HomeResponse>,
                    response: Response<HomeResponse>
                ) {
                    binding.progressBar.visibility = View.GONE
                    val list = response.body()?.data?.map {
                        HomeModel(it.id, it.idAccount, it.idPortofolio, it.idSkill, it.email, it.name, it.jobTitle, it.statusJob,
                            it.address, it.city, it.workplace, it.image, it.description, it.createdAt, it.updatedAt)
                    } ?: listOf()

                    (binding.recycleView.adapter as HomeAdapter).addList(list)
                    Log.d("test", "response = ${response.body()}")
                }
            })
        }


        Log.d("test", "service = " + service.getProfileJobSeekerRequest().toString())

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val text = parent?.getItemAtPosition(position).toString()
        Log.d("spinner-home", "get text = $text")
        when (text) {
            "Android Developer" -> {
                useRetrofitToCallAPI(text)
                Log.d("spinner-home", "android developer selected = $text")
            }
            else -> {
                useRetrofitToCallAPI(text)
                Log.d("spinner-home", "android developer selected = $text")
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}