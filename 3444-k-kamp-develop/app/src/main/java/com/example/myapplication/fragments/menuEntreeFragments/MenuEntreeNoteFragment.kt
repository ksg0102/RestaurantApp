package com.example.myapplication.fragments.menuEntreeFragments

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

import com.example.myapplication.R
import com.example.myapplication.activities.MenuActivity


/** This fragment class is Step 6 in the ordering process in which the customer can decide to
 * add an additional note
 */
class MenuEntreeNoteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_entree_note, container, false)
        runGraidentAnimation(view)

        /* Initialize buttons */
        val addNoteButton = view.findViewById<Button>(R.id.button_add_note)
        val noNoteButton = view.findViewById<Button>(R.id.button_no_note)

        val helpButtonNote = view.findViewById<ImageButton>(R.id.button_help_image_note)
        val refillButtonNote = view.findViewById<ImageButton>(R.id.button_refill_image_note)

        /* Set button actions */
        addNoteButton.setOnClickListener {
            (activity as MenuActivity).note = view.findViewById<EditText>(R.id.text_note).text.toString()
            (activity as MenuActivity).replaceFragment(MenuEntreeExtrasFragment(), "")

        }

        noNoteButton.setOnClickListener {
            (activity as MenuActivity).note = ""
            (activity as MenuActivity).replaceFragment(MenuEntreeExtrasFragment(), "")
        }

        /* Listeners to address Help and Refill requests */
        helpButtonNote.setOnClickListener{
            Toast.makeText((activity as MenuActivity).applicationContext,
                "A waiter will help you shortly", Toast.LENGTH_LONG).show()
        }

        refillButtonNote.setOnClickListener{
            Toast.makeText((activity as MenuActivity).applicationContext,
                "A waiter refill your drink shortly", Toast.LENGTH_LONG).show()
        }
        return view
    }


    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.menu_entree_note)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }


}
