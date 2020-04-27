package com.example.myapplication.fragments.managerMenu

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.myapplication.R
import com.example.myapplication.activities.CustomerAccountActivity
import com.example.myapplication.activities.WaiterActivity
import com.example.myapplication.activities.ManagerActivity
import com.example.myapplication.apipackage.ResponseTables
import com.example.myapplication.apipackage.RetrofitClient
import com.example.myapplication.apipackage.Table
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt


class ManagerTableStatusFragment : Fragment() {
    lateinit var tableList: List<Table>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_manager_table_status, container, false)
        runGraidentAnimation(view)

        /* Initialize layout variable that will be dynamically programmed on */
        val layout = view.findViewById<LinearLayout>(R.id.manager_notifications)

        /* First get all the tables */

        /* RetrofitClient.instance.allTables().enqueue(object: Callback<ResponseTables> {
            override fun onFailure(call: Call<ResponseTables>, t: Throwable) {
                Toast.makeText(
                    activity as ManagerActivity,
                    t.message,
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(call: Call<ResponseTables>, response: Response<ResponseTables>) {

                /* Get all current status of all tables */
                tableList = response.body()!!.tables

                for (i in 0..tableList.size) {
                    val table = tableList[i]
                        val textView = TextView((activity as ManagerActivity))
                        val tableNum = table.number
                        val tableStatus = table.tableStatus

                        /* Display table status  */
                        textView.text = createNotificationDisplay(tableNum, tableStatus)
                        setTextViewAttributes(textView)
                        layout.addView(textView)
                        addLineSeperator(layout)
                }
            }
        })

         */


        /* Create Resolve button */

        val modifyButton = Button(activity as ManagerActivity)
        modifyButton.text = getString(R.string.modify_table_status)
        modifyButton.setBackgroundColor(Color.BLACK)
        modifyButton.setTextColor(Color.WHITE)
        layout.addView(modifyButton)

        modifyButton.setOnClickListener {
            (activity as ManagerActivity).replaceFragment(ManagerModifyTableStatus(), "")
        }

        return view
    }

    private fun runGraidentAnimation(v: View) {
        val constraintLayout = v.findViewById<ScrollView>(R.id.manager_view_alerts)
        val animationDrawable = constraintLayout?.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

    fun createNotificationDisplay(tableNum: Int, tableStatus: String): String {
        return "Table number $tableNum has send a request \n$tableStatus. \n"
    }

    /** HELPER METHOD: Method is used to display dynamic text properly */
    private fun setTextViewAttributes(textView: TextView) {
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        params.setMargins(
            convertDpToPixel(16),
            convertDpToPixel(16),
            0, 0
        )

        textView.setTextColor(Color.BLACK)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        textView.layoutParams = params
    }


    /** Helper method for setTextAttributes() */
    private fun convertDpToPixel(dp: Int): Int {
        val metrics = Resources.getSystem().displayMetrics
        val px = dp * (metrics.densityDpi / 160f)
        return px.roundToInt()
    }

    /** HELPER METHOD: Displays line */
    private fun addLineSeperator(layout:LinearLayout) {
        val lineLayout = LinearLayout(activity as WaiterActivity)
        lineLayout.setBackgroundColor(Color.BLACK)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            2
        )
        params.setMargins(0, convertDpToPixel(10), 0, convertDpToPixel(10))
        lineLayout.layoutParams = params
        layout.addView(lineLayout)
    }


}
