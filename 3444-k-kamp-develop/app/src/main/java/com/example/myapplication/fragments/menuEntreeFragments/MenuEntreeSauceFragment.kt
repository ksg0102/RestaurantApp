package com.example.myapplication.fragments.menuEntreeFragments

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.ChefActivity
import com.example.myapplication.activities.MenuActivity
import com.example.myapplication.apipackage.ResponseOrders
import com.example.myapplication.apipackage.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/** This fragment class is Step 34in the ordering process in which the customer enters
 * the sauce wanted
 */
//var sauceCounts = mutableListOf(0,0,0,0)
class MenuEntreeSauceFragment : Fragment() {
    var sauceCounts = mutableListOf(0,0,0,0)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_entree_sauce, container, false)
        runGraidentAnimation(view)

        //TODO: Verify that this works.

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
                    view.findViewById<TextView>(R.id.text_no_sauce_popular).apply {
                        if (output != null) {
                            text = output.get(0).entree[0].toString()
                        }
                    }

                    if (output != null) {
                        for (i in 0..(output.size-1)) {
                            for (j in 0..(output.get(i).entree.size-1)) {
                                if (output.get(i).entree[j].sauceType == "ranch") {
                                    sauceCounts[3] += 1
                                }
                                else if (output.get(i).entree[j].sauceType == "blue cheese") {
                                    sauceCounts[2] += 1
                                }
                                else if (output.get(i).entree[j].sauceType == "honey mustard") {
                                    sauceCounts[1] += 1
                                }
                                else sauceCounts[0] +=1

                            }
                        }

                    }
                    if (sauceCounts[3] >= sauceCounts[2] && sauceCounts[3] >= sauceCounts[1] && sauceCounts[3] >= sauceCounts[0]) {
                        view.findViewById<TextView>(R.id.text_ranch_popular).apply { visibility = View.VISIBLE }
                    }
                    else if (sauceCounts[2] >= sauceCounts[3] && sauceCounts[2] >= sauceCounts[1] && sauceCounts[2] >= sauceCounts[0]) {
                        view.findViewById<TextView>(R.id.text_cheese_popular).apply { visibility = View.VISIBLE }
                    }
                    else if (sauceCounts[1] >= sauceCounts[3] && sauceCounts[1] >= sauceCounts[2] && sauceCounts[1] >= sauceCounts[0]) {
                        view.findViewById<TextView>(R.id.text_mustard_popular).apply { visibility = View.VISIBLE }
                    }
                    else {
                        view.findViewById<TextView>(R.id.text_no_sauce_popular).apply { visibility = View.VISIBLE }
                    }

                }

            })

        /* Initialize sauce option buttons */
        val ranchButton = view.findViewById<Button>(R.id.button_ranch)
        val bleuCheeseButton = view.findViewById<Button>(R.id.button_bleu_cheese)
        val honeyMustardButton = view.findViewById<Button>(R.id.button_honey_mustard)
        val noSauceButton = view.findViewById<Button>(R.id.button_no_sauce)

        val helpButtonSauce = view.findViewById<ImageButton>(R.id.button_help_image_sauce)
        val refillButtonSauce = view.findViewById<ImageButton>(R.id.button_refill_image_sauce)

        /* Store sauce option in order and proceed to nest fragment */

        ranchButton.setOnClickListener{
            (activity as MenuActivity).sauceType = getString(R.string.ranch)
            (activity as MenuActivity).entreeId+=100
            (activity as MenuActivity).idStringEntree = (activity as MenuActivity).idStringEntree +(activity as MenuActivity).entreeId.toString() + " "
            (activity as MenuActivity).entreeId = 0
            (activity as MenuActivity).replaceFragment(MenuEntreeSauceQuantityFragment(), "")
        }

        bleuCheeseButton.setOnClickListener {
            (activity as MenuActivity).sauceType = getString(R.string.bleu_cheese)
            (activity as MenuActivity).entreeId+=200
            (activity as MenuActivity).idStringEntree = (activity as MenuActivity).idStringEntree +(activity as MenuActivity).entreeId.toString() + " "
            (activity as MenuActivity).entreeId = 0
            (activity as MenuActivity).replaceFragment(MenuEntreeSauceQuantityFragment(), "")
        }

        honeyMustardButton.setOnClickListener{
            (activity as MenuActivity).sauceType = getString(R.string.honey_mustard)
            (activity as MenuActivity).entreeId+=300
            (activity as MenuActivity).idStringEntree = (activity as MenuActivity).idStringEntree +(activity as MenuActivity).entreeId.toString() + " "
            (activity as MenuActivity).entreeId = 0
            (activity as MenuActivity).replaceFragment(MenuEntreeSauceQuantityFragment(), "")
        }

        noSauceButton.setOnClickListener{
            (activity as MenuActivity).sauceType = getString(R.string.none)
            (activity as MenuActivity).entreeId+=400
            (activity as MenuActivity).idStringEntree = (activity as MenuActivity).idStringEntree +(activity as MenuActivity).entreeId.toString() + " "
            (activity as MenuActivity).entreeId = 0
            (activity as MenuActivity).replaceFragment(MenuEntreeNoteFragment(), "")
        }

        /* Listeners to address Help and Refill requests */
        helpButtonSauce.setOnClickListener{
            Toast.makeText((activity as MenuActivity).applicationContext, "A waiter will help you shortly", Toast.LENGTH_LONG).show()
        }

        refillButtonSauce.setOnClickListener{
            Toast.makeText((activity as MenuActivity).applicationContext, "A waiter refill your drink shortly", Toast.LENGTH_LONG).show()
        }

        return view
    }


    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.menu_entree_sauce)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }
}
