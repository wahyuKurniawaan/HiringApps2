package com.wahyu.hiringapps2.dashboard.profile.editProfile

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface EditProfileApiService {

    @GET("profile-recruiter/")
    suspend fun getProfileRecruiterByUserIdRequest(
        @Query("search[profile_recruiter.user_id]") id: Int
    ): EditProfileResponse

    @Multipart
    @PUT("profile-recruiter/{id}")
    suspend fun editProfileRequest(
        @Path("id") id: Int,
        @Part("company_field") companyField: RequestBody,
        @Part("city") city: RequestBody,
        @Part("description") description: RequestBody,
        @Part("instagram") instagram: RequestBody,
        @Part("linkedin") linkedin: RequestBody,
        @Part image: MultipartBody.Part,
        @Part("user_name") name: RequestBody,
        @Part("user_email") email: RequestBody,
        @Part("user_password") password: RequestBody,
        @Part("user_company") user_company: RequestBody,
        @Part("role_job") roleJob: RequestBody,
        @Part("phone_number") phoneNumber: RequestBody,
    ) : PutEditProfileResponse

    data class PutEditProfileResponse(val success: Boolean, val message: String?)
}