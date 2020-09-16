package com.wahyu.hiringapps2.onBoard.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.wahyu.hiringapps2.R
import kotlinx.android.synthetic.main.fragment_third_screen.view.*

class ThirdScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_third_screen, container, false)

//        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPagerContainer)

        view.textViewFinish.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_signInActivity)
            onBoardingFinished()
        }

        return view
    }

    private fun onBoardingFinished() {
        val sharedRef = requireActivity().getSharedPreferences("onBoard", Context.MODE_PRIVATE)
        val editor = sharedRef.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

}