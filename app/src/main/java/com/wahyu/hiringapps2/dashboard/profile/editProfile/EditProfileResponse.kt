package com.wahyu.hiringapps2.dashboard.profile.editProfile

import com.google.gson.annotations.SerializedName

class EditProfileResponse(val success: Boolean, val message: String?, val data: List<DataResult>?) {

    data class DataResult(
        @SerializedName("id_profile_recruiter") val id: Int?,
        @SerializedName("user_id") val userId: Int?,
        @SerializedName("user_name") val name: String?,
        @SerializedName("user_email") val email: String?,
        @SerializedName("user_company") val companyName: String?,
        @SerializedName("role_job") val roleJob: String?,
        @SerializedName("phone_number") val phoneNumber: String?,
        @SerializedName("user_role") val userRole: String?,
        @SerializedName("profile_image") val profileImage: String?,
        @SerializedName("company_field") val companyField: String?,
        @SerializedName("city") val city: String?,
        @SerializedName("description") val description: String?,
        @SerializedName("instagram") val instagramLink: String?,
        @SerializedName("linkedin") val linkedinLink: String?,
    )
}