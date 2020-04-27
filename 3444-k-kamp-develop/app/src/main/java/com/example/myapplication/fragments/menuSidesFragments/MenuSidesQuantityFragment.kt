package com.example.myapplication.fragments.menuSidesFragments

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.Button
import android.widget.EditText
import com.example.myapplication.R
import com.example.myapplication.activities.MenuActivity

class MenuSidesQuantityFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_sides_quantity, container, false)
        runGraidentAnimation(view)

        /* Initialize side quantity button */
        val nextButton = view.findViewById<Button>(R.id.button_add_side_quantity)

        /* Store the quantity and proceed to next fragment */

        nextButton.setOnClickListener {
            var sideQuantity = view.findViewById<EditText>(R.id.side_quantity).text.toString().toInt()
            if (sideQuantity < (activity as MenuActivity).MIN_NUM_SIDES) {
                Toast.makeText(
                    (activity as MenuActivity).applicationContext,
                    "Please enter a valid quantity",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                (activity as MenuActivity).sideItemQuantity = sideQuantity
                sideQuantity*=1000
                (activity as MenuActivity).sideId+=sideQuantity
                (activity as MenuActivity).sideIdSe = (activity as MenuActivity).sideIdSe + (activity as MenuActivity).sideId.toString() + " "
                (activity as MenuActivity).replaceFragment(MenuSidesNotesFragment(), "")
            }

            (activity as MenuActivity).sideId = 0
        }


        /* Initialize help and refill buttons */
        val helpButtonSide = view.findViewById<ImageButton>(R.id.button_help_image_side_quantity)
        val refillButtonSide = view.findViewById<ImageButton>(R.id.button_refill_image_side_quantity)

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
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.menu_side_quantity)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

}
