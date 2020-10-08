package com.wahyu.hiringapps2.dashboard.profile

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileApiService {

    @GET("profile-recruiter/")
    fun getProfileRecruiterByEmailRequest(@Query("search[user_email]") email: String
    ) : Call<ProfileResponse>
}