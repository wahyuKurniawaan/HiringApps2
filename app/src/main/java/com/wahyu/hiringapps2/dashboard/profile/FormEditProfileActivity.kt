package com.wahyu.hiringapps2.dashboard.profile

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.databinding.ActivityFormEditProfileBinding
import com.wahyu.hiringapps2.util.BaseActivity

class FormEditProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityFormEditProfileBinding

    override fun initView() {

    }

    override fun initListener() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_form_edit_profile)
    }
}