package com.example.myapplication.fragments.gameFragments

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
import com.example.myapplication.fragments.MenuGameOptionsFragment

class GameYahtzeeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_yahtzee, container, false)
        runGraidentAnimation(view)

        //Set listeners to take the player to games
        view.findViewById<Button>(R.id.yahStartButton).setOnClickListener { (activity as MenuActivity).replaceFragment(GameYahtzeePlayFragment(), "") }
        view.findViewById<Button>(R.id.yahBackToMenu).setOnClickListener { (activity as MenuActivity).replaceFragment(MenuGameOptionsFragment(), "")}

        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.yahtzeeMenuLayout)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

}
