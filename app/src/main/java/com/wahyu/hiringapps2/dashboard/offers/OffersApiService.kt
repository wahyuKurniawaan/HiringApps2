package com.wahyu.hiringapps2.dashboard.offers

import retrofit2.http.GET
import retrofit2.http.Query

interface OffersApiService {

    @GET("offers/")
    suspend fun getOffersListRequest(@Query("search[offered_by]") userEmail: String?) : OffersResponse

}