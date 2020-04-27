package com.example.myapplication.fragments.menuDrinksFragments

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.MenuActivity
import com.example.myapplication.fragments.menuSidesFragments.MenuSidesNotesFragment

/** This class is step 2 in ordering a drink
 * in which the customer selects the drink quantity
 */
class MenuDrinksQuantityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_drinks_quantity, container, false)
        runGraidentAnimation(view)

        if ((activity as MenuActivity).getCurrentDay() == getString(R.string.sunday)) {
            view.findViewById<TextView>(R.id.text_drink_price).text = getString(R.string.free_drinks)
        }

        /* If user has selected water display message that water is free */
        if((activity as MenuActivity).drinkItem == "Water"){
            view.findViewById<TextView>(R.id.text_drink_price).text = getString(R.string.water_is_complimentary)
        }

        /* Initialize drink quantity button */
        val nextButton = view.findViewById<Button>(R.id.button_add_drink_quantity)

        /* Store the quantity and proceed to next fragment */
        nextButton.setOnClickListener {
            var drinkQuantity = view.findViewById<EditText>(R.id.drink_quantity).text.toString().toInt()
            /* Verify that at least one drink quantity has been entered */
            if (drinkQuantity < (activity as MenuActivity).MIN_NUM_DRINKS) {
                Toast.makeText(
                    (activity as MenuActivity).applicationContext, "Please enter a valid quantity", Toast.LENGTH_SHORT).show()

                /* Store quantity */
            } else {

                (activity as MenuActivity).drinkItemQuantity = drinkQuantity
                drinkQuantity*=1000
                (activity as MenuActivity).drinkId+=drinkQuantity
                (activity as MenuActivity).idDrinkSe = (activity as MenuActivity).idDrinkSe + (activity as MenuActivity).drinkId.toString() + " "
                (activity as MenuActivity).replaceFragment(MenuDrinksNotesFragment(), "")
            }

            (activity as MenuActivity).drinkId = 0
        }

        /* Initialize help and refill buttons */
        val helpButtonDrink = view.findViewById<ImageButton>(R.id.button_help_image_drink_quantity)
        val refillButtonDrink = view.findViewById<ImageButton>(R.id.button_refill_image_drink_quantity)

        /* Listeners to address Help and Refill requests */
        helpButtonDrink.setOnClickListener{
            Toast.makeText((activity as MenuActivity).applicationContext, "A waiter will help you shortly", Toast.LENGTH_LONG).show()
        }

        refillButtonDrink.setOnClickListener{
            Toast.makeText((activity as MenuActivity).applicationContext, "A waiter refill your drink shortly", Toast.LENGTH_LONG).show()
        }

        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.menu_drink_quantity)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

}
