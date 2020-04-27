package com.example.myapplication.fragments.menuPaymentFragments

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.MenuActivity
import com.example.myapplication.apipackage.ResponseSurvey
import com.example.myapplication.apipackage.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MenuSurveyFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_survey, container, false)
        runGraidentAnimation(view)

        /* Initialize responses */


        /* TODO: SEND THESE RESPONSES TO THE DATABASE */

        /* Go the final fragment */
        val nextButton = view.findViewById<Button>(R.id.survey_next_button)
        nextButton.setOnClickListener {
            val responseOne = view.findViewById<EditText>(R.id.question_1).text.toString().toInt()
            val responseTwo = view.findViewById<EditText>(R.id.question_3).text.toString().toInt()
            val responseThree = view.findViewById<EditText>(R.id.question_2).text.toString().toInt()
            RetrofitClient.instance.createSurvey(
                responseOne,
                responseTwo,
                responseThree
            )
                .enqueue(object : Callback<ResponseSurvey> {
                    override fun onFailure(call: Call<ResponseSurvey>, t: Throwable) {
                        Toast.makeText(
                            activity as MenuActivity,
                            t.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    override fun onResponse(
                        call: Call<ResponseSurvey>,
                        response: Response<ResponseSurvey>
                    ) {
                        Toast.makeText(
                            activity as MenuActivity,
                            response.body()?.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })

            Toast.makeText(
                (activity as MenuActivity).applicationContext,
                "Survey has been sucessfully submitted!",
                Toast.LENGTH_SHORT).
                show()
            (activity as MenuActivity).replaceFragment(MenuExitFragment(), "") }

        /* Initialize help and refill button */
        val helpButton = view.findViewById<ImageButton>(R.id.button_help_image_survey)
        val refillButton = view.findViewById<ImageButton>(R.id.button_refill_image_survey)

        /* Listeners to address Help and Refill requests */
        helpButton.setOnClickListener{
            Toast.makeText((activity as MenuActivity).applicationContext, "A waiter will help you shortly", Toast.LENGTH_LONG).show()
        }

        refillButton.setOnClickListener{
            Toast.makeText((activity as MenuActivity).applicationContext, "A waiter refill your drink shortly", Toast.LENGTH_LONG).show()
        }

        return view
    }
    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.menu_survey)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }


}
