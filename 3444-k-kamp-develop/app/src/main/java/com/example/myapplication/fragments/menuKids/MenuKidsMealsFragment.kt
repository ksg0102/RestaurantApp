package com.example.myapplication.fragments.menuKids

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.TextView

import com.example.myapplication.R
import com.example.myapplication.activities.MenuActivity
import com.example.myapplication.apipackage.ResponseTable
import com.example.myapplication.apipackage.RetrofitClient
import com.example.myapplication.data_structs.Entree
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuKidsMealsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_kids_meals, container, false)
        runGraidentAnimation(view)

        /* Initialize buttons */
        val kidsBarbecueInfoButton = view.findViewById<ImageButton>(R.id.image_button_kids_barbecue)
        val kidsLemonInfoButton = view.findViewById<ImageButton>(R.id.image_button_kids_lemon)
        val kidsBarbecueButton = view.findViewById<Button>(R.id.button_barbecue_kids)
        val kidsLemonButton = view.findViewById<Button>(R.id.button_lemon_kids)

        kidsBarbecueInfoButton.setOnClickListener{ (activity as MenuActivity).replaceFragment(KidsBarbecueComboFragment(), "") }
        kidsLemonInfoButton.setOnClickListener { (activity as MenuActivity).replaceFragment(KidsLemonComboFragment(), "") }

        kidsBarbecueButton.setOnClickListener {
            (activity as MenuActivity).meatType = getString(R.string.boneless)
            (activity as MenuActivity).flavorType = getString(R.string.barbecue)
            (activity as MenuActivity).sauceType = getString(R.string.ranch)
            (activity as MenuActivity).sauceQuantity = 1
            (activity as MenuActivity).numWings = (activity as MenuActivity).KIDS_NUM_WINGS

            (activity as MenuActivity).replaceFragment(MenuKidsNoteFragment(), "")
        }

        kidsLemonButton.setOnClickListener {
            (activity as MenuActivity).meatType = getString(R.string.boneless)
            (activity as MenuActivity).flavorType = getString(R.string.lemon_pepper)
            (activity as MenuActivity).sauceType = getString(R.string.honey_mustard)
            (activity as MenuActivity).sauceQuantity = 1
            (activity as MenuActivity).numWings = (activity as MenuActivity).KIDS_NUM_WINGS

            (activity as MenuActivity).replaceFragment(MenuKidsNoteFragment(), "")
        }

        val helpButtonNote = view.findViewById<ImageButton>(R.id.button_help_image_kids)
        val refillButtonNote = view.findViewById<ImageButton>(R.id.button_refill_image_kids)

        /* Listeners to address Help and Refill requests */
        /* Send help notification to the waiter */
        helpButtonNote.setOnClickListener{
            //Toast.makeText((activity as MenuActivity).applicationContext, "A waiter will help you shortly", Toast.LENGTH_LONG).show()

            /* Save table status to the database  */
            /*
            RetrofitClient.instance.updateTable((activity as MenuActivity).table.number, "Needs Help",
                needHelp = true,
                needRefill = false,
                orderTotal = 0.0
            ).enqueue(object: Callback<ResponseTable> {
                override fun onFailure(call: Call<ResponseTable>, t: Throwable) {
                    Toast.makeText(
                        activity as MenuActivity,
                        t.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
                override fun onResponse(call: Call<ResponseTable>, response: Response<ResponseTable>) {
                    Toast.makeText(
                        activity as MenuActivity,
                        "A waiter will help you shortly",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

             */
        }


        /* Send refill notification to the waiter */
        refillButtonNote.setOnClickListener{
            //Toast.makeText((activity as MenuActivity).applicationContext, "A waiter refill your drink shortly", Toast.LENGTH_LONG).show()

            /* Save table status to database */
            /*
            RetrofitClient.instance.updateTable((activity as MenuActivity).table.number, "Needs Refill",
                needHelp = false,
                needRefill = true,
                orderTotal = 0.0
            ).enqueue(object: Callback<ResponseTable> {
                override fun onFailure(call: Call<ResponseTable>, t: Throwable) {
                    Toast.makeText(
                        activity as MenuActivity,
                        t.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
                override fun onResponse(call: Call<ResponseTable>, response: Response<ResponseTable>) {
                    Toast.makeText(
                        activity as MenuActivity,
                        "A waiter will refill your drink shortly",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

             */
        }

        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.menu_kids_menu)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

}
