package com.example.myapplication.fragments.managerMenu

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
import com.example.myapplication.activities.CustomerAccountActivity
import com.example.myapplication.activities.ManagerActivity
import com.example.myapplication.apipackage.*
import kotlinx.android.synthetic.main.fragment_manager_view_all_employees.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ManagerViewAllEmployeesFragment : Fragment() {

    var index = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_manager_view_all_employees, container, false)
        runGraidentAnimation(view)
        val inde2 = 0
        view.findViewById<Button>(R.id.leave_button).setOnClickListener { (activity as ManagerActivity).replaceFragment(ManagerMenuFragment(), "")}

        //view.findViewById<Button>(R.id.get_employees_button).setOnClickListener {
            RetrofitClient.instance.getAllEmp().enqueue(object : Callback<ResponseEmployees> {
                override fun onFailure(call: Call<ResponseEmployees>, t: Throwable) {
                    view.findViewById<TextView>(R.id.employee_position)
                        .apply { text = "UNSUCCESSFUL" }
                }

                override fun onResponse(
                    call: Call<ResponseEmployees>,
                    response: Response<ResponseEmployees>
                ) {

                    val output = response.body()?.employees

                    if (output != null) {
                        view.findViewById<EditText>(R.id.employee_id).hint =
                            output.get(index).id.toString()

                        view.findViewById<EditText>(R.id.employee_name).hint =
                            output.get(index).name

                        view.findViewById<EditText>(R.id.employee_wage).hint =
                            output.get(index).wage.toString()

                        view.findViewById<EditText>(R.id.employee_role).hint =
                            output.get(index).role

                        view.findViewById<EditText>(R.id.employee_hours).hint =
                            output.get(index).hours.toString()

                        view.findViewById<EditText>(R.id.employee_tips).hint =
                            output.get(index).tips.toString()

                        view.findViewById<EditText>(R.id.employee_compmeals).hint =
                            output.get(index).compmeals.toString()
                    }

                    /*view.findViewById<Button>(R.id.leave_button).setOnClickListener {
                        (activity as ManagerActivity).replaceFragment(
                            ManagerViewEmployeeFragment(),
                            ""
                        )
                    }*/
                    view.findViewById<Button>(R.id.previous_button)
                        .setOnClickListener { empsPrevious(output) }
                    view.findViewById<Button>(R.id.next_button)
                        .setOnClickListener { empsNext(output) }

                }
            })



            view.findViewById<Button>(R.id.update_employee).setOnClickListener {

                var empID = 0
                if (employee_id.text.isNullOrEmpty()) { empID = employee_id.hint.toString().toInt()}
                else empID = employee_id.text.toString().toInt()
                var ename = ""
                if (employee_name.text.isNullOrEmpty()) {ename = employee_name.hint.toString().trim()}
                else ename = employee_name.text.toString().trim()
                var ewage = 0
                if (employee_wage.text.isNullOrEmpty()) {ewage = employee_wage.hint.toString().toInt()}
                else ewage = employee_wage.text.toString().toInt()
                var erole = ""
                if (employee_role.text.isNullOrEmpty()) {erole = employee_role.hint.toString().trim()}
                else erole = employee_role.text.toString().trim()
                var ehours = 0
                if (employee_hours.text.isNullOrEmpty()) {ehours = employee_hours.hint.toString().toInt()}
                else ehours = employee_hours.text.toString().toInt()
                var etips = 0.0
                if (employee_tips.text.isNullOrEmpty()) {etips = employee_tips.hint.toString().toDouble()}
                else etips = employee_tips.text.toString().toDouble()
                var ecompmeals = 0
                if (employee_compmeals.text.isNullOrEmpty()) {ecompmeals = employee_compmeals.hint.toString().toInt()}
                else ecompmeals = employee_compmeals.text.toString().toInt()

                RetrofitClient.instance.updateEmp(empID, ename, ewage, erole, ehours, etips, ecompmeals)
                .enqueue(object : Callback<ResponseEmployee> {
                    override fun onFailure(call: Call<ResponseEmployee>, t: Throwable) {
                        Toast.makeText(
                            activity as ManagerActivity,
                            "Customer Already Exist",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onResponse(
                        call: Call<ResponseEmployee>,
                        response: Response<ResponseEmployee>
                    ) {
                        Toast.makeText(
                            activity as ManagerActivity,
                            "update sucessfull",
                            Toast.LENGTH_LONG
                        ).show()

                    }


                })

            }
        //view.findViewById<Button>(R.id.leave_button).setOnClickListener { (activity as ManagerActivity).replaceFragment(ManagerMenuFragment(), "")}
        return view
    }

    fun empsPrevious(employeeList: List<Employee>?) {
        if (index == 0) {
            if (employeeList != null) {
                index = employeeList.size - 1
            }
        }
        else index -= 1



      view?.findViewById<TextView>(R.id.employee_position)?.apply{
            if (employeeList != null) {
                text = (index+1).toString() + " OF " + employeeList.size.toString()
            }
        
        }

      view?.findViewById<EditText>(R.id.employee_id)?.apply { hint = employeeList?.get(index)?.id.toString()
            text.clear()}
        view?.findViewById<EditText>(R.id.employee_name)?.apply { hint = employeeList?.get(index)?.name
            text.clear()}
        view?.findViewById<EditText>(R.id.employee_wage)?.apply { hint = employeeList?.get(index)?.wage.toString()
            text.clear()}
        view?.findViewById<EditText>(R.id.employee_role)?.apply { hint = employeeList?.get(index)?.role
            text.clear()}
        view?.findViewById<EditText>(R.id.employee_hours)?.apply { hint = employeeList?.get(index)?.hours.toString()
            text.clear()}
        view?.findViewById<EditText>(R.id.employee_tips)?.apply { hint = employeeList?.get(index)?.tips.toString()
            text.clear()}
        view?.findViewById<EditText>(R.id.employee_compmeals)?.apply { hint = employeeList?.get(index)?.compmeals.toString()
            text.clear()}
    }
    fun empsNext(employeeList: List<Employee>?) {
        if (employeeList != null) {
            if (index == employeeList.size - 1) index = 0
            else index += 1
        }

        view?.findViewById<TextView>(R.id.employee_position)?.apply{
            if (employeeList != null) {
                text = (index+1).toString() + " OF " + employeeList.size.toString()
            }
        }

        view?.findViewById<EditText>(R.id.employee_id)?.apply { hint = employeeList?.get(index)?.id.toString()
            text.clear()}
        view?.findViewById<EditText>(R.id.employee_name)?.apply { hint = employeeList?.get(index)?.name
            text.clear()}
        view?.findViewById<EditText>(R.id.employee_wage)?.apply { hint = employeeList?.get(index)?.wage.toString()
            text.clear()}
        view?.findViewById<EditText>(R.id.employee_role)?.apply { hint = employeeList?.get(index)?.role
            text.clear()}
        view?.findViewById<EditText>(R.id.employee_hours)?.apply { hint = employeeList?.get(index)?.hours.toString()
            text.clear()}
        view?.findViewById<EditText>(R.id.employee_tips)?.apply { hint = employeeList?.get(index)?.tips.toString()
            text.clear()}
        view?.findViewById<EditText>(R.id.employee_compmeals)?.apply { hint = employeeList?.get(index)?.compmeals.toString()
            text.clear()}
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.manager_view_all_employees)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }
}
