package com.wahyu.hiringapps2.dashboard.project

import retrofit2.Call
import retrofit2.http.GET

interface ProjectsApiService {

    @GET("project")
    fun getAllProjectData() : Call<ProjectsResponse>
}