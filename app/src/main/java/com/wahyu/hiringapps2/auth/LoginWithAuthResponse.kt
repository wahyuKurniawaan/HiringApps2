package com.wahyu.hiringapps2.auth

import com.google.gson.annotations.SerializedName

data class LoginWithAuthResponse (val status: String?, val message: String?, val data: DataResult?) {

    data class DataResult(@SerializedName("user_id") val id: Int?,
                          @SerializedName("user_name") val name: String?,
                          @SerializedName("user_email") val email: String?,
                          @SerializedName("user_role") val role: String?,
                          @SerializedName("user_status") val status: String?,
                          @SerializedName("tokenLogin") val token: String?
    )
}