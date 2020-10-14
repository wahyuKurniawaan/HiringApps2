package com.wahyu.hiringapps2.dashboard.profile

import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileApiService {

    @GET("profile-recruiter/")
    suspend fun getProfileRecruiterByUserIdRequest(@Query("search[profile_recruiter.user_id]") id: Int
    ) : ProfileResponse
}