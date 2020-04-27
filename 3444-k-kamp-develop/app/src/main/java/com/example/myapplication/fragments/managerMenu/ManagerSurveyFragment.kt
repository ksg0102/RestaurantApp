package com.example.myapplication.fragments.managerMenu

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.ManagerActivity
import com.example.myapplication.apipackage.ResponseSurveys
import com.example.myapplication.apipackage.RetrofitClient
import com.example.myapplication.apipackage.Survey
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback

class ManagerSurveyFragment : Fragment() {
    var index = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_manager_survey, container, false)
        runGraidentAnimation(view)

        /* Initialize buttons */
        val getSurveyButton = view.findViewById<Button>(R.id.button_get_surveys)
        val nextButton = view.findViewById<Button>(R.id.button_next_survey)
        val previousButton = view.findViewById<Button>(R.id.button_previous_survey)

        getSurveyButton.setOnClickListener {
            // TODO: Database call to return list of surveys
            // TODO display first index responses


            RetrofitClient.instance.allSurveys()
                .enqueue(object : Callback<ResponseSurveys> {
                    override fun onFailure(call: Call<ResponseSurveys>, t: Throwable) {
                        Toast.makeText(activity as ManagerActivity,"Failure", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<ResponseSurveys>,
                        response: Response<ResponseSurveys>
                    ) {
                        val output = response.body()?.surveys

                        if (output != null) {
                            view.findViewById<TextView>(R.id.question_1_response).text = output.get(index).firstq.toString()
                            view.findViewById<TextView>(R.id.question_2_response).text = output.get(index).secondq.toString()
                            view.findViewById<TextView>(R.id.question_3_response).text = output.get(index).thirdq.toString()
                        }
                        else {
                            view.findViewById<TextView>(R.id.question_1_response).text = "NO DATA"
                        }

                        nextButton.setOnClickListener { surveyNext(output) }
                        previousButton.setOnClickListener { surveyPrevious(output) }
                    }

                })


        }

        return view
    }

    fun surveyNext(surveys: List<Survey>?) {
        if (surveys != null) {
            if (index == surveys.size - 1) index = 0
            else index += 1
            view?.findViewById<TextView>(R.id.question_1_response)?.text = surveys[index].firstq.toString()
            view?.findViewById<TextView>(R.id.question_2_response)?.text = surveys[index].secondq.toString()
            view?.findViewById<TextView>(R.id.question_3_response)?.text = surveys[index].thirdq.toString()
        }
    }

    fun surveyPrevious(surveys: List<Survey>?) {
        if (surveys != null) {
            if (index == 0) index = surveys.size - 1
            else index -= 1
            view?.findViewById<TextView>(R.id.question_1_response)?.text = surveys[index].firstq.toString()
            view?.findViewById<TextView>(R.id.question_2_response)?.text = surveys[index].secondq.toString()
            view?.findViewById<TextView>(R.id.question_3_response)?.text = surveys[index].thirdq.toString()
        }
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ConstraintLayout>(R.id.manager_survey)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

}
