package com.example.myapplication.fragments.menuPaymentFragments

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.CustomerAccountActivity
import com.example.myapplication.activities.MenuActivity
import com.example.myapplication.apipackage.ResponseEmployee
import com.example.myapplication.apipackage.ResponseTable
import com.example.myapplication.apipackage.RetrofitClient
import kotlinx.android.synthetic.main.fragment_menu_cash_check.*
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class MenuCashCheckFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_cash_check, container, false)
        runGraidentAnimation(view)

        /* Create a TextView in the XML and put a logo or something at top */

        /* Get and display the new order total giving a 15%, 20%, and 25% tip by implementing a method in the MenuActivity class */
        val orderTotal = (activity as MenuActivity).getOrderTotal()
        val yetTipTotal = view.findViewById<TextView>(R.id.cash_order_price)
        yetTipTotal.setTextColor(Color.BLACK)
        yetTipTotal.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        yetTipTotal.text = "$orderTotal"

        val noTipButton = view.findViewById<Button>(R.id.no_tip)//create button on xml
        val fifteenButton = view.findViewById<Button>(R.id.cash_fifteen)
        val twentyButton = view.findViewById<Button>(R.id.cash_twenty)
        val twentyfiveButton = view.findViewById<Button>(R.id.cash_twenty_five)

        noTipButton.setOnClickListener {
            val noTip = (activity as MenuActivity).calculateTip(0.00)
            val showNoTip = view.findViewById<TextView>(R.id.cash_total_price)
            showNoTip.setTextColor(Color.BLACK)
            showNoTip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            showNoTip.text="$noTip"
        }

        fifteenButton.setOnClickListener {
            val fifteenTip = (activity as MenuActivity).calculateTip(0.15)
            val showFifteenTip = view.findViewById<TextView>(R.id.cash_total_price)
            showFifteenTip.setTextColor(Color.BLACK)
            showFifteenTip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            showFifteenTip.text = "$fifteenTip"
        }

        twentyButton.setOnClickListener {
            val twentyTip = (activity as MenuActivity).calculateTip(0.20)
            val showTwentyTip = view.findViewById<TextView>(R.id.cash_total_price)
            showTwentyTip.setTextColor(Color.BLACK)
            showTwentyTip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            showTwentyTip.text = "$twentyTip"
        }

        twentyfiveButton.setOnClickListener {
            val twentyfiveTip = (activity as MenuActivity).calculateTip(0.25)
            val showTwentyfiveTip = view.findViewById<TextView>(R.id.cash_total_price)
            showTwentyfiveTip.setTextColor(Color.BLACK)
            showTwentyfiveTip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            showTwentyfiveTip.text = "$twentyfiveTip"
        }


        /* Create a "Pay" button */
        val cashCheckoutButton = view.findViewById<Button>(R.id.button_cash_checkout)

        /* Initialize payButton and setOnClickListener() */
        cashCheckoutButton.setOnClickListener {
            /*
            Toast.makeText((activity as MenuActivity).applicationContext, "A waiter is coming to collect bill", Toast.LENGTH_LONG).show()
            /* TODO Send an alarm to a waiter to collect a bill */
            RetrofitClient.instance.updateTable((activity as MenuActivity).table.number, "Needs Refill",
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

            //If the user did not manually enter an amount, use the Hint to take the total.
            val finalCost =
                view.findViewById<TextView>(R.id.cash_total_price).text.toString().toDouble()
                val tip = finalCost - orderTotal
                //TODO: Should update waiter with this tip value. Needs testing
                var waiter: com.example.myapplication.apipackage.Employee
                RetrofitClient.instance.getEmp((activity as MenuActivity).waiterID).enqueue(object: Callback<ResponseEmployee> {
                    override fun onFailure(call: Call<ResponseEmployee>, t: Throwable) {}
                    override fun onResponse(
                        call: Call<ResponseEmployee>,
                        response: Response<ResponseEmployee>
                    ) {
                        if (response.body() != null) {waiter = response.body()?.employee!!
                            RetrofitClient.instance.updateEmp(waiter.id, waiter.name, waiter.wage, waiter.role, waiter.hours, waiter.tips+tip, waiter.compmeals)
                        }
                    }
                })


                //TODO: Ensure this fits with customer-side login scripts.
            //TODO: Like with the credit card version, this can't point to CustomerAccountActivity.
                /*if ((activity as CustomerAccountActivity).currentCustomer != null) {
                    (activity as MenuActivity).replaceFragment(MenuAddRewardsFragment(), "")
                } else {*/
                    (activity as MenuActivity).replaceFragment(MenuFinalGameFragment(), "")
                //}

            (activity as MenuActivity).replaceFragment(MenuAddRewardsFragment(),"")
        }

        /* [in listener] display toast that waiter is coming to collect bill */
        /* go to MenuAddRewardsFragment() */


        /* Intialialize and set up help and refill button actions */
        val helpButtonMain = view.findViewById<ImageButton>(R.id.button_help_image_payment_cash_check)
        val refillButtonMain = view.findViewById<ImageButton>(R.id.button_refill_image_payment_cash_check)
        /* Send help notification to the waiter */
        helpButtonMain.setOnClickListener{
            //Toast.makeText((activity as MenuActivity).applicationContext, "A waiter will help you shortly", Toast.LENGTH_LONG).show()
/*
            /* Save table status to the database  */
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
        refillButtonMain.setOnClickListener{
            //Toast.makeText((activity as MenuActivity).applicationContext, "A waiter refill your drink shortly", Toast.LENGTH_LONG).show()
/*
            /* Save table status to database */
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
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.menu_payment_cash_check)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }


}
