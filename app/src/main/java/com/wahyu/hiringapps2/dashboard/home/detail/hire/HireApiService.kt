package com.wahyu.hiringapps2.dashboard.home.detail.hire

import com.wahyu.hiringapps2.dashboard.project.ProjectsResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface HireApiService {

    @GET("project")
    suspend fun getAllProjectData() : ProjectsResponse

    @FormUrlEncoded
    @POST("offers")
    suspend fun setOffersData(
        @Field("id_profile_job_seeker") IdJobSeeker: Int?,
        @Field("id_project") idProject: Int?
    ) : PostOffersResponse

    data class PostOffersResponse(val success: Boolean, val message: String?)
}