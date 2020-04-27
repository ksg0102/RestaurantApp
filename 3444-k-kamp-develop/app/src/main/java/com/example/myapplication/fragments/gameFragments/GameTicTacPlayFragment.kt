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

class GameTicTacPlayFragment : Fragment() {
    //Definition of important variables such that they can be universally accessed in the fragmgent.
    var boardState = mutableListOf<Int>(0, 0, 0, 0, 0, 0, 0, 0, 0)
    var ttTurn = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_tic_tac_play, container, false)
        runGraidentAnimation(view)

        //Setting up the... many button listeners.
        view.findViewById<Button>(R.id.ticTacQuitButton).setOnClickListener{ (activity as MenuActivity).replaceFragment(GameTicTacFragment(), "")}
        view.findViewById<Button>(R.id.ticTacReplayButton).setOnClickListener{ ticTacResetBoard() }
        view.findViewById<Button>(R.id.ticTacB11).setOnClickListener{ ticTacTurn(0) }
        view.findViewById<Button>(R.id.ticTacB12).setOnClickListener{ ticTacTurn(1) }
        view.findViewById<Button>(R.id.ticTacB13).setOnClickListener{ ticTacTurn(2) }
        view.findViewById<Button>(R.id.ticTacB21).setOnClickListener{ ticTacTurn(3) }
        view.findViewById<Button>(R.id.ticTacB22).setOnClickListener{ ticTacTurn(4) }
        view.findViewById<Button>(R.id.ticTacB23).setOnClickListener{ ticTacTurn(5) }
        view.findViewById<Button>(R.id.ticTacB31).setOnClickListener{ ticTacTurn(6) }
        view.findViewById<Button>(R.id.ticTacB32).setOnClickListener{ ticTacTurn(7) }
        view.findViewById<Button>(R.id.ticTacB33).setOnClickListener{ ticTacTurn(8) }

        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.ticTacPlayLayout)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

    //Hiding every token from the board and returning the buttons previously removed in the game.
    fun ticTacResetBoard() {
        view?.findViewById<Button>(R.id.ticTacReplayButton)?.apply {visibility = View.GONE}
        view?.findViewById<Button>(R.id.ticTacQuitButton)?.apply {visibility = View.GONE}
        view?.findViewById<Button>(R.id.ticTacB11)?.apply {visibility = View.VISIBLE}
        view?.findViewById<Button>(R.id.ticTacB12)?.apply {visibility = View.VISIBLE}
        view?.findViewById<Button>(R.id.ticTacB13)?.apply {visibility = View.VISIBLE}
        view?.findViewById<Button>(R.id.ticTacB21)?.apply {visibility = View.VISIBLE}
        view?.findViewById<Button>(R.id.ticTacB22)?.apply {visibility = View.VISIBLE}
        view?.findViewById<Button>(R.id.ticTacB23)?.apply {visibility = View.VISIBLE}
        view?.findViewById<Button>(R.id.ticTacB31)?.apply {visibility = View.VISIBLE}
        view?.findViewById<Button>(R.id.ticTacB32)?.apply {visibility = View.VISIBLE}
        view?.findViewById<Button>(R.id.ticTacB33)?.apply {visibility = View.VISIBLE}
        view?.findViewById<TextView>(R.id.ticTacX11)?.apply {visibility = View.GONE}
        view?.findViewById<TextView>(R.id.ticTacX12)?.apply {visibility = View.GONE}
        view?.findViewById<TextView>(R.id.ticTacX13)?.apply {visibility = View.GONE}
        view?.findViewById<TextView>(R.id.ticTacX21)?.apply {visibility = View.GONE}
        view?.findViewById<TextView>(R.id.ticTacX22)?.apply {visibility = View.GONE}
        view?.findViewById<TextView>(R.id.ticTacX23)?.apply {visibility = View.GONE}
        view?.findViewById<TextView>(R.id.ticTacX31)?.apply {visibility = View.GONE}
        view?.findViewById<TextView>(R.id.ticTacX32)?.apply {visibility = View.GONE}
        view?.findViewById<TextView>(R.id.ticTacX33)?.apply {visibility = View.GONE}
        view?.findViewById<TextView>(R.id.ticTacO11)?.apply {visibility = View.GONE}
        view?.findViewById<TextView>(R.id.ticTacO12)?.apply {visibility = View.GONE}
        view?.findViewById<TextView>(R.id.ticTacO13)?.apply {visibility = View.GONE}
        view?.findViewById<TextView>(R.id.ticTacO21)?.apply {visibility = View.GONE}
        view?.findViewById<TextView>(R.id.ticTacO22)?.apply {visibility = View.GONE}
        view?.findViewById<TextView>(R.id.ticTacO23)?.apply {visibility = View.GONE}
        view?.findViewById<TextView>(R.id.ticTacO31)?.apply {visibility = View.GONE}
        view?.findViewById<TextView>(R.id.ticTacO32)?.apply {visibility = View.GONE}
        view?.findViewById<TextView>(R.id.ticTacO33)?.apply {visibility = View.GONE}
        view?.findViewById<TextView>(R.id.ticTacTurnMark)?.apply {text = "PLAYER ONE'S TURN"}
        boardState = mutableListOf<Int>(0, 0, 0, 0, 0, 0, 0, 0, 0)
    }

    //Driver code - parameter passed is the position clicked. Button is hidden and the correct symbol is displayed. Game state is then checked.
    fun ticTacTurn(pos: Int) {
        if (ttTurn == 1) {
            when (pos) {
                0 -> {
                    view?.findViewById<Button>(R.id.ticTacB11)?.apply{visibility = View.GONE}
                    view?.findViewById<TextView>(R.id.ticTacX11)?.apply{visibility = View.VISIBLE}
                }
                1 -> {
                    view?.findViewById<Button>(R.id.ticTacB12)?.apply{visibility = View.GONE}
                    view?.findViewById<TextView>(R.id.ticTacX12)?.apply{visibility = View.VISIBLE}
                }
                2 -> {
                    view?.findViewById<Button>(R.id.ticTacB13)?.apply{visibility = View.GONE}
                    view?.findViewById<TextView>(R.id.ticTacX13)?.apply{visibility = View.VISIBLE}
                }
                3 -> {
                    view?.findViewById<Button>(R.id.ticTacB21)?.apply{visibility = View.GONE}
                    view?.findViewById<TextView>(R.id.ticTacX21)?.apply{visibility = View.VISIBLE}
                }
                4 -> {
                    view?.findViewById<Button>(R.id.ticTacB22)?.apply{visibility = View.GONE}
                    view?.findViewById<TextView>(R.id.ticTacX22)?.apply{visibility = View.VISIBLE}
                }
                5 -> {
                    view?.findViewById<Button>(R.id.ticTacB23)?.apply{visibility = View.GONE}
                    view?.findViewById<TextView>(R.id.ticTacX23)?.apply{visibility = View.VISIBLE}
                }
                6 -> {
                    view?.findViewById<Button>(R.id.ticTacB31)?.apply{visibility = View.GONE}
                    view?.findViewById<TextView>(R.id.ticTacX31)?.apply{visibility = View.VISIBLE}
                }
                7 -> {
                    view?.findViewById<Button>(R.id.ticTacB32)?.apply{visibility = View.GONE}
                    view?.findViewById<TextView>(R.id.ticTacX32)?.apply{visibility = View.VISIBLE}
                }
                8 -> {
                    view?.findViewById<Button>(R.id.ticTacB33)?.apply{visibility = View.GONE}
                    view?.findViewById<TextView>(R.id.ticTacX33)?.apply{visibility = View.VISIBLE}
                }
            }
            boardState[pos] = ttTurn
            ttTurn += 1
            view?.findViewById<TextView>(R.id.ticTacTurnMark)?.apply {text = "PLAYER TWO'S TURN"}
        }
        else {
            when (pos) {
                0 -> {
                    view?.findViewById<Button>(R.id.ticTacB11)?.apply{visibility = View.GONE}
                    view?.findViewById<TextView>(R.id.ticTacO11)?.apply{visibility = View.VISIBLE}
                }
                1 -> {
                    view?.findViewById<Button>(R.id.ticTacB12)?.apply{visibility = View.GONE}
                    view?.findViewById<TextView>(R.id.ticTacO12)?.apply{visibility = View.VISIBLE}
                }
                2 -> {
                    view?.findViewById<Button>(R.id.ticTacB13)?.apply{visibility = View.GONE}
                    view?.findViewById<TextView>(R.id.ticTacO13)?.apply{visibility = View.VISIBLE}
                }
                3 -> {
                    view?.findViewById<Button>(R.id.ticTacB21)?.apply{visibility = View.GONE}
                    view?.findViewById<TextView>(R.id.ticTacO21)?.apply{visibility = View.VISIBLE}
                }
                4 -> {
                    view?.findViewById<Button>(R.id.ticTacB22)?.apply{visibility = View.GONE}
                    view?.findViewById<TextView>(R.id.ticTacO22)?.apply{visibility = View.VISIBLE}
                }
                5 -> {
                    view?.findViewById<Button>(R.id.ticTacB23)?.apply{visibility = View.GONE}
                    view?.findViewById<TextView>(R.id.ticTacO23)?.apply{visibility = View.VISIBLE}
                }
                6 -> {
                    view?.findViewById<Button>(R.id.ticTacB31)?.apply{visibility = View.GONE}
                    view?.findViewById<TextView>(R.id.ticTacO31)?.apply{visibility = View.VISIBLE}
                }
                7 -> {
                    view?.findViewById<Button>(R.id.ticTacB32)?.apply{visibility = View.GONE}
                    view?.findViewById<TextView>(R.id.ticTacO32)?.apply{visibility = View.VISIBLE}
                }
                8 -> {
                    view?.findViewById<Button>(R.id.ticTacB33)?.apply{visibility = View.GONE}
                    view?.findViewById<TextView>(R.id.ticTacO33)?.apply{visibility = View.VISIBLE}
                }
            }
            boardState[pos] = ttTurn
            ttTurn -= 1
            view?.findViewById<TextView>(R.id.ticTacTurnMark)?.apply {text = "PLAYER ONE'S TURN"}
        }

        var gameState = checkRows()
        //If an end to the game is declared, remove all remaining buttons and show the post-game text and buttons.
        if (gameState > 0) {
            view?.findViewById<Button>(R.id.ticTacB11)?.apply {visibility = View.GONE}
            view?.findViewById<Button>(R.id.ticTacB12)?.apply {visibility = View.GONE}
            view?.findViewById<Button>(R.id.ticTacB13)?.apply {visibility = View.GONE}
            view?.findViewById<Button>(R.id.ticTacB21)?.apply {visibility = View.GONE}
            view?.findViewById<Button>(R.id.ticTacB22)?.apply {visibility = View.GONE}
            view?.findViewById<Button>(R.id.ticTacB23)?.apply {visibility = View.GONE}
            view?.findViewById<Button>(R.id.ticTacB31)?.apply {visibility = View.GONE}
            view?.findViewById<Button>(R.id.ticTacB32)?.apply {visibility = View.GONE}
            view?.findViewById<Button>(R.id.ticTacB33)?.apply {visibility = View.GONE}
            view?.findViewById<Button>(R.id.ticTacReplayButton)?.apply {visibility = View.VISIBLE}
            view?.findViewById<Button>(R.id.ticTacQuitButton)?.apply {visibility = View.VISIBLE}
            if (gameState == 1) view?.findViewById<TextView>(R.id.ticTacTurnMark)?.apply {text = "PLAYER ONE WINS!"}
            else if (gameState == 2) view?.findViewById<TextView>(R.id.ticTacTurnMark)?.apply {text = "PLAYER TWO WINS!"}
            else view?.findViewById<TextView>(R.id.ticTacTurnMark)?.apply {text = "IT'S A DRAW!"}
        }
    }

    //Code to check for rows.
    fun checkRows(): Int {
        //First row
        if (boardState[0] == boardState[3] && boardState[0] == boardState[6] && boardState[0] > 0) { return boardState[0] }
        //Second row
        if (boardState[1] == boardState[4] && boardState[1] == boardState[7] && boardState[1] > 0) { return boardState[1] }
        //Third row
        if (boardState[2] == boardState[5] && boardState[2] == boardState[8] && boardState[2] > 0) { return boardState[2] }
        //First column
        if (boardState[0] == boardState[1] && boardState[0] == boardState[2] && boardState[0] > 0) { return boardState[0] }
        //Second column
        if (boardState[3] == boardState[4] && boardState[3] == boardState[5] && boardState[3] > 0) { return boardState[3] }
        //Third column
        if (boardState[6] == boardState[7] && boardState[6] == boardState[8] && boardState[6] > 0) { return boardState[6] }
        //First diagonal
        if (boardState[0] == boardState[4] && boardState[0] == boardState[8] && boardState[0] > 0) { return boardState[0] }
        //Second diagonal
        if (boardState[2] == boardState[4] && boardState[2] == boardState[6] && boardState[2] > 0) { return boardState[2] }
        if (boardState[0] > 0 && boardState[1] > 0 && boardState[2] > 0 && boardState[3] > 0 && boardState[4] > 0
            && boardState[5] > 0 && boardState[6] > 0 && boardState[7] > 0 && boardState[8] > 0) {
            return 3
        }
        return 0
    }

}
