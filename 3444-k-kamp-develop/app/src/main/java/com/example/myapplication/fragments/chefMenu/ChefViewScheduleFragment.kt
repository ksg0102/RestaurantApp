package com.example.myapplication.fragments.chefMenu

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
import com.example.myapplication.activities.ChefActivity
import com.example.myapplication.activities.CustomerAccountActivity
import com.example.myapplication.activities.ManagerActivity
import com.example.myapplication.activities.WaiterActivity
import com.example.myapplication.apipackage.*
import com.example.myapplication.fragments.managerMenu.ManagerMenuFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChefViewScheduleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chef_view_schedule, container, false)
        runGraidentAnimation(view)

        /* Initialize Buttons */
        val informationButton = view.findViewById<Button>(R.id.button_get_info_chef)
        val updateHoursButton = view.findViewById<Button>(R.id.button_update_hours_chef)
        val chef_id = view.findViewById<EditText>(R.id.id_chef)

        informationButton.setOnClickListener {
            val id = view.findViewById<EditText>(R.id.id_chef).text.toString().toInt()

            RetrofitClient.instance.getAllEmp()
                .enqueue(object : Callback<ResponseEmployees> {
                    override fun onFailure(call: Call<ResponseEmployees>, t: Throwable) {
                        Toast.makeText(
                            activity as ChefActivity,
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
                            if(id === id2 && empInfo.get(iterator).role == "chef"){
                                view.findViewById<TextView>(R.id.chefHour).apply { text = empInfo.get(iterator).hours.toString()
                                    visibility = View.VISIBLE }
                                view.findViewById<TextView>(R.id.chefWage).apply {  text = empInfo.get(iterator).wage.toString()
                                    visibility=View.VISIBLE}

                                exit = false
                            }
                            iterator += 1

                        }
                        if (exit) {
                            Toast.makeText(
                                activity as ChefActivity,
                                "INVALID ENTRY",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }


                })
        }

       updateHoursButton.setOnClickListener {
           // TODO: Update chef hours in database
           Toast.makeText((activity as ChefActivity).applicationContext,
               "Unable to update hours at this time", Toast.LENGTH_SHORT).show()
       }

        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.chef_view_schedule)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

}
