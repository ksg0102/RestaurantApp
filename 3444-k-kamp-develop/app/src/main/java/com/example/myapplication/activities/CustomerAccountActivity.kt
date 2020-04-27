package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.fragments.CustomerAcctSelectFragment
import com.example.myapplication.database.CustomerDataMapHelper
import com.example.myapplication.R
import com.example.myapplication.data_structs.Customer
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class CustomerAccountActivity : AppCompatActivity() {
    val activity = this@CustomerAccountActivity
    val customer_db = CustomerDataMapHelper(activity)
    lateinit var currentCustomer: Customer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_account)
        supportActionBar!!.hide()

        /* Direct to account select screen */
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.customer_acct_fragment_container, CustomerAcctSelectFragment())
        transaction.commit()

        //generateCustomers()

    }

    /** Populate customer database with customers */
    private fun generateCustomers(){
        val customer1 = Customer("123456789", "Pam Beasly", "cece", "April 4", 3, 40)
        val customer2 = Customer("123456788", "Stan Lee", "marvel", "April 5", 6, 100)

        customer_db.addCustomer(customer1)
        customer_db.addCustomer(customer2)
    }


    /**
     * This method is to validate the input text fields and verify login credentials from database
     */
    fun verifyLogin(id :String, password:String): Boolean {

        if (id.isEmpty() || password.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter an ID and password", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!customer_db.isValidCustomer(id)){
            Toast.makeText(applicationContext, "Please enter a valid ID", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!customer_db.verfiyCustomerPassword(id, password)) {
            Toast.makeText(applicationContext, "Please re-enter your password", Toast.LENGTH_SHORT).show()
            return false
        }
        Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_LONG).show()
        return true
    }



    fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.customer_acct_fragment_container, fragment, tag).addToBackStack("").commit()
    }

    fun resetCustomer(){
        lateinit var currentCustomer: Customer
    }


}