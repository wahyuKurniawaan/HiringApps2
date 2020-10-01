package com.wahyu.hiringapps2.auth

import android.content.Context
import android.util.Log
import com.wahyu.hiringapps2.util.KeySharedPreferences
import com.wahyu.hiringapps2.util.SharedPreferencesUtil
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(val mContext: Context) : Interceptor {

    private val sharedPref = SharedPreferencesUtil(mContext)

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {

        val token = sharedPref.getString(KeySharedPreferences.PREF_TOKEN)
        Log.d("test", "token = $token")
        proceed(
            request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        )
    }


}