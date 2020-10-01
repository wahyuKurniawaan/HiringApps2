package com.wahyu.hiringapps2.retrofit

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.databinding.ActivityLearnRetrofitBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LearnRetrofitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLearnRetrofitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_learn_retrofit)

        binding.rvProject.adapter = ProjectAdapter()
        binding.rvProject.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        useRetrofitToCallAPI()
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
                (binding.rvProject.adapter as ProjectAdapter).addList(list)
            }
        })
        Log.d("test", "service = " + service.getAllProjectData().toString())
    }
}