package com.example.myapplication.fragments.menuSpecials

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
import com.example.myapplication.data_structs.Drink
import com.example.myapplication.data_structs.Entree
import com.example.myapplication.fragments.MainMenuFragment


class MenuFiveDollarFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_five_dollar, container, false)
        runGraidentAnimation(view)


        /* Intialize buttons */
        val mealOneInfo = view.findViewById<ImageButton>(R.id.imageButton_meal_1)
        val mealTwoInfo = view.findViewById<ImageButton>(R.id.imageButton_meal_2)

        /* Navigate user to info page */
        mealOneInfo.setOnClickListener { (activity as MenuActivity).replaceFragment(MenuMealOneFragment(), "") }
        mealTwoInfo.setOnClickListener { (activity as MenuActivity).replaceFragment(MenuMealTwoFragment(), "") }


        /* Add options to cart */
        val mealOneButton = view.findViewById<Button>(R.id.button_value_meal_1)
        val mealTwoButton = view.findViewById<Button>(R.id.button_value_meal_2)

        mealOneButton.setOnClickListener {
            /* 4 boneless bbq wings with ranch and lemonade */

            /* Adding order for database */
            (activity as MenuActivity).entreeId = 4112
            (activity as MenuActivity).drinkId = 503
            (activity as MenuActivity).idStringEntree = (activity as MenuActivity).idStringEntree +(activity as MenuActivity).entreeId.toString() + " "
            (activity as MenuActivity).idDrinkSe = (activity as MenuActivity).idDrinkSe + (activity as MenuActivity).drinkId.toString() + " "

            /* Adding order to view in cart */
            (activity as MenuActivity).entree = Entree("Boneless", "Barbecue", 4, "Ranch", 1, "", 5.0)

            /* Add entree to entree list stored for the table */
            (activity as MenuActivity).addEntree()

            /* Reset entree variable for next one */
            (activity as MenuActivity).resetEntree()

            /* Add drink to view in cart */
            (activity as MenuActivity).drink = Drink("Lemonade", 1, "PART OF $5 ENTREE", 0.0)

            /* Add drink to drink list that will be in the order */
            (activity as MenuActivity).addDrink()

            /* Reset drink variable for next one */
            (activity as MenuActivity).resetDrink()

            Toast.makeText((activity as MenuActivity).applicationContext, "Item has been added to cart", Toast.LENGTH_SHORT).show()
            (activity as MenuActivity).replaceFragment(MainMenuFragment(), "")

        }

        mealTwoButton.setOnClickListener {
            /* 4 boneless lemon pepper wings with honey mustard and soda */
            (activity as MenuActivity).entreeId = 4362
            (activity as MenuActivity).drinkId = 501
            (activity as MenuActivity).idStringEntree = (activity as MenuActivity).idStringEntree +(activity as MenuActivity).entreeId.toString() + " "
            (activity as MenuActivity).idDrinkSe = (activity as MenuActivity).idDrinkSe + (activity as MenuActivity).drinkId.toString() + " "

            /* Adding order to view in cart */
            (activity as MenuActivity).entree = Entree("Boneless", "Lemon Pepper", 4,
                "Honey Mustard", 1, "", 5.0)

            /* Add entree to entree list stored for the table */
            (activity as MenuActivity).addEntree()

            /* Reset entree variable for next one */
            (activity as MenuActivity).resetEntree()

            /* Add drink to view in cart */
            (activity as MenuActivity).drink = Drink("Soft Drink", 1, "PART OF $5 ENTREE", 0.0)

            /* Add drink to drink list that will be in the order */
            (activity as MenuActivity).addDrink()

            /* Reset drink variable for next one */
            (activity as MenuActivity).resetDrink()

            Toast.makeText((activity as MenuActivity).applicationContext, "Item has been added to cart", Toast.LENGTH_SHORT).show()
            (activity as MenuActivity).replaceFragment(MainMenuFragment(), "")

        }


        /* Initialize help and refill button */
        val helpButton = view.findViewById<ImageButton>(R.id.button_help_image_five)
        val refillButton = view.findViewById<ImageButton>(R.id.button_refill_image_five)

        /* Listeners to address Help and Refill requests */
        helpButton.setOnClickListener{
            Toast.makeText((activity as MenuActivity).applicationContext, "A waiter will help you shortly", Toast.LENGTH_LONG).show()
        }

        refillButton.setOnClickListener{
            Toast.makeText((activity as MenuActivity).applicationContext, "A waiter refill your drink shortly", Toast.LENGTH_LONG).show()
        }
        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.menu_five)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }


}
