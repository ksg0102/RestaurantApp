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

class GameBlackjackPlayFragment : Fragment() {
    //Definition of important variables such that they can be universally accessed in the fragmgent.
    var deckOfCards = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52)
    var playerHand = mutableListOf<Int>()
    var dealerHand = mutableListOf<Int>()
    var playerValue = countHand(playerHand)
    var dealerValue = countHand(dealerHand)
    var playerStand = false
    var dealerStand = false
    var lastDealerHand = 2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_blackjack_play, container, false)
        runGraidentAnimation(view)
        view.findViewById<Button>(R.id.bjQuitButton).setOnClickListener{ (activity as MenuActivity).replaceFragment(GameBlackjackFragment(),"") }
        view.findViewById<Button>(R.id.bjHitButton).setOnClickListener{ hit() }
        view.findViewById<Button>(R.id.bjStandButton).setOnClickListener { stand() }

        //Resetting of variables in case a player leaves the fragment and returns - also sets up starting hands.
        deckOfCards = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52)
        playerHand = mutableListOf<Int>(drawCard(deckOfCards), drawCard(deckOfCards))
        dealerHand = mutableListOf<Int>(drawCard(deckOfCards), drawCard(deckOfCards))
        playerValue = countHand(playerHand)
        dealerValue = countHand(dealerHand)
        playerStand = false
        dealerStand = false
        lastDealerHand = 2

        //Setting GUI to game state.
        view.findViewById<TextView>(R.id.bjPlayerHand1).apply {text = cardstr(playerHand[0])}
        view.findViewById<TextView>(R.id.bjPlayerHand2).apply {text = cardstr(playerHand[1])}
        view.findViewById<TextView>(R.id.bjPlayerValue).apply {text = "VALUE: $playerValue" }
        view.findViewById<TextView>(R.id.bjDealerHand2).apply {text = cardstr(dealerHand[1])}

        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.bjFrameLayout)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

    //Player hits - adds one card to player, checks if they went bust, then dealer plays.
    fun hit() {
        playerHand.add(drawCard(deckOfCards))
        playerValue = countHand(playerHand)
        if (playerValue > 21) playerStand = true
        dealerStand = dealerChoose(dealerValue, playerValue, playerStand)
        if (!dealerStand) {
            dealerHand.add(drawCard(deckOfCards))
            dealerValue = countHand(dealerHand)
        }

        if (playerStand) {
            while (!dealerStand) {
                dealerStand = dealerChoose(dealerValue, playerValue, playerStand)
                if (!dealerStand) {
                    dealerHand.add(drawCard(deckOfCards))
                    dealerValue = countHand(dealerHand)
                }
            }
        }

        redraw()
    }

    //Player stands - total is locked in, then dealer plays.
    fun stand() {
        playerStand = true
        while (!dealerStand) {
            dealerStand = dealerChoose(dealerValue, playerValue, playerStand)
            if (!dealerStand) {
                dealerHand.add(drawCard(deckOfCards))
                dealerValue = countHand(dealerHand)
            }
        }

        redraw()
    }

    //Runs all of the textview changes to apply between actions.
    fun redraw() {
        //Draw only the latest card in the hand, since a player can't gain multiple cards between redraws
        when (playerHand.size) {
            3 -> {
                view?.findViewById<TextView>(R.id.bjPlayerHand3)?.apply {
                    text = cardstr(playerHand[2])
                    visibility = View.VISIBLE
                }
            }
            4 -> {
                view?.findViewById<TextView>(R.id.bjPlayerHand4)?.apply {
                    text = cardstr(playerHand[3])
                    visibility = View.VISIBLE
                }
            }
            5 -> {
                view?.findViewById<TextView>(R.id.bjPlayerHand5)?.apply {
                    text = cardstr(playerHand[4])
                    visibility = View.VISIBLE
                }
            }
            6 -> {
                view?.findViewById<TextView>(R.id.bjPlayerHand6)?.apply {
                    text = cardstr(playerHand[5])
                    visibility = View.VISIBLE
                }
            }
            7 -> {
                view?.findViewById<TextView>(R.id.bjPlayerHand7)?.apply {
                    text = cardstr(playerHand[6])
                    visibility = View.VISIBLE
                }
            }
            8 -> {
                view?.findViewById<TextView>(R.id.bjPlayerHand8)?.apply {
                    text = cardstr(playerHand[7])
                    visibility = View.VISIBLE
                }
            }
            9 -> {
                view?.findViewById<TextView>(R.id.bjPlayerHand9)?.apply {
                    text = cardstr(playerHand[8])
                    visibility = View.VISIBLE
                }
            }
            10 -> {
                view?.findViewById<TextView>(R.id.bjPlayerHand10)?.apply {
                    text = cardstr(playerHand[9])
                    visibility = View.VISIBLE
                }
            }
            11 -> {
                view?.findViewById<TextView>(R.id.bjPlayerHand11)?.apply {
                    text = cardstr(playerHand[10])
                    visibility = View.VISIBLE
                }
            }
        }
        val playVal = "VALUE: $playerValue"
        view?.findViewById<TextView>(R.id.bjPlayerValue)?.apply {text = playVal}
        //Draw all cards between the last redraw and this one for the dealer, who can draw indefinitely as long as the player stands first
        for (i in lastDealerHand+1..dealerHand.size) {
            when (i) {
                3 -> view?.findViewById<TextView>(R.id.bjDealerHand3)?.apply {
                    text = cardstr(dealerHand[2])
                    visibility = View.VISIBLE
                }
                4 -> view?.findViewById<TextView>(R.id.bjDealerHand4)?.apply {
                    text = cardstr(dealerHand[3])
                    visibility = View.VISIBLE
                }
                5 -> view?.findViewById<TextView>(R.id.bjDealerHand5)?.apply {
                    text = cardstr(dealerHand[4])
                    visibility = View.VISIBLE
                }
                6 -> view?.findViewById<TextView>(R.id.bjDealerHand6)?.apply {
                    text = cardstr(dealerHand[5])
                    visibility = View.VISIBLE
                }
                7 -> view?.findViewById<TextView>(R.id.bjDealerHand7)?.apply {
                    text = cardstr(dealerHand[6])
                    visibility = View.VISIBLE
                }
                8 -> view?.findViewById<TextView>(R.id.bjDealerHand8)?.apply {
                    text = cardstr(dealerHand[7])
                    visibility = View.VISIBLE
                }
                9 -> view?.findViewById<TextView>(R.id.bjDealerHand9)?.apply {
                    text = cardstr(dealerHand[8])
                    visibility = View.VISIBLE
                }
                10 -> view?.findViewById<TextView>(R.id.bjDealerHand10)?.apply {
                    text = cardstr(dealerHand[9])
                    visibility = View.VISIBLE
                }
                11 -> view?.findViewById<TextView>(R.id.bjDealerHand11)?.apply {
                    text = cardstr(dealerHand[10])
                    visibility = View.VISIBLE
                }
            }
        }

        //Endgame cleanup - show dealer values, hide play buttons, show quit button
        if (playerStand && dealerStand) {
            val dealVal = "VALUE: $dealerValue"
            view?.findViewById<TextView>(R.id.bjDealerHand1)?.apply {text = cardstr(dealerHand[0])}
            view?.findViewById<TextView>(R.id.bjDealerValue)?.apply {text = dealVal}
            var conclusion = "YOU WIN!"
            if (playerValue > 21) conclusion = "YOU WENT BUST!"
            if (dealerValue > playerValue && dealerValue < 22) conclusion = "YOU LOSE!"

            view?.findViewById<TextView>(R.id.bjEndText)?.apply {
                text = conclusion
                visibility = View.VISIBLE
            }

            view?.findViewById<Button>(R.id.bjHitButton)?.apply {visibility = View.INVISIBLE}
            view?.findViewById<Button>(R.id.bjStandButton)?.apply {visibility = View.INVISIBLE}
            view?.findViewById<Button>(R.id.bjQuitButton)?.apply {visibility = View.VISIBLE}
        }
    }

    //Simple function to get a random value from the deck and then remove it.
    fun drawCard(deck: MutableList<Int>): Int {
        val pos = (1..deck.size).random() - 1
        val card = deck[pos]
        deck.removeAt(pos)
        return card
    }

    //Score tallying. Aces high unless it would cause someone to go bust, at which point aces low.
    fun countHand(cards: MutableList<Int>): Int {
        var sum = 0
        var aces = 0
        for (i in cards) {
            //This equation results in: ERROR = 0, ACE = 1, TWO = 2, etc
            when ((i + 3) / 4) {
                1 -> {
                    sum += 11
                    aces++
                }
                2 -> sum += 2
                3 -> sum += 3
                4 -> sum += 4
                5 -> sum += 5
                6 -> sum += 6
                7 -> sum += 7
                8 -> sum += 8
                9 -> sum += 9
                10 -> sum += 10
                11 -> sum += 10
                12 -> sum += 10
                13 -> sum += 10
            }
        }

        //Have assumed a default to high ace, but if high ace will cause the hand to go bust it will automatically become low ace.
        while (sum > 21 && aces > 0) {
            sum -= 10
            aces--
        }
        return sum
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

    fun dealerChoose(value: Int, playerVal: Int, playerStand: Boolean) : Boolean {
        //If dealer has hand of 17 or greater, dealer stands
        if (value > 16) return true
        //If dealer hand beats player hand and player stands, dealer stands
        else if (value >= playerVal && playerStand) return true
        return false
    }
}