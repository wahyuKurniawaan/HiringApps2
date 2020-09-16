package com.wahyu.hiringapps2.onBoard

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.wahyu.hiringapps2.util.BaseActivity
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.databinding.OnboardActivityBinding

class OnBoardActivity : BaseActivity() {

    private lateinit var binding: OnboardActivityBinding

    override fun initView() {

    }

    override fun initListener() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.onboard_activity)
        initView()
        initListener()
    }
}