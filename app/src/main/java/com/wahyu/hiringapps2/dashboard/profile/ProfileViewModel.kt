package com.wahyu.hiringapps2.dashboard.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wahyu.hiringapps2.util.Key
import com.wahyu.hiringapps2.util.SharedPreferencesUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ProfileViewModel : ViewModel(), CoroutineScope {

    val isLoadingLiveData = MutableLiveData<Boolean>()
    val isProfileLiveData = MutableLiveData<Boolean>()
    val listLiveData = MutableLiveData<List<ProfileModel>>()

    private lateinit var service: ProfileApiService
    private lateinit var sharedPref: SharedPreferencesUtil

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setProfileService(service: ProfileApiService) {
        this.service = service
    }

    fun setSharedPref(sharePref: SharedPreferencesUtil) {
        this.sharedPref = sharePref
    }

    fun callProfileApi() {
        launch {
            isLoadingLiveData.value = true
            val response = withContext(Dispatchers.IO) {
                try {
                    service.getProfileRecruiterByUserIdRequest(sharedPref.getInt(Key.PREF_USER_ID)!!)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Job() + Dispatchers.Main) {
                        isProfileLiveData.value = false
                    }
                }
            }
            if (response is ProfileResponse) {
                val list = response.data?.map {
                    ProfileModel(it.id, it.userId, it.name, it.email, it.companyName, it.roleJob, it.phoneNumber, it.userRole,
                    it.profileImage, it.companyField, it.city, it.description, it.instagramLink, it.linkedinLink)
                } ?: listOf()
                listLiveData.value = list
                isProfileLiveData.value = true
            }
            isLoadingLiveData.value = false
        }
    }
}