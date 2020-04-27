package com.example.myapplication.fragments.menuSpecials

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.MenuActivity
import com.example.myapplication.fragments.MainMenuFragment
import com.example.myapplication.fragments.menuDrinksFragments.MenuDrinksFragment
import com.example.myapplication.fragments.menuEntreeFragments.MenuEntreeMeatFragment
import com.example.myapplication.fragments.menuKids.MenuKidsMealsFragment


class MenuSpecialsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_specials, container, false)
        runGraidentAnimation(view)
        val currentDay = (activity as MenuActivity).getCurrentDay()
        view.findViewById<TextView>(R.id.today_special_day).text = "Today's Special: $currentDay"
        val redeemButton = view.findViewById<Button>(R.id.button_redeem)

        //Help and refill buttons
        val helpButtonSpecials = view.findViewById<ImageButton>(R.id.button_help_image_specials)
        val refillButtonSpecials = view.findViewById<ImageButton>(R.id.button_refill_image_specials)
        /* Send help notification to the waiter */
        helpButtonSpecials.setOnClickListener{
            Toast.makeText((activity as MenuActivity).applicationContext, "A waiter will help you shortly", Toast.LENGTH_LONG).show()
            /*
            /* Save table status to the database  */
            RetrofitClient.instance.updateTable((activity as MenuActivity).table.number, "Needs Help",
                needHelp = true,
                needRefill = false,
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

             */
        }
        /* Send refill notification to the waiter */
        refillButtonSpecials.setOnClickListener{
            Toast.makeText((activity as MenuActivity).applicationContext, "A waiter refill your drink shortly", Toast.LENGTH_LONG).show()
/*
            /* Save table status to database */
            RetrofitClient.instance.updateTable((activity as MenuActivity).table.number, "Needs Refill",
                needHelp = false,
                needRefill = true,
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

 */
        }

        /* Sunday Special */
        if (currentDay == getString(R.string.sunday)) {
            view.findViewById<TextView>(R.id.text_specials_msg).text =
                getString(R.string.sunday_deal)
            redeemButton.visibility = View.VISIBLE
            redeemButton.setOnClickListener {
                Toast.makeText(
                    (activity as MenuActivity).applicationContext,
                    "Discount is already applied",
                    Toast.LENGTH_SHORT
                ).show()
                (activity as MenuActivity).replaceFragment(MenuDrinksFragment(), "")
            }
        }
        /* Monday Special */
        if (currentDay == getString(R.string.monday)) {
            view.findViewById<TextView>(R.id.text_specials_msg).text =
                getString(R.string.monday_deal)
            redeemButton.visibility = View.VISIBLE
            redeemButton.setOnClickListener {
                Toast.makeText(
                    (activity as MenuActivity).applicationContext,
                    "Discount is already applied",
                    Toast.LENGTH_SHORT
                ).show()
                (activity as MenuActivity).replaceFragment(MenuKidsMealsFragment(), "")
            }
        }

        /* Wednesday Special */
        if (currentDay == getString(R.string.wednesday)) {
            view.findViewById<TextView>(R.id.text_specials_msg).text =
                getString(R.string.wednesday_deal)
            redeemButton.visibility = View.VISIBLE
            redeemButton.setOnClickListener {
                Toast.makeText(
                    (activity as MenuActivity).applicationContext,
                    "Discount is already applied",
                    Toast.LENGTH_SHORT
                ).show()
                (activity as MenuActivity).replaceFragment(MenuEntreeMeatFragment(), "")
            }
        }

         /* Thursday Special */
            if (currentDay == getString(R.string.thursday)) {
                view.findViewById<TextView>(R.id.text_specials_msg).text = getString(R.string.thursday_deal)
                redeemButton.visibility = View.VISIBLE
                redeemButton.setOnClickListener {
                    Toast.makeText((activity as MenuActivity).applicationContext,
                        "Order your wings first!", Toast.LENGTH_SHORT).show()
                    (activity as MenuActivity).replaceFragment(MenuEntreeMeatFragment(), "")
                }
            }

        /* Friday Special */
        if (currentDay == getString(R.string.friday)) {
            view.findViewById<TextView>(R.id.text_specials_msg).text = getString(R.string.friday_deal)
            redeemButton.visibility = View.VISIBLE
            redeemButton.setOnClickListener {
                Toast.makeText((activity as MenuActivity).applicationContext,
                    "A waiter will come serve your shortly", Toast.LENGTH_SHORT).show()
                (activity as MenuActivity).replaceFragment(MainMenuFragment(), "")
            }
        }

        /* Navigate user to $5 menu */
        val fiveMenuButton = view.findViewById<Button>(R.id.button_five_dollar_menu)
        fiveMenuButton.setOnClickListener {
            (activity as MenuActivity).replaceFragment(MenuFiveDollarFragment(), "")
        }

        return view
    }
    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.menu_specials)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }



}
