package com.example.myapplication.fragments.waiterMenu

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.ChefActivity
import com.example.myapplication.activities.CustomerAccountActivity
import com.example.myapplication.activities.WaiterActivity
import com.example.myapplication.activities.MenuActivity
import com.example.myapplication.apipackage.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import android.graphics.Typeface
import android.widget.*
import com.example.myapplication.fragments.MenuPlaceOrderFragment
import kotlin.math.roundToInt


class WaiterTableAlertFragment : Fragment() {

    /* Create empty table list */
    var serviceTableList = arrayListOf<Table>()
    lateinit var tableList: List<Table>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_waiter_table_alert, container, false)
        runGraidentAnimation(view)
        RetrofitClient.instance.checkHelp()
            .enqueue(object: Callback<ResponseNice> {
                override fun onFailure(call: Call<ResponseNice>, t: Throwable) {
                    Toast.makeText(activity as WaiterActivity, "Order not ready", Toast.LENGTH_LONG).show()
                }
                override fun onResponse(
                    call: Call<ResponseNice>,
                    response: Response<ResponseNice>
                ) {
                    val output = response.body()?.tables

                    //view.findViewById<TextView>(R.id.employee_name).text = output

                    if (output != null) {
                        var newString="Needs Help: \n"
                        for(i in (0..output.size-1)){
                            newString+= output.get(i).toString() + "\n"

                        }
                        view.findViewById<TextView>(R.id.help).text = newString
                    }
                }
            })
        RetrofitClient.instance.checkRefill()
            .enqueue(object: Callback<ResponseNice> {
                override fun onFailure(call: Call<ResponseNice>, t: Throwable) {
                    Toast.makeText(activity as WaiterActivity, "Order not ready", Toast.LENGTH_LONG).show()
                }
                override fun onResponse(
                    call: Call<ResponseNice>,
                    response: Response<ResponseNice>
                ) {
                    val output = response.body()?.tables

                    if (output != null) {
                        var newString="Needs Refill: \n"
                        for(i in (0..output.size-1)){
                            newString+= output.get(i).toString() + "\n"

                        }
                        view.findViewById<TextView>(R.id.refill).text = newString
                    }
                }
            })

        val clear = view.findViewById<Button>(R.id.clearAll)
        clear.setOnClickListener {

            if (view.findViewById<EditText>(R.id.tableIdTocl).text.toString().isEmpty()) {
                Toast.makeText(activity as WaiterActivity, "Please enter an ID.", Toast.LENGTH_LONG)
                    .show()
            } else {
                val id = view.findViewById<EditText>(R.id.tableIdTocl).text.toString().toInt()


                RetrofitClient.instance.clearHelp(id)
                    .enqueue(object : Callback<ResponseTable> {
                        override fun onFailure(call: Call<ResponseTable>, t: Throwable) {
                            Toast.makeText(
                                activity as WaiterActivity,
                                "Order not ready",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        override fun onResponse(
                            call: Call<ResponseTable>,
                            response: Response<ResponseTable>
                        ) {
                            Toast.makeText(
                                activity as WaiterActivity,
                                "Table Helped",
                                Toast.LENGTH_LONG
                            ).show()
                            (activity as WaiterActivity).replaceFragment(WaiterMenuFragment(), "")
                        }
                    })
            }
        }
        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.waiter_view_alerts)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

}