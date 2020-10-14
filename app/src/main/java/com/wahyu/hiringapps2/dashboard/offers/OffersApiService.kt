package com.wahyu.hiringapps2.dashboard.offers

import retrofit2.http.GET

interface OffersApiService {

    @GET("offers/")
    suspend fun getOffersListRequest() : OffersResponse

}