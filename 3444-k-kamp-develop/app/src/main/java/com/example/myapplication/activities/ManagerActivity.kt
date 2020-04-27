package com.example.myapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

import com.example.myapplication.*
import com.example.myapplication.fragments.managerMenu.ManagerMenuFragment


class ManagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager)
        supportActionBar!!.hide()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.manager_fragment_container,
            ManagerMenuFragment()
        )
        transaction.commit()
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.manager_fragment_container, fragment, tag).addToBackStack("").commit()
    }

}


