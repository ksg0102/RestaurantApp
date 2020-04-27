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
import com.example.myapplication.activities.ChefActivity
import com.example.myapplication.activities.ManagerActivity
import com.example.myapplication.apipackage.Order
import com.example.myapplication.apipackage.ResponseOrders
import com.example.myapplication.apipackage.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManagerOrderHistoryFragment : Fragment() {

    var index = 0
    var indez = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_manager_order_history, container, false)
        runGraidentAnimation(view)

        val getManOrder = view.findViewById<Button>(R.id.manOrdHistGetAll)

        getManOrder.setOnClickListener {
            RetrofitClient.instance.allOrdersManager()
                .enqueue(object : Callback<ResponseOrders> {
                    override fun onFailure(call: Call<ResponseOrders>, t: Throwable) {
                        Toast.makeText(activity as ManagerActivity, "Failure", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onResponse(call: Call<ResponseOrders>, response: Response<ResponseOrders>) {

                        /* Toast.makeText(activity as ManagerActivity, "Order History received", Toast.LENGTH_SHORT)
                             .show()*/


                        val output = response.body()?.orders

                        if (output != null) {
                            view.findViewById<EditText>(R.id.managerOrderHistID).hint =
                                output.get(index).id.toString()

                            view.findViewById<EditText>(R.id.managerOrderHistTable).hint =
                                output.get(index).tableNum.toString()

                            var outputString = ""
                            for (indez in 0..(output.get(index).entree.size - 1)) {

                                outputString += output.get(index).entree.get(indez).quantity.toString() + " " +
                                        output.get(index).entree.get(indez).meatType + " " +
                                        output.get(index).entree.get(indez).flavor + " " +
                                        output.get(index).entree.get(indez).sauceType + "\n"
                            }
                            indez = 0
                            view.findViewById<EditText>(R.id.managerOrderHistEntree).hint = outputString

                            outputString = ""
                            for (indez in 0..(output.get(index).entree.size - 1)) {
                                outputString += output.get(index).side.get(indez).quantity.toString() + " " +
                                        output.get(index).side.get(indez).item + "\n"
                            }
                            indez = 0
                            view.findViewById<EditText>(R.id.managerOrderHistSide).hint = outputString

                            outputString = ""
                            for (indez in 0..(output.get(index).entree.size - 1)) {
                                outputString += output.get(index).drink.get(indez).quantity.toString() + " " +
                                        output.get(index).drink.get(indez).item + "\n"
                            }
                            indez = 0
                            view.findViewById<EditText>(R.id.managerOrderHistDrink).hint = outputString

                            view.findViewById<EditText>(R.id.managerOrderHistNote).hint =
                                output.get(index).note

                            view.findViewById<EditText>(R.id.managerOrderHistTotal).hint =
                                output.get(index).orderTotal.toString()

                            view.findViewById<EditText>(R.id.managerOrderHistStatus).hint =
                                output.get(index).status.toString()
                        }
                        else { view.findViewById<EditText>(R.id.managerOrderHistID).hint = "NO DATA"}

                        view.findViewById<Button>(R.id.manOrdHistPrev)
                            .setOnClickListener { orderPrevious(output) }
                        view.findViewById<Button>(R.id.manOrdHistNext)
                            .setOnClickListener { orderNext(output) }
                    }

                })
        }

        return view
    }


    fun orderPrevious(orderList: List<Order>?) {
        if (orderList != null) {
            if (index == 0) {
                index = orderList.size - 1
            } else {
                index -= 1
            }

            view?.findViewById<EditText>(R.id.managerOrderHistID)
                ?.apply {
                    hint = orderList.get(index).id.toString()
                    text.clear()
                }
            view?.findViewById<EditText>(R.id.managerOrderHistTable)
                ?.apply {
                    hint = orderList.get(index).tableNum.toString()
                    text.clear()
                }

            var outputString = ""
            for (indez in 0..(orderList.get(index).entree.size - 1)) {
                outputString +=
                    orderList.get(index).entree.get(indez).quantity.toString() + " " +
                            orderList.get(index).entree.get(indez).meatType + " " +
                            orderList.get(index).entree.get(indez).flavor + " " +
                            orderList.get(index).entree.get(indez).sauceType + "\n"
            }
            indez = 0
            view?.findViewById<EditText>(R.id.managerOrderHistEntree)?.apply {
                hint = outputString
                text.clear()
            }

            outputString = ""
            for (indez in 0..(orderList.get(index).side.size - 1)) {
                outputString += orderList.get(index).side.get(indez).quantity.toString() + " " +
                        orderList.get(index).side.get(indez).item + "\n"
            }
            indez = 0
            view?.findViewById<EditText>(R.id.managerOrderHistSide)?.apply {
                hint = outputString
                text.clear()
            }

            outputString = ""

            for (indez in 0..(orderList.get(index).drink.size - 1)) {
                outputString += orderList.get(index).drink.get(indez).quantity.toString() + " " +
                        orderList.get(index).drink.get(indez).item + "\n"
            }

            indez = 0
            view?.findViewById<EditText>(R.id.managerOrderHistDrink)?.apply {
                hint = outputString
                text.clear()
            }
            view?.findViewById<EditText>(R.id.managerOrderHistNote)
                ?.apply {
                    hint = orderList.get(index).note
                    text.clear()
                }
            view?.findViewById<EditText>(R.id.managerOrderHistTotal)
                ?.apply {
                    hint = orderList.get(index).orderTotal.toString()
                    text.clear()
                }
            view?.findViewById<EditText>(R.id.managerOrderHistStatus)
                ?.apply {
                    hint = orderList.get(index).status.toString()
                    text.clear()
                }

        }
    }

    fun orderNext(orderList: List<Order>?) {
        if (orderList != null) {
            if (index == orderList.size - 1) {
                index = 0
            } else {
                index += 1
            }


            view?.findViewById<EditText>(R.id.managerOrderHistID)
                ?.apply {
                    hint = orderList.get(index).id.toString()
                    text.clear()
                }
            view?.findViewById<EditText>(R.id.managerOrderHistTable)
                ?.apply {
                    hint = orderList.get(index).tableNum.toString()
                    text.clear()
                }

            var outputString = ""
            for (indez in 0..(orderList.get(index).entree.size - 1)) {
                outputString +=
                    orderList.get(index).entree.get(indez).quantity.toString() + " " +
                            orderList.get(index).entree.get(indez).meatType + " " +
                            orderList.get(index).entree.get(indez).flavor + " " +
                            orderList.get(index).entree.get(indez).sauceType + "\n"
            }

            indez = 0
            view?.findViewById<EditText>(R.id.managerOrderHistEntree)?.apply {
                hint = outputString
                text.clear()
            }

            outputString = ""
            for (indez in 0..(orderList.get(index).side.size - 1)) {
                outputString += orderList.get(index).side.get(indez).quantity.toString() + " " +
                        orderList.get(index).side.get(indez).item + "\n"
            }

            indez = 0
            view?.findViewById<EditText>(R.id.managerOrderHistSide)?.apply {
                hint = outputString
                text.clear()
            }

            outputString = ""
            for (indez in 0..(orderList.get(index).drink.size - 1)) {
                outputString += orderList.get(index).drink.get(indez).quantity.toString() + " " +
                        orderList.get(index).drink.get(indez).item + "\n"
            }

            indez = 0
            view?.findViewById<EditText>(R.id.managerOrderHistDrink)?.apply {
                hint = outputString
                text.clear()
            }
            view?.findViewById<EditText>(R.id.managerOrderHistNote)
                ?.apply {
                    hint = orderList.get(index).note
                    text.clear()
                }
            view?.findViewById<EditText>(R.id.managerOrderHistTotal)
                ?.apply {
                    hint = orderList.get(index).orderTotal.toString()
                    text.clear()
                }
            view?.findViewById<EditText>(R.id.managerOrderHistStatus)
                ?.apply {
                    hint = orderList.get(index).status.toString()
                    text.clear()
                }
        }

    }
    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.manager_view_orders)

        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }


}

