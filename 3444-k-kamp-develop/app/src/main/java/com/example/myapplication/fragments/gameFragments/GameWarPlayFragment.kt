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
import com.example.myapplication.fragments.MenuGameOptionsFragment

class GameWarPlayFragment : Fragment() {
    //Definition of important variables such that they can be universally accessed in the fragmgent.
    var deck = mutableListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52)
    var decks = cardSplit(deck)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_war_play, container, false)
        runGraidentAnimation(view)
        view.findViewById<Button>(R.id.warEndButton).setOnClickListener{ (activity as MenuActivity).replaceFragment(GameWarFragment(),"") }
        view.findViewById<Button>(R.id.warNextButton).setOnClickListener{ warRound(view) }

        //War is a deterministic "game", so the first round is already played upon entry.
        warRound(view)

        view.findViewById<TextView>(R.id.WarPlayerDeck).apply { text = "CARDS: " + decks[0].size}
        view.findViewById<TextView>(R.id.WarEnemyDeck).apply { text = "CARDS: " + decks[1].size}

        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.frameLayout6)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

    //Card translator
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
    //Splits the deck into two half-sized player decks, guaranteed not to contain any matched cards.
    fun cardSplit(deck: MutableList<Int>) : MutableList<MutableList<Int>> {
        var position = 0
        var playerDeck = mutableListOf<Int>()
        var enemyDeck = mutableListOf<Int>()
        for (i in (1..26)) {
            position = (1..deck.size).random()
            playerDeck.add(deck[position-1])
            deck.removeAt(position-1)
        }
        for (i in (1..26)) {
            position = (1..deck.size).random()
            enemyDeck.add(deck[position-1])
            deck.removeAt(position-1)
        }
        val decks = mutableListOf<MutableList<Int>>(playerDeck, enemyDeck)
        return decks
    }

    //Driver code - compares the "top" of each player's deck. If those cards are matched, compare two down, etc. Winner takes all cards down to the winning set.
    fun warRound(view: View) {
        view.findViewById<TextView>(R.id.WarPlayerDeck).apply { text = "CARDS: " + decks[0].size }
        view.findViewById<TextView>(R.id.WarEnemyDeck).apply { text = "CARDS: " + decks[1].size }

        var contests = 0
        while (true) {
            if (decks[0].size < (contests * 2 + 1)) {
                view.findViewById<TextView>(R.id.WarVictory).apply { text = "YOU HAVE RUN OUT OF CARDS! YOU LOSE!" }
                break
            }
            if (decks[1].size < (contests * 2 + 1)) {
                view.findViewById<TextView>(R.id.WarVictory).apply { text = "THE OTHER PLAYER HAS RUN OUT OF CARDS! YOU WIN!" }
                break
            }

            if (contests == 0) {
                view.findViewById<TextView>(R.id.WarPlayerCard).apply { text = cardstr(decks[0][0]) }
                view.findViewById<TextView>(R.id.WarEnemyCard).apply { text = cardstr(decks[1][0]) }
            }
            else {
                view.findViewById<TextView>(R.id.WarPlayerContest).apply { text = cardstr(decks[0][contests * 2]) }
                view.findViewById<TextView>(R.id.WarEnemyContest).apply { text = cardstr(decks[1][contests * 2]) }
                view.findViewById<TextView>(R.id.WarPlayerAdd).apply { text = "+" + ((contests*2)-1)}
                view.findViewById<TextView>(R.id.WarEnemyAdd).apply { text = "+" + ((contests*2)-1)}
            }
            if (((decks[0][2*contests]+3)/4) == ((decks[1][2*contests]+3)/4)) {
                contests += 1
            }
            else break
        }
        val hand = mutableListOf<Int>()

        //Add the cards to the pot, removing them from each player's deck
        hand.add(decks[0][0])
        decks[0].removeAt(0)
        hand.add(decks[1][0])
        decks[1].removeAt(0)

        var i : Int = 0
        while (i < contests) {
            //Add the mystery cards to the pot
            hand.add(decks[0][0])
            decks[0].removeAt(0)
            hand.add(decks[1][0])
            decks[1].removeAt(0)
            //Add the cards used for the contest to the pot
            hand.add(decks[0][0])
            decks[0].removeAt(0)
            hand.add(decks[1][0])
            decks[1].removeAt(0)
            i += 1
        }

        //Manage visibility of the play space based on what happened in the round
        if (contests == 0) {
            view.findViewById<TextView>(R.id.WarEnemyAdd).apply { visibility = View.INVISIBLE }
            view.findViewById<TextView>(R.id.WarPlayerAdd).apply { visibility = View.INVISIBLE }
            view.findViewById<TextView>(R.id.WarPlayerContest).apply { visibility = View.INVISIBLE }
            view.findViewById<TextView>(R.id.WarEnemyContest).apply { visibility = View.INVISIBLE }
        }
        else {
            view.findViewById<TextView>(R.id.WarEnemyAdd).apply { visibility = View.VISIBLE }
            view.findViewById<TextView>(R.id.WarPlayerAdd).apply { visibility = View.VISIBLE }
            view.findViewById<TextView>(R.id.WarPlayerContest).apply { visibility = View.VISIBLE }
            view.findViewById<TextView>(R.id.WarEnemyContest).apply { visibility = View.VISIBLE }
        }

        //If the final card played by the player was victorious
        if (hand[hand.size-2] > hand[hand.size-1]) {
            //Add hand to player deck
            decks[0].addAll(hand)
            view.findViewById<TextView>(R.id.WarVictory).apply { text = "YOU WIN THE ROUND!" }
        }
        //Otherwise
        else {
            //Add hand to opposition deck
            decks[1].addAll(hand)
            view.findViewById<TextView>(R.id.WarVictory).apply { text = "YOU LOSE THE ROUND!" }
        }
    }
}
