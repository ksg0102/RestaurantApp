package com.example.myapplication.fragments.waiterMenu

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.WaiterActivity
import com.example.myapplication.apipackage.ResponseNice
import com.example.myapplication.apipackage.ResponseOrder
import com.example.myapplication.apipackage.ResponseTables
import com.example.myapplication.apipackage.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WaiterKitchenAlertFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_waiter_kitchen_alert, container, false)
        runGraidentAnimation(view)

        RetrofitClient.instance.serve()
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
                        var newString="Outgoing Orders: \n"
                        for(i in (0..output.size-1)){
                            newString+= output.get(i).toString() + "\n"

                        }
                        view.findViewById<TextView>(R.id.orderReady).text = newString
                    }
                }
            })

        val clear = view.findViewById<Button>(R.id.clearAll)
        clear.setOnClickListener {
            if (view.findViewById<EditText>(R.id.tableIdTocl).text.toString().isEmpty()) {
                Toast.makeText(activity as WaiterActivity, "Please enter an ID.", Toast.LENGTH_LONG).show()
            } else {
                val id = view.findViewById<EditText>(R.id.tableIdTocl).text.toString().toInt()


                RetrofitClient.instance.toHistory(id)
                    .enqueue(object : Callback<ResponseOrder> {
                        override fun onFailure(call: Call<ResponseOrder>, t: Throwable) {
                            Toast.makeText(
                                activity as WaiterActivity,
                                "Order not ready",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        override fun onResponse(
                            call: Call<ResponseOrder>,
                            response: Response<ResponseOrder>
                        ) {
                            Toast.makeText(
                                activity as WaiterActivity,
                                "Table Cleared",
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
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.waiter_kitchen_alert)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }
}
