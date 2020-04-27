package com.example.myapplication.fragments.chefMenu

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
//import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.data_structs.Entree
import com.example.myapplication.activities.ChefActivity
import com.example.myapplication.apipackage.*
import kotlinx.android.synthetic.main.fragment_chef_view_orders.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChefViewOrdersFragment : Fragment() {

    var index =0
    var indez =0
    var idD = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chef_view_orders, container, false)

        runGraidentAnimation(view)



        val getOrder = view.findViewById<Button>(R.id.chefOrderGetOrds)

        getOrder.setOnClickListener {
            RetrofitClient.instance.allorders()
                .enqueue(object : Callback<ResponseOrders> {
                    override fun onFailure(call: Call<ResponseOrders>, t: Throwable) {
                        Toast.makeText(activity as ChefActivity,"Failure", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<ResponseOrders>,
                        response: Response<ResponseOrders>
                    ) {
                        val output = response.body()?.orders

                        if (output != null) {
                            view.findViewById<TextView>(R.id.chefOrderID).text =
                                output.get(index).id.toString()
                            idD=output.get(index).id.toString().toInt()

                            view.findViewById<TextView>(R.id.chefOrderTable).text =
                                output.get(index).tableNum.toString()

                            var outputString = ""
                            for(indez in 0..(output.get(index).entree.size-1)) {
                                outputString +=   output.get(index).entree.get(indez).quantity.toString() + " " +
                                        output.get(index).entree.get(indez).meatType + " " +
                                        output.get(index).entree.get(indez).flavor + " " +
                                        output.get(index).entree.get(indez).sauceType +"\n"
                            }
                            indez=0
                            view.findViewById<TextView>(R.id.chefOrderEntree).text = outputString

                            outputString = ""
                            for(indez in 0..(output.get(index).entree.size-1)) {
                                outputString += output.get(index).side.get(indez).quantity.toString() + " "+
                                        output.get(index).side.get(indez).item+"\n"
                            }
                            indez=0
                            view.findViewById<TextView>(R.id.chefOrderSides).text = outputString

                            outputString = ""
                            for(indez in 0..(output.get(index).entree.size-1)) {
                                outputString+= output.get(index).drink.get(indez).quantity.toString() + " " +
                                        output.get(index).drink.get(indez).item+"\n"
                            }
                            indez=0
                            view.findViewById<TextView>(R.id.chefOrderDrinks).text = outputString

                            view.findViewById<TextView>(R.id.chefOrderNotes).text =
                                output.get(index).note

                            view.findViewById<TextView>(R.id.chefOrderTotal).text =
                                output.get(index).orderTotal.toString()

                            view.findViewById<EditText>(R.id.chefOrderStatus).hint =
                                output.get(index).status.toString()
                        }
                        else {
                            view.findViewById<TextView>(R.id.chefOrderID).text = "NO DATA"
                        }

                        view.findViewById<Button>(R.id.chefOrderNext)
                            .setOnClickListener { orderPrevious(output) }
                        view.findViewById<Button>(R.id.chefOrderPrevious)
                            .setOnClickListener { orderNext(output) }
                    }

                })

            val updateStatus = view.findViewById<Button>(R.id.chefOrderUpdate)
            updateStatus.setOnClickListener {
                //idD = view.findViewById<TextView>(R.id.chefOrderID).text.toString().toInt()
               RetrofitClient.instance.finish(idD)
                    .enqueue(object : Callback<ResponseOrder> {
                        override fun onFailure(call: Call<ResponseOrder>, t: Throwable) {
                            Toast.makeText(activity as ChefActivity, "Failure", Toast.LENGTH_SHORT)
                                .show()
                        }

                        override fun onResponse(
                            call: Call<ResponseOrder>,
                            response: Response<ResponseOrder>
                        ) {
                            Toast.makeText(activity as ChefActivity, "Order Updated", Toast.LENGTH_SHORT)
                                .show()
                        }

                    })
            }


        }



        return view
    }



   fun orderPrevious(orderList:List<Order>?) {
       if (orderList != null) {
           if (index == 0) {
               index = orderList.size - 1
           } else {
               index -= 1
           }

           view?.findViewById<TextView>(R.id.chefOrderID)
               ?.apply { text = orderList.get(index).id.toString() }
           view?.findViewById<TextView>(R.id.chefOrderTable)
               ?.apply { text = orderList.get(index).tableNum.toString() }

           var outputString = ""

           for (indez in 0..(orderList.get(index).entree.size - 1)) {
               outputString +=
                   orderList.get(index).entree.get(indez).quantity.toString() + " " +
                           orderList.get(index).entree.get(indez).meatType + " " +
                           orderList.get(index).entree.get(indez).flavor + " " +
                           orderList.get(index).entree.get(indez).sauceType + "\n"
           }

           indez = 0
           view?.findViewById<TextView>(R.id.chefOrderEntree)?.apply { text = outputString }

           outputString = ""

           for (indez in 0..(orderList.get(index).side.size - 1)) {
               outputString += orderList.get(index).side.get(indez).quantity.toString() + " " +
                       orderList.get(index).side.get(indez).item + "\n"
           }

           indez = 0
           view?.findViewById<TextView>(R.id.chefOrderSides)?.apply { text = outputString }

           outputString = ""

           for (indez in 0..(orderList.get(index).drink.size - 1)) {
               outputString += orderList.get(index).drink.get(indez).quantity.toString() + " " +
                       orderList.get(index).drink.get(indez).item + "\n"
           }

           indez = 0
           view?.findViewById<TextView>(R.id.chefOrderDrinks)?.apply { text = outputString }
           view?.findViewById<TextView>(R.id.chefOrderNotes)
               ?.apply { text = orderList.get(index).note }
           view?.findViewById<TextView>(R.id.chefOrderTotal)
               ?.apply { text = orderList.get(index).orderTotal.toString() }
           view?.findViewById<EditText>(R.id.chefOrderStatus)
               ?.apply { hint = orderList.get(index).status.toString() }

       }
   }
    fun orderNext(orderList: List<Order>?) {
        if (orderList != null) {
            if (index == orderList.size - 1) {
                index = 0
            } else {
                index += 1
            }

            view?.findViewById<TextView>(R.id.chefOrderID)
                ?.apply { text = orderList.get(index).id.toString() }
            view?.findViewById<TextView>(R.id.chefOrderTable)
                ?.apply { text = orderList.get(index).tableNum.toString() }

            var outputString = ""

            for (indez in 0..(orderList.get(index).entree.size - 1)) {
                outputString +=
                    orderList.get(index).entree.get(indez).quantity.toString() + " " +
                            orderList.get(index).entree.get(indez).meatType + " " +
                            orderList.get(index).entree.get(indez).flavor + " " +
                            orderList.get(index).entree.get(indez).sauceType + "\n"
            }

            indez = 0
            view?.findViewById<TextView>(R.id.chefOrderEntree)?.apply { text = outputString }

            outputString = ""

            for (indez in 0..(orderList.get(index).side.size - 1)) {
                outputString += orderList.get(index).side.get(indez).quantity.toString() + " " +
                        orderList.get(index).side.get(indez).item + "\n"
            }
            indez = 0
            view?.findViewById<TextView>(R.id.chefOrderSides)?.apply { text = outputString }

            outputString = ""
            for (indez in 0..(orderList.get(index).drink.size - 1)) {
                outputString += orderList.get(index).drink.get(indez).quantity.toString() + " " +
                        orderList.get(index).drink.get(indez).item + "\n"
            }

            indez = 0
            view?.findViewById<TextView>(R.id.chefOrderDrinks)?.apply { text = outputString }
            view?.findViewById<TextView>(R.id.chefOrderNotes)
                ?.apply { text = orderList.get(index).note }
            view?.findViewById<TextView>(R.id.chefOrderTotal)
                ?.apply { text = orderList.get(index).orderTotal.toString() }
            view?.findViewById<EditText>(R.id.chefOrderStatus)
                ?.apply { hint = orderList.get(index).status.toString() }
        }

    }


    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.chef_view_orders)

        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }
}