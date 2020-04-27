package com.example.myapplication.activities


import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.ImageButton
import android.widget.Toast

import androidx.fragment.app.Fragment
import com.example.myapplication.*
import com.example.myapplication.data_structs.Drink
import com.example.myapplication.fragments.MainMenuFragment
import com.example.myapplication.data_structs.Entree
import com.example.myapplication.data_structs.Side
import com.example.myapplication.data_structs.Order
import com.example.myapplication.apipackage.Table
import com.example.myapplication.fragments.CustomerTableNumberFragment
import java.util.*

/** This activity deals with menu operations */

class MenuActivity : AppCompatActivity() {

    val activity = this@MenuActivity

    /* Store current table information */
    var table = Table(0, "None", 0 , 0, 0.0)
    var waiterID = -1

    var entreeId = 0

    var idStringEntree = ""

    var idDrinkSe = ""

    var drinkId = 0

    var sideId = 0
    var sideIdSe = ""
    var tableNumber = 0
    /* Setting up variables that are part of Entree() */
    lateinit var meatType: String
    lateinit var flavorType: String
    lateinit var sauceType: String
    var sauceQuantity = 0
    var numWings = 0
    lateinit var note:String
    var entreePrice = 0.0

    lateinit var entree: Entree

    /* Set up variables that are part of Side() */
    lateinit var sideItem: String
    var sideItemQuantity = 0
    lateinit var sideNote: String
    var sidePrice = 0.0

    lateinit var side: Side

    /* Set up variables that are part of Drink() */
    lateinit var drinkItem: String
    var drinkItemQuantity = 0
    lateinit var drinkNote: String
    var drinkPrice = 0.0

    lateinit var drink: Drink

    /* Set up Order() that stores list of entrees, sides, and drinks */
    lateinit var order: Order
    lateinit var orderTotal: String
    var orderNotes = ""

    /* Setting up arrays to store all the entrees, sides, and drinks in a session */
    val entreeList = arrayListOf<Entree>()
    val sideList = arrayListOf<Side>()
    val drinkList = arrayListOf<Drink>()
    val orderList = arrayListOf<Order>()


    /* Constants */
    val MIN_NUM_WINGS = 3
    val MIN_NUM_SIDES = 1
    val MIN_NUM_DRINKS = 1
    val PRICE_PER_BONELESS = 1.50
    val PRICE_PER_BONE = 1.75
    val PRICE_PER_TENDER = 2.00
    val PRICE_PER_SAUCE = 0.75
    val PRICE_PER_SIDE = 2.00
    val PRICE_PER_DRINK = 1.50
    val KIDS_NUM_WINGS = 3
    val KIDS_COMBO_PRICE = 5.00

    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        /*val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.menu_fragment_container,
            MainMenuFragment()

         */
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.menu_fragment_container, CustomerTableNumberFragment())
        transaction.commit()

        supportActionBar!!.hide()
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.menu_fragment_container, fragment, tag).addToBackStack("").commit()
    }

    /* Add entree to list stored for the table  */
    fun addEntree() { entreeList.add(entree) }

    /* Reset entree variable */
    fun resetEntree() {
        lateinit var meatType: String
        lateinit var flavorType: String
        lateinit var sauceType: String
        var sauceQuantity = 0
        var numWings = 0
        lateinit var note:String
        var entreePrice = 0.0

        lateinit var entree: Entree

    }

    /* Get number of entrees per single order */
    fun getNumEntrees():Int{ return entreeList.size }

    fun addSide(){ sideList.add(side) }

    fun resetSide(){
        lateinit var sideItem: String
        var sideItemQuantity = 0
        lateinit var sideNote: String
        var sidePrice = 0.0

        lateinit var side: Side
    }

    fun getNumSides(): Int { return sideList.size}

    fun addDrink() { drinkList.add(drink) }

    fun resetDrink(){
        lateinit var drinkItem: String
        var drinkItemQuantity = 0
        lateinit var drinkNote: String
        var drinkPrice = 0.0
    }

    fun getNumDrinks(): Int{ return drinkList.size}

    fun createOrder(){
        order = Order(entreeList, sideList, drinkList, orderTotal)
    }

    fun addOrder() { orderList.add(order) }

    fun getNumOrders():Int {return orderList.size}

    fun resetOrder(){
        lateinit var order: Order
    }

    fun getOrderTotal(): Double {
        var totalPrice = 0.0
        for (order in orderList){
            totalPrice += order.orderTotal.toDouble()
        }
        return totalPrice
        }

    /* Method returns current weekday */
    /*
    fun getCurrentDay(): String{
        val daysArray = arrayOf("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
        val calendar = Calendar.getInstance()
        val dayIndex = calendar.get(Calendar.DAY_OF_WEEK)
        return daysArray[dayIndex]
    }
*/
    fun getCurrentDay(): String{
        val daysArray = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        return daysArray[currentDay - 1]
    }

    /** Method calculates the tip based off of percentage and order total
     * @return new total with added tip
     */
    fun calculateTip(percentage: Double): Double {
        var includingTip = getOrderTotal()

        if(percentage == 0.0)
            return includingTip

        includingTip += includingTip*percentage.toDouble()

        includingTip = "%.2f".format(includingTip).toDouble()

        return includingTip
    }
}
