package com.wahyu.hiringapps2.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.auth.ApiClient
import com.wahyu.hiringapps2.databinding.ActivitySignUpBinding
import com.wahyu.hiringapps2.login.SignInActivity
import com.wahyu.hiringapps2.util.BaseActivity
import com.wahyu.hiringapps2.util.KeySharedPreferences
import com.wahyu.hiringapps2.util.SharedPreferencesUtil
import kotlinx.coroutines.*

class SignUpActivity : BaseActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var sharedPref: SharedPreferencesUtil
    private lateinit var coroutineScope: CoroutineScope

    override fun initView() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.role_accounts,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
        }

    override fun initListener() {
        binding.spinner.onItemSelectedListener = this

        binding.buttonSignUp.setOnClickListener {
            callSignInApi()
        }

        binding.textSignIn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        sharedPref = SharedPreferencesUtil(this)
        initView()
        initListener()

    }

    private fun saveSession(email: String, password: String) {
        sharedPref.put(KeySharedPreferences.PREF_EMAIL, email)
        sharedPref.put(KeySharedPreferences.PREF_PASSWORD, password)
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
    }

    private fun callSignInApi() {
        binding.progressBar.visibility = View.VISIBLE
        val service = ApiClient.getApiClient(this)?.create(SignUpApiService::class.java)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service?.registerRequest(
                        binding.etFullName.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString(),
                        binding.spinner.selectedItem.toString()
                    )
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is SignUpResponse) {
                binding.progressBar.visibility = View.GONE

                if (response.success) {
                    Log.d("register", "response $response")
                    val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                    intent.putExtra(KeyExtraIntent.EXTRA_EMAIL, "${binding.etEmail.editableText}")
                    intent.putExtra(
                        KeyExtraIntent.EXTRA_PASSWORD,
                        "${binding.etPassword.editableText}"
                    )
                    saveSession(
                        binding.etEmail.editableText.toString(),
                        binding.etPassword.editableText.toString()
                    )
                    sharedPref.put(
                        KeySharedPreferences.PREF_FULL_NAME,
                        binding.etFullName.editableText.toString()
                    )
                    startActivity(intent)
                } else {
                    setErrorDialog("Error Register!", response.message)
                }

            }
        }
    }

    override fun onDestroy() {
        if (!sharedPref.getBoolean(KeySharedPreferences.PREF_IS_LOGIN)) coroutineScope.cancel()
        super.onDestroy()
    }
}