package com.wahyu.hiringapps2.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.dashboard.HomeActivity
import com.wahyu.hiringapps2.databinding.ActivitySignInBinding
import com.wahyu.hiringapps2.register.SignUpActivity
import com.wahyu.hiringapps2.util.ApiClient
import com.wahyu.hiringapps2.util.BaseActivity
import com.wahyu.hiringapps2.util.Key
import com.wahyu.hiringapps2.util.SharedPreferencesUtil

class SignInActivity : BaseActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var sharedPref: SharedPreferencesUtil
    private lateinit var viewModel: SignInViewModel

    override fun initView() {
        binding.etInputEmail.text = intent.getStringExtra(KeyExtraIntent.EXTRA_EMAIL)?.toEditable()
        binding.etInputPassword.text = intent.getStringExtra(KeyExtraIntent.EXTRA_PASSWORD)?.toEditable()
    }

    override fun initListener() {
        binding.buttonSignIn.setOnClickListener {
            sharedPref.put(Key.PREF_EMAIL, binding.etInputEmail.text.toString())
            viewModel.callSignInApi(binding.etInputEmail.text.toString(), binding.etInputPassword.text.toString())
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
        val service = ApiClient.getApiClient(this)?.create(SignInApiService::class.java)

        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        viewModel.setSharedPref(sharedPref)

        if (service != null) {
            viewModel.setLoginService(service)
        }
        initView()
        initListener()
        subscribeLiveData()
    }

    override fun onStart() {
        super.onStart()
        if (sharedPref.getBoolean(Key.PREF_IS_LOGIN)) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        closeWithDialogConfirm()
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.isLoginLiveData.observe(this, {
            if (it) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            } else {
                setErrorDialog("Error Login", viewModel.errorMessageLiveData.value)
            }
        })
    }
}