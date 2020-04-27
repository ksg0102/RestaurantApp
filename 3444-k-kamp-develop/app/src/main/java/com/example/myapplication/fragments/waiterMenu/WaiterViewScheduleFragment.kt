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
import com.example.myapplication.apipackage.ResponseEmployees
import com.example.myapplication.apipackage.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WaiterViewScheduleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_waiter_view_schedule, container, false)
        runGraidentAnimation(view)

        val informationButton = view.findViewById<Button>(R.id.button_get_info_waiter)
        val updateHoursButton = view.findViewById<Button>(R.id.button_update_hours_waiter)


            informationButton.setOnClickListener {
                val id = view.findViewById<EditText>(R.id.id_waiter)?.text.toString().toInt()

                RetrofitClient.instance.getAllEmp()
                    .enqueue(object : Callback<ResponseEmployees> {
                        override fun onFailure(call: Call<ResponseEmployees>, t: Throwable) {
                            Toast.makeText(
                                activity as WaiterActivity,
                                "Customer Already Exist",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        override fun onResponse(
                            call: Call<ResponseEmployees>,
                            response: Response<ResponseEmployees>
                        ) {
                            val empInfo = response.body()?.employees
                            var id2 = 0
                            var iterator = 0
                            var exit = true
                            while(true) {
                                if (empInfo != null) {
                                    if (iterator == empInfo.size) {
                                        break
                                    }
                                }
                                else{
                                    break
                                }
                                id2 = empInfo.get(iterator).id
                                if(id === id2 && empInfo.get(iterator).role == "waiter"){
                                    view.findViewById<TextView>(R.id.waiterHour)
                                        .apply { text = empInfo.get(iterator).hours.toString()
                                            visibility = View.VISIBLE }
                                    view.findViewById<TextView>(R.id.waiterWage)
                                        .apply {  text = empInfo.get(iterator).wage.toString()
                                            visibility=View.VISIBLE}
                                    view.findViewById<TextView>(R.id.waiterTips)
                                        .apply {  text = empInfo.get(iterator).tips.toString()
                                            visibility=View.VISIBLE}
                                    exit = false
                                }
                                iterator += 1

                                }
                            if (exit) {
                                Toast.makeText(
                                    activity as WaiterActivity,
                                    "INVALID ENTRY",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                        }



                    })

            }



            updateHoursButton.setOnClickListener {
                // TODO: Update chef hours in database
                Toast.makeText((activity as WaiterActivity).applicationContext,
                    "Unable to update hours at this time", Toast.LENGTH_SHORT).show()
            }

        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.waiter_view_schedule)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

}
