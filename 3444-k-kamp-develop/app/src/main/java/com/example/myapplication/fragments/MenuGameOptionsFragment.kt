package com.example.myapplication.fragments

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.MenuActivity
import com.example.myapplication.fragments.gameFragments.*

class MenuGameOptionsFragment : Fragment() {
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_game_options, container, false)
        runGraidentAnimation(view)

        val blackjackButton = view.findViewById<Button>(R.id.blackjackButton)
        val hiLoButton = view.findViewById<Button>(R.id.hlButton)
        val warButton = view.findViewById<Button>(R.id.warButton)
        val ticTacButton = view.findViewById<Button>(R.id.tictacButton)
        val yahButton = view.findViewById<Button>(R.id.yahButton)
        val exitButton = view.findViewById<Button>(R.id.gameExitButton)

        blackjackButton.setOnClickListener{ (activity as MenuActivity).replaceFragment(GameBlackjackFragment(),"") }
        hiLoButton.setOnClickListener{ (activity as MenuActivity).replaceFragment(GameHLFragment(),"") }
        warButton.setOnClickListener{(activity as MenuActivity).replaceFragment(GameWarFragment(),"") }
        ticTacButton.setOnClickListener{ (activity as MenuActivity).replaceFragment(GameTicTacFragment(),"") }
        yahButton.setOnClickListener{ (activity as MenuActivity).replaceFragment(GameYahtzeeFragment(),"") }
        exitButton.setOnClickListener{ (activity as MenuActivity).replaceFragment(KidsModeLogoutFragment(),"") }

        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.menuGameLayout)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }
}
