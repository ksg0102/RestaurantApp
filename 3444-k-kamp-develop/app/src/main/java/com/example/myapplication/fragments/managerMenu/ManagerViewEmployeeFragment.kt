package com.example.myapplication.fragments.managerMenu

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.ManagerActivity
import com.example.myapplication.apipackage.ResponseEmployee
import com.example.myapplication.apipackage.RetrofitClient
import kotlinx.android.synthetic.main.fragment_manager_view_employee.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ManagerViewEmployeeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_manager_view_employee, container, false)
        runGraidentAnimation(view)

        val addButton = view.findViewById<Button>(R.id.button_add)
        val removebutton = view.findViewById<Button>(R.id.button_remove)
        //val editButton = view.findViewById<Button>(R.id.button_edit)
        val viewAllButton = view.findViewById<Button>(R.id.button_view_all)

        //editButton.setOnClickListener { (activity as ManagerActivity).replaceFragment(ManagerEditEmployeesFragment(), "") }
        viewAllButton.setOnClickListener { (activity as ManagerActivity).replaceFragment(ManagerViewAllEmployeesFragment(), "") }

        addButton.setOnClickListener {
            val id = Integer.parseInt(employee_id.text.toString().trim())
            val password = employee_password.text.toString().trim()
            val name = employee_name.text.toString().trim()
            val wage= Integer.parseInt(employee_wage.text.toString().trim())
            val role = employee_role.text.toString().trim()
            val hours = Integer.parseInt(employee_hours.text.toString().trim())
            val tips = employee_tips.text.toString().toDouble()
            val cmeals = Integer.parseInt(employee_compmeals.text.toString().trim())

            /*if (id == NULL) {
                employee_id.error = "Please enter an ID"
                employee_id.requestFocus()
                return@setOnClickListener
            }*/

            if (password.isEmpty()) {
                employee_password.error = "Please enter a name"
                employee_password.requestFocus()
                return@setOnClickListener
            }
            if (name.isEmpty()) {
                employee_name.error = "Please enter a name"
                employee_name.requestFocus()
                return@setOnClickListener
            }
            /*if (wage.isEmpty()) {
                employee_wage.error = "Please enter a phone number"
                employee_wage.requestFocus()
                return@setOnClickListener
            }*/
            if (role.isEmpty()) {
                employee_role.error = "Please enter a password"
                employee_role.requestFocus()
                return@setOnClickListener
            }
            /*if (hours.isEmpty()) {
                employee_hours.error = "Please enter your birthday"
                employee_hours.requestFocus()
                return@setOnClickListener
            }*/

            RetrofitClient.instance.createEmp(
                id,
                password,
                name,
                wage,
                role,
                hours,
                tips,
                cmeals
            )
                .enqueue(object : Callback<ResponseEmployee> {
                    override fun onFailure(call: Call<ResponseEmployee>, t: Throwable) {
                        Toast.makeText(
                            activity as ManagerActivity,
                            t.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onResponse(
                        call: Call<ResponseEmployee>,
                        response: Response<ResponseEmployee>
                    ) {
                        Toast.makeText(
                            activity as ManagerActivity,
                            response.body()?.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }

        removebutton.setOnClickListener{

            var id = view.findViewById<EditText>(R.id.employee_id).text.toString().toInt()

            RetrofitClient.instance.deleteEmployee(id)
                .enqueue(object : Callback<ResponseEmployee> {
                    override fun onFailure(call: Call<ResponseEmployee>, t: Throwable) {
                        Toast.makeText(
                            activity as ManagerActivity,
                            "FAIL",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onResponse(
                        call: Call<ResponseEmployee>,
                        response: Response<ResponseEmployee>
                    ) {
                        Toast.makeText(
                            activity as ManagerActivity,
                            "FIRED",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }
        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.manager_view_employee)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

}
