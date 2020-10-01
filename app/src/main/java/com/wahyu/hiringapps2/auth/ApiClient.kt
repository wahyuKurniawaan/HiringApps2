package com.wahyu.hiringapps2.auth

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {

        private const val BASE_URL = "http://34.234.66.114:8080/"
        private var retrofit: Retrofit? = null

        fun getApiClient(mContext: Context) : Retrofit? {

            if (retrofit == null) {
                val okHttpClient = OkHttpClient.Builder().addInterceptor(HeaderInterceptor(mContext)).build()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }
}