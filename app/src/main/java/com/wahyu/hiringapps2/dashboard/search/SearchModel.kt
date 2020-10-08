package com.wahyu.hiringapps2.dashboard.search

data class SearchModel(
    val id: Int?,
    val idAccount: Int?,
    val idPortofolio: Int?,
    val idSkill: String?,
    val email: String?,
    val name: String?,
    val jobTitle: String?,
    val statusJob: String?,
    val address: String?,
    val city: String?,
    val workplace: String?,
    val image: String?,
    val description: String?,
    val createdAt: String?,
    val updatedAt: String?,
)