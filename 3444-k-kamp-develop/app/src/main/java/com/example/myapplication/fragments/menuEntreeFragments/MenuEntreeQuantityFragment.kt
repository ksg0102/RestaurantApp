package com.example.myapplication.fragments.menuEntreeFragments

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.MenuActivity

/** This fragment class is Step 3 in the ordering process in which the customer enters
 * the number of wings desired for a given flavor
 */
class MenuEntreeQuantityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_entree_quantity, container, false)
        runGraidentAnimation(view)

        /* Display price per wing depending on meat type */
        if ((activity as MenuActivity).getCurrentDay() == getString(R.string.wednesday)) {
            view.findViewById<TextView>(R.id.text_wing_price).text = getString(R.string.sixty_cent_wings)
        }

        val wingPrice =  view.findViewById<TextView>(R.id.text_wing_price)

        val meatType = (activity as MenuActivity).meatType
        if(meatType == "Boneless"){ wingPrice.text = getString(R.string.price_per_boneless_wing) }
        if(meatType == "Bone"){ wingPrice.text = getString(R.string.price_per_bone_wing) }
        if(meatType == "Tenders"){ wingPrice.text = getString(R.string.price_per_tender) }


        /* Initialize button to go to next fragment and save data */
        val nextButton = view.findViewById<Button>(R.id.button_next)
        val helpButtonQuantity = view.findViewById<ImageButton>(R.id.button_help_image_quantity)
        val refillButtonQuantity = view.findViewById<ImageButton>(R.id.button_refill_image_quantity)

        /* Save quantity and proceed to next fragment */
        nextButton.setOnClickListener{

            /* Check that a quantity of MIN_NUM_WINGS or more was entered */
            val wing_quantity = view.findViewById<EditText>(R.id.wing_quantity).text.toString().toInt()
            if (wing_quantity < (activity as MenuActivity).MIN_NUM_WINGS){
                Toast.makeText((activity as MenuActivity).applicationContext, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                /* Proceed if a quantity of more than MIN_NUM_WINGS was entered */

            } else {
                (activity as MenuActivity).numWings =
                    view.findViewById<EditText>(R.id.wing_quantity).text.toString().toInt()
                var idQuantity = wing_quantity*1000
                (activity as MenuActivity).entreeId+=idQuantity
               // (activity as MenuActivity).idStringEntree = (activity as MenuActivity).idStringEntree +(activity as MenuActivity).entreeId.toString() + " "
                (activity as MenuActivity).replaceFragment(MenuEntreeSauceFragment(), "")
            }

            //(activity as MenuActivity).entreeId = 0
        }

        /* Listeners to address Help and Refill requests */
        helpButtonQuantity.setOnClickListener{
            Toast.makeText((activity as MenuActivity).applicationContext, "A waiter will help you shortly", Toast.LENGTH_LONG).show()
        }

        refillButtonQuantity.setOnClickListener{
            Toast.makeText((activity as MenuActivity).applicationContext, "A waiter refill your drink shortly", Toast.LENGTH_LONG).show()
        }

        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.menu_entree_quantity)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }
}
