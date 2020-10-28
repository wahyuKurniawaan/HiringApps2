package com.wahyu.hiringapps2.dashboard.project

import com.google.gson.annotations.SerializedName

data class ProjectsResponse(val success: Boolean, val message: String?, val data: List<Project>?) {

    data class Project(@SerializedName("id_project") val id: Int?,
                       @SerializedName("user_id") val userId: Int?,
                       @SerializedName("name") val name: String?,
                       @SerializedName("project_description") val description: String?,
                       @SerializedName("price") val price: Long?,
                       @SerializedName("duration") val duration: String?,
                       @SerializedName("project_image") val projectImage: String?
    )

    data class addProjectResponse(val success: Boolean, val message: String?)
}