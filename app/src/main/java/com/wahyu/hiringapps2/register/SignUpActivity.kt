package com.wahyu.hiringapps2.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.databinding.ActivitySignUpBinding
import com.wahyu.hiringapps2.login.SignInActivity
import com.wahyu.hiringapps2.util.ApiClient
import com.wahyu.hiringapps2.util.BaseActivity
import com.wahyu.hiringapps2.util.Key
import com.wahyu.hiringapps2.util.SharedPreferencesUtil

class SignUpActivity : BaseActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var sharedPref: SharedPreferencesUtil
    private lateinit var viewModel: SignUpViewModel

    override fun initView() {}

    override fun initListener() {

        binding.buttonSignUp.setOnClickListener {
            viewModel.callSignUpApi(
                binding.etFullName.text.toString(),
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString(),
                binding.etCompanyName.text.toString(),
                binding.etRoleJob.text.toString(),
                binding.etPhoneNumber.text.toString()
            )
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
        val service = ApiClient.getApiClient(this)?.create(SignUpApiService::class.java)

        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        viewModel.setSharedPref(sharedPref)

        if (service != null) {
            viewModel.setRegisterService(service)
        }

        initView()
        initListener()
        subscribeLiveData()
    }

    private fun saveSession(email: String, password: String) {
        sharedPref.put(Key.PREF_EMAIL, email)
        sharedPref.put(Key.PREF_PASSWORD, password)
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.isRegisterLiveData.observe(this, {
            if (it) {
                val intent = Intent(this, SignInActivity::class.java)
                intent.putExtra(KeyExtraIntent.EXTRA_EMAIL, "${binding.etEmail.editableText}")
                intent.putExtra(KeyExtraIntent.EXTRA_PASSWORD, "${binding.etPassword.editableText}")
                saveSession(
                    binding.etEmail.editableText.toString(),
                    binding.etPassword.editableText.toString()
                )
                sharedPref.put(Key.PREF_FULL_NAME, binding.etFullName.editableText.toString())
                startActivity(intent)
            } else {
                setErrorDialog("Error Register", viewModel.errorMessageLiveData.value)
            }
        })
    }
}
