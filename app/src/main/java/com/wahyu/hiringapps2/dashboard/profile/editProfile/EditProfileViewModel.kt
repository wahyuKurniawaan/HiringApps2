package com.wahyu.hiringapps2.dashboard.profile.editProfile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wahyu.hiringapps2.util.Key
import com.wahyu.hiringapps2.util.SharedPreferencesUtil
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlin.coroutines.CoroutineContext

class EditProfileViewModel : ViewModel(), CoroutineScope {

    val isLoadingLiveData = MutableLiveData<Boolean>()
    val isEditProfileLiveData = MutableLiveData<Boolean>()
    val listLiveData = MutableLiveData<List<EditProfileModel>>()

    private lateinit var service: EditProfileApiService
    private lateinit var sharedPref: SharedPreferencesUtil

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setEditProfileService(service: EditProfileApiService) {
        this.service = service
    }

    fun setSharedPref(sharePref: SharedPreferencesUtil) {
        this.sharedPref = sharePref
    }

    fun callGetProfileDataApi() {
        launch {
            isLoadingLiveData.value = true
            val response = withContext(Dispatchers.IO) {
                try {
                    service.getProfileRecruiterByUserIdRequest(sharedPref.getInt(Key.PREF_USER_ID)!!)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Job() + Dispatchers.Main) {
                        isEditProfileLiveData.value = false
                    }
                }
            }
            if (response is EditProfileResponse) {
                val list = response.data?.map {
                    EditProfileModel(it.id, it.userId, it.name, it.email, it.companyName, it.roleJob, it.phoneNumber, it.userRole,
                        it.profileImage, it.companyField, it.city, it.description, it.instagramLink, it.linkedinLink)
                } ?: listOf()
                listLiveData.value = list
                isEditProfileLiveData.value = true
            }
            isLoadingLiveData.value = false
        }
    }

    fun callEditProfileDataApi(id: Int, companyField: RequestBody, city: RequestBody, description: RequestBody, instagram: RequestBody,
                               linkedin: RequestBody,profileImage: MultipartBody.Part, userName: RequestBody, userEmail: RequestBody, userPassword: RequestBody,
                               userCompany: RequestBody, roleJob: RequestBody, phoneNumber: RequestBody) {
        launch {
            isLoadingLiveData.value = true
            val response = withContext(Dispatchers.IO) {
                try {
                    service.editProfileRequest(id, companyField, city, description, instagram, linkedin, profileImage, userName, userEmail,
                        userPassword, userCompany, roleJob, phoneNumber)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Job() + Dispatchers.Main) {
//                        isEditProfileLiveData.value = false
                    }
                }
            }
            if (response is EditProfileResponse) {
                val list = response.data?.map {
                    EditProfileModel(it.id, it.userId, it.name, it.email, it.companyName, it.roleJob, it.phoneNumber, it.userRole,
                        it.profileImage, it.companyField, it.city, it.description, it.instagramLink, it.linkedinLink)
                } ?: listOf()
                listLiveData.value = list
                isEditProfileLiveData.value = true
            }
            isLoadingLiveData.value = false
        }
    }
}