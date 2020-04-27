package com.example.myapplication.fragments

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.example.myapplication.R
import com.example.myapplication.activities.CustomerAccountActivity
import com.example.myapplication.apipackage.ResponseCustomers
import com.example.myapplication.apipackage.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EmployeeLoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_employee_login, container, false)
        runGraidentAnimation(view)

        /*val username = view.findViewById<EditText>(R.id.employee_username)
        username.getText().toString()
        val password = view.findViewById<EditText>(R.id.employee_password)
        password.getText().toString()*/
        val loginButton = view.findViewById<Button>(R.id.login_button)

        loginButton.setOnClickListener {
            RetrofitClient.instance.allcustomers(
            )
                .enqueue(object : Callback<ResponseCustomers> {
                    override fun onFailure(call: Call<ResponseCustomers>, t: Throwable) {
                        Toast.makeText(
                            activity as CustomerAccountActivity,
                            "Customer Already Exist",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onResponse(
                        call: Call<ResponseCustomers>,
                        response: Response<ResponseCustomers>
                    ) {
                        val output = response.body()?.customers?.get(0)?.name
                        /*Toast.makeText(
                            activity as CustomerAccountActivity,
                            response.body()?.customer,
                            Toast.LENGTH_LONG
                        ).show()*/
                        view.findViewById<TextView>(R.id.textView11).text = output

                    }


                })

        }




        return view
    }

    private fun runGraidentAnimation(v: View) {
        val relativeLayout = v.findViewById<RelativeLayout>(R.id.fragment_employee_login)
        val animationDrawable = relativeLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

}

