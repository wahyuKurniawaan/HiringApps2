package com.wahyu.hiringapps2.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.dashboard.HomeActivity
import com.wahyu.hiringapps2.databinding.ActivitySignInBinding
import com.wahyu.hiringapps2.register.SignUpActivity
import com.wahyu.hiringapps2.util.ApiClient
import com.wahyu.hiringapps2.util.BaseActivity
import com.wahyu.hiringapps2.util.KeySharedPreferences
import com.wahyu.hiringapps2.util.SharedPreferencesUtil
import kotlinx.coroutines.*

class SignInActivity : BaseActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var sharedPref: SharedPreferencesUtil
    private lateinit var coroutineScope: CoroutineScope

    override fun initView() {

        binding.etInputEmail.text = intent.getStringExtra(KeyExtraIntent.EXTRA_EMAIL)?.toEditable()
        binding.etInputPassword.text =
            intent.getStringExtra(KeyExtraIntent.EXTRA_PASSWORD)?.toEditable()
    }

    override fun initListener() {
        binding.buttonSignIn.setOnClickListener {
            sharedPref.put(KeySharedPreferences.PREF_EMAIL, binding.etInputEmail.text.toString())
            callSignInApi()
        }

        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        sharedPref = SharedPreferencesUtil(this)
        initView()
        initListener()
    }

    override fun onStart() {
        super.onStart()
        if (sharedPref.getBoolean(KeySharedPreferences.PREF_IS_LOGIN)) {
            intentMoveToHomeActivity()
        }
    }

    override fun onBackPressed() {
        closeWithDialogConfirm()
    }

    private fun intentMoveToHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun callSignInApi() {
        binding.progressBar.visibility = View.VISIBLE
        val service = ApiClient.getApiClient(this)?.create(SignInApiService::class.java)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        coroutineScope.launch {
            Log.d("test", "login = ${Thread.currentThread().name}")

            val response = withContext(Dispatchers.IO) {
                Log.d("test", "call API = ${Thread.currentThread().name}")

                try {
                    service?.loginRequest(
                        binding.etInputEmail.text.toString(),
                        binding.etInputPassword.text.toString()
                    )
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is SignInResponse) {
                binding.progressBar.visibility = View.GONE

                if (response.success) {
                    sharedPref.put(KeySharedPreferences.PREF_TOKEN, response.data?.token.toString())
                    sharedPref.put(KeySharedPreferences.PREF_IS_LOGIN, true)
                    Toast.makeText(this@SignInActivity, response.message, Toast.LENGTH_SHORT).show()
                    intentMoveToHomeActivity()
                } else {
                    setErrorDialog("Error Login!", response.message)
                }

            }
        }
    }

    override fun onDestroy() {
        if (!sharedPref.getBoolean(KeySharedPreferences.PREF_IS_LOGIN)) coroutineScope.cancel()
        super.onDestroy()
    }
}