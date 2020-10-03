package com.wahyu.hiringapps2.dashboard.home

import retrofit2.Call
import retrofit2.http.GET

interface HomeApiService {

    @GET("profile-job-seeker/")
    fun getProfileJobSeekerRequest() : Call<HomeResponse>
}