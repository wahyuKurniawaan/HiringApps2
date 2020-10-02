package com.wahyu.hiringapps2.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.wahyu.hiringapps2.util.BaseActivity
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.databinding.ActivitySplashBinding
import com.wahyu.hiringapps2.login.SignInActivity
import com.wahyu.hiringapps2.onBoard.OnBoardActivity

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun initView() {
        val anim = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                val intent = Intent(this@SplashActivity,
                    if (onBoardingFinished()) SignInActivity::class.java else OnBoardActivity::class.java)
                startActivity(intent)
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })

        binding.tvSplashText.startAnimation(anim)
    }

    override fun initListener() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        initView()
        initListener()

    }

    private fun onBoardingFinished(): Boolean {
        val sharedRef = getSharedPreferences("onBoard", Context.MODE_PRIVATE)
        return sharedRef.getBoolean("Finished", false)
    }
}