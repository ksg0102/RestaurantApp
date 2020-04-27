package com.example.myapplication.fragments.menuSidesFragments

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.MenuActivity
import com.example.myapplication.data_structs.Side
import com.example.myapplication.fragments.MainMenuFragment


class MenuSidesNotesFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_sides_notes, container, false)
        runGraidentAnimation(view)

        /* Initialize add to cart button */
        val addSideButton = view.findViewById<Button>(R.id.button_add_side_note)

        addSideButton.setOnClickListener {

            /* Create Side() object */
            val __sideItem = (activity as MenuActivity).sideItem
            val __sideItemQuantity = (activity as MenuActivity).sideItemQuantity
            val __sideItemNote = view.findViewById<EditText>(R.id.text_side_note).text.toString()
            val __sidePrice = getSidePrice(__sideItemQuantity)

            val __side = Side(__sideItem, __sideItemQuantity, __sideItemNote, __sidePrice)
            (activity as MenuActivity).side = __side

            /* Add Side to side list that will be in the order */
            (activity as MenuActivity).addSide()

            /* Reset side variable for next one */
            (activity as MenuActivity).resetSide()

            /* Direct back to main menu */
            Toast.makeText((activity as MenuActivity).applicationContext,
                "Side has been added to cart", Toast.LENGTH_SHORT).show()
            (activity as MenuActivity).replaceFragment(MainMenuFragment(), "")
        }

        /* Initialize help and refill buttons */
        val helpButtonSide = view.findViewById<ImageButton>(R.id.button_help_image_side_note)
        val refillButtonSide = view.findViewById<ImageButton>(R.id.button_refill_image_side_note)

        /* Listeners to address Help and Refill requests */
        helpButtonSide.setOnClickListener{
            Toast.makeText((activity as MenuActivity).applicationContext, "A waiter will help you shortly", Toast.LENGTH_LONG).show()
        }

        refillButtonSide.setOnClickListener{
            Toast.makeText((activity as MenuActivity).applicationContext, "A waiter refill your drink shortly", Toast.LENGTH_LONG).show()
        }


        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.menu_side_note)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

    private fun getSidePrice(sideQuantity: Int): Double{
        /* Thursday Deal */
        if ((activity as MenuActivity).getCurrentDay() == getString(R.string.thursday) &&
            (activity as MenuActivity).getNumEntrees() > 0) {
            return 0.0
        }
        return (activity as MenuActivity).PRICE_PER_SIDE * sideQuantity
    }
}
