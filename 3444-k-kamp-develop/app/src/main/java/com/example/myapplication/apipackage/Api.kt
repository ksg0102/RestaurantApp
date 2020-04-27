package com.example.myapplication.apipackage

import retrofit2.Call
import retrofit2.http.*

interface Api {
    @FormUrlEncoded
    @POST("createMenuItem")
    fun createMenuItem(
        @Field("id") id:Int,
        @Field("name") name:String,
        @Field("cost") cost:Float,
        @Field("descrip") descrip:String
    ):Call<ResponseBase>

    @FormUrlEncoded
    @POST("createOrder")
    fun createOrder(
        @Field("tableNum") tableNum:Int,
        @Field("entree") entree: String,
        @Field("side") side: String,
        @Field("drink") drink: String,
        @Field("note")note:String,
        @Field("orderTotal") orderTotal: Double,
        @Field("status")status:Int
    ):Call<ResponseOrder>

    @FormUrlEncoded
    @POST("createEmp")
    fun createEmp(
        @Field("id") id:Int,
        @Field("password") password:String,
        @Field("name") name: String,
        @Field("wage") wage: Int,
        @Field("role") role: String,
        @Field("hours") hours: Int,
        @Field("tips")tips:Double,
        @Field("compmeals")compmeals:Int
    ):Call<ResponseEmployee>

    @FormUrlEncoded
    @POST("empLogin")
    fun userLogin(
        @Field("id") id:Int,
        @Field("password") password: String
    ):Call<ResponseEmployee>

    @FormUrlEncoded
    @GET("getItem/{id}")
    fun getItem(
        @Path("id") id:Int
    ):Call<ResponseBase>

    @GET("getEmp/{id}")
    fun getEmp(
        @Path("id") id:Int
    ):Call<ResponseEmployee>

    @FormUrlEncoded
    @GET("getItems")
    fun getItems(
    ):Call<ResponseItems>


    @GET("getOrders")
    fun allorders(
    ):Call<ResponseOrders>

    @GET("getAllEmp")
    fun getAllEmp(
    ):Call<ResponseEmployees>

    @FormUrlEncoded
    @PUT("updateItem/{id}")
    fun updateItem(
        @Path("id") id:Int,
        @Field("name") name:String,
        @Field("cost") cost: Float,
        @Field("descrip") descrip: String
    ):Call<ResponseItem>

    @FormUrlEncoded
    @PUT("changeOrder/{id}")
    fun changeOrder(
        @Path("id") id:Int,
        @Field("tableNum") email:Int,
        @Field("entree") entree: String,
        @Field("side") side: String,
        @Field("drink") drink: String,
        @Field("orderTotal") orderTotal: Float
    ):Call<ResponseOrder>

    @FormUrlEncoded
    @PUT("updateEmp/{id}")
    fun updateEmp(
        @Path("id") id:Int,
        @Field("name") name: String,
        @Field("wage") wage: Int,
        @Field("role") role: String,
        @Field("hours") hours: Int,
        @Field("tips")tips:Double,
        @Field("compmeals")compmeals:Int
    ):Call<ResponseEmployee>

    @FormUrlEncoded
    @DELETE("deleteItem/{id}")
    fun deleteItem(
        @Path("id") id:Int
    ):Call<ResponseBase>

    @DELETE("deleteOrder/{id}")
    fun deleteOrder(
        @Path("id") id:Int
    ):Call<ResponseBase>

    @FormUrlEncoded
    @DELETE("clearOrderQueue")
    fun clearOrderQueue(
    ):Call<ResponseBase>

    @DELETE("deleteEmployee/{id}")
    fun deleteEmployee(
        @Path("id") id:Int
    ):Call<ResponseEmployee>

