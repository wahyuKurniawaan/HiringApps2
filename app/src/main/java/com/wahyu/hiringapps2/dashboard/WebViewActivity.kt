package com.wahyu.hiringapps2.dashboard

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.wahyu.hiringapps2.util.BaseActivity
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.databinding.ActivityWebViewBinding
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : BaseActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun initView() {

    }

    override fun initListener() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view)
        binding.webView.webViewClient = WebViewClient()
//        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl("https://github.com/wahyuKurniawaan")
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) webView.goBack()
        else super.onBackPressed()
    }
}