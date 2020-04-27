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

class GameYahtzeePlayFragment : Fragment() {
    var activeDice = mutableListOf<Int>(0, 0, 0, 0, 0)
    var keptDice = mutableListOf<Int>(0, 0, 0, 0, 0)
    var rerolls = 3
    var playerTwo = false
    var playerOneScore = 0
    var playerTwoScore = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_game_yahtzee_play, container, false)
        runGraidentAnimation(view)

        //Establishing of the listeners for the game
        view.findViewById<Button>(R.id.yahKeep1).setOnClickListener{ keepRoll(1) }
        view.findViewById<Button>(R.id.yahKeep2).setOnClickListener{ keepRoll(2) }
        view.findViewById<Button>(R.id.yahKeep3).setOnClickListener{ keepRoll(3) }
        view.findViewById<Button>(R.id.yahKeep4).setOnClickListener{ keepRoll(4) }
        view.findViewById<Button>(R.id.yahKeep5).setOnClickListener{ keepRoll(5) }
        view.findViewById<Button>(R.id.yahRerollButton).setOnClickListener{ reroll() }
        view.findViewById<Button>(R.id.yahTallyScore).setOnClickListener{ endTurn() }
        view.findViewById<Button>(R.id.yahQuitButton).setOnClickListener{ (activity as MenuActivity).replaceFragment(GameYahtzeeFragment(), "") }
        view.findViewById<Button>(R.id.yahReplayButton).setOnClickListener{ replayGame() }

        //Initial rolling of the dice and setting of display fields.
        for (i in (0..4)) {
            activeDice[i] = (1..6).random()
        }
        view.findViewById<TextView>(R.id.yahAct1).apply { text = activeDice[0].toString() }
        view.findViewById<TextView>(R.id.yahAct2).apply { text = activeDice[1].toString() }
        view.findViewById<TextView>(R.id.yahAct3).apply { text = activeDice[2].toString() }
        view.findViewById<TextView>(R.id.yahAct4).apply { text = activeDice[3].toString() }
        view.findViewById<TextView>(R.id.yahAct5).apply { text = activeDice[4].toString() }

        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.yahtzeePlayLayout)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

    //Parameter passed as int denotes which of the five dice is being withheld from further rerolls. Number position moves and button hides to indicate this.
    fun keepRoll(die: Int) {
        keptDice[die-1] = activeDice[die-1]
        activeDice[die-1] = 0
        when (die) {
            1 -> {
                view?.findViewById<TextView>(R.id.yahKept1)?.apply {
                    text = keptDice[die-1].toString()
                    visibility = View.VISIBLE
                }
                view?.findViewById<TextView>(R.id.yahAct1)?.apply {
                    text = "0"
                    visibility = View.INVISIBLE
                }
                view?.findViewById<Button>(R.id.yahKeep1)?.apply { visibility = View.INVISIBLE }
            }
            2 -> {
                view?.findViewById<TextView>(R.id.yahKept2)?.apply {
                    text = keptDice[die-1].toString()
                    visibility = View.VISIBLE
                }
                view?.findViewById<TextView>(R.id.yahAct2)?.apply {
                    text = "0"
                    visibility = View.INVISIBLE
                }
                view?.findViewById<Button>(R.id.yahKeep2)?.apply { visibility = View.INVISIBLE }
            }
            3 -> {
                view?.findViewById<TextView>(R.id.yahKept3)?.apply {
                    text = keptDice[die-1].toString()
                    visibility = View.VISIBLE
                }
                view?.findViewById<TextView>(R.id.yahAct3)?.apply {
                    text = "0"
                    visibility = View.INVISIBLE
                }
                view?.findViewById<Button>(R.id.yahKeep3)?.apply { visibility = View.INVISIBLE }
            }
            4 -> {
                view?.findViewById<TextView>(R.id.yahKept4)?.apply {
                    text = keptDice[die-1].toString()
                    visibility = View.VISIBLE
                }
                view?.findViewById<TextView>(R.id.yahAct4)?.apply {
                    text = "0"
                    visibility = View.INVISIBLE
                }
                view?.findViewById<Button>(R.id.yahKeep4)?.apply { visibility = View.INVISIBLE }
            }
            5 -> {
                view?.findViewById<TextView>(R.id.yahKept5)?.apply {
                    text = keptDice[die-1].toString()
                    visibility = View.VISIBLE
                }
                view?.findViewById<TextView>(R.id.yahAct5)?.apply {
                    text = "0"
                    visibility = View.INVISIBLE
                }
                view?.findViewById<Button>(R.id.yahKeep5)?.apply { visibility = View.INVISIBLE }
            }
        }
    }

    //Re-randomizes all dice not kept and updates display fields.
    fun reroll() {
        for (i in (0..4)) {
            if (activeDice[i] != 0) {
                activeDice[i] = (1..6).random()
            }
        }
        view?.findViewById<TextView>(R.id.yahAct1)?.apply { text = activeDice[0].toString() }
        view?.findViewById<TextView>(R.id.yahAct2)?.apply { text = activeDice[1].toString() }
        view?.findViewById<TextView>(R.id.yahAct3)?.apply { text = activeDice[2].toString() }
        view?.findViewById<TextView>(R.id.yahAct4)?.apply { text = activeDice[3].toString() }
        view?.findViewById<TextView>(R.id.yahAct5)?.apply { text = activeDice[4].toString() }
        rerolls -= 1
        view?.findViewById<TextView>(R.id.yahRerollText)?.apply { text = rerolls.toString() }
        if (rerolls == 0) endTurn()
    }

    /*This isn't how actual Yahtzee works - let's call this a "simple" version. For kids!
    *
    * There's only a single round - players get 1 point for every 1, 2 points for every 2, etc. They also get points for their sum, and for:
    * Three of a kind: The sum again
    * Four of a kind: The sum again (for 4x the sum in total)
    * Five of a kind (aka Yahtzee): 50 points
    * Three of a kind and a pair (aka full house): 25 points
    * Four in a row (small straight, for example, 1, 2, 3, and 4): 30 points
    * Five in a row (large straight, for example, 1, 2, 3, 4, and 5): 40 points
    * All points are cumulative.*/
    fun endTurn() {
        var sum = 0
        var ones = 0
        var twos = 0
        var threes = 0
        var fours = 0
        var fives = 0
        var sixes = 0
        if (!playerTwo) {
            playerTwo = true

            //Score tallying logic.
            for (i in (0..4)) {
                when (activeDice[i]) {
                    0 -> {
                        when (keptDice[i]) {
                            1 -> { ones += 1 }
                            2 -> { twos += 1 }
                            3 -> { threes += 1 }
                            4 -> { fours += 1 }
                            5 -> { fives += 1 }
                            6 -> { sixes += 1 }
                        }
                    }
                    1 -> { ones += 1}
                    2 -> { twos += 1}
                    3 -> { threes += 1}
                    4 -> { fours += 1}
                    5 -> { fives += 1}
                    6 -> { sixes += 1}
                }
            }

            //Top half: sums
            view?.findViewById<TextView>(R.id.yahOnes1)?.apply { text = ones.toString()  }
            view?.findViewById<TextView>(R.id.yahTwos1)?.apply { text = (twos * 2).toString()  }
            view?.findViewById<TextView>(R.id.yahThrees1)?.apply { text = (threes * 3).toString()  }
            view?.findViewById<TextView>(R.id.yahFours1)?.apply { text = (fours * 4).toString()  }
            view?.findViewById<TextView>(R.id.yahFives1)?.apply { text = (fives * 5).toString()  }
            view?.findViewById<TextView>(R.id.yahSixes1)?.apply { text = (sixes * 6).toString()  }
            sum = ones + (twos * 2) + (threes * 3) + (fours * 4) + (fives * 5) + (sixes * 6)
            view?.findViewById<TextView>(R.id.yahSum1)?.apply { text = sum.toString() }
            view?.findViewById<TextView>(R.id.yahChance1)?.apply { text = sum.toString() }

            playerOneScore = sum * 2

            //Bottom half: combos
            if (ones > 2) {
                view?.findViewById<TextView>(R.id.yahThreeKind1)?.apply { sum.toString() }
                playerOneScore += sum
                if (ones > 3) {
                    view?.findViewById<TextView>(R.id.yahFourKind1)?.apply { sum.toString() }
                    playerOneScore += sum
                    if (ones == 5) {
                        view?.findViewById<TextView>(R.id.yahYahtzee1)?.apply { text = "50" }
                        playerOneScore += 50
                    }
                }
                if (twos == 2 || threes == 2 || fours == 2 || fives == 2 || sixes == 2) {
                    view?.findViewById<TextView>(R.id.yahFullHouse1)?.apply { text = "25" }
                    playerOneScore += 25
                }
            }
            else if (twos > 2) {
                view?.findViewById<TextView>(R.id.yahThreeKind1)?.apply { sum.toString() }
                playerOneScore += sum
                if (twos > 3) {
                    view?.findViewById<TextView>(R.id.yahFourKind1)?.apply { sum.toString() }
                    playerOneScore += sum
                    if (twos == 5) {
                        view?.findViewById<TextView>(R.id.yahYahtzee1)?.apply { text = "50" }
                        playerOneScore += 50
                    }
                }
                if (ones == 2 || threes == 2 || fours == 2 || fives == 2 || sixes == 2) {
                    view?.findViewById<TextView>(R.id.yahFullHouse1)?.apply { text = "25" }
                    playerOneScore += 25
                }
            }
            else if (threes > 2) {
                view?.findViewById<TextView>(R.id.yahThreeKind1)?.apply { sum.toString() }
                playerOneScore += sum
                if (threes > 3) {
                    view?.findViewById<TextView>(R.id.yahFourKind1)?.apply { sum.toString() }
                    playerOneScore += sum
                    if (threes == 5) {
                        view?.findViewById<TextView>(R.id.yahYahtzee1)?.apply { text = "50" }
                        playerOneScore += 50
                    }
                }
                if (ones == 2 || twos == 2 || fours == 2 || fives == 2 || sixes == 2) {
                    view?.findViewById<TextView>(R.id.yahFullHouse1)?.apply { text = "25" }
                    playerOneScore += 25
                }
            }
            else if (fours > 2) {
                view?.findViewById<TextView>(R.id.yahThreeKind1)?.apply { sum.toString() }
                playerOneScore += sum
                if (fours > 3) {
                    view?.findViewById<TextView>(R.id.yahFourKind1)?.apply { sum.toString() }
                    playerOneScore += sum
                    if (fours == 5) {
                        view?.findViewById<TextView>(R.id.yahYahtzee1)?.apply { text = "50" }
                        playerOneScore += 50
                    }
                }
                if (ones == 2 || twos == 2 || threes == 2 || fives == 2 || sixes == 2) {
                    view?.findViewById<TextView>(R.id.yahFullHouse1)?.apply { text = "25" }
                    playerOneScore += 25
                }
            }
            else if (fives > 2) {
                view?.findViewById<TextView>(R.id.yahThreeKind1)?.apply { sum.toString() }
                playerOneScore += sum
                if (fives > 3) {
                    view?.findViewById<TextView>(R.id.yahFourKind1)?.apply { sum.toString() }
                    playerOneScore += sum
                    if (fives == 5) {
                        view?.findViewById<TextView>(R.id.yahYahtzee1)?.apply { text = "50" }
                        playerOneScore += 50
                    }
                }
                if (ones == 2 || twos == 2 || threes == 2 || fours == 2 || sixes == 2) {
                    view?.findViewById<TextView>(R.id.yahFullHouse1)?.apply { text = "25" }
                    playerOneScore += 25
                }
            }
            else if (sixes > 2) {
                view?.findViewById<TextView>(R.id.yahThreeKind1)?.apply { sum.toString() }
                playerOneScore += sum
                if (sixes > 3) {
                    view?.findViewById<TextView>(R.id.yahFourKind1)?.apply { sum.toString() }
                    playerOneScore += sum
                    if (sixes == 5) {
                        view?.findViewById<TextView>(R.id.yahYahtzee1)?.apply { text = "50" }
                        playerOneScore += 50
                    }
                }
                if (ones == 2 || twos == 2 || threes == 2 || fours == 2 || fives == 2) {
                    view?.findViewById<TextView>(R.id.yahFullHouse1)?.apply { text = "25" }
                    playerOneScore += 25
                }
            }

            if ((ones > 0 && twos > 0 && threes > 0 && fours > 0) || (twos > 0 && threes > 0 && fours > 0 && fives > 0) || (threes > 0 && fours > 0 && fives > 0 && sixes > 0)) {
                view?.findViewById<TextView>(R.id.yahSStraight1)?.apply { text = "30" }
                playerOneScore += 30
            }
            if ((ones == 1 && twos == 1 && threes == 1 && fours == 1 && fives == 1) || (twos == 1 && threes == 1 && fours == 1 && fives == 1 && sixes == 1)) {
                view?.findViewById<TextView>(R.id.yahLStraight1)?.apply { text = "40" }
                playerOneScore += 40
            }

            view?.findViewById<TextView>(R.id.yahTotal1)?.apply { text = playerOneScore.toString() }

            rerolls = 3

            for (i in (0..4)) {
                activeDice[i] = (1..6).random()
                keptDice[i] = 0
            }
            view?.findViewById<TextView>(R.id.yahAct1)?.apply {
                text = activeDice[0].toString()
                visibility = View.VISIBLE
            }
            view?.findViewById<TextView>(R.id.yahKept1)?.apply {
                text = "0"
                visibility = View.INVISIBLE
            }
            view?.findViewById<Button>(R.id.yahKeep1)?.apply { visibility = View.VISIBLE }
            view?.findViewById<TextView>(R.id.yahAct2)?.apply {
                text = activeDice[1].toString()
                visibility = View.VISIBLE
            }
            view?.findViewById<TextView>(R.id.yahKept2)?.apply {
                text = "0"
                visibility = View.INVISIBLE
            }
            view?.findViewById<Button>(R.id.yahKeep2)?.apply { visibility = View.VISIBLE }
            view?.findViewById<TextView>(R.id.yahAct3)?.apply {
                text = activeDice[2].toString()
                visibility = View.VISIBLE
            }
            view?.findViewById<TextView>(R.id.yahKept3)?.apply {
                text = "0"
                visibility = View.INVISIBLE
            }
            view?.findViewById<Button>(R.id.yahKeep3)?.apply { visibility = View.VISIBLE }
            view?.findViewById<TextView>(R.id.yahAct4)?.apply {
                text = activeDice[3].toString()
                visibility = View.VISIBLE
            }
            view?.findViewById<TextView>(R.id.yahKept4)?.apply {
                text = "0"
                visibility = View.INVISIBLE
            }
            view?.findViewById<Button>(R.id.yahKeep4)?.apply { visibility = View.VISIBLE }
            view?.findViewById<TextView>(R.id.yahAct5)?.apply {
                text = activeDice[4].toString()
                visibility = View.VISIBLE
            }
            view?.findViewById<TextView>(R.id.yahKept5)?.apply {
                text = "0"
                visibility = View.INVISIBLE
            }
            view?.findViewById<Button>(R.id.yahKeep5)?.apply { visibility = View.VISIBLE }
            view?.findViewById<TextView>(R.id.yahTurnMark)?.apply { text = "PLAYER TWO'S TURN" }
            view?.findViewById<TextView>(R.id.yahRerollText)?.apply { text = rerolls.toString() }

        }
        else {
            playerTwo = false

            //Score tallying logic.
            for (i in (0..4)) {
                when (activeDice[i]) {
                    0 -> {
                        when (keptDice[i]) {
                            1 -> { ones += 1 }
                            2 -> { twos += 1 }
                            3 -> { threes += 1 }
                            4 -> { fours += 1 }
                            5 -> { fives += 1 }
                            6 -> { sixes += 1 }
                        }
                    }
                    1 -> { ones += 1}
                    2 -> { twos += 1}
                    3 -> { threes += 1}
                    4 -> { fours += 1}
                    5 -> { fives += 1}
                    6 -> { sixes += 1}
                }
            }

            //Top half: sums
            view?.findViewById<TextView>(R.id.yahOnes2)?.apply { text = ones.toString()  }
            view?.findViewById<TextView>(R.id.yahTwos2)?.apply { text = (twos * 2).toString()  }
            view?.findViewById<TextView>(R.id.yahThrees2)?.apply { text = (threes * 3).toString()  }
            view?.findViewById<TextView>(R.id.yahFours2)?.apply { text = (fours * 4).toString()  }
            view?.findViewById<TextView>(R.id.yahFives2)?.apply { text = (fives * 5).toString()  }
            view?.findViewById<TextView>(R.id.yahSixes2)?.apply { text = (sixes * 6).toString()  }
            sum = ones + (twos * 2) + (threes * 3) + (fours * 4) + (fives * 5) + (sixes * 6)
            view?.findViewById<TextView>(R.id.yahSum2)?.apply { text = sum.toString() }
            view?.findViewById<TextView>(R.id.yahChance2)?.apply { text = sum.toString() }

            playerTwoScore = sum * 2

            //Bottom half: combos
            if (ones > 2) {
                view?.findViewById<TextView>(R.id.yahThreeKind2)?.apply { sum.toString() }
                playerTwoScore += sum
                if (ones > 3) {
                    view?.findViewById<TextView>(R.id.yahFourKind2)?.apply { sum.toString() }
                    playerTwoScore += sum
                    if (ones == 5) {
                        view?.findViewById<TextView>(R.id.yahYahtzee2)?.apply { text = "50" }
                        playerTwoScore += 50
                    }
                }
                if (twos == 2 || threes == 2 || fours == 2 || fives == 2 || sixes == 2) {
                    view?.findViewById<TextView>(R.id.yahFullHouse2)?.apply { text = "25" }
                    playerTwoScore += 25
                }
            }
            else if (twos > 2) {
                view?.findViewById<TextView>(R.id.yahThreeKind2)?.apply { sum.toString() }
                playerTwoScore += sum
                if (twos > 3) {
                    view?.findViewById<TextView>(R.id.yahFourKind2)?.apply { sum.toString() }
                    playerTwoScore += sum
                    if (twos == 5) {
                        view?.findViewById<TextView>(R.id.yahYahtzee2)?.apply { text = "50" }
                        playerTwoScore += 50
                    }
                }
                if (ones == 2 || threes == 2 || fours == 2 || fives == 2 || sixes == 2) {
                    view?.findViewById<TextView>(R.id.yahFullHouse2)?.apply { text = "25" }
                    playerTwoScore += 25
                }
            }
            else if (threes > 2) {
                view?.findViewById<TextView>(R.id.yahThreeKind2)?.apply { sum.toString() }
                playerTwoScore += sum
                if (threes > 3) {
                    view?.findViewById<TextView>(R.id.yahFourKind2)?.apply { sum.toString() }
                    playerTwoScore += sum
                    if (threes == 5) {
                        view?.findViewById<TextView>(R.id.yahYahtzee2)?.apply { text = "50" }
                        playerTwoScore += 50
                    }
                }
                if (ones == 2 || twos == 2 || fours == 2 || fives == 2 || sixes == 2) {
                    view?.findViewById<TextView>(R.id.yahFullHouse2)?.apply { text = "25" }
                    playerTwoScore += 25
                }
            }
            else if (fours > 2) {
                view?.findViewById<TextView>(R.id.yahThreeKind2)?.apply { sum.toString() }
                playerTwoScore += sum
                if (fours > 3) {
                    view?.findViewById<TextView>(R.id.yahFourKind2)?.apply { sum.toString() }
                    playerTwoScore += sum
                    if (fours == 5) {
                        view?.findViewById<TextView>(R.id.yahYahtzee2)?.apply { text = "50" }
                        playerTwoScore += 50
                    }
                }
                if (ones == 2 || twos == 2 || threes == 2 || fives == 2 || sixes == 2) {
                    view?.findViewById<TextView>(R.id.yahFullHouse2)?.apply { text = "25" }
                    playerTwoScore += 25
                }
            }
            else if (fives > 2) {
                view?.findViewById<TextView>(R.id.yahThreeKind2)?.apply { sum.toString() }
                playerTwoScore += sum
                if (fives > 3) {
                    view?.findViewById<TextView>(R.id.yahFourKind2)?.apply { sum.toString() }
                    playerTwoScore += sum
                    if (fives == 5) {
                        view?.findViewById<TextView>(R.id.yahYahtzee2)?.apply { text = "50" }
                        playerTwoScore += 50
                    }
                }
                if (ones == 2 || twos == 2 || threes == 2 || fours == 2 || sixes == 2) {
                    view?.findViewById<TextView>(R.id.yahFullHouse2)?.apply { text = "25" }
                    playerTwoScore += 25
                }
            }
            else if (sixes > 2) {
                view?.findViewById<TextView>(R.id.yahThreeKind2)?.apply { sum.toString() }
                playerTwoScore += sum
                if (sixes > 3) {
                    view?.findViewById<TextView>(R.id.yahFourKind2)?.apply { sum.toString() }
                    playerTwoScore += sum
                    if (sixes == 5) {
                        view?.findViewById<TextView>(R.id.yahYahtzee2)?.apply { text = "50" }
                        playerTwoScore += 50
                    }
                }
                if (ones == 2 || twos == 2 || threes == 2 || fours == 2 || fives == 2) {
                    view?.findViewById<TextView>(R.id.yahFullHouse2)?.apply { text = "25" }
                    playerTwoScore += 25
                }
            }

            if ((ones > 0 && twos > 0 && threes > 0 && fours > 0) || (twos > 0 && threes > 0 && fours > 0 && fives > 0) || (threes > 0 && fours > 0 && fives > 0 && sixes > 0)) {
                view?.findViewById<TextView>(R.id.yahSStraight2)?.apply { text = "30" }
                playerTwoScore += 30
            }
            if ((ones == 1 && twos == 1 && threes == 1 && fours == 1 && fives == 1) || (twos == 1 && threes == 1 && fours == 1 && fives == 1 && sixes == 1)) {
                view?.findViewById<TextView>(R.id.yahLStraight2)?.apply { text = "40" }
                playerTwoScore += 40
            }

            view?.findViewById<TextView>(R.id.yahTotal2)?.apply { text = playerTwoScore.toString() }

            view?.findViewById<Button>(R.id.yahRerollButton)?.apply { visibility = View.INVISIBLE }
            view?.findViewById<Button>(R.id.yahTallyScore)?.apply { visibility = View.INVISIBLE }
            view?.findViewById<TextView>(R.id.yahTurnMark)?.apply { visibility = View.INVISIBLE }
            view?.findViewById<Button>(R.id.yahReplayButton)?.apply { visibility = View.VISIBLE }
            view?.findViewById<Button>(R.id.yahQuitButton)?.apply { visibility = View.VISIBLE }
            view?.findViewById<Button>(R.id.yahKeep1)?.apply { visibility = View.INVISIBLE }
            view?.findViewById<Button>(R.id.yahKeep2)?.apply { visibility = View.INVISIBLE }
            view?.findViewById<Button>(R.id.yahKeep3)?.apply { visibility = View.INVISIBLE }
            view?.findViewById<Button>(R.id.yahKeep4)?.apply { visibility = View.INVISIBLE }
            view?.findViewById<Button>(R.id.yahKeep5)?.apply { visibility = View.INVISIBLE }

            if (playerOneScore == playerTwoScore) {
                view?.findViewById<TextView>(R.id.yahVictory)?.apply {
                    text = "IT'S A DRAW!"
                    visibility = View.VISIBLE
                }
            }
            else if (playerOneScore > playerTwoScore) {
                view?.findViewById<TextView>(R.id.yahVictory)?.apply {
                    text = "PLAYER ONE WINS!"
                    visibility = View.VISIBLE
                }
            }
            else {
                view?.findViewById<TextView>(R.id.yahVictory)?.apply {
                    text = "PLAYER TWO WINS!"
                    visibility = View.VISIBLE
                }
            }
        }
    }

    //Resets all of the text, dice, and variables for a new game.
    fun replayGame() {
        playerOneScore = 0
        playerTwoScore = 0
        rerolls = 3
        for (i in (0..4)) {
            activeDice[i] = (1..6).random()
            keptDice[i] = 0
        }

        view?.findViewById<TextView>(R.id.yahAct1)?.apply {
            text = activeDice[0].toString()
            visibility = View.VISIBLE
        }
        view?.findViewById<TextView>(R.id.yahKept1)?.apply {
            text = "0"
            visibility = View.INVISIBLE
        }
        view?.findViewById<Button>(R.id.yahKeep1)?.apply { visibility = View.VISIBLE }
        view?.findViewById<TextView>(R.id.yahAct2)?.apply {
            text = activeDice[1].toString()
            visibility = View.VISIBLE
        }
        view?.findViewById<TextView>(R.id.yahKept2)?.apply {
            text = "0"
            visibility = View.INVISIBLE
        }
        view?.findViewById<Button>(R.id.yahKeep2)?.apply { visibility = View.VISIBLE }
        view?.findViewById<TextView>(R.id.yahAct3)?.apply {
            text = activeDice[2].toString()
            visibility = View.VISIBLE
        }
        view?.findViewById<TextView>(R.id.yahKept3)?.apply {
            text = "0"
            visibility = View.INVISIBLE
        }
        view?.findViewById<Button>(R.id.yahKeep3)?.apply { visibility = View.VISIBLE }
        view?.findViewById<TextView>(R.id.yahAct4)?.apply {
            text = activeDice[3].toString()
            visibility = View.VISIBLE
        }
        view?.findViewById<TextView>(R.id.yahKept4)?.apply {
            text = "0"
            visibility = View.INVISIBLE
        }
        view?.findViewById<Button>(R.id.yahKeep4)?.apply { visibility = View.VISIBLE }
        view?.findViewById<TextView>(R.id.yahAct5)?.apply {
            text = activeDice[4].toString()
            visibility = View.VISIBLE
        }
        view?.findViewById<TextView>(R.id.yahKept5)?.apply {
            text = "0"
            visibility = View.INVISIBLE
        }
        view?.findViewById<Button>(R.id.yahKeep5)?.apply { visibility = View.VISIBLE }
        view?.findViewById<TextView>(R.id.yahTurnMark)?.apply {
            text = "PLAYER ONE'S TURN"
            visibility = View.VISIBLE
        }
        view?.findViewById<TextView>(R.id.yahRerollText)?.apply { text = rerolls.toString() }

        view?.findViewById<Button>(R.id.yahReplayButton)?.apply { visibility = View.INVISIBLE }
        view?.findViewById<Button>(R.id.yahQuitButton)?.apply { visibility = View.INVISIBLE }
        view?.findViewById<Button>(R.id.yahRerollButton)?.apply { visibility = View.VISIBLE }
        view?.findViewById<Button>(R.id.yahTallyScore)?.apply { visibility = View.VISIBLE }

        view?.findViewById<TextView>(R.id.yahVictory)?.apply { visibility = View.INVISIBLE }

        //Reset the score trackers
        view?.findViewById<TextView>(R.id.yahOnes1)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahOnes2)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahTwos1)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahTwos2)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahThrees1)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahThrees2)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahFours1)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahFours2)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahFives1)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahFives2)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahSixes1)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahSixes2)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahSum1)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahSum2)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahBonus1)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahBonus2)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahThreeKind1)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahThreeKind2)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahFourKind1)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahFourKind2)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahFullHouse1)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahFullHouse2)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahSStraight1)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahSStraight2)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahLStraight1)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahLStraight2)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahChance1)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahChance2)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahYahtzee1)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahYahtzee2)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahTotal1)?.apply { text = "0" }
        view?.findViewById<TextView>(R.id.yahTotal2)?.apply { text = "0" }
    }
}
