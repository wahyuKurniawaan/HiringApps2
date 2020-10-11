package com.wahyu.hiringapps2.dashboard.profile

import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileApiService {

    @GET("profile-recruiter/")
    suspend fun getProfileRecruiterByEmailRequest(@Query("search[user_email]") email: String
    ) : ProfileResponse
}