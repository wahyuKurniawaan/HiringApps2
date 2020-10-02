package com.wahyu.hiringapps2.register

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SignUpApiService {

    @FormUrlEncoded
    @POST("users/register")
    suspend fun registerRequest(
        @Field("user_name") name: String?,
        @Field("user_email") email: String?,
        @Field("user_password") password: String?,
        @Field("user_role") role: String?,
    ): SignUpResponse

}