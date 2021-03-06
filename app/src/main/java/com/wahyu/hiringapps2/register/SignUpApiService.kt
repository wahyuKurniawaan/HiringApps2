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
        @Field("user_company") companyName: String?,
        @Field("role_job") roleJob: String?,
        @Field("phone_number") phoneNumber: String?,
    ): SignUpResponse

    @FormUrlEncoded
    @POST("profile-recruiter")
    suspend fun createProfileRecruiter(
        @Field("user_id") userId: Int?
    ): CreateProfileResponse

            data class CreateProfileResponse(val success: Boolean, val message: String?)
}