    @FormUrlEncoded
    @POST("createcustomer")
    fun createcustomer(
        @Field("phone") phone:String,
        @Field("name") name:String,
        @Field("password") password:String,
        @Field("birthday") birthday:String,
        @Field("visited") visited:Int,
        @Field("credits")credits:Int
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST("customerlogin")
    fun customerLogin(
        @Field("phone") phone:String,
        @Field("password") password: String
    ):Call<ResponseCustomer>

    @GET("allcustomers")
    fun allcustomers(
    ):Call<ResponseCustomers>

    @FormUrlEncoded
    @PUT("updatecustomer/{id}")
    fun updatecustomer(
        @Path("id") id:Int,
        @Field("phone") phone: String,
        @Field("name") name: String,
        @Field("password") password: String,
        @Field("birthday") birthday: String,
        @Field("visited") visited: Int,
        @Field("credits") credits: Int
    ):Call<ResponseCustomer>

    @FormUrlEncoded
    @PUT("updateCustomerPassword")
    fun updateCustomerPassword(
        @Field("currentpassword") currentpassword:String,
        @Field("newpassword") newpassword: String,
        @Field("phone") phone: String
    ):Call<ResponseBase>

    @FormUrlEncoded
    @DELETE("deleteuser/{id}")
    fun deleteuser(
        @Path("id") id:Int
    ):Call<ResponseBase>

    @FormUrlEncoded
    @POST("createingredient")
    fun createingredient(
        @Field("food") food:String,
        @Field("amount") amount:Int
    ):Call<ResponseBase>



    @GET("allingredients")
    fun getAllIngredients():Call<ResponseIngredients>

    @FormUrlEncoded
    @PUT("updateIngredient/{id}")
    fun updateIngredient(
        @Path("id") id:Int,
        @Field("food") food:String,
        @Field("foodnum") foodnum:Int
    ):Call<ResponseIngredient>

    @FormUrlEncoded
    @DELETE("trashfood/{id}")
    fun trashfood(
        @Path("id") id:Int
    )

    //TODO: Implement
    @GET("allSurveys")
    fun allSurveys():Call<ResponseSurveys>

    @FormUrlEncoded
    @POST("createsurvey")
    fun createSurvey(
        @Field("firstq") firstq: Int,
        @Field("secondq") secondq: Int,
        @Field("thirdq") thirdq: Int
    ):Call<ResponseSurvey>

    @FormUrlEncoded
    @POST("createTable")
    fun createTable(
        @Field("number") number:Int,
        @Field("status") tableStatus:String,
        @Field("needHelp") needHelp:Boolean,
        @Field("needRefill") needRefill:Boolean,
        @Field("orderTotal")orderTotal:Double
    ):Call<ResponseBase>

    @GET("allTables")
    fun allTables(
    ):Call<ResponseTables>


    @GET("getTable/{number}")
    fun getTable(
        @Path("number") number:Int
    ):Call<ResponseTable>

    @FormUrlEncoded
    @PUT("updateTable/{number}")
    fun updateTable(
        @Path("number") number:Int,
        @Field("status") tableStatus:String,
        @Field("needHelp") needHelp:Int,
        @Field("needRefill") needRefill:Int,
        @Field("orderTotal")orderTotal:Double
    ):Call<ResponseTable>

    @FormUrlEncoded
    @DELETE("deleteTable/{number}")
    fun deleteTable(
        @Path("id") id:Int
    ):Call<ResponseBase>

    @GET("service")
    fun serve(
    ):Call<ResponseNice>


    @PUT("orderDone/{id}")
    fun finish(
        @Path("id")id:Int
    ):Call<ResponseOrder>

    @PUT("toHistory/{id}")
    fun toHistory(
        @Path("id")id:Int
    ):Call<ResponseOrder>

    @GET("getOrdersManager")
    fun allOrdersManager(
    ):Call<ResponseOrders>

    @GET("checktableshelp")
    fun checkHelp(
    ):Call<ResponseNice>

    @GET("checktablesrefill")
    fun checkRefill(
    ):Call<ResponseNice>

    @PUT("toClear/{id}")
    fun clearHelp(
        @Path("id")id:Int
    ):Call<ResponseTable>
}
