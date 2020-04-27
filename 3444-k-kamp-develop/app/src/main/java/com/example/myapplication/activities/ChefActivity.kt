package com.example.myapplication.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

import com.example.myapplication.*
import com.example.myapplication.fragments.chefMenu.ChefMenuFragment


class ChefActivity : AppCompatActivity() {

    private val activity = this@ChefActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chef)
        supportActionBar!!.hide()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.chef_fragment_container,
            ChefMenuFragment()
        )
        transaction.commit()
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.chef_fragment_container, fragment, tag).addToBackStack("").commit()
    }

}