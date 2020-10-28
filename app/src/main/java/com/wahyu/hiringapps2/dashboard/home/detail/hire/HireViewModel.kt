package com.wahyu.hiringapps2.dashboard.home.detail.hire

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonParser
import com.wahyu.hiringapps2.dashboard.project.ProjectsModel
import com.wahyu.hiringapps2.dashboard.project.ProjectsResponse
import com.wahyu.hiringapps2.util.Key
import com.wahyu.hiringapps2.util.SharedPreferencesUtil
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class HireViewModel : ViewModel(), CoroutineScope {

    val isLoadingLiveData = MutableLiveData<Boolean>()
    val isOffersLiveData = MutableLiveData<Boolean>()
    val listProjectLiveData = MutableLiveData<List<ProjectsModel>>()
    val messageErrorLiveData = MutableLiveData<List<String>>()

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
                    service.getAllProjectData(sharedPref.getInt(Key.PREF_USER_ID))
                } catch (e: Throwable) {
                    when (e) {
                        is HttpException -> {
                            val message: String
                            val errorJsonString = e.response()?.errorBody()?.string()
                            message = JsonParser().parse(errorJsonString).asJsonObject["message"].asString
                            withContext(Job() + Dispatchers.Main) {
                                messageErrorLiveData.value = listOf(message)
                            }
                        }
                        else -> e.printStackTrace()
                    }
                }
            }
            if (response is ProjectsResponse) {
                val list = response.data?.map {
                    ProjectsModel(it.id, it.userId, it.name, it.description, it.price, it.duration, it.projectImage)
                } ?: listOf()
                withContext(Dispatchers.Main) {
                    listProjectLiveData.value = list
                }
            }
            isLoadingLiveData.value = false
        }
    }

    fun callOffersApi(idJobSeeker: Int, idProject: Int, userId: Int, offeredBy: String) {
        launch {
            isLoadingLiveData.value = true
            val response = withContext(Dispatchers.IO) {
                try {
                    service.setOffersData(idJobSeeker, idProject, userId, offeredBy)
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