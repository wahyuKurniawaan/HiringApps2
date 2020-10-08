package com.wahyu.hiringapps2.dashboard.search

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {

    @GET("profile-job-seeker/")
    fun getProfileJobSeekerByJobTitleRequest(@Query("search[job_title]") search: String) : Call<SearchResponse>

    @GET("profile-job-seeker/")
    fun getProfileJobSeekerByNameRequest(@Query("search[full_name]") search: String) : Call<SearchResponse>

    @GET("profile-job-seeker/")
    fun getProfileJobSeekerBySkillRequest(@Query("search[id_skill]") search: String) : Call<SearchResponse>

    @GET("profile-job-seeker/")
    fun getProfileJobSeekerByLocationRequest(@Query("search[city]") search: String) : Call<SearchResponse>

    @GET("profile-job-seeker/")
    fun getProfileJobSeekerByStatusJobRequest(@Query("search[status_job]") search: String) : Call<SearchResponse>
}