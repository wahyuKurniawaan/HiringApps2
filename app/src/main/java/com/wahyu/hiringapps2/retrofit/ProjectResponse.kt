package com.wahyu.hiringapps2.retrofit

import com.google.gson.annotations.SerializedName

data class ProjectResponse(val status: String?, val message: String?, val data: List<Project>) {

    data class Project(@SerializedName("id_project") val id: Int?,
                       val name: String?,
                       val description: String?,
                       val price: Long?,
                       val duration: String?
    )
}