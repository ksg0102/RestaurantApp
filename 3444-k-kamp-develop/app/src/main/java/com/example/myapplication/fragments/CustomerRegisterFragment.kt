package com.example.myapplication.fragments

import android.content.Intent
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
import com.example.myapplication.data_structs.Customer

import com.example.myapplication.R
import com.example.myapplication.activities.CustomerAccountActivity
import com.example.myapplication.activities.MenuActivity
import com.example.myapplication.apipackage.DefaultResponse
import com.example.myapplication.apipackage.RetrofitClient
import kotlinx.android.synthetic.main.fragment_customer_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerRegisterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_customer_register, container, false)
        runGraidentAnimation(view)

        /* Account creation -- add new customer to database */
        /*val registerButton = view.findViewById<Button>(R.id.button_customer_register)
        registerButton.setOnClickListener{

            val name = view.findViewById<EditText>(R.id.customer_name).text.toString()
            val phoneNum = view.findViewById<EditText>(R.id.customer_phone).text.toString()
            val password = view.findViewById<EditText>(R.id.customer_password).text.toString()
            val birthday = view.findViewById<EditText>(R.id.customer_birthday).text.toString()

            val newCustomer = Customer(phoneNum, name, password, birthday, 0, 0)
            (activity as CustomerAccountActivity).customer_db.addCustomer(newCustomer)
            (activity as CustomerAccountActivity).currentCustomer = newCustomer

            Toast.makeText((activity as CustomerAccountActivity).applicationContext, "Account successfully created", Toast.LENGTH_LONG).show()

            val intent = Intent (activity, MenuActivity::class.java)
            activity?.startActivity(intent)
        }*/
        val registerButton = view.findViewById<Button>(R.id.button_customer_register)
        registerButton.setOnClickListener {
            val name = customer_name.text.toString().trim()
            val phone = customer_phone.text.toString().trim()
            val password = customer_password.text.toString().trim()
            val birthday = customer_birthday.text.toString().trim()

            if (name.isEmpty()) {
                customer_name.error = "Please enter a name"
                customer_name.requestFocus()
                return@setOnClickListener
            }

            if (phone.isEmpty()) {
                customer_phone.error = "Please enter a phone number"
                customer_phone.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                customer_password.error = "Please enter a password"
                customer_password.requestFocus()
                return@setOnClickListener
            }
            if (birthday.isEmpty()) {
                customer_birthday.error = "Please enter your birthday"
                customer_birthday.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.createcustomer(
                phone,
                name,
                password,
                birthday,
                visited = 1,
                credits = 10
            )
                .enqueue(object : Callback<DefaultResponse> {
                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(
                            activity as CustomerAccountActivity,
                            t.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        Toast.makeText(
                            activity as CustomerAccountActivity,
                            response.body()?.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                })
            val intent = Intent(activity, MenuActivity::class.java)
            activity?.startActivity(intent)
        }
        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.fragment_customer_acct_register)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }


}
