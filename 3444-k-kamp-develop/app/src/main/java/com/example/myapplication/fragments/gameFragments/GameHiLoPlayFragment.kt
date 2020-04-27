package com.example.myapplication.fragments.gameFragments

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.MenuActivity

class GameHiLoPlayFragment : Fragment() {
    //Definition of important variables such that they can be universally accessed in the fragmgent.
    var firstcard = 0
    var secondcard = 0
    var hiOrLo = false

    fun gamestart(/*inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?*/) : View? {
        val view = view

        firstcard = (1..52).random()
        secondcard = (1..52).random()
        while (firstcard == secondcard) secondcard = (1..52).random()
        hiOrLo = firstcard < secondcard
        
        return view
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_game_hi_lo_play, container, false)
        runGraidentAnimation(view)
        view.findViewById<Button>(R.id.hlLoButton).setOnClickListener { hlClickLo(view) }
        view.findViewById<Button>(R.id.hlHiButton).setOnClickListener { hlClickHi(view) }
        view.findViewById<Button>(R.id.hlReplayButton).setOnClickListener { hlReplay(view) }
        view.findViewById<Button>(R.id.hlStart).setOnClickListener { hlStartGame(view) }

        val quitButton = view.findViewById<Button>(R.id.hlQuitButton)
        quitButton.setOnClickListener{ (activity as MenuActivity).replaceFragment(GameHLFragment(), "")}

