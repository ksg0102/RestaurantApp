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
import com.example.myapplication.activities.CustomerAccountActivity
import com.example.myapplication.activities.ManagerActivity
import com.example.myapplication.apipackage.*
import com.example.myapplication.fragments.chefMenu.ChefMenuFragment
import kotlinx.android.synthetic.main.fragment_chef_inventory.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ManagerInventoryFragment : Fragment() {
    var index = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_employee_inventory, container, false)
        runGradientAnimation(view)

        view.findViewById<Button>(R.id.managerInventoryFetch).setOnClickListener{
            RetrofitClient.instance.getAllIngredients().enqueue(object: Callback<ResponseIngredients> {

                override fun onFailure(call: Call<ResponseIngredients>, t: Throwable) {
                    view.findViewById<TextView>(R.id.managerInventoryPosition).apply{ text = "FETCH UNSUCCESSFUL" }
                }
                override fun onResponse(call: Call<ResponseIngredients>, response: Response<ResponseIngredients>) {

                    val output = response.body()?.ingts

                    if (output != null) {
                        view.findViewById<TextView>(R.id.managerInventoryName).text = output.get(index).food
                    }

                    if (output != null) {
                        view.findViewById<EditText>(R.id.managerInventoryQuantity).hint = output.get(index).foodnum.toString()
                    }


                    view.findViewById<Button>(R.id.managerInventoryLeave).setOnClickListener{ (activity as ManagerActivity).replaceFragment(
                        ManagerMenuFragment(), "") }
                    view.findViewById<Button>(R.id.managerInventoryBack).setOnClickListener{ ingsBack(output) }
                    //view.findViewById<Button>(R.id.managerInventoryUpdate).setOnClickListener{ ingsUpdate(output) }
                    view.findViewById<Button>(R.id.managerInventoryNext).setOnClickListener{ ingsForward(output) }



                }
            })
        }


        val updateButton = view.findViewById<Button>(R.id.managerInventoryUpdate)
        updateButton.setOnClickListener{


            val ingId = view.findViewById<EditText>(R.id.managerInventoryId).text.toString().toInt()
            val name = view.findViewById<EditText>(R.id.managerInventoryName).text.toString()
            val quantity = view.findViewById<EditText>(R.id.managerInventoryQuantity).text.toString().toInt()


            RetrofitClient.instance.updateIngredient(ingId, name, quantity)
                .enqueue(object : Callback<ResponseIngredient> {
                    override fun onFailure(call: Call<ResponseIngredient>, t: Throwable) {
                        Toast.makeText(
                            activity as ManagerActivity,
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
                            activity as ManagerActivity,
                            "Update Sucessful",
                            Toast.LENGTH_LONG
                        ).show()

                    }


                })
        }

        view.findViewById<Button>(R.id.managerInventoryLeave).setOnClickListener { (activity as ManagerActivity).replaceFragment(ManagerMenuFragment(), "")}

        return view
    }

    private fun runGradientAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.managerInventory)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

    fun ingsBack(chefIngs:List<Ingredient>?) {
        if (chefIngs != null) {
            if (index == 0) {
                    index = chefIngs.size - 1
            } else index -= 1

            view?.findViewById<TextView>(R.id.managerInventoryPosition)?.apply {
                text = (index + 1).toString() + " OF " + chefIngs.size.toString()
            }
            view?.findViewById<TextView>(R.id.managerInventoryName)?.apply {
                text = chefIngs.get(index).food
            }
            view?.findViewById<EditText>(R.id.managerInventoryQuantity)
                ?.apply { hint = chefIngs.get(index).foodnum.toString() }
        }
    }

    fun ingsForward(chefIngs:List<Ingredient>?) {
        if (chefIngs != null) {
            if (index == chefIngs.size - 1) index = 0
            else index += 1


            view?.findViewById<TextView>(R.id.managerInventoryPosition)?.apply {
                text = (index + 1).toString() + " OF " + chefIngs.size.toString()
            }
            view?.findViewById<TextView>(R.id.managerInventoryName)?.apply {
                text = chefIngs.get(index).food
            }
            view?.findViewById<EditText>(R.id.managerInventoryQuantity)
                ?.apply { hint = chefIngs.get(index).foodnum.toString() }
        }
    }


/*TODO:    fun ingsAdd() {
        var name = ""
        if (view?.findViewById<EditText>(R.id.managerInventoryName)?.text.isNullOrEmpty()) {name = manIngs[index].food}
        else {
            name = view?.findViewById<EditText>(R.id.managerInventoryName)?.text.toString()
            manIngs[index].food = view?.findViewById<EditText>(R.id.managerInventoryName)?.text.toString()
        }
        var quantity = 0
        if (view?.findViewById<EditText>(R.id.managerInventoryQuantity)?.text.isNullOrEmpty()) {quantity = manIngs[index].amount}
        else {
            quantity = view?.findViewById<EditText>(R.id.managerInventoryQuantity)?.text.toString().toInt()
            manIngs[index].amount = view?.findViewById<EditText>(R.id.managerInventoryQuantity)?.text.toString().toInt()
        }

        RetrofitClient.instance.createingredient(name, quantity)

        RetrofitClient.instance.allingredients().enqueue(object: Callback<ResponseIngredients> {
            override fun onFailure(call: Call<ResponseIngredients>, t: Throwable) {

            }
            override fun onResponse(call: Call<ResponseIngredients>, response: Response<ResponseIngredients>) {
                manIngs = response.body()!!.ingredients
            }
        })

        index = 0
        view?.findViewById<TextView>(R.id.managerInventoryName)?.apply { text = manIngs[index].food}
        view?.findViewById<EditText>(R.id.managerInventoryQuantity)?.apply { hint = manIngs[index].amount.toString() }
    }*/
    /*fun ingsDelete() {
        RetrofitClient.instance.trashfood(manIngs[index].id)

        RetrofitClient.instance.allingredients().enqueue(object: Callback<ResponseIngredients> {
            override fun onFailure(call: Call<ResponseIngredients>, t: Throwable) {

            }
            override fun onResponse(call: Call<ResponseIngredients>, response: Response<ResponseIngredients>) {
                manIngs = response.body()!!.ingredients
            }
        })

        index = 0
        view?.findViewById<TextView>(R.id.managerInventoryName)?.apply { text = manIngs[index].food}
        view?.findViewById<EditText>(R.id.managerInventoryQuantity)?.apply { hint = manIngs[index].amount.toString() }
    }*/
}
