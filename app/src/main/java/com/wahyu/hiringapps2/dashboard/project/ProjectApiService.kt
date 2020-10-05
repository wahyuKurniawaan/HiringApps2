package com.wahyu.hiringapps2.dashboard.project

import retrofit2.Call
import retrofit2.http.GET

interface ProjectApiService {

    @GET("project")
    fun getAllProjectData() : Call<ProjectResponse>
}