        //Resetting of initial variables in case a player somehow leaves and rejoins to manipulate information.
        firstcard = (1..52).random()
        secondcard = (1..52).random()
        while (firstcard == secondcard) secondcard = (1..52).random()
        hiOrLo = firstcard < secondcard

        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.frameLayout4)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

    //Sets visibilities according to the initial game state and changes textviews.
    fun hlStartGame(view: View) {
        view.findViewById<TextView>(R.id.hlFirstLabel).apply { visibility = View.VISIBLE }
        view.findViewById<TextView>(R.id.hlFirstCard).apply {
            text = cardstr(firstcard)
            visibility = View.VISIBLE
        }
        view.findViewById<TextView>(R.id.hlSecondCard).apply { text = cardstr(secondcard) }
        view.findViewById<Button>(R.id.hlHiButton).apply { visibility = View.VISIBLE }
        view.findViewById<Button>(R.id.hlLoButton).apply { visibility = View.VISIBLE }
        view.findViewById<Button>(R.id.hlStart).apply { visibility = View.INVISIBLE }
    }

    //Player guesses high - hidden card is revealed.
    fun hlClickHi(view: View) {
        view.findViewById<TextView>(R.id.hlSecondLabel).apply { visibility = View.VISIBLE }
        view.findViewById<TextView>(R.id.hlSecondCard).apply { visibility = View.VISIBLE }
        if (hiOrLo) {
            view.findViewById<TextView>(R.id.hlResultText).apply {
                text = "YOU WIN!"
                visibility = View.VISIBLE
            }
        }
        else {
            view.findViewById<TextView>(R.id.hlResultText).apply {
                text = "YOU LOSE!"
                visibility = View.VISIBLE
            }
        }
        view.findViewById<Button>(R.id.hlHiButton).apply {visibility = View.GONE}
        view.findViewById<Button>(R.id.hlLoButton).apply {visibility = View.GONE}
        view.findViewById<Button>(R.id.hlReplayButton).apply {visibility = View.VISIBLE}
        view.findViewById<Button>(R.id.hlQuitButton).apply {visibility = View.VISIBLE}
    }
    //Player guesses low - hidden card is revealed.
    fun hlClickLo(view: View) {
        view.findViewById<TextView>(R.id.hlSecondLabel).apply { visibility = View.VISIBLE }
        view.findViewById<TextView>(R.id.hlSecondCard).apply { visibility = View.VISIBLE }
        if (hiOrLo) {
            view.findViewById<TextView>(R.id.hlResultText).apply {
                text = "YOU LOSE!"
                visibility = View.VISIBLE
            }
        }
        else {
            view.findViewById<TextView>(R.id.hlResultText).apply {
                text = "YOU WIN!"
                visibility = View.VISIBLE
            }
        }
        view.findViewById<Button>(R.id.hlHiButton).apply {visibility = View.GONE}
        view.findViewById<Button>(R.id.hlLoButton).apply {visibility = View.GONE}
        view.findViewById<Button>(R.id.hlReplayButton).apply {visibility = View.VISIBLE}
        view.findViewById<Button>(R.id.hlQuitButton).apply {visibility = View.VISIBLE}
    }

    //Game resets - visibilities are returned to normal and new cards are drawn.
    fun hlReplay(view: View) {
        firstcard = (1..52).random()
        secondcard = (1..52).random()
        while (firstcard == secondcard) secondcard = (1..52).random()
        hiOrLo = firstcard < secondcard

        view.findViewById<TextView>(R.id.hlFirstCard).apply { text = cardstr(firstcard)}
        view.findViewById<TextView>(R.id.hlSecondLabel).apply {visibility = View.INVISIBLE}
        view.findViewById<TextView>(R.id.hlSecondCard).apply {
            text = cardstr(secondcard)
            visibility = View.INVISIBLE
        }
        view.findViewById<Button>(R.id.hlHiButton).apply { visibility = View.VISIBLE }
        view.findViewById<Button>(R.id.hlLoButton).apply { visibility = View.VISIBLE }
        view.findViewById<Button>(R.id.hlReplayButton).apply { visibility = View.GONE }
        view.findViewById<Button>(R.id.hlQuitButton).apply { visibility = View.GONE }
        view.findViewById<TextView>(R.id.hlResultText).apply {visibility = View.INVISIBLE }
    }

    //Card translator.
    fun cardstr(card: Int) : String {
        when (card) {
            1 -> return "ACE OF CLUBS"
            2 -> return "ACE OF HEARTS"
            3 -> return "ACE OF DIAMONDS"
            4 -> return "ACE OF SPADES"
            5 -> return "TWO OF CLUBS"
            6 -> return "TWO OF HEARTS"
            7 -> return "TWO OF DIAMONDS"
            8 -> return "TWO OF SPADES"
            9 -> return "THREE OF CLUBS"
            10 -> return "THREE OF HEARTS"
            11 -> return "THREE OF DIAMONDS"
            12 -> return "THREE OF SPADES"
            13 -> return "FOUR OF CLUBS"
            14 -> return "FOUR OF HEARTS"
            15 -> return "FOUR OF DIAMONDS"
            16 -> return "FOUR OF SPADES"
            17 -> return "FIVE OF CLUBS"
            18 -> return "FIVE OF HEARTS"
            19 -> return "FIVE OF DIAMONDS"
            20 -> return "FIVE OF SPADES"
            21 -> return "SIX OF CLUBS"
            22 -> return "SIX OF HEARTS"
            23 -> return "SIX OF DIAMONDS"
            24 -> return "SIX OF SPADES"
            25 -> return "SEVEN OF CLUBS"
            26 -> return "SEVEN OF HEARTS"
            27 -> return "SEVEN OF DIAMONDS"
            28 -> return "SEVEN OF SPADES"
            29 -> return "EIGHT OF CLUBS"
            30 -> return "EIGHT OF HEARTS"
            31 -> return "EIGHT OF DIAMONDS"
            32 -> return "EIGHT OF SPADES"
            33 -> return "NINE OF CLUBS"
            34 -> return "NINE OF HEARTS"
            35 -> return "NINE OF DIAMONDS"
            36 -> return "NINE OF SPADES"
            37 -> return "TEN OF CLUBS"
            38 -> return "TEN OF HEARTS"
            39 -> return "TEN OF DIAMONDS"
            40 -> return "TEN OF SPADES"
            41 -> return "JACK OF CLUBS"
            42 -> return "JACK OF HEARTS"
            43 -> return "JACK OF DIAMONDS"
            44 -> return "JACK OF SPADES"
            45 -> return "QUEEN OF CLUBS"
            46 -> return "QUEEN OF HEARTS"
            47 -> return "QUEEN OF DIAMONDS"
            48 -> return "QUEEN OF SPADES"
            49 -> return "KING OF CLUBS"
            50 -> return "KING OF HEARTS"
            51 -> return "KING OF DIAMONDS"
            52 -> return "KING OF SPADES"
        }
        return "ERROR"
    }

}
