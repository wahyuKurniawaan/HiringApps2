package com.wahyu.hiringapps2.dashboard.home.detail.hire

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wahyu.hiringapps2.dashboard.project.ProjectsModel
import com.wahyu.hiringapps2.dashboard.project.ProjectsResponse
import com.wahyu.hiringapps2.util.SharedPreferencesUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class HireViewModel : ViewModel(), CoroutineScope {

    val isLoadingLiveData = MutableLiveData<Boolean>()
    val isOffersLiveData = MutableLiveData<Boolean>()
    val listProjectLiveData = MutableLiveData<List<ProjectsModel>>()

    private lateinit var service: HireApiService
    private lateinit var sharedPref: SharedPreferencesUtil

    override val coroutineContext: CoroutineContext get() = Job() + Dispatchers.Main

    fun setHireService(service: HireApiService) {
        this.service = service
    }

    fun setSharedPref(sharePref: SharedPreferencesUtil) {
        this.sharedPref = sharePref
    }

    fun callHireApi() {
        launch {
            isLoadingLiveData.value = true
            val response = withContext(Dispatchers.IO) {
                try {
                    service.getAllProjectData()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                    }
                }
            }
            if (response is ProjectsResponse) {
                val list = response.data?.map {
                    ProjectsModel(it.id, it.name, it.description, it.price, it.duration)
                } ?: listOf()
                withContext(Dispatchers.Main) {
                    listProjectLiveData.value = list
                }
            }
            isLoadingLiveData.value = false
        }
    }

    fun callOffersApi(idJobSeeker: Int, idProject: Int) {
        launch {
            isLoadingLiveData.value = true
            val response = withContext(Dispatchers.IO) {
                try {
                    service.setOffersData(idJobSeeker, idProject)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (response is HireApiService.PostOffersResponse) {
                isOffersLiveData.value = response.success
            }
            isLoadingLiveData.value = false
        }
    }
}