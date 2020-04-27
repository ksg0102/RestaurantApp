package com.example.myapplication.fragments.managerMenu

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.example.myapplication.R
import com.example.myapplication.activities.ManagerActivity
import com.example.myapplication.activities.MenuActivity
import com.example.myapplication.apipackage.ResponseTable
import com.example.myapplication.apipackage.RetrofitClient
import com.example.myapplication.apipackage.Table
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ManagerApplyDiscountFragment : Fragment() {

    var tableTotal = 0.0
    var table_num: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_manager_apply_discount, container, false)
        runGraidentAnimation(view)

        /* Initialize buttons */
        val discountButton = view.findViewById<Button>(R.id.button_calculate_discount)
        val updateTotalButton = view.findViewById<Button>(R.id.button_update_table_total)


        discountButton.setOnClickListener {
            table_num = view.findViewById<EditText>(R.id.discount_table_number).text.toString().toInt()
            val percentage = view.findViewById<EditText>(R.id.discount_percent).text.toString().toDouble()

            /*
            /* Get original table total from database  */
            RetrofitClient.instance.getTable(table_num).enqueue(object: Callback<ResponseTable> {
                override fun onFailure(call: Call<ResponseTable>, t: Throwable) {
                    Toast.makeText(
                        activity as ManagerActivity,
                        t.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onResponse(call: Call<ResponseTable>, response: Response<ResponseTable>) {
                    //TODO: FIX ORDER TOTAL VARIABLE
                    //tableTotal = response.body()!!.Tables.orderTotal
                    tableTotal = 10.00
                    tableTotal = getDiscountTotal(tableTotal, percentage)

                    /* Display new total */
                    val newTotalStr = "New Total: $$tableTotal"
                    view.findViewById<TextView>(R.id.text_new_total).text = newTotalStr
                    view.findViewById<TextView>(R.id.text_new_total).visibility = View.VISIBLE
                }
            })
            */
            tableTotal = 10.00
            tableTotal = getDiscountTotal(tableTotal, percentage)

            val newTotalStr = "New Total: $$tableTotal"
            view.findViewById<TextView>(R.id.text_new_total).text = newTotalStr
            view.findViewById<TextView>(R.id.text_new_total).visibility = View.VISIBLE

        }

        updateTotalButton.setOnClickListener {
        /*
            /* Update new table total to database */
            RetrofitClient.instance.updateTable(table_num, "Serviced",
                needHelp = false,
                needRefill = false,orderTotal = 0.0
            ).enqueue(object: Callback<ResponseTable> {
                override fun onFailure(call: Call<ResponseTable>, t: Throwable) {
                    Toast.makeText(
                        activity as ManagerActivity,
                        t.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
                override fun onResponse(call: Call<ResponseTable>, response: Response<ResponseTable>) {
                    Toast.makeText(
                        activity as ManagerActivity,
                        "Discount applied successfully!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

         */
            (activity as ManagerActivity).replaceFragment(ManagerMenuFragment(), "")
        }
        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.managerDiscount)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

    private fun getDiscountTotal(orderTotal: Double, percentage: Double): Double {
        return (1 - percentage) * orderTotal
    }
}
