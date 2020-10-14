package com.wahyu.hiringapps2.dashboard.profile.editProfile

import retrofit2.http.*

interface EditProfileApiService {

    @GET("profile-recruiter/")
    suspend fun getProfileRecruiterByUserIdRequest(
        @Query("search[profile_recruiter.user_id]") id: Int
    ): EditProfileResponse

    @FormUrlEncoded
    @PUT("profile-recruiter/{id}")
    suspend fun editProfileRequest(
        @Path("id") id: Int,
        @Field("company_field") companyField: String?,
        @Field("city") city: String?,
        @Field("description") description: String?,
        @Field("instagram") instagram: String?,
        @Field("linkedin") linkedin: String?,
        @Field("user_name") name: String?,
        @Field("user_email") email: String?,
        @Field("user_password") password: String?,
        @Field("user_company") user_company: String?,
        @Field("role_job") roleJob: String?,
        @Field("phone_number") phoneNumber: String?,
        // belum selesai
    ): EditProfileResponse
}