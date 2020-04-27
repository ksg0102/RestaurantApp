package com.example.myapplication.fragments.gameFragments

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.MenuActivity
import com.example.myapplication.fragments.MainMenuFragment
import com.example.myapplication.fragments.MenuGameOptionsFragment

class KidsModeLogoutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_kids_mode_logout, container, false)
        runGraidentAnimation(view)

        view.findViewById<Button>(R.id.kidsModeLogoutCancel).setOnClickListener{ (activity as MenuActivity).replaceFragment(MenuGameOptionsFragment(),"") }
        view.findViewById<Button>(R.id.kidsModeLogoutConfirm).setOnClickListener {
            //Exit only allowed with the password previously set or with an employee override using the code, "EMPLOYEE OVERRIDE"
            if (kidsModeLogoutValidate(view.findViewById<EditText>(R.id.kidsModeLogoutEntry).text.toString(), kidsModePassword)) {
                kidsModePassword = ""
                (activity as MenuActivity).replaceFragment(MainMenuFragment(),"")
            }
            else {
                Toast.makeText(activity?.applicationContext, "Please re-enter your password", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    fun kidsModeLogoutValidate(input: String, password: String) : Boolean {
        if (input == password || input == "EMPLOYEE OVERRIDE") return true
        else return false
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.frameLayout8)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

}
