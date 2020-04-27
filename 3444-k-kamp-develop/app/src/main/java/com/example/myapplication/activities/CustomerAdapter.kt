package com.example.myapplication.activities


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.apipackage.Customer



 abstract class CustomerAdapter(
    private val mCtx: Context,
    userList: List<Customer>
) :
    RecyclerView.Adapter<CustomerAdapter.UsersViewHolder?>() {

    private val userList: List<Customer>

    @NonNull
    fun onCreateViewHolder(
        @NonNull parent: ViewGroup?,
        viewType: Int
    ): UsersViewHolder {
        val view: View =
            LayoutInflater.from(mCtx).inflate(R.layout.recyclerview, parent, false)
        return UsersViewHolder(view)
    }

    override fun onBindViewHolder(
        @NonNull holder: UsersViewHolder,
        position: Int
    ) {
        val user: Customer  = userList[position]
        holder.customer_phone.setText(user.name)
        holder.customer_name.setText(user.phone)
        holder.customer_birthday.setText(user.birthday)
        holder.customer_visited.setText(user.visited)
        holder.customer_credits.setText(user.credits)
    }

    /*val itemCount: Int
        get() = userList.size*/

    inner class UsersViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var customer_phone: TextView
        var customer_name: TextView
        var customer_birthday: TextView
        var customer_visited: TextView
        var customer_credits: TextView

        init {
            customer_phone = itemView.findViewById(R.id.customer_phone)
            customer_name = itemView.findViewById(R.id.customer_name)
            customer_birthday = itemView.findViewById(R.id.customer_birthday)
            customer_visited = itemView.findViewById(R.id.customer_visited)
            customer_credits = itemView.findViewById(R.id.customer_credits)
        }
    }

    init {
        this.userList = userList
    }
}
