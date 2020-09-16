package com.wahyu.hiringapps2.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.databinding.ActivitySignUpBinding
import com.wahyu.hiringapps2.util.BaseActivity
import com.wahyu.hiringapps2.util.KeySharedPreferences
import com.wahyu.hiringapps2.util.SharedPreferencesUtil

class SignUpActivity : BaseActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var sharedPref: SharedPreferencesUtil

    override fun initView() {

    }

    override fun initListener() {
        binding.buttonSignUp.setOnClickListener {

            val intent = Intent(this, SignInActivity::class.java)
            intent.putExtra(KeyExtraIntent.EXTRA_EMAIL, "${binding.inputEmail.editableText}")
            intent.putExtra(KeyExtraIntent.EXTRA_PASSWORD, "${binding.inputPassword.editableText}")

            saveSession(binding.inputEmail.editableText.toString(), binding.inputPassword.editableText.toString())
            sharedPref.put(KeySharedPreferences.PREF_FIRST_NAME, binding.inputFirstName.editableText.toString())
            sharedPref.put(KeySharedPreferences.PREF_LAST_NAME, binding.inputLastName.editableText.toString())
            Log.d("test", "shared first name = ${sharedPref.getString(KeySharedPreferences.PREF_FIRST_NAME)}")
            Log.d("test", "shared last name = ${sharedPref.getString(KeySharedPreferences.PREF_LAST_NAME)}")
            startActivity(intent)

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
}