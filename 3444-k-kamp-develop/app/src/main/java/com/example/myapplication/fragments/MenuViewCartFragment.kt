package com.example.myapplication.fragments

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.MenuActivity
import com.example.myapplication.data_structs.Drink
import com.example.myapplication.data_structs.Entree
import com.example.myapplication.data_structs.Side
import android.widget.TextView
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_menu_view_cart.*
import android.util.TypedValue
import android.graphics.Color
import android.util.DisplayMetrics
import android.content.res.Resources
import java.lang.reflect.Type
import kotlin.math.roundToInt
import android.graphics.Typeface
import android.view.Menu
import android.widget.Button
import android.widget.Toast

/** This class shows all the items that have been added to the cart and order price total */
class MenuViewCartFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_view_cart, container, false)


        /* Initialize price totals
        var entreePriceTotal = 0.0
        var sidePriceTotal = 0.0
        var drinkPriceTotal = 0.0
         */

        /* Initialize layout variable that will be dynamically programmed on */
        val layout = view.findViewById<LinearLayout>(R.id.menu_view_cart)

        /* Display all the stored entrees */
        val entreePriceTotal = displayEntrees(layout)

        /* Display all stored sides */
        val sidePriceTotal = displaySides(layout)

        /* Display all stored drinks */
        val drinkPriceTotal = displayDrinks(layout)

        /* Display "Order Total" title and calculate total  */
        displayTitleText(layout, getString(R.string.order_total))
        val orderTotalCalculation = "%.2f".format(entreePriceTotal + sidePriceTotal + drinkPriceTotal).toDouble()
        (activity as MenuActivity).orderTotal = orderTotalCalculation.toString()

        val orderTotal = (activity as MenuActivity).orderTotal
        displayTitleText(layout, "$orderTotal")

        /* Create Order() with most up to date entree, side, and drink information */
        (activity as MenuActivity).createOrder()



        val nextButton = Button(activity as MenuActivity)
        nextButton.text = getString(R.string.next)
        nextButton.setBackgroundColor(Color.BLACK)
        nextButton.setTextColor(Color.WHITE)
        layout.addView(nextButton)

        nextButton.setOnClickListener {
            (activity as MenuActivity).replaceFragment(MenuPlaceOrderFragment(), "")
        }


        return view
    }

    /** Method displays all the stored entrees and returns total cost of entrees  */
    fun displayEntrees(layout: LinearLayout): Double{

        /* Initialize price total */
        var entreePriceTotal = 0.0

        /* Get number of entrees */
        val numEntrees = (activity as MenuActivity).getNumEntrees() - 1

        /* Iterate and display entrees */
        for (i in 0..numEntrees) {
            val textView = TextView((activity as MenuActivity))
            val entree = (activity as MenuActivity).entreeList[i]
            textView.text = createEntreeDisplay(entree)
            entreePriceTotal += entree.price
            setTextViewAttributes(textView)
            layout.addView(textView)
        }
        /* Separate entree section from next section */
        addLineSeperator(layout)
        return entreePriceTotal
    }

    /** Method formats string that shows entree information */
    fun createEntreeDisplay(entree: Entree): String{
        val entreeQuantity = entree.quantity.toString()
        val entreeFlavor = entree.flavor.toString()
        val entreeMeatType = entree.meatType.toString()
        val entreeSauceQuantity = entree.sauceQuantity.toString()
        val entreeSauceType = entree.sauceType.toString()
        val entreeAdditionalNote = entree.note.toString()
        val entreePrice = entree.price.toString()

        (activity as MenuActivity).orderNotes = (activity as MenuActivity).orderNotes + entreeAdditionalNote + " "

        if (entreeMeatType == "Boneless" || entreeMeatType == "Bone"){
            return("$entreeQuantity $entreeFlavor $entreeMeatType Wings with $entreeSauceQuantity" +
                    " $entreeSauceType serving(s). \nAdditional notes: $entreeAdditionalNote \nPrice: $$entreePrice")

        }
        return("$entreeQuantity $entreeFlavor $entreeMeatType with $entreeSauceQuantity" +
                " $entreeSauceType serving(s). \nAdditional notes: $entreeAdditionalNote \nPrice: $$entreePrice")

    }

    /** Method displays "Sides" title after all the entrees have been displayed */
    fun displayTitleText(layout: LinearLayout, text: String){
        val sideTextView = TextView(activity as MenuActivity)
        sideTextView.text = text
        sideTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
        sideTextView.setTypeface(null, Typeface.BOLD)
        sideTextView.setTextColor(Color.BLACK)
        layout.addView(sideTextView)
    }

    /** Method displays all the stored sides and returns total side cost */
    fun displaySides (layout: LinearLayout): Double{

        /* Display "Sides" title */
        displayTitleText(layout, getString(R.string.sides))

        /* Initialize price total */
        var sidePriceTotal = 0.0

        /* Get number of sides */
        val numSides = (activity as MenuActivity).getNumSides() - 1

        /* Iterate and display sides */
        for (i in 0..numSides) {
            val textView = TextView((activity as MenuActivity))
            val side = (activity as MenuActivity).sideList[i]
            textView.text = createSideDisplay(side)
            sidePriceTotal += side.price
            setTextViewAttributes(textView)
            layout.addView(textView)
        }
        /* Separate sides section from next section */
        addLineSeperator(layout)
        return sidePriceTotal
    }

    /** Method formats string that shows side information */
    fun createSideDisplay(side: Side): String {
        val sideItem = side.item.toString()
        val sideQuantity = side.quantity.toString()
        val sideNote = side.note.toString()
        val sidePrice = side.price.toString()

        (activity as MenuActivity).orderNotes = (activity as MenuActivity).orderNotes + sideNote + " "

        return ("$sideQuantity serving(s) of $sideItem \nAdditional note: $sideNote \nPrice: $$sidePrice")

    }


    /** Method displays all the stored drinks and returns total drink cost  */
    fun displayDrinks (layout: LinearLayout): Double{

        /* Display "Drinks title */
        displayTitleText(layout, getString(R.string.drinks))

        /* Initialize price total */
        var drinkPriceTotal = 0.0

        /* Get number of drinks */
        val numDrinks = (activity as MenuActivity).getNumDrinks() - 1

        /* Iterate and display entrees */
        for (i in 0..numDrinks) {
            val textView = TextView((activity as MenuActivity))
            val drink = (activity as MenuActivity).drinkList[i]
            textView.text = createDrinkDisplay(drink)
            drinkPriceTotal += drink.price
            setTextViewAttributes(textView)
            layout.addView(textView)
        }

        /* Separate drink section from next section */
        addLineSeperator(layout)
        return drinkPriceTotal
    }

    /** Method formats string that shows drink information */
    fun createDrinkDisplay(drink: Drink): String {
        val drinkItem = drink.item.toString()
        val drinkQuantity = drink.quantity.toString()
        val drinkNote = drink.note.toString()
        val drinkPrice = drink.price.toString()

        (activity as MenuActivity).orderNotes = (activity as MenuActivity).orderNotes + drinkNote + " "

        return ("$drinkQuantity serving(s) of $drinkItem \nAdditional note: $drinkNote \nPrice: $$drinkPrice")

    }

    /** Method calculates the order total */



    /** HELPER METHOD: Method is used to display dynamic text properly */
    private fun setTextViewAttributes(textView: TextView) {
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        params.setMargins(
            convertDpToPixel(16),
            convertDpToPixel(16),
            0, 0
        )

        textView.setTextColor(Color.BLACK)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        textView.layoutParams = params
    }

    /** Helper method for setTextAttributes() */
    private fun convertDpToPixel(dp: Int): Int {
        val metrics = Resources.getSystem().displayMetrics
        val px = dp * (metrics.densityDpi / 160f)
        return px.roundToInt()
    }

    /** HELPER METHOD: Displays line */
    private fun addLineSeperator(layout:LinearLayout) {
        val lineLayout = LinearLayout(activity as MenuActivity)
        lineLayout.setBackgroundColor(Color.BLACK)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            2
        )
        params.setMargins(0, convertDpToPixel(10), 0, convertDpToPixel(10))
        lineLayout.layoutParams = params
        layout.addView(lineLayout)
    }


}
