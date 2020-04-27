package com.example.myapplication.fragments.menuPaymentFragments

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.MenuActivity
import com.example.myapplication.apipackage.ResponseTable
import com.example.myapplication.apipackage.RetrofitClient
import com.example.myapplication.fragments.menuEntreeFragments.MenuEntreeMeatFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/** This class displays the users price totals and then asks for preferred method of payment */

class MenuPaymentMethodFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_payment_method, container, false)

        //Initialize button variables
        val creditButton = view.findViewById<Button>(R.id.button_credit_card)
        val cashButton = view.findViewById<Button>(R.id.button_cash)

        /* call getOrderTotal() from MenuActivity and display total */
        val orderTotal = (activity as MenuActivity).getOrderTotal()
        val totaltext = view.findViewById<TextView>(R.id.totalText)
        totaltext.text = "$$orderTotal"


        /* create a TextView in XML with "Please select Payment method */


        /* Create button in XML for Credit, Debit, Cash, etc. (follow style of buttons from other screens) */


        /* setOnClickListener() to go to requested payment method string */
        creditButton.setOnClickListener{
            (activity as MenuActivity).replaceFragment(MenuCreditCardFragment(),"")
        }

        cashButton.setOnClickListener{ (activity as MenuActivity).replaceFragment(
            MenuCashCheckFragment(),"") }


        /* Intialialize and set up help and refill button actions */
        val helpButtonMain = view.findViewById<ImageButton>(R.id.button_help_image_payment_method)
        val refillButtonMain = view.findViewById<ImageButton>(R.id.button_refill_image_payment_method)
        /* Send help notification to the waiter */
        helpButtonMain.setOnClickListener{
            //Toast.makeText((activity as MenuActivity).applicationContext, "A waiter will help you shortly", Toast.LENGTH_LONG).show()

            /* Save table status to the database  */
            RetrofitClient.instance.updateTable((activity as MenuActivity).table.number, "Needs Help",
                needHelp = 1,
                needRefill = 0,
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
        }


        /* Send refill notification to the waiter */
        refillButtonMain.setOnClickListener{
            //Toast.makeText((activity as MenuActivity).applicationContext, "A waiter refill your drink shortly", Toast.LENGTH_LONG).show()

            /* Save table status to database */
            RetrofitClient.instance.updateTable((activity as MenuActivity).table.number, "Needs Refill",
                needHelp = 0,
                needRefill = 1,
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
        }
        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.menu_payment_method)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }




}
