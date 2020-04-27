package com.example.myapplication.fragments.managerMenu

import android.graphics.drawable.AnimationDrawable
import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.ManagerActivity


class ManagerEditMenuFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_employee_edit_menu, container, false)
        runGraidentAnimation(view)
        /* Initialize buttons */
        val addButton = view.findViewById<ImageButton>(R.id.imageButton_add_item_manager)
        val changeButton = view.findViewById<ImageButton>(R.id.imageButton_change_menu_mananger)
        val removeButton = view.findViewById<ImageButton>(R.id.imageButton_remove_item_manager)

        addButton.setOnClickListener {
            Toast.makeText((activity as ManagerActivity).applicationContext,
            "Unable to do this at this time", Toast.LENGTH_SHORT).show()
            (activity as ManagerActivity).replaceFragment(ManagerMenuFragment(), "")
        }

        changeButton.setOnClickListener {
            Toast.makeText((activity as ManagerActivity).applicationContext,
                "Unable to do this at this time", Toast.LENGTH_SHORT).show()
            (activity as ManagerActivity).replaceFragment(ManagerMenuFragment(), "")
        }

        removeButton.setOnClickListener {
            Toast.makeText((activity as ManagerActivity).applicationContext,
                "Unable to do this at this time", Toast.LENGTH_SHORT).show()
            (activity as ManagerActivity).replaceFragment(ManagerMenuFragment(), "")
        }


        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.manager_edit_menu)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

}
