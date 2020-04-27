package com.example.myapplication.fragments.managerMenu

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.ManagerActivity

class ManagerMenuFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_manager_menu_container, container, false)
        runGraidentAnimation(view)

        /* Initialize the buttons */
        val applyDiscountButton = view.findViewById<Button>(R.id.button_apply_discount)
        val tableStatusButton = view.findViewById<Button>(R.id.button_table_status)
        val orderHistoryButton = view.findViewById<Button>(R.id.button_order_history)
        val editMenuButton = view.findViewById<Button>(R.id.button_adjust_menu)
        val viewEmployeeButton = view.findViewById<Button>(R.id.button_view_employee)
        val inventoryButton = view.findViewById<Button>(R.id.button_inventory)
        val surveyButton = view.findViewById<Button>(R.id.button_survey)


        /* Navigate user to correct screen */
        applyDiscountButton.setOnClickListener { (activity as ManagerActivity).replaceFragment(ManagerApplyDiscountFragment(), "") }
        tableStatusButton.setOnClickListener { (activity as ManagerActivity).replaceFragment(ManagerTableStatusFragment(), "") }
        orderHistoryButton.setOnClickListener { (activity as ManagerActivity).replaceFragment(ManagerOrderHistoryFragment(), "") }
        editMenuButton.setOnClickListener { (activity as ManagerActivity).replaceFragment(ManagerEditMenuFragment(), "") }
        viewEmployeeButton.setOnClickListener { (activity as ManagerActivity).replaceFragment(ManagerViewEmployeeFragment(), "") }
        inventoryButton.setOnClickListener { (activity as ManagerActivity).replaceFragment(ManagerInventoryFragment(), "") }
        surveyButton.setOnClickListener { (activity as ManagerActivity).replaceFragment(ManagerSurveyFragment(),"") }


        return view
    }


    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.mananger_menu)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }


}
