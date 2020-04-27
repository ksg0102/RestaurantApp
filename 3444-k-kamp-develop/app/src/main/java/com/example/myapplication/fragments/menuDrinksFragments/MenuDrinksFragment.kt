package com.example.myapplication.fragments.menuDrinksFragments

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapplication.R
import com.example.myapplication.activities.ChefActivity
import com.example.myapplication.activities.MenuActivity
import com.example.myapplication.apipackage.ResponseOrders
import com.example.myapplication.apipackage.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/** This class is step 1 in ordering a drink
 * in which the customer selects a drink option and
 * can view drink descriptions
 */
class MenuDrinksFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_drinks, container, false)
        runGraidentAnimation(view)

        //TODO: Check that this works.
        var drinkCounts = mutableListOf(0,0,0,0)
        RetrofitClient.instance.allorders()
            .enqueue(object : Callback<ResponseOrders> {
                override fun onFailure(call: Call<ResponseOrders>, t: Throwable) {
                    Toast.makeText(activity as MenuActivity,"Error retrieving order popularity", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<ResponseOrders>,
                    response: Response<ResponseOrders>
                ) {
                    val output = response.body()?.orders

                    if (output != null) {
                        for (i in 0..(output.size-1)) {
                            for (j in 0..(output.get(i).drink.size-1)) {
                                when (output.get(i).drink[j].item) {
                                    "lemonade" -> {
                                        drinkCounts[0] += output.get(i).drink[j].quantity
                                    }
                                    "soft drink" -> {
                                        drinkCounts[1] += output.get(i).drink[j].quantity
                                    }
                                    "sweet tea" -> {
                                        drinkCounts[2] += output.get(i).drink[j].quantity
                                    }
                                    "water" -> {
                                        drinkCounts[3] += output.get(i).drink[j].quantity
                                    }
                                }
                            }
                        }

                    }
                    if (drinkCounts[3] >= drinkCounts[2] && drinkCounts[3] >= drinkCounts[1] && drinkCounts[3] >= drinkCounts[0]) {
                        view.findViewById<TextView>(R.id.text_Water_Popular).apply { visibility = View.VISIBLE }
                    }
                    else if(drinkCounts[2] >= drinkCounts[3] && drinkCounts[2] >= drinkCounts[1] && drinkCounts[2] >= drinkCounts[0]) {
                        view.findViewById<TextView>(R.id.text_Tea_Popular).apply { visibility = View.VISIBLE }
                    }
                    else if(drinkCounts[1] >= drinkCounts[3] && drinkCounts[1] >= drinkCounts[2] && drinkCounts[1] >= drinkCounts[0]) {
                        view.findViewById<TextView>(R.id.text_Soda_Popular).apply { visibility = View.VISIBLE }
                    }
                    else {
                        view.findViewById<TextView>(R.id.text_Lemonade_Popular).apply { visibility = View.VISIBLE }
                    }
                }

            })


        /* Initialize icon info buttons */
        val lemonadeInfoButton = view.findViewById<ImageButton>(R.id.button_lemonade_image)
        val sodaInfoButton = view.findViewById<ImageButton>(R.id.button_soda_image)
        val teaInfoButton = view.findViewById<ImageButton>(R.id.button_tea_image)
        val waterInfoButton = view.findViewById<ImageButton>(R.id.button_water_image)

        /* Listeners to direct to respective description fragment */
        lemonadeInfoButton.setOnClickListener { (activity as MenuActivity).replaceFragment(MenuDrinksLemonadeFragment(), "") }
        sodaInfoButton.setOnClickListener { (activity as MenuActivity).replaceFragment(MenuDrinksSodaFragment(), "") }
        teaInfoButton.setOnClickListener { (activity as MenuActivity).replaceFragment(MenuDrinksTeaFragment(), "") }
        waterInfoButton.setOnClickListener { (activity as MenuActivity).replaceFragment(MenuDrinksWaterFragment(), "") }


        /* Initialize drink option buttons */
        val lemonadeButton = view.findViewById<Button>(R.id.button_lemonade)
        val sodaButton = view.findViewById<Button>(R.id.button_soda)
        val teaButton = view.findViewById<Button>(R.id.button_tea)
        val waterButton = view.findViewById<Button>(R.id.button_water)

        /* Listeners to store side and direct to proceeding fragment */
        lemonadeButton.setOnClickListener {
            (activity as MenuActivity).drinkItem = getString(R.string.lemonade)
            (activity as MenuActivity).drinkId = 503
            (activity as MenuActivity).replaceFragment(MenuDrinksQuantityFragment(), "")
        }

        sodaButton.setOnClickListener {
            (activity as MenuActivity).drinkItem = getString(R.string.soft_drink)
            (activity as MenuActivity).drinkId = 501
            (activity as MenuActivity).replaceFragment(MenuDrinksQuantityFragment(), "")
        }

        teaButton.setOnClickListener {
            (activity as MenuActivity).drinkItem = getString(R.string.sweet_tea)
            (activity as MenuActivity).drinkId = 502
            (activity as MenuActivity).replaceFragment(MenuDrinksQuantityFragment(), "")
        }

        waterButton.setOnClickListener {
            (activity as MenuActivity).drinkItem = getString(R.string.water)
            (activity as MenuActivity).drinkId = 504
            (activity as MenuActivity).replaceFragment(MenuDrinksQuantityFragment(), "")
        }

        /* Initialize help and refill buttons */
        val helpButtonDrink = view.findViewById<ImageButton>(R.id.button_help_image_drinks)
        val refillButtonDrink = view.findViewById<ImageButton>(R.id.button_refill_image_drinks)

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
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.menu_drink)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }
}
