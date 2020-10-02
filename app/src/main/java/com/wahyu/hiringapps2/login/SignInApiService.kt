package com.wahyu.hiringapps2.login

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SignInApiService {

    @FormUrlEncoded
    @POST("users/login")
    suspend fun loginRequest(
        @Field("user_email") email: String?,
        @Field("user_password") password: String?
    ): SignInResponse
}