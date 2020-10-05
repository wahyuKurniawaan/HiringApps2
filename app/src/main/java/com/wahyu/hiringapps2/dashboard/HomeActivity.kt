package com.wahyu.hiringapps2.dashboard

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.dashboard.search.SearchFragment
import com.wahyu.hiringapps2.dashboard.profile.ProfileFragment
import com.wahyu.hiringapps2.dashboard.home.HomeFragment
import com.wahyu.hiringapps2.dashboard.project.ProjectsFragment
import com.wahyu.hiringapps2.databinding.ActivityMainBinding
import com.wahyu.hiringapps2.util.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private val homeFragment = HomeFragment()
    private val offersFragment = SearchFragment()
    private val profileFragment = ProfileFragment()
    private val projectsFragment = ProjectsFragment()


    override fun initView() {
        makeCurrentFragment(homeFragment)
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }

    override fun initListener() {
        bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_nav_home -> makeCurrentFragment(homeFragment)
                R.id.bottom_nav_search -> makeCurrentFragment(offersFragment)
                R.id.bottom_nav_profile -> makeCurrentFragment(profileFragment)
                R.id.bottom_nav_projects -> makeCurrentFragment(projectsFragment)
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()
        initListener()
    }

    override fun onBackPressed() {
        closeWithDialogConfirm()
    }
}