package com.example.myapplication.fragments

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import android.content.Intent
import com.example.myapplication.R
import com.example.myapplication.activities.CustomerAccountActivity
import com.example.myapplication.activities.EmployeeLoginActivity

import com.example.myapplication.activities.MainActivity


class LandingPageFragment : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_landing_page, container, false)
        runGraidentAnimation(view)
        val customerLoginButton = view.findViewById<Button>(R.id.button_customer)
        val staffLoginButton = view.findViewById<Button>(R.id.button_staff)


        customerLoginButton.setOnClickListener{
            val intent = Intent (activity, CustomerAccountActivity::class.java)
            activity?.startActivity(intent)
            //(activity as MainActivity).replaceFragment(CustomerTableNumberFragment(), "")
        }
        staffLoginButton.setOnClickListener{
            val intent = Intent (activity, EmployeeLoginActivity::class.java)
            activity?.startActivity(intent)
        }


        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.login_layout)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

}

