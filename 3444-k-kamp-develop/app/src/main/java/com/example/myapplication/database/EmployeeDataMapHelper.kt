package com.example.myapplication.database

import android.content.Context
import com.example.myapplication.data_structs.Employee

/* Class establishes a hash table as a temporary database to store employee information */

class EmployeeDataMapHelper(context:Context) {

    var employeeMap: HashMap<String, Employee> = HashMap<String, Employee>()

    fun addEmployee(employee: Employee){
        val id = employee.id
        addEmployee(id, employee)
    }

    fun addEmployee(id: String, employee: Employee){ employeeMap[id] = employee }

    fun isValidEmployee(id:String) : Boolean { return employeeMap.containsKey(id) }

    fun removeEmployee(id: String){ employeeMap.remove(id) }

    fun verfiyEmployeePassword(id:String, inputPassword: String) : Boolean {
        val employee = employeeMap[id]
        val password = employee?.password

        if (password.equals(inputPassword)){
            return true
        }
        return false
    }

    fun mapIsEmpty(): Boolean{
        return employeeMap.isEmpty()
    }

    /* Getter Methods */

    fun getEmployeeName(id: String) : String? {
        val employee = employeeMap[id]
        return employee?.name
    }

    fun getEmployeePhone(id: String) : String? {
        val employee = employeeMap[id]
        return employee?.phoneNum
    }

    fun getEmployeePassword(id: String) : String? {
        val employee = employeeMap[id]
        return employee?.password
    }

    fun getEmployeePosisition(id: String) : String? {
        val employee = employeeMap[id]
        return employee?.position
    }

    fun getEmployeeWage(id: String): Double? {
        val employee = employeeMap[id]
        return employee?.wage
    }

    fun getEmployeeHours(id: String): Double? {
        val employee = employeeMap[id]
        return employee?.hours
    }

}

