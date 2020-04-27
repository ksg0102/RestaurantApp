package com.example.myapplication.fragments.menuKids

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.MenuActivity
import com.example.myapplication.apipackage.ResponseTable
import com.example.myapplication.apipackage.RetrofitClient
import com.example.myapplication.data_structs.Entree
import com.example.myapplication.fragments.MainMenuFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuKidsNoteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_kids_note, container, false)
        runGraidentAnimation(view)

        /* Initialize buttons */
        val addNoteButton = view.findViewById<Button>(R.id.button_add_note_kids)
        val noNoteButton = view.findViewById<Button>(R.id.button_no_note_kids)

        addNoteButton.setOnClickListener {
            val note = view.findViewById<EditText>(R.id.text_note_kids).text.toString()
            setKidsEntree(note)

            Toast.makeText((activity as MenuActivity).applicationContext,
                "Entree has been added to cart", Toast.LENGTH_SHORT).show()
            (activity as MenuActivity).replaceFragment(MainMenuFragment(), "")
        }

        noNoteButton.setOnClickListener{
            setKidsEntree("")
            Toast.makeText((activity as MenuActivity).applicationContext,
                "Entree has been added to cart", Toast.LENGTH_SHORT).show()
            (activity as MenuActivity).replaceFragment(MainMenuFragment(), "")

        }

        val helpButtonNote = view.findViewById<ImageButton>(R.id.button_help_image_note_kids)
        val refillButtonNote = view.findViewById<ImageButton>(R.id.button_refill_image_note_kids)

        /* Listeners to address Help and Refill requests */
        /* Send help notification to the waiter */
        helpButtonNote.setOnClickListener{
            //Toast.makeText((activity as MenuActivity).applicationContext, "A waiter will help you shortly", Toast.LENGTH_LONG).show()

            /* Save table status to the database  */
            /*
            RetrofitClient.instance.updateTable((activity as MenuActivity).table.number, "Needs Help",
                needHelp = true,
                needRefill = false,
                orderTotal = 0.0
            ).enqueue(object: Callback<ResponseTable> {
                override fun onFailure(call: Call<ResponseTable>, t: Throwable) {
                    Toast.makeText(
                        activity as MenuActivity,
                        t.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
                override fun onResponse(call: Call<ResponseTable>, response: Response<ResponseTable>) {
                    Toast.makeText(
                        activity as MenuActivity,
                        "A waiter will help you shortly",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

             */
        }


        /* Send refill notification to the waiter */
        refillButtonNote.setOnClickListener{
            //Toast.makeText((activity as MenuActivity).applicationContext, "A waiter refill your drink shortly", Toast.LENGTH_LONG).show()

            /*
            /* Save table status to database */
            RetrofitClient.instance.updateTable((activity as MenuActivity).table.number, "Needs Refill",
                needHelp = false,
                needRefill = true,
                orderTotal = 0.0
            ).enqueue(object: Callback<ResponseTable> {
                override fun onFailure(call: Call<ResponseTable>, t: Throwable) {
                    Toast.makeText(
                        activity as MenuActivity,
                        t.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
                override fun onResponse(call: Call<ResponseTable>, response: Response<ResponseTable>) {
                    Toast.makeText(
                        activity as MenuActivity,
                        "A waiter will refill your drink shortly",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

             */
        }

        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.menu_kids_note)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

    fun setKidsEntree(note: String) {
        val __meatType = (activity as MenuActivity).meatType
        val __flavorType = (activity as MenuActivity).flavorType
        val __sauceType = (activity as MenuActivity).sauceType
        val __sauceQuantity = (activity as MenuActivity).sauceQuantity
        val __numWings = (activity as MenuActivity).numWings
        val __note = note
        val __price = (activity as MenuActivity).KIDS_COMBO_PRICE

        val __entree = Entree(__meatType, __flavorType, __numWings, __sauceType, __sauceQuantity, __note, __price)
        (activity as MenuActivity).entree = __entree

        /* Add entree to entree list stored for the table */
        (activity as MenuActivity).addEntree()

        /* Reset entree variable for next one */
        (activity as MenuActivity).resetEntree()

    }
}
