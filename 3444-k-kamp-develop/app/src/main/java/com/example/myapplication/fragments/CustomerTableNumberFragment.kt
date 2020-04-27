package com.example.myapplication.fragments

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.Button
import android.content.Intent
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.activities.CustomerAccountActivity

import com.example.myapplication.R
import com.example.myapplication.activities.ChefActivity
import com.example.myapplication.activities.MainActivity
import com.example.myapplication.activities.MenuActivity
import com.example.myapplication.apipackage.ResponseEmployees
import com.example.myapplication.apipackage.ResponseTables
import com.example.myapplication.apipackage.RetrofitClient
import kotlinx.android.synthetic.main.fragment_customer_table_number.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.myapplication.apipackage.ResponseTable

class CustomerTableNumberFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_customer_table_number, container, false)
        runGraidentAnimation(view)

        val nextButtonTable = view.findViewById<Button>(R.id.button_next_table_num)

        nextButtonTable.setOnClickListener {
            (activity as MenuActivity).tableNumber = view.findViewById<EditText>(R.id.table_number).text.toString().toInt()
            val tableNum = (activity as MenuActivity).tableNumber.toInt()
            if (tableNum < 1 || tableNum > 20) {
                Toast.makeText(
                    activity as MenuActivity,
                    "Please enter a number between 1 and 20",
                    Toast.LENGTH_LONG
                ).show()
            }
            else {
                (activity as MenuActivity).table.number = (activity as MenuActivity).tableNumber

                //Waiter IDs
                /*if (tableNum < 11) {
                    //(activity as MenuActivity).waiterID = 1

                    RetrofitClient.instance.getAllEmp()
                        .enqueue(object : Callback<ResponseEmployees> {
                            override fun onFailure(call: Call<ResponseEmployees>, t: Throwable) {
                                Toast.makeText(
                                    activity as MenuActivity,
                                    t.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            override fun onResponse(
                                call: Call<ResponseEmployees>,
                                response: Response<ResponseEmployees>
                            ) {
                                var index = 0
                                while (true) {
                                    if (response.body()?.employees?.get(index)?.role == "waiter") {
                                        (activity as MenuActivity).waiterID =
                                            response.body()?.employees?.get(index)?.id!!
                                        break
                                    }
                                    index += 1
                                }
                            }
                        })
                } else {
                    //(activity as MenuActivity).waiterID = 2

                    RetrofitClient.instance.getAllEmp()
                        .enqueue(object : Callback<ResponseEmployees> {
                            override fun onFailure(call: Call<ResponseEmployees>, t: Throwable) {
                                Toast.makeText(
                                    activity as MenuActivity,
                                    t.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            override fun onResponse(
                                call: Call<ResponseEmployees>,
                                response: Response<ResponseEmployees>
                            ) {
                                var index = 0
                                var second = false
                                while (true) {
                                    if (response.body()?.employees?.get(index)?.role == "waiter") second =
                                        true
                                    if (response.body()?.employees?.get(index)?.role == "waiter" && second) {
                                        (activity as MenuActivity).waiterID =
                                            response.body()?.employees?.get(index)?.id!!
                                        break
                                    }
                                    index += 1
                                }
                            }
                        })
                }*/

                /*


                /* Save table status to database */
                RetrofitClient.instance.updateTable(
                    tableNum, "Ordering",
                    needHelp = false,
                    needRefill = false,
                    orderTotal = 0.0
                ).enqueue(object : Callback<ResponseTable> {
                    override fun onFailure(call: Call<ResponseTable>, t: Throwable) {
                        Toast.makeText(
                            activity as MenuActivity,
                            t.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onResponse(
                        call: Call<ResponseTable>,
                        response: Response<ResponseTable>
                    ) {
                        Toast.makeText(
                            activity as MenuActivity,
                            "Assigning you your waiter",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })

                 */

                (activity as MenuActivity).replaceFragment(MainMenuFragment(), "")
            }
        }




        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.customer_table_number)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

}
