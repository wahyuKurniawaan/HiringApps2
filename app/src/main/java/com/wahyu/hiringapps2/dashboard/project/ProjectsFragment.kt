package com.wahyu.hiringapps2.dashboard.project

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
import com.wahyu.hiringapps2.databinding.FragmentProjectsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProjectsFragment : Fragment() {

    private lateinit var binding: FragmentProjectsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_projects, container, false)

        binding.recycleView.adapter = ProjectAdapter()
        binding.recycleView.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)

        useRetrofitToCallAPI()
        return binding.root
    }

    private fun useRetrofitToCallAPI() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://34.234.66.114:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProjectApiService::class.java)

        binding.progressBar.visibility = View.VISIBLE
        service.getAllProjectData().enqueue(object : Callback<ProjectResponse> {
            override fun onFailure(call: Call<ProjectResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Log.d("test, on failure = ", t.message ?: "error")

            }

            override fun onResponse(
                call: Call<ProjectResponse>,
                response: Response<ProjectResponse>
            ) {
                binding.progressBar.visibility = View.GONE
                val list = response.body()?.data?.map {
                    ProjectModel(it.id, it.name, it.description, it.price, it.duration)
                } ?: listOf()
                (binding.recycleView.adapter as ProjectAdapter).addList(list)
            }
        })
        Log.d("test", "service = " + service.getAllProjectData().toString())
    }
}