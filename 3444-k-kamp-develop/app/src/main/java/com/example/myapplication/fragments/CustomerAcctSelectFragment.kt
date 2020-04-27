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
import com.example.myapplication.activities.MenuActivity


/* This fragment allows for the customer to sign in, make an account, or go to the menu */
class CustomerAcctSelectFragment : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_customer_acct_select, container, false)
        runGraidentAnimation(view)

        /* Create variables for the buttons */
        val sign_in_button = view.findViewById<Button>(R.id.button_sign_in)
        val register_button = view.findViewById<Button>(R.id.button_register)
        val skip_register_button = view.findViewById<Button>(R.id.button_skip_registration)

        /* Implement button actions */
        sign_in_button.setOnClickListener{
            (activity as CustomerAccountActivity).replaceFragment(CustomerLoginFragment(),"")
        }

        register_button.setOnClickListener{
            (activity as CustomerAccountActivity).replaceFragment(CustomerRegisterFragment(),"")
        }

        skip_register_button.setOnClickListener{
            val intent = Intent (activity, MenuActivity::class.java)
            activity?.startActivity(intent)
        }

        return view
    }

    /* This method runs the gradient background on the screen using components in the XML  */
    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.fragment_customer_acct_select)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

}
