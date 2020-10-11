package com.wahyu.hiringapps2.dashboard.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class HomeViewModel : ViewModel(), CoroutineScope {

    val isLoadingLiveData = MutableLiveData<Boolean>()
    val listLiveData = MutableLiveData<List<HomeModel>>()

    private lateinit var service: HomeApiService

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setHomeService(service: HomeApiService) {
        this.service = service
    }

    fun callHomeApi(jobTitle: String) {
        launch {
            isLoadingLiveData.value = true
            val response = withContext(Dispatchers.IO) {
                try {
                    service.getProfileJobSeekerByJobTitleRequest(jobTitle)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (response is HomeResponse) {
                val list = response.data?.map {
                    HomeModel(it.id, it.idAccount, it.idPortofolio, it.idSkill, it.email, it.name, it.jobTitle, it.statusJob,
                        it.address, it.city, it.workplace, it.image, it.description, it.createdAt, it.updatedAt)
                } ?: listOf()
                listLiveData.value = list
            }
            isLoadingLiveData.value = false
        }
    }
}