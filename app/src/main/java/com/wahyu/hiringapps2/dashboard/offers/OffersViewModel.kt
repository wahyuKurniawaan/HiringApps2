package com.wahyu.hiringapps2.dashboard.offers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class OffersViewModel: ViewModel(), CoroutineScope {

    val isLoadingLiveData = MutableLiveData<Boolean>()
    val listOffersLiveData = MutableLiveData<List<OffersModel>>()

    private lateinit var service: OffersApiService

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setOffersService(service: OffersApiService) {
        this.service = service
    }

    fun callOffersApi() {
        launch {
            isLoadingLiveData.value = true
            val response = withContext(Dispatchers.IO) {
                try {
                    service.getOffersListRequest()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (response is OffersResponse) {
                val list = response.data?.map {
                    OffersModel(it.id, it.projectName, it.projectDescription, it.price, it.duration, it.name)
                } ?: listOf()
                listOffersLiveData.value = list
            }
            isLoadingLiveData.value = false
        }
    }
}