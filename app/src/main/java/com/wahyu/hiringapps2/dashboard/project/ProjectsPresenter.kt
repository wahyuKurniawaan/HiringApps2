package com.wahyu.hiringapps2.dashboard.project

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectsPresenter(private val coroutineScope: CoroutineScope, private val service: ProjectsApiService?) :
    ProjectsContract.Presenter {

    private var view: ProjectsContract.View? = null

    override fun bindToView(view: ProjectsContract.View) {
        this.view = view
    }

    override fun unBind() {
        this.view = null
    }

    override fun callProjectApi() {
        coroutineScope.launch {
            view?.showProgressBar()
            withContext(Dispatchers.IO) {
                service?.getAllProjectData()?.enqueue(object : Callback<ProjectsResponse> {

                    override fun onResponse(call: Call<ProjectsResponse>, response: Response<ProjectsResponse>) {
                        Log.d("android1", "on response : $response")
                        val list = response.body()?.data?.map {
                            ProjectsModel(it.id, it.name, it.description, it.price, it.duration)
                        } ?: listOf()
                        view?.addListProject(list)
                    }

                    override fun onFailure(call: Call<ProjectsResponse>, t: Throwable) {
                        Log.d("android1", "error : ${t.printStackTrace()}")
                    }

                })
            }
            view?.hideProgressBar()
        }
    }
}