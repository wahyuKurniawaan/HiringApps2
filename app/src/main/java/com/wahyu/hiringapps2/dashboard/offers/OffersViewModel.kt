package com.wahyu.hiringapps2.dashboard.offers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wahyu.hiringapps2.util.Key
import com.wahyu.hiringapps2.util.SharedPreferencesUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class OffersViewModel : ViewModel(), CoroutineScope {

    val isLoadingLiveData = MutableLiveData<Boolean>()
    val listOffersLiveData = MutableLiveData<List<OffersModel>>()
    val isOffersLiveData = MutableLiveData<Boolean>()

    private lateinit var service: OffersApiService
    private lateinit var sharedPref: SharedPreferencesUtil

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setOffersService(service: OffersApiService) {
        this.service = service
    }

    fun setSharedPref(sharedPref: SharedPreferencesUtil) {
        this.sharedPref = sharedPref
    }

    fun callOffersApi() {
        launch {
            isLoadingLiveData.value = true
            val response = withContext(Dispatchers.IO) {
                try {
                    service.getOffersListRequest(sharedPref.getString(Key.PREF_EMAIL))
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (response is OffersResponse) {
                val list = response.data?.map {
                    OffersModel(it.id, it.userId, it.projectName, it.projectDescription, it.price, it.duration, it.projectImage,
                        it.name, it.status)
                } ?: listOf()
                listOffersLiveData.value = list
                isOffersLiveData.value = response.success
            }
            isLoadingLiveData.value = false
        }
    }
}