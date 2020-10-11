package com.wahyu.hiringapps2.dashboard.home

import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApiService {

    @GET("profile-job-seeker/")
    fun getProfileJobSeekerRequest() : HomeResponse

    @GET("profile-job-seeker/")
    suspend fun getProfileJobSeekerByJobTitleRequest(@Query("search[job_title]") search: String,
    ) : HomeResponse
}