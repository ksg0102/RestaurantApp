package com.example.myapplication.database


import android.content.Context
import com.example.myapplication.data_structs.Customer

/* Class establishes a hash table as a temporary database to store employee information */

class CustomerDataMapHelper(context:Context) {

    /* <Phone_number, Customer > */
    var customerMap: HashMap<String, Customer> = HashMap<String, Customer>()

    fun addCustomer(customer: Customer){
        val phoneNum = customer.phoneNumber
        addCustomer(phoneNum, customer)
    }

    fun addCustomer(id: String, customer: Customer){ customerMap[id] = customer }

    fun isValidCustomer(id:String) : Boolean { return customerMap.containsKey(id) }

    fun removeCustomer(id: String){ customerMap.remove(id) }

    fun verfiyCustomerPassword(id:String, inputPassword: String) : Boolean {
        val customer = customerMap[id]
        val password = customer?.password

        if (password.equals(inputPassword)){
            return true
        }
        return false
    }

    fun mapIsEmpty(): Boolean{
        return customerMap.isEmpty()
    }

    /* Getter Methods */
    fun getCustomerName(phoneNum: String): String{
        val customer = customerMap[phoneNum]
        return customer!!.name
    }

    fun getCustomerBirthday(phoneNum: String): String{
        val customer = customerMap[phoneNum]
        return customer!!.birthday
    }

    fun getCustomerPassword(phoneNum: String): String {
        val customer = customerMap[phoneNum]
        return customer!!.password
    }

    fun getCustomerTimes(phoneNum: String): Int {
        val customer = customerMap[phoneNum]
        return customer!!.numTimesVisited
    }

    fun getCustomerCredits(phoneNum: String): Int {
        val customer = customerMap[phoneNum]
        return customer!!.numCredits
    }

    fun getCustomer(id: String): Customer {
        val name = getCustomerName(id)
        val birthday = getCustomerBirthday(id)
        val password = getCustomerPassword(id)
        val times = getCustomerTimes(id)
        val credits = getCustomerCredits(id)

        return Customer(id, name, birthday, password, times, credits)
    }

}
