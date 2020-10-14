package com.wahyu.hiringapps2.dashboard.home.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wahyu.hiringapps2.dashboard.home.HomeModel
import com.wahyu.hiringapps2.dashboard.home.HomeResponse
import com.wahyu.hiringapps2.util.SharedPreferencesUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DetailProfileViewModel : ViewModel(), CoroutineScope {

    val isLoadingLiveData = MutableLiveData<Boolean>()
    val isDetailProfileLiveData = MutableLiveData<Boolean>()
    val detailProfileLiveData = MutableLiveData<List<HomeModel>>()

    private lateinit var service: DetailProfileApiService
    private lateinit var sharedPref: SharedPreferencesUtil

    override val coroutineContext: CoroutineContext get() = Job() + Dispatchers.Main

    fun setDetailProfileService(service: DetailProfileApiService) {
        this.service = service
    }

    fun setSharedPref(sharePref: SharedPreferencesUtil) {
        this.sharedPref = sharePref
    }

    fun callDetailProfileApi(id: Int) {
        launch {
            isLoadingLiveData.value = true
            val response = withContext(Dispatchers.IO) {
                try {
                    service.getProfileJobSeekerByIdRequest(id)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        isDetailProfileLiveData.value = false
                    }
                }
            }
            if (response is HomeResponse) {
                val list = response.data?.map {
                    HomeModel(it.id, it.idAccount, it.idPortofolio, it.idSkill, it.email, it.name, it.jobTitle, it.statusJob,
                        it.address, it.city, it.workplace, it.image, it.description, it.createdAt, it.updatedAt)
                } ?: listOf()
                detailProfileLiveData.value = list
                isDetailProfileLiveData.value = true
            }
            isLoadingLiveData.value = false
        }
    }
}