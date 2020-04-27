package com.example.myapplication.fragments.menuEntreeFragments

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapplication.R
import com.example.myapplication.activities.MenuActivity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.activities.ChefActivity
import com.example.myapplication.apipackage.ResponseOrders
import com.example.myapplication.apipackage.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuEntreeMeatFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_entree, container, false)
        runGraidentAnimation(view)

        //TODO: Verify that this works.
        var meatCounts = mutableListOf(0,0,0)
       RetrofitClient.instance.allorders()
            .enqueue(object : Callback<ResponseOrders> {
                override fun onFailure(call: Call<ResponseOrders>, t: Throwable) {
                    Toast.makeText(activity as MenuActivity,"Error retrieving order popularity", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<ResponseOrders>,
                    response: Response<ResponseOrders>
                ) {
                    val output = response.body()?.orders

                    if (output != null) {
                        for (i in 0..(output.size-1)) {
                            for (j in 0..(output.get(i).entree.size-1)) {
                                if (output.get(i).entree[j].meatType == "boneless") meatCounts[0] += 1
                                else if (output.get(i).entree[j].meatType == "bone") meatCounts[1] += 1
                                else meatCounts[2] += 1
                            }
                        }

                    }
                    if(meatCounts[2] >= meatCounts[1] && meatCounts[2] >= meatCounts[0]) {
                        view.findViewById<TextView>(R.id.text_Tender_Popular).apply { visibility = View.VISIBLE }
                    }
                    else if(meatCounts[1] >= meatCounts[2] && meatCounts[1] >= meatCounts[0]) {
                        view.findViewById<TextView>(R.id.text_Bone_Popular).apply { visibility = View.VISIBLE }
                    }
                    else {
                        view.findViewById<TextView>(R.id.text_Boneless_Popular).apply { visibility = View.VISIBLE }
                    }
                }

            })

        /* Initialize meat type buttons */
        val boneless_button = view.findViewById<Button>(R.id.button_boneless)
        val bone_button = view.findViewById<Button>(R.id.button_bone)
        val tenders_button = view.findViewById<Button>(R.id.button_tenders)

        val helpButtonMeat = view.findViewById<ImageButton>(R.id.button_help_image_meat)
        val refillButtonMeat = view.findViewById<ImageButton>(R.id.button_refill_image_meat)


        /* Save selected meat type to order and proceed to step 2 */
        boneless_button.setOnClickListener{
            (activity as MenuActivity).meatType = "Boneless"
            (activity as MenuActivity).entreeId+=2
            (activity as MenuActivity).replaceFragment(MenuEntreeFlavorFragment(), "")


        }

        bone_button.setOnClickListener{
            (activity as MenuActivity).meatType = "Bone"
            (activity as MenuActivity).entreeId+=1
            (activity as MenuActivity).replaceFragment(MenuEntreeFlavorFragment(), "")

        }

        tenders_button.setOnClickListener{
            (activity as MenuActivity).meatType = "Tenders"
            (activity as MenuActivity).entreeId+=3
            (activity as MenuActivity).replaceFragment(MenuEntreeFlavorFragment(), "")

        }


        /* Listeners to address Help and Refill requests */
        helpButtonMeat.setOnClickListener{
            Toast.makeText((activity as MenuActivity).applicationContext, "A waiter will help you shortly", Toast.LENGTH_LONG).show()
        }

        refillButtonMeat.setOnClickListener{
            Toast.makeText((activity as MenuActivity).applicationContext, "A waiter refill your drink shortly", Toast.LENGTH_LONG).show()
        }


        return view
    }


    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.me_1)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }
}