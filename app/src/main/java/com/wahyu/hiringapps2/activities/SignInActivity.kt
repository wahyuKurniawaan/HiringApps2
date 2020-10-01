package com.wahyu.hiringapps2.activities

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.dashboard.HomeActivity
import com.wahyu.hiringapps2.databinding.ActivitySignInBinding
import com.wahyu.hiringapps2.util.BaseActivity
import com.wahyu.hiringapps2.util.KeySharedPreferences
import com.wahyu.hiringapps2.util.SharedPreferencesUtil

class SignInActivity : BaseActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var sharedPref: SharedPreferencesUtil

    override fun initView() {

        binding.etInputEmail.text = intent.getStringExtra(KeyExtraIntent.EXTRA_EMAIL)?.toEditable()
        binding.etInputPassword.text = intent.getStringExtra(KeyExtraIntent.EXTRA_PASSWORD)?.toEditable()
    }

    override fun initListener() {
        binding.buttonSignIn.setOnClickListener {
            when {
                isEmailAndPasswordNotEmpty() -> {
                    when {
                        isEmailAndPasswordCorrect() -> {
                            sharedPref.put(KeySharedPreferences.PREF_IS_LOGIN, true)
                            setShortToast("Login berhasil")
                            intentMoveToHomeActivity()
                        }
                        !isEmailCorrect() -> setShortToast("Email belum terdaftar")
                        else -> setShortToast("Password tidak sesuai")
                    }
                }
                isEmailAndPasswordEmpty() -> setShortToast("Email dan Password tidak boleh kosong")
                isEmailEmpty() -> setShortToast("Email tidak boleh kosong")
                else -> setShortToast("Password tidak boleh kosong")
            }
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

    private fun intentMoveToHomeActivity () {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun isEmailAndPasswordEmpty() : Boolean = binding.etInputEmail.editableText.isNullOrEmpty() && binding.etInputPassword.editableText.isNullOrEmpty()
    private fun isEmailEmpty() : Boolean = binding.etInputEmail.editableText.isNullOrEmpty()
    private fun isEmailAndPasswordNotEmpty() : Boolean = binding.etInputEmail.editableText.isNotEmpty() && binding.etInputPassword.editableText.isNotEmpty()
    private fun isEmailAndPasswordCorrect() : Boolean = binding.etInputEmail.editableText.toString() == sharedPref.getString(KeySharedPreferences.PREF_EMAIL) && binding.etInputPassword.editableText.toString() == sharedPref.getString(KeySharedPreferences.PREF_PASSWORD)
    private fun isEmailCorrect() : Boolean = binding.etInputEmail.editableText.toString() == sharedPref.getString(KeySharedPreferences.PREF_EMAIL)
}