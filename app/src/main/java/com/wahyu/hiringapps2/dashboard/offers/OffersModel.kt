package com.wahyu.hiringapps2.dashboard.offers

data class OffersModel(
    val id: Int?,
    val userId: Int?,
    val name: String?,
    val description: String?,
    val price: Long?,
    val duration: String?,
    val image: String?,
    val jobSeekerName: String?,
    val status: String?
    )