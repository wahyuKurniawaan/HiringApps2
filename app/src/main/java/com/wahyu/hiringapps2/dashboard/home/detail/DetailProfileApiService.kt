package com.wahyu.hiringapps2.dashboard.home.detail

import com.wahyu.hiringapps2.dashboard.home.HomeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DetailProfileApiService {

    @GET("profile-job-seeker/")
    suspend fun getProfileJobSeekerByIdRequest(@Query("search[id_profile_job_seeker]") id: Int,
    ) : HomeResponse
}