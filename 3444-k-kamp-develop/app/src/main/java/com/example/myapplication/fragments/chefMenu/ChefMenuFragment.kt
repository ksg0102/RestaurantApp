package com.example.myapplication.fragments.chefMenu

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.ChefActivity


class ChefMenuFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chef_menu, container, false)
        runGraidentAnimation(view)

        /* Create button on XML for all the options and name them appropriately */
        /* Initialize buttons : val buttonOption = view.findViewByID<Button>(R.id.NAME_OF_YOUR_BUTTON) */
        val viewOrdersButton = view.findViewById<Button>(R.id.button_view_orders)
        val inventoryButton = view.findViewById<Button>(R.id.button_inventory)
        val editMenuButton = view.findViewById<Button>(R.id.button_edit_menu)
        val viewScheduleButton = view.findViewById<Button>(R.id.button_view_schedule)

        /* Set onClickListener() -- buttonOption.setOnClickListener {} */
        /* In onClickListner , go to new fragment (ex. (activity as WaiterActivity).replaceFragment(WaiterTableAlertFragment(), "") */
        viewOrdersButton.setOnClickListener { (activity as ChefActivity).replaceFragment(ChefViewOrdersFragment(), "") }
        inventoryButton.setOnClickListener { (activity as ChefActivity).replaceFragment(ChefInventoryFragment(), "") }
        editMenuButton.setOnClickListener { (activity as ChefActivity).replaceFragment(ChefEditMenuFragment(), "")}
        viewScheduleButton.setOnClickListener { (activity as ChefActivity).replaceFragment(ChefViewScheduleFragment(), "") }

        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.chef_menu)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

}
