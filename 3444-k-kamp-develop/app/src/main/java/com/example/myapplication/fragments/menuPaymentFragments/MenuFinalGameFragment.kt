package com.example.myapplication.fragments.menuPaymentFragments

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.MenuActivity
import com.example.myapplication.apipackage.ResponseTable
import com.example.myapplication.apipackage.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random


class MenuFinalGameFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_final_game, container, false)
        runGraidentAnimation(view)

        //   Toast.makeText((activity as MenuActivity).applicationContext, "Game free desert!",Toast.LENGTH_LONG).show()

        /* if they win for now just make Toast saying a free dessert is on its way */

        val finalRunButton = view.findViewById<Button>(R.id.final_game_button)
        var randomInt: Int
        var count = 0

        finalRunButton.setOnClickListener {
            if(count == 0){
                randomInt = (1..5).random()
                val randomNumGame = view.findViewById<EditText>(R.id.Random_input).text.toString().toInt()
                val ourRandomNum = view.findViewById<TextView>(R.id.textView12)
                ourRandomNum.setTextColor(Color.BLACK)
                ourRandomNum.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                ourRandomNum.text = "$randomInt"

                if(randomNumGame == randomInt){
                    Toast.makeText((activity as MenuActivity).applicationContext, "You earned free desert!", Toast.LENGTH_LONG).show()
                }
                if(randomNumGame != randomInt){
                    Toast.makeText((activity as MenuActivity).applicationContext, "Oops, better luck next visit!", Toast.LENGTH_LONG).show()
                }
                /* TODO send an alarm to staff for free desert*/

                count++
            }
            else{
                Toast.makeText((activity as MenuActivity).applicationContext, "You only have one chance.", Toast.LENGTH_LONG).show()
            }
        }

        /* initialize a next button and set onClickListner to go to MenuExitFragment() */
        val finalButton = view.findViewById<Button>(R.id.final_game_next_button)

        finalButton.setOnClickListener {
            (activity as MenuActivity).replaceFragment(MenuSurveyFragment(), "")
        }

        /* Intialialize and set up help and refill button actions */
        val helpButtonMain = view.findViewById<ImageButton>(R.id.button_help_image_payment_final_game)
        val refillButtonMain = view.findViewById<ImageButton>(R.id.button_refill_image_payment_final_game)
        /* Send help notification to the waiter */
        helpButtonMain.setOnClickListener{
            //Toast.makeText((activity as MenuActivity).applicationContext, "A waiter will help you shortly", Toast.LENGTH_LONG).show()

            /* Save table status to the database  */
            RetrofitClient.instance.updateTable((activity as MenuActivity).table.number, "Needs Help",
                needHelp = 1,
                needRefill = 0,
                orderTotal = 0.0
            ).enqueue(object: Callback<ResponseTable> {
                override fun onFailure(call: Call<ResponseTable>, t: Throwable) {
                    Toast.makeText(
                        activity as MenuActivity,
                        t.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
                override fun onResponse(call: Call<ResponseTable>, response: Response<ResponseTable>) {
                    Toast.makeText(
                        activity as MenuActivity,
                        "A waiter will help you shortly",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }


        /* Send refill notification to the waiter */
        refillButtonMain.setOnClickListener{
            //Toast.makeText((activity as MenuActivity).applicationContext, "A waiter refill your drink shortly", Toast.LENGTH_LONG).show()

            /* Save table status to database */
            RetrofitClient.instance.updateTable((activity as MenuActivity).table.number, "Needs Refill",
                needHelp = 0,
                needRefill = 1,
                orderTotal = 0.0
            ).enqueue(object: Callback<ResponseTable> {
                override fun onFailure(call: Call<ResponseTable>, t: Throwable) {
                    Toast.makeText(
                        activity as MenuActivity,
                        t.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
                override fun onResponse(call: Call<ResponseTable>, response: Response<ResponseTable>) {
                    Toast.makeText(
                        activity as MenuActivity,
                        "A waiter will refill your drink shortly",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.menu_payment_final_game)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

    private fun buttonRun(v: View) {


    }

}
