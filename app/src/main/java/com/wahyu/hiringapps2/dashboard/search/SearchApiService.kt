package com.wahyu.hiringapps2.dashboard.search

import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {

    @GET("profile-job-seeker/")
    suspend fun getProfileJobSeekerByJobTitleRequest(@Query("search[job_title]") search: String) : SearchResponse

    @GET("profile-job-seeker/")
    suspend fun getProfileJobSeekerByNameRequest(@Query("search[full_name]") search: String) : SearchResponse

    @GET("profile-job-seeker/")
    suspend fun getProfileJobSeekerBySkillRequest(@Query("search[id_skill]") search: String) : SearchResponse

    @GET("profile-job-seeker/")
    suspend fun getProfileJobSeekerByLocationRequest(@Query("search[city]") search: String) : SearchResponse

    @GET("profile-job-seeker/")
    suspend fun getProfileJobSeekerByStatusJobRequest(@Query("search[status_job]") search: String) : SearchResponse
}