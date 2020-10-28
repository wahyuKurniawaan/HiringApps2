package com.wahyu.hiringapps2.dashboard.home.detail.hire

import com.wahyu.hiringapps2.dashboard.project.ProjectsResponse
import retrofit2.http.*

interface HireApiService {

    @GET("project")
    suspend fun getAllProjectData(@Query("search[user_id]") userId: Int?) : ProjectsResponse

    @FormUrlEncoded
    @POST("offers")
    suspend fun setOffersData(
        @Field("id_profile_job_seeker") IdJobSeeker: Int?,
        @Field("id_project") idProject: Int?,
        @Field("user_id") userId: Int?,
        @Field("offered_by") offeredBy: String?,
    ) : PostOffersResponse

    data class PostOffersResponse(val success: Boolean, val message: String?)
}