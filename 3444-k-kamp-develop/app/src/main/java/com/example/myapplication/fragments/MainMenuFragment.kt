package com.example.myapplication.fragments

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapplication.R
import com.example.myapplication.activities.MenuActivity
import com.example.myapplication.apipackage.ResponseTable
import com.example.myapplication.apipackage.RetrofitClient
import com.example.myapplication.fragments.menuDrinksFragments.MenuDrinksFragment
import com.example.myapplication.fragments.menuEntreeFragments.MenuEntreeMeatFragment
import com.example.myapplication.fragments.menuPaymentFragments.MenuPaymentMethodFragment
import com.example.myapplication.fragments.menuSidesFragments.MenuSidesFragment
import com.example.myapplication.fragments.gameFragments.KidsModeLoginFragment
import com.example.myapplication.fragments.menuKids.MenuKidsMealsFragment
import com.example.myapplication.fragments.menuSpecials.MenuFiveDollarFragment
import com.example.myapplication.fragments.menuSpecials.MenuSpecialsFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainMenuFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_menu_container, container, false)
        runGraidentAnimation(view)

        /* Initilizae button variables */
        val entreeButton = view.findViewById<Button>(R.id.button_entree)
        val sidesButton = view.findViewById<Button>(R.id.button_sides)
        val drinksButton = view.findViewById<Button>(R.id.button_drinks)
        val specialsButton = view.findViewById<Button>(R.id.button_specials)
        val gamesButton = view.findViewById<Button>(R.id.button_games)
        val kidsMenuButton = view.findViewById<Button>(R.id.button_kids_menu)


        val helpButtonMain = view.findViewById<ImageButton>(R.id.button_help_image_main_menu)
        val refillButtonMain = view.findViewById<ImageButton>(R.id.button_refill_image_main_menu)
        val cartButton = view.findViewById<ImageButton>(R.id.button_cart_main_menu)
        val paymentButton = view.findViewById<ImageButton>(R.id.button_payment_main_menu)

        /* Set up button actions to go to respective page */

        entreeButton.setOnClickListener{ (activity as MenuActivity).replaceFragment(MenuEntreeMeatFragment(),"") }
        sidesButton.setOnClickListener{ (activity as MenuActivity).replaceFragment(MenuSidesFragment(),"") }
        drinksButton.setOnClickListener{(activity as MenuActivity).replaceFragment(MenuDrinksFragment(),"") }
        specialsButton.setOnClickListener{ (activity as MenuActivity).replaceFragment(
            MenuSpecialsFragment(),"") }
        gamesButton.setOnClickListener{ (activity as MenuActivity).replaceFragment(KidsModeLoginFragment(),"") }
        kidsMenuButton.setOnClickListener{ (activity as MenuActivity).replaceFragment(MenuKidsMealsFragment(),"") }
        view.findViewById<Button>(R.id.value_Meal_Button).setOnClickListener{ (activity as MenuActivity).replaceFragment(
            MenuFiveDollarFragment(), "")}


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
                needHelp = 0 ,
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
        cartButton.setOnClickListener {
            (activity as MenuActivity).replaceFragment(MenuViewCartFragment(), "")
        }

        paymentButton.setOnClickListener {
            if ((activity as MenuActivity).getNumOrders() < 1){
                Toast.makeText((activity as MenuActivity).applicationContext,
                    "Please place an order first in order to make payment", Toast.LENGTH_LONG).show()
            } else {
                (activity as MenuActivity).replaceFragment(MenuPaymentMethodFragment(), "")
            }
        }

        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.main_menu)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }


}
