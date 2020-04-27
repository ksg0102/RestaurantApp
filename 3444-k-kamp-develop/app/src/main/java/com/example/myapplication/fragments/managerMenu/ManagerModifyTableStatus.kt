package com.example.myapplication.fragments.managerMenu

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.Button
import android.widget.Toast

import com.example.myapplication.R
import com.example.myapplication.apipackage.ResponseTable
import com.example.myapplication.apipackage.RetrofitClient
import com.example.myapplication.activities.ManagerActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ManagerModifyTableStatus : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_manager_modify_table_status, container, false)
        runGraidentAnimation(view)

        /*
        var __needHelp = false
        var __needRefill = false

         */

        val tableNum = view.findViewById<EditText>(R.id.modify_table_number).text.toString().toInt()
        val newTableStatus = view.findViewById<EditText>(R.id.modify_table_status).text.toString()
        val newOrderTotal = view.findViewById<EditText>(R.id.modify_table_total).text.toString().toDouble()
        val modifyButton = view.findViewById<Button>(R.id.button_modify_table_status_option)

        modifyButton.setOnClickListener {
            /* Save table status to the database  */
            /*
            if (newTableStatus == getString(R.string.help_status)) { __needHelp = true }
            if (newTableStatus == getString(R.string.refill_status)) { __needRefill = true}



            RetrofitClient.instance.updateTable(tableNum, newTableStatus,
                needHelp = __needHelp,
                needRefill = __needRefill
		//TODO:
                //newOrderTotal
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
                        "Status changed successfully",
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
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.manager_modify_status)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }


}
