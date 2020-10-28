package com.wahyu.hiringapps2.dashboard.offers

import com.google.gson.annotations.SerializedName

data class OffersResponse(val success: Boolean, val message: String?, val data: List<DataResult>?) {

    data class DataResult(
        @SerializedName("id_offers") val id: Int?,
        @SerializedName("id_profile_job_seeker") val idProfileJobSeeker: Int?,
        @SerializedName("id_account_job_seeker") val idAccount: Int?,
        @SerializedName("id_portofolio_job_seeker") val idPortofolio: Int?,
        @SerializedName("skill") val idSkill: String?,
        @SerializedName("user_email") val email: String?,
        @SerializedName("user_name") val name: String?,
        @SerializedName("job_title") val jobTitle: String?,
        @SerializedName("status_job") val statusJob: String?,
        @SerializedName("address") val address: String?,
        @SerializedName("city") val city: String?,
        @SerializedName("workplace") val workplace: String?,
        @SerializedName("image") val image: String?,
        @SerializedName("description") val description: String?,
        @SerializedName("created_at") val createdAt: String?,
        @SerializedName("updated_at") val updatedAt: String?,
        @SerializedName("user_id") val userId: Int?,
        @SerializedName("name") val projectName: String?,
        @SerializedName("project_description") val projectDescription: String?,
        @SerializedName("price") val price: Long?,
        @SerializedName("duration") val duration: String?,
        @SerializedName("project_image") val projectImage: String?,
        @SerializedName("status") val status: String?,
    )
}