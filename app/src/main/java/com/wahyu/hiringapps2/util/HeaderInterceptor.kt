package com.wahyu.hiringapps2.util

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(val mContext: Context) : Interceptor {

    private val sharedPref = SharedPreferencesUtil(mContext)

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {

        val token = sharedPref.getString(KeySharedPreferences.PREF_TOKEN)
        proceed(
            request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        )
    }


}