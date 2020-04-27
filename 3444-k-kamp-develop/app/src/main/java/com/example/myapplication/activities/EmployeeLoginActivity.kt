package com.example.myapplication.activities

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast

import com.example.myapplication.*
import com.example.myapplication.apipackage.ResponseEmployee
import com.example.myapplication.apipackage.RetrofitClient
import com.example.myapplication.data_structs.Employee
import com.example.myapplication.database.EmployeeDataMapHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/* Class logs employees in */
/* Database is currently a hashmap -- SQLLite database has bugs in its functionality */
class EmployeeLoginActivity : AppCompatActivity() {

    private val activity = this@EmployeeLoginActivity
    private val employee_db = EmployeeDataMapHelper(activity)
    //private val database = EmployeeDatabaseHelper(activity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.fragment_employee_login)
        supportActionBar!!.hide()

        runGraidentAnimation()
        generateStaff()
        //generateDBStaff()

        /* Perform once login button in clicked */
        val loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            /* Take user input */
            val u = findViewById<EditText>(R.id.employee_username)

            // username is the ID
            val username = u.getText().toString()
            val p = findViewById<EditText>(R.id.employee_password)
            val password = p.getText().toString()

            /* Verify password */
            if (verifyLogin(username, password)) {

                /*Reroute to kitchen staff, mananger, or waiter */
                val employeePos = employee_db.getEmployeePosisition(username)

                if (employeePos == "Manager"){
                    val intent = Intent (activity, ManagerActivity::class.java)
                    activity.startActivity(intent)
                }

                if (employeePos == "Waiter"){
                    val intent = Intent (activity, WaiterActivity::class.java)
                    activity.startActivity(intent)
                }

                if (employeePos == "Chef"){
                    val intent = Intent (activity, ChefActivity::class.java)
                    activity.startActivity(intent)
                }
            }
        }
    }

    /**
     * Method generates random staff -- for testing purposes
     */
    private fun generateStaff(){
        val manager = Employee(
            "1234",
            "Micheal Scott",
            "9726368842",
            "office",
            "Manager",
            40.0,
            40.0
        )
        val waiter1 = Employee(
            "5678",
            "Dwight Schrute",
            "2141234567",
            "beets",
            "Waiter",
            20.0,
            40.0
        )
        val waiter2= Employee(
            "4321",
            "Jim Halpert",
            "9400000000",
            "pam",
            "Waiter",
            20.0,
            40.0
        )
        val chef = Employee(
            "9876",
            "Kevin Malone",
            "4697777777",
            "chilli",
            "Chef",
            40.0,
            40.0
        )
        employee_db.addEmployee(manager)
        employee_db.addEmployee(waiter1)
        employee_db.addEmployee(waiter2)
        employee_db.addEmployee(chef)
    }

    /* For SQLite Implementation
    private fun generateDBStaff() {
        val manager = Employee ("1234", "Micheal Scott", "9726368842", "office", "Manager", 40.0, 40.0)
        val waiter1 = Employee ("5678", "Dwight Schrute", "2141234567", "beets", "Waiter", 20.0, 40.0)
        val waiter2= Employee ("4321", "Jim Halpert", "9400000000", "pam", "Waiter", 20.0, 40.0)
        val chef = Employee ("9876", "Kevin Malone", "4697777777", "chilli", "Chef", 40.0, 40.0)

        database.addEmployee(manager)
        database.addEmployee(waiter1)
        database.addEmployee(waiter2)
        database.addEmployee(chef)
    }
    */

    /**
     * This method is to validate the input text fields and verify login credentials from database
     */
    private fun verifyLogin(id :String, password:String): Boolean {

        if (id.isEmpty() || password.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter an ID and password", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!employee_db.isValidEmployee(id)){
            Toast.makeText(applicationContext, "Please enter a valid ID", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!employee_db.verfiyEmployeePassword(id, password)) {
            Toast.makeText(applicationContext, "Please re-enter your password", Toast.LENGTH_SHORT).show()
            return false
        }
        Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_LONG).show()
        return true
    }

    /* For SQLLite implemenation
    private fun verifyFromSQLLite(id :String, password:String): Boolean {
        if (id.isEmpty() || password.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter an ID and password", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!database.checkUser(id)) {
            Toast.makeText(applicationContext, "Please re-enter your password", Toast.LENGTH_SHORT).show()
            return false
        }

        Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_LONG).show()
        return true

    }
*/

    private fun runGraidentAnimation() {
        val relativeLayout = findViewById<RelativeLayout>(R.id.fragment_employee_login)
        val animationDrawable = relativeLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

}

