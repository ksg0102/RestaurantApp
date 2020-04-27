package com.example.myapplication.fragments.menuPaymentFragments

import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
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
import com.example.myapplication.data_structs.Employee
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuCreditCardFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_credit_card, container, false)
        runGraidentAnimation(view)

        /* Create a TextView in the XML and put a logo or something at top */

        /* Get and display the new order total giving a 15%, 20%, and 25% tip by implementing a method in the MenuActivity class */

        /* Create an EditText in the XML for the amount the user wants to pay */

        /* Create respective EditText fields in the XML for card number, expiration date, and security code */

        /* Create a "Pay" button */
        val total = (activity as MenuActivity).getOrderTotal()
        val newTotal = view.findViewById<TextView>(R.id.card_total)
        newTotal.setTextColor(Color.BLACK)
        newTotal.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        newTotal.text = "$total"

        /* Initialize payButton and setOnClickListener() */
        val payButton = view.findViewById<Button>(R.id.button_pay)
        payButton.setOnClickListener{
            //Mock credit card info checker. Just tests for proper length on inputs.
            if (view.findViewById<EditText>(R.id.cardNumber).text.length != 16 ||
                    view.findViewById<EditText>(R.id.date).text.length != 4 ||
                    view.findViewById<EditText>(R.id.securityCode).text.length != 3) {
                Toast.makeText((activity as MenuActivity).applicationContext, "Please re-enter your card information.", Toast.LENGTH_SHORT).show()
            }
            else {
                //If the user did not manually enter an amount, use the Hint to take the total.
                if (view.findViewById<EditText>(R.id.card_total).text.isNullOrEmpty()) {
                    val finalCost =
                        view.findViewById<EditText>(R.id.card_total).hint.toString().toDouble()
                    val tip = finalCost - total
                    //TODO: Should update waiter with this tip value. Needs testing
                    var waiter: com.example.myapplication.apipackage.Employee
                    RetrofitClient.instance.getEmp((activity as MenuActivity).waiterID).enqueue(object: Callback<ResponseEmployee> {
                        override fun onFailure(call: Call<ResponseEmployee>, t: Throwable) {}
                        override fun onResponse(
                            call: Call<ResponseEmployee>,
                            response: Response<ResponseEmployee>
                        ) {
                            if (response.body() != null) {
                                waiter = response.body()?.employee!!
                                RetrofitClient.instance.updateEmp(waiter.id, waiter.name, waiter.wage, waiter.role, waiter.hours, waiter.tips+tip, waiter.compmeals)
                            }
                        }
                    })


                    //TODO: Ensure this fits with customer-side login scripts.
                    //WARNING: CURRENTCUSTOMER CANNOT BE PULLED FROM CUSTOMERACCOUNTACTIVITY - PUTTING IN CUSTOMERACCOUNTACTIVITY CAUSES A CRASH.
                    /*if ((activity as MenuActivity).currentCustomer != null) {
                        (activity as MenuActivity).replaceFragment(MenuAddRewardsFragment(), "")
                    } else {*/
                        (activity as MenuActivity).replaceFragment(MenuFinalGameFragment(), "")
                    //}
                }
                //If the user entered an amount which is less than their total, throw an error.
                else if (view.findViewById<EditText>(R.id.card_total).text.toString().toDouble() < total) {
                    Toast.makeText(
                        (activity as MenuActivity).applicationContext,
                        "Please enter a valid amount",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val finalCost =
                        view.findViewById<EditText>(R.id.card_total).text.toString().toDouble()
                    val tip = finalCost - total
                    //TODO: Should update waiter with this tip value. Needs testing
                    var waiter: com.example.myapplication.apipackage.Employee
                    RetrofitClient.instance.getEmp((activity as MenuActivity).waiterID).enqueue(object: Callback<ResponseEmployee> {
                        override fun onFailure(call: Call<ResponseEmployee>, t: Throwable) {}
                        override fun onResponse(
                            call: Call<ResponseEmployee>,
                            response: Response<ResponseEmployee>
                        ) {
                            waiter = response.body()?.employee!!
                            RetrofitClient.instance.updateEmp(waiter.id, waiter.name, waiter.wage, waiter.role, waiter.hours, waiter.tips+tip, waiter.compmeals)
                        }
                    })

                    //TODO: Yeah this kinda just explodes because we're not able to access CustomerAccountActivity from here.
                    //if ((activity as CustomerAccountActivity).currentCustomer != null) {
                    //    (activity as MenuActivity).replaceFragment(MenuAddRewardsFragment(), "")
                    //} else {
                        (activity as MenuActivity).replaceFragment(MenuFinalGameFragment(), "")
                    //}
                }
            }
        }

        val nobutton = view.findViewById<Button>(R.id.button_notip)
        val fifteenbutton = view.findViewById<Button>(R.id.button_fifteentip)
        val twentybutton = view.findViewById<Button>(R.id.button_twentytip)
        val twentyfivebutton = view.findViewById<Button>(R.id.button_twentyfivetip)

        nobutton.setOnClickListener {
            val notip = (activity as MenuActivity).calculateTip(0.0)
            /*val shownotip =*/ view.findViewById<EditText>(R.id.card_total).apply {
                hint = "$notip"
                text.clear()
                setTextColor(Color.BLACK)
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            }
        }

        fifteenbutton.setOnClickListener {
            val fifteentip = (activity as MenuActivity).calculateTip(0.15)
            val showfifteentip = view.findViewById<EditText>(R.id.card_total).apply {
                hint = "$fifteentip"
                text.clear()
                setTextColor(Color.BLACK)
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            }
        }

        twentybutton.setOnClickListener {
            val twentytip = (activity as MenuActivity).calculateTip(0.20)
            val showtwentytip = view.findViewById<EditText>(R.id.card_total).apply {
                hint = "$twentytip"
                text.clear()
                setTextColor(Color.BLACK)
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            }
        }

        twentyfivebutton.setOnClickListener {
            val twentyfivetip = (activity as MenuActivity).calculateTip(0.25)
            val showtwentyfivetip = view.findViewById<EditText>(R.id.card_total).apply {
                hint = "$twentyfivetip"
                text.clear()
                setTextColor(Color.BLACK)
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            }
        }

        /* Intialialize and set up help and refill button actions */
        val helpButtonMain = view.findViewById<ImageButton>(R.id.button_help_image_payment_credit_card)
        val refillButtonMain = view.findViewById<ImageButton>(R.id.button_refill_image_payment_credit_card)
        /* Send help notification to the waiter */
        helpButtonMain.setOnClickListener{
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
        refillButtonMain.setOnClickListener{
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
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.menu_payment_credit_card)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }
}
