package com.example.myapplication.fragments.gameFragments

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.MenuActivity
import com.example.myapplication.fragments.MainMenuFragment
import com.example.myapplication.fragments.MenuGameOptionsFragment

var kidsModePassword = ""

class KidsModeLoginFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_kids_mode_login, container, false)
        runGraidentAnimation(view)

        view.findViewById<Button>(R.id.kidModeLoginCancel).setOnClickListener{ (activity as MenuActivity).replaceFragment(MainMenuFragment(),"") }
        view.findViewById<Button>(R.id.kidModeLoginConfirm).setOnClickListener{
            //Kids' Mode password becomes whatever has been entered on this screen
            kidsModePassword = view.findViewById<EditText>(R.id.kidModeLoginEntry).text.toString()
            (activity as MenuActivity).replaceFragment(MenuGameOptionsFragment(),"")
        }
        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.frameLayout7)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }
}
