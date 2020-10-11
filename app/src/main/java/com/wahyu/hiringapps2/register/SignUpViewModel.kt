package com.wahyu.hiringapps2.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonParser
import com.wahyu.hiringapps2.util.SharedPreferencesUtil
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class SignUpViewModel : ViewModel(), CoroutineScope {

    val isLoadingLiveData = MutableLiveData<Boolean>()
    val isRegisterLiveData = MutableLiveData<Boolean>()
    val errorMessageLiveData = MutableLiveData<String>()

    private lateinit var service: SignUpApiService
    private lateinit var sharedPref: SharedPreferencesUtil

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setRegisterService(service: SignUpApiService) {
        this.service = service
    }

    fun setSharedPref(sharePref: SharedPreferencesUtil) {
        this.sharedPref = sharePref
    }

    fun callSignUpApi(
        name: String,
        email: String,
        password: String,
        companyName: String,
        roleJob: String,
        PhoneNumber: String
    ) {
        launch {
            isLoadingLiveData.value = true
            @Suppress("DEPRECATION") val response = withContext(Dispatchers.IO) {
                try {
                    service.registerRequest(name, email, password, companyName, roleJob, PhoneNumber)
                } catch (e: Throwable) {
                    when (e) {
                        is HttpException -> {
                            val message: String
                            val errorJsonString = e.response()?.errorBody()?.string()
                            message = JsonParser().parse(errorJsonString).asJsonObject["message"].asString
                            withContext(Job() + Dispatchers.Main) {
                                errorMessageLiveData.value = message
                                isRegisterLiveData.value = false
                            }
                        }
                        else -> e.printStackTrace()
                    }
                }
            }
            if (response is SignUpResponse) {
                isRegisterLiveData.value = true

            }
            isLoadingLiveData.value = false
        }
    }
}