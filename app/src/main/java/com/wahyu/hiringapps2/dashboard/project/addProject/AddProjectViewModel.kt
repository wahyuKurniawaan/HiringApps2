package com.wahyu.hiringapps2.dashboard.project.addProject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wahyu.hiringapps2.dashboard.project.ProjectsApiService
import com.wahyu.hiringapps2.dashboard.project.ProjectsResponse
import com.wahyu.hiringapps2.util.SharedPreferencesUtil
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlin.coroutines.CoroutineContext

class AddProjectViewModel : ViewModel(), CoroutineScope {

    val isAddProjectLiveData = MutableLiveData<Boolean>()

    private lateinit var service: ProjectsApiService
    private lateinit var sharedPref: SharedPreferencesUtil

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setEditProfileService(service: ProjectsApiService) {
        this.service = service
    }

    fun setSharedPref(sharePref: SharedPreferencesUtil) {
        this.sharedPref = sharePref
    }

    fun callCreateProjectDataApi(userId: RequestBody, name: RequestBody, description: RequestBody, price: RequestBody,
                                 duration: RequestBody, image: MultipartBody.Part) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.postProject(userId, name, description, price, duration, image)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    }
                }

            if (response is ProjectsResponse.addProjectResponse) {
                isAddProjectLiveData.value = response.success
            }
        }
    }
}