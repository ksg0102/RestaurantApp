package com.example.myapplication.fragments.menuEntreeFragments

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
import com.example.myapplication.data_structs.Entree
import com.example.myapplication.fragments.MainMenuFragment
import com.example.myapplication.fragments.menuDrinksFragments.MenuDrinksFragment
import com.example.myapplication.fragments.menuSidesFragments.MenuSidesFragment

class MenuEntreeExtrasFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_entree_extras, container, false)
        runGraidentAnimation(view)

        /* Initialize buttons */
        val sidesButton = view.findViewById<Button>(R.id.button_go_to_sides)
        val drinksButton = view.findViewById<Button>(R.id.button_go_to_drinks)
        val menuButton = view.findViewById<Button>(R.id.button_go_to_menu)

        val helpButtonNote = view.findViewById<ImageButton>(R.id.button_help_image_extras)
        val refillButtonNote = view.findViewById<ImageButton>(R.id.button_refill_image_extras)


        /* Form Entree() */
        val __meatType = (activity as MenuActivity).meatType
        val __flavorType = (activity as MenuActivity).flavorType
        val __sauceType = (activity as MenuActivity).sauceType
        val __sauceQuantity = (activity as MenuActivity).sauceQuantity
        val __numWings = (activity as MenuActivity).numWings
        val __note= (activity as MenuActivity).note
        val __price = getEntreePrice(__meatType, __numWings, __sauceQuantity)

        val __entree = Entree(__meatType, __flavorType, __numWings, __sauceType, __sauceQuantity, __note, __price)
        (activity as MenuActivity).entree = __entree

        /* Add entree to entree list stored for the table */
        (activity as MenuActivity).addEntree()

        /* Reset entree variable for next one */
        (activity as MenuActivity).resetEntree()

        /* Set on click listeners to direct to appropiate fragment */
        sidesButton.setOnClickListener {
            Toast.makeText((activity as MenuActivity).applicationContext,
                "Entree has been added to cart", Toast.LENGTH_SHORT).show()
            (activity as MenuActivity).replaceFragment(MenuSidesFragment(), "")
        }

        drinksButton.setOnClickListener {
            Toast.makeText((activity as MenuActivity).applicationContext,
                "Entree has been added to cart", Toast.LENGTH_SHORT).show()
            (activity as MenuActivity).replaceFragment(MenuDrinksFragment(), "")
        }

        menuButton.setOnClickListener {
            Toast.makeText((activity as MenuActivity).applicationContext,
                "Entree has been added to cart", Toast.LENGTH_SHORT).show()
            (activity as MenuActivity).replaceFragment(MainMenuFragment(), "")
        }

        /* Listeners to address Help and Refill requests */
        helpButtonNote.setOnClickListener{
            Toast.makeText((activity as MenuActivity).applicationContext,
                "A waiter will help you shortly", Toast.LENGTH_LONG).show()
        }

        refillButtonNote.setOnClickListener{
            Toast.makeText((activity as MenuActivity).applicationContext,
                "A waiter refill your drink shortly", Toast.LENGTH_LONG).show()
        }
        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.menu_entree_extras)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

    /* Method calculates the price of the entree */
  private fun getEntreePrice(meatType: String, numWings: Int, sauceQuantity: Int): Double{
      val price = 0.0

      /* Wednesday special */
      if ((activity as MenuActivity).getCurrentDay() == getString(R.string.wednesday)){
          return 0.60 * numWings + ((activity as MenuActivity).PRICE_PER_SAUCE * sauceQuantity)
      }
      if (meatType == "Boneless"){
          return ((activity as MenuActivity).PRICE_PER_BONELESS * numWings) +
                  ((activity as MenuActivity).PRICE_PER_SAUCE * sauceQuantity)
      }

      if (meatType == "Bone"){
          return ((activity as MenuActivity).PRICE_PER_BONE * numWings) +
                  ((activity as MenuActivity).PRICE_PER_SAUCE * sauceQuantity)
      }

      if (meatType == "Tenders"){
          return ((activity as MenuActivity).PRICE_PER_TENDER * numWings) +
                  ((activity as MenuActivity).PRICE_PER_SAUCE * sauceQuantity)
      }
      return price
  }

}
