
package com.example.myapplication.fragments.menuPaymentFragments

import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.CustomerAccountActivity
import com.example.myapplication.activities.MainActivity
import com.example.myapplication.activities.MenuActivity

class MenuExitFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_exit, container, false)
        runGraidentAnimation(view)

        /* Display TextView saying thank you for coming or we hope to see you again -- put an icon and make it look nice */

        /* Create and initialize a Reset button and set onClickListener */
        val resetButt = view.findViewById<Button>(R.id.button_reset)
        resetButt.setOnClickListener{
            // (activity as CustomerAccountActivity).resetCustomer()
            (activity as MenuActivity).resetDrink()
            (activity as MenuActivity).resetEntree()
            (activity as MenuActivity).resetSide()
            (activity as MenuActivity).resetOrder()

            val intent = Intent(activity, MainActivity::class.java)
            activity?.startActivity(intent)
        }
        /* [in listener] call resetCustomer() (from CustomerActivity) and reset EVERYTHING else (Orders, Entrees, Orders, Drinks, Sides)
        anything you can think of

        Go back and start the Main Activity:
         val intent = Intent (activity, MainActivity::class.java)
            activity?.startActivity(intent)
         */


        /* Intialialize and set up help and refill button actions */
        val helpButtonMain = view.findViewById<ImageButton>(R.id.button_help_image_payment_exit)
        helpButtonMain.setOnClickListener{
            Toast.makeText((activity as MenuActivity).applicationContext, "A waiter will help you shortly", Toast.LENGTH_LONG).show()
        }
        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.menu_payment_exit)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }


}
