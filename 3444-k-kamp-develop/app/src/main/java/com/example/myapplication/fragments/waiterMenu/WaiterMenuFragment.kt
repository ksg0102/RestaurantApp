package com.example.myapplication.fragments.waiterMenu

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.WaiterActivity


class WaiterMenuFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_waiter_menu, container, false)
        runGraidentAnimation(view)

        /* Create button on XML for all the options and name them appropriately */
        /* Initialize buttons : val buttonOption = view.findViewByID<Button>(R.id.NAME_OF_YOUR_BUTTON) */
        val tableAlertButton = view.findViewById<Button>(R.id.button_table_alert)
        val kitchenAlertButton = view.findViewById<Button>(R.id.button_kitchen_alert)
        val viewScheduleButton = view.findViewById<Button>(R.id.button_view_schedule)

        /* Set onClickListener() -- buttonOption.setOnClickListener {} */
        /* In onClickListner , go to new fragment (ex. (activity as WaiterActivity).replaceFragment(WaiterTableAlertFragment(), "") */
        tableAlertButton.setOnClickListener { (activity as WaiterActivity).replaceFragment(WaiterTableAlertFragment(), "") }
        kitchenAlertButton.setOnClickListener { (activity as WaiterActivity).replaceFragment(WaiterKitchenAlertFragment(), "") }
        viewScheduleButton.setOnClickListener { (activity as WaiterActivity).replaceFragment(WaiterViewScheduleFragment(), "") }

        return view
    }


    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.waiter_menu)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }


}
