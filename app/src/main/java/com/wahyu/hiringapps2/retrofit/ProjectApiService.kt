package com.wahyu.hiringapps2.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface ProjectApiService {

    @GET("project")
    fun getAllProjectData() : Call<ProjectResponse>
}