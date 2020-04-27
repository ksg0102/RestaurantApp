package com.example.myapplication.fragments.chefMenu

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
import com.example.myapplication.activities.CustomerAccountActivity
import com.example.myapplication.apipackage.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChefInventoryFragment : Fragment() {
    var index = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chef_inventory, container, false)
        runGradientAnimation(view)

        view.findViewById<Button>(R.id.chefInventoryLeave).setOnClickListener { (activity as ChefActivity).replaceFragment(ChefMenuFragment(), "") }

        view.findViewById<Button>(R.id.chefInventoryFetch).setOnClickListener{
            RetrofitClient.instance.getAllIngredients().enqueue(object: Callback<ResponseIngredients> {
                override fun onFailure(call: Call<ResponseIngredients>, t: Throwable) {
                    view.findViewById<TextView>(R.id.chefInventoryPosition).apply{ text = "FETCH UNSUCCESSFUL" }
                }
                override fun onResponse(call: Call<ResponseIngredients>, response: Response<ResponseIngredients>) {
                    val output = response.body()?.ingts

                    if (output != null) {
                        view.findViewById<TextView>(R.id.chefInventoryName).text = output.get(index).food
                    }
                    if (output != null) {
                        view.findViewById<EditText>(R.id.chefInventoryQuantity).hint = output.get(index).foodnum.toString()
                    }


                    view.findViewById<Button>(R.id.chefInventoryLeave).setOnClickListener{ (activity as ChefActivity).replaceFragment(ChefMenuFragment(), "") }
                    view.findViewById<Button>(R.id.chefInventoryBack).setOnClickListener{ ingsBack(output) }
                    //view.findViewById<Button>(R.id.chefInventoryUpdate).setOnClickListener{ ingsUpdate(output) }
                    view.findViewById<Button>(R.id.chefInventoryNext).setOnClickListener{ ingsForward(output) }





                    view.findViewById<TextView>(R.id.chefInventoryPosition).apply{
                        if (output != null) {
                            text = (index+1).toString() + " OF " + output.size.toString()
                        }
                    }
                }
            })
        }

        return view
    }

    private fun runGradientAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.chef_inventory)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

    fun ingsBack(chefIngs:List<Ingredient>?) {
        if (index == 0) {
            if (chefIngs != null) {
                index = chefIngs.size - 1
            }
        }
        else index -= 1

        view?.findViewById<TextView>(R.id.chefInventoryPosition)?.apply{
            if (chefIngs != null) {
                text = (index+1).toString() + " OF " + chefIngs.size.toString()
            }
        }
        view?.findViewById<TextView>(R.id.chefInventoryName)?.apply { text =
            chefIngs?.get(index)?.food
        }
        view?.findViewById<EditText>(R.id.chefInventoryQuantity)?.apply { hint = chefIngs?.get(index)?.foodnum.toString()}
    }
    fun ingsForward(chefIngs:List<Ingredient>?) {
        if (chefIngs != null) {
            if (index == chefIngs.size - 1) index = 0
            else index += 1
        }

        view?.findViewById<TextView>(R.id.chefInventoryPosition)?.apply{
            if (chefIngs != null) {
                text = (index+1).toString() + " OF " + chefIngs.size.toString()
            }
        }
        view?.findViewById<TextView>(R.id.chefInventoryName)?.apply { text =
            chefIngs?.get(index)?.food
        }
        view?.findViewById<EditText>(R.id.chefInventoryQuantity)?.apply { hint = chefIngs?.get(index)?.foodnum.toString()}
    }
    /*fun ingsUpdate(chefIngs:List<Ingredient>?) {
        val ingId = chefIngs?.get(index)?.id
        val name = chefIngs?.get(index)?.food
        var quantity = 0
        view?.findViewById<EditText>(R.id.chefInventoryQuantity)?.hint = chefIngs?.get(index)?.foodnum?.toString()

        else {
            quantity =
                view?.findViewById<EditText>(R.id.chefInventoryQuantity)?.text.toString().toInt()
            chefIngs?.get(index)?.foodnum =
                view?.findViewById<EditText>(R.id.chefInventoryQuantity)?.text.toString().toInt()
        }
        if (ingId != null) {
            if (name != null) {
                RetrofitClient.instance.updateIngredient(ingId, name, quantity)
                    .enqueue(object : Callback<ResponseIngredient> {
                        override fun onFailure(call: Call<ResponseIngredient>, t: Throwable) {
                            Toast.makeText(
                                activity as ChefActivity,
                                "Customer Already Exist",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        override fun onResponse(
                            call: Call<ResponseIngredient>,
                            response: Response<ResponseIngredient>
                        ) {
                            //val output = response.body()?.ingredient
                            Toast.makeText(
                                activity as ChefActivity,
                                "Update Sucessful",
                                Toast.LENGTH_LONG
                            ).show()

                        }


                    })
            }
        }

    }*/
}
