package com.wahyu.hiringapps2.register

import com.google.gson.annotations.SerializedName

data class SignUpResponse(val success: Boolean, val message: String?, val data: DataResult?) {

    data class DataResult(
        @SerializedName("user_id") val id: Int?,
        @SerializedName("user_name") val name: String?,
        @SerializedName("user_email") val email: String?,
        @SerializedName("user_company") val companyName: String?,
        @SerializedName("role_job") val roleJob: String?,
        @SerializedName("phone_number") val phoneNumber: String?,
        @SerializedName("user_status") val status: String?,
        @SerializedName("user_role") val role: String?,
        @SerializedName("created_at") val createdAt: String?
    )
}