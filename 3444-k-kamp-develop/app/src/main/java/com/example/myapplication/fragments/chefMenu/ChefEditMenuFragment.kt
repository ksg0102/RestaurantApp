package com.example.myapplication.fragments.chefMenu

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.ChefActivity


class ChefEditMenuFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chef_edit_menu, container, false)
        runGraidentAnimation(view)

        val addButton = view.findViewById<ImageButton>(R.id.imageButton_add_item_chef)
        val changeButton = view.findViewById<ImageButton>(R.id.imageButton_change_menu_chef)
        val removeButton = view.findViewById<ImageButton>(R.id.imageButton_remove_item_chef)

        addButton.setOnClickListener {
            Toast.makeText((activity as ChefActivity).applicationContext,
                "Unable to do this at this time", Toast.LENGTH_SHORT).show()
            (activity as ChefActivity).replaceFragment(ChefMenuFragment(), "")
        }

        changeButton.setOnClickListener {
            Toast.makeText((activity as ChefActivity).applicationContext,
                "Unable to do this at this time", Toast.LENGTH_SHORT).show()
            (activity as ChefActivity).replaceFragment(ChefMenuFragment(), "")
        }

        removeButton.setOnClickListener {
            Toast.makeText((activity as ChefActivity).applicationContext,
                "Unable to do this at this time", Toast.LENGTH_SHORT).show()
            (activity as ChefActivity).replaceFragment(ChefMenuFragment(), "")
        }

        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.chef_edit_menu)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }
}
