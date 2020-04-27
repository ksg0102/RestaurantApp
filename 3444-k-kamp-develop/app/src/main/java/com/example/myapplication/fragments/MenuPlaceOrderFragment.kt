package com.example.myapplication.fragments

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.Button

import com.example.myapplication.R
import com.example.myapplication.activities.MenuActivity
import com.example.myapplication.apipackage.DefaultResponse
import com.example.myapplication.apipackage.ResponseOrder
import com.example.myapplication.apipackage.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MenuPlaceOrderFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_place_order, container, false)
        runGraidentAnimation(view)

        val yesButton = view.findViewById<Button>(R.id.button_yes)
        val noButton = view.findViewById<Button>(R.id.button_no)

        yesButton.setOnClickListener {

            if( (activity as MenuActivity).idStringEntree.isNotEmpty()){
                (activity as MenuActivity).idStringEntree = (activity as MenuActivity).idStringEntree.dropLast(1)
            }else{
                (activity as MenuActivity).idStringEntree = "605"
            }
            if( (activity as MenuActivity).sideIdSe.isNotEmpty()){
                (activity as MenuActivity).sideIdSe = (activity as MenuActivity).sideIdSe.dropLast(1)
            }else{
                (activity as MenuActivity).sideIdSe ="405"
            }
            if( (activity as MenuActivity).idDrinkSe.isNotEmpty()){
                (activity as MenuActivity).idDrinkSe = (activity as MenuActivity).idDrinkSe.dropLast(1)
            }else{
                (activity as MenuActivity).idDrinkSe = "505"
            }
            if( (activity as MenuActivity).orderNotes.isNotEmpty()){
                (activity as MenuActivity).orderNotes = (activity as MenuActivity).orderNotes.dropLast(1)
            }else{
                (activity as MenuActivity).orderNotes = "705"
            }

            RetrofitClient.instance.createOrder(
                (activity as MenuActivity).tableNumber,
                (activity as MenuActivity).idStringEntree,
                (activity as MenuActivity).sideIdSe,
                (activity as MenuActivity).idDrinkSe,
                //(activity as MenuActivity).sideIdSe,
                (activity as MenuActivity).orderNotes,
                (activity as MenuActivity).orderTotal.toDouble(),
                0

            ).enqueue(object : Callback<ResponseOrder> {
                override fun onFailure(call: Call<ResponseOrder>, t: Throwable) {
                    Toast.makeText(
                        activity as MenuActivity,
                        t.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onResponse(call: Call<ResponseOrder>, response: Response<ResponseOrder>) {
                    Toast.makeText(
                    activity as MenuActivity,
                    "Order placed sucessfully!",
                    Toast.LENGTH_LONG
                ).show()
                }
            })

           (activity as MenuActivity).addOrder()
            (activity as MenuActivity).resetOrder()

            /* Reset order strings */
            (activity as MenuActivity).idStringEntree = ""
            (activity as MenuActivity).idDrinkSe = ""
            (activity as MenuActivity).sideIdSe = ""


            (activity as MenuActivity).replaceFragment(MainMenuFragment(), "")
        }

        noButton.setOnClickListener {
            Toast.makeText((activity as MenuActivity).applicationContext, "Please continue your selection", Toast.LENGTH_SHORT).show()
            (activity as MenuActivity).replaceFragment(MainMenuFragment(), "")
        }

        /* Intialialize and set up help and refill button actions */
        val helpButtonMain = view.findViewById<ImageButton>(R.id.button_help_image_order)
        val refillButtonMain = view.findViewById<ImageButton>(R.id.button_refill_image_order)
        helpButtonMain.setOnClickListener{
            Toast.makeText((activity as MenuActivity).applicationContext, "A waiter will help you shortly", Toast.LENGTH_LONG).show()
        }

        refillButtonMain.setOnClickListener{
            Toast.makeText((activity as MenuActivity).applicationContext, "A waiter refill your drink shortly", Toast.LENGTH_LONG).show()
        }

        return view
    }


    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.menu_place_order)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

}
