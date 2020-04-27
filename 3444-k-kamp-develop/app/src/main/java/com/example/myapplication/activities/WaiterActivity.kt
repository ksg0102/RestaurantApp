package com.example.myapplication.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

import com.example.myapplication.*
import com.example.myapplication.fragments.waiterMenu.WaiterMenuFragment


class WaiterActivity : AppCompatActivity() {

    private val activity = this@WaiterActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiter)
        supportActionBar!!.hide()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.waiter_fragment_container,
            WaiterMenuFragment()
        )
        transaction.commit()
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.waiter_fragment_container, fragment, tag).addToBackStack("").commit()
    }

}