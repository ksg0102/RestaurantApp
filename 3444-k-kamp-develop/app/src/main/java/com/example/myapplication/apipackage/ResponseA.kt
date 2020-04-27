package com.example.myapplication.apipackage


import com.google.gson.annotations.SerializedName
import com.example.myapplication.data_structs.Entree
import com.example.myapplication.data_structs.Drink
import com.example.myapplication.data_structs.Side

data class Item(val id: Int, val name: String, val cost: Float, val descrip: String)
data class Customer(var id: Int, var phone: String, var name: String, var password: String, var birthday: String, var visited: Int, var credits: Int)
data class Employee(var id: Int, var password: String, var name: String, var wage: Int, var role: String, var hours: Int, var tips: Double, var compmeals: Int)
data class Order(var id: Int, var tableNum: Int, var entree: List<Entree>, var side: List<Side>, var drink: List<Drink>, var note: String, var orderTotal: Double, var status: Int )
data class Ingredient(var id: Int, var food: String, var foodnum: Int)
data class Table(var number: Int, var tableStatus: String, var needHelp: Int, var needRefill: Int, var orderTotal: Double)
data class Survey(var id: Int, var firstq: Int, var secondq: Int, var thirdq: Int)
data class ResponseBase(@SerializedName("error") val error: Boolean, val message: String)
data class ResponseItem(@SerializedName("error") val error: Boolean, val message: String, val item: Item)
data class ResponseItems(@SerializedName("error") val error: Boolean, val message: String, val items: List<Item>)
data class ResponseCustomer( val error: Boolean, val message: String, val customer: Customer)
data class ResponseCustomers(@SerializedName("error") val error: Boolean, val message: String, val customers: List<Customer>)
data class ResponseEmployee(val error: Boolean, val message: String, val employee: Employee)
data class ResponseEmployees(@SerializedName("error") val error: Boolean, val message: String, val employees: List<Employee>)
data class ResponseOrder(@SerializedName("error") val error: Boolean, val message: String, val order: Order)
data class ResponseOrders( val error: Boolean, val orders: List<Order>)
data class ResponseIngredients(val error: Boolean, val ingts: List<Ingredient>)
data class ResponseIngredient( val error: Boolean, val message: String, val ing: Ingredient)
data class ResponseTables(@SerializedName("error") val error: Boolean, val message: String, val tables: List<Table>)
data class ResponseTable(@SerializedName("error") val error: Boolean, val table: Table)
data class ResponseSurveys(val error: Boolean, val message: String, val surveys: List<Survey>)
data class ResponseSurvey(@SerializedName("error") val error: Boolean, val message: String, val survey: Survey)
data class ResponseNice(val error: Boolean, val tables: List<Int>)
data class RRRR(val error: Boolean, val message: String)

