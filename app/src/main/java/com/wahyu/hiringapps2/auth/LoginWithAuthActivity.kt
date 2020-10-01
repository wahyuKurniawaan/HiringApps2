 package com.wahyu.hiringapps2.auth

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.databinding.ActivityLoginWithAuthBinding
import com.wahyu.hiringapps2.util.KeySharedPreferences
import com.wahyu.hiringapps2.util.SharedPreferencesUtil
import kotlinx.coroutines.*

class LoginWithAuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginWithAuthBinding
    private lateinit var sharedpref: SharedPreferencesUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_with_auth)
        sharedpref = SharedPreferencesUtil(this)

        binding.btnLogin.setOnClickListener {
            callAuthApi()
        }
    }

    private fun callAuthApi() {
        val service = ApiClient.getApiClient(this)?.create(AuthApiService::class.java)

        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        coroutineScope.launch {
            Log.d("test", "login = ${Thread.currentThread().name}")

            val response = withContext(Dispatchers.IO) {
                Log.d("test", "call API = ${Thread.currentThread().name}")

                try {
                    service?.loginRequest(binding.etEmail.text.toString(), binding.etPassword.text.toString())
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is LoginWithAuthResponse) {
                if (response.data?.role == "admin") {
                    sharedpref.put(KeySharedPreferences.PREF_TOKEN, response.data.token.toString())
                    Toast.makeText(this@LoginWithAuthActivity, "Login bethasil", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@LoginWithAuthActivity, "${response.message}", Toast.LENGTH_SHORT).show()
                }
                Log.d("test", "response = $response")}

        }
    }
}