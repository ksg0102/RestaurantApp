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

class GameWarFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_war, container, false)
        runGraidentAnimation(view)

        //Set listeners to take the player to games
        val playButton = view.findViewById<Button>(R.id.warStartButton)
        val exitButton = view.findViewById<Button>(R.id.warLeaveButton)

        playButton.setOnClickListener{ (activity as MenuActivity).replaceFragment(GameWarPlayFragment(),"") }
        exitButton.setOnClickListener{ (activity as MenuActivity).replaceFragment(MenuGameOptionsFragment(),"") }

        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.frameLayout5)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

}
