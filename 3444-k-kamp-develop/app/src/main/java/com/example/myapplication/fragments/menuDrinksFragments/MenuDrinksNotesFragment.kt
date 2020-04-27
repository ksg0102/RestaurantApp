package com.example.myapplication.fragments.menuDrinksFragments

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.MenuActivity
import com.example.myapplication.data_structs.Drink
import com.example.myapplication.fragments.MainMenuFragment

/** This class is step 3 in ordering a drink
 * in which the customer can add an additional note
 * and the drink gets added to the cart
 */

class MenuDrinksNotesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_drinks_notes, container, false)
        runGraidentAnimation(view)

        /* Initialize add to cart button */
        val addDrinkButton = view.findViewById<Button>(R.id.button_add_drink_note)

        addDrinkButton.setOnClickListener {

            /* Create Drink() object */
            val __drinkItem = (activity as MenuActivity).drinkItem
            val __drinkItemQuantity = (activity as MenuActivity).drinkItemQuantity
            val __drinkItemNote = view.findViewById<EditText>(R.id.text_drink_note).text.toString()
            val __drinkPrice = getDrinkPrice(__drinkItem, __drinkItemQuantity)

            val __drink = Drink(__drinkItem, __drinkItemQuantity, __drinkItemNote, __drinkPrice)
            (activity as MenuActivity).drink = __drink

            /* Add Side to side list that will be in the order */
            (activity as MenuActivity).addDrink()

            /* Reset side variable for next one */
            (activity as MenuActivity).resetDrink()

            /* Direct back to main menu */
            Toast.makeText((activity as MenuActivity).applicationContext,
                "Drink has been added to cart", Toast.LENGTH_SHORT).show()
            (activity as MenuActivity).replaceFragment(MainMenuFragment(), "")
        }


        /* Initialize help and refill buttons */
        val helpButtonDrink = view.findViewById<ImageButton>(R.id.button_help_image_drink_note)
        val refillButtonDrink = view.findViewById<ImageButton>(R.id.button_refill_image_drink_note)

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
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.menu_drink_note)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

    /* Method calculates price of drink order */
    fun getDrinkPrice(drinkType: String, drinkQuantity: Int): Double{
        if ((activity as MenuActivity).getCurrentDay() == getString(R.string.sunday)) { return 0.0 }
        if (drinkType == "Water"){ return 0.0 }
        else { return drinkQuantity * (activity as MenuActivity).PRICE_PER_DRINK }
    }

}
