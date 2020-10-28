package com.wahyu.hiringapps2.dashboard.project

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ProjectsApiService {

    @GET("project")
    suspend fun getAllProjectData(@Query("search[user_id]") userId: Int?) : ProjectsResponse

    @POST("project")
    suspend fun postProject(
        @Part("user_id") userId: RequestBody?,
        @Part("name") projectName: RequestBody?,
        @Part("project_description") projectDescription: RequestBody?,
        @Part("price") projectPrice: RequestBody?,
        @Part("duration") projectDuration: RequestBody?,
        @Part image: MultipartBody.Part?
    ) : ProjectsResponse.addProjectResponse
}
