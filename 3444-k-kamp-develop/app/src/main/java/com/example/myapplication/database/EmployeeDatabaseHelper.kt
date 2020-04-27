package com.example.myapplication.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.data_structs.Employee
import java.util.*


class EmployeeDatabaseHelper(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {


    // create table sql query
    private val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_EMPLOYEE + "("
            + COLUMN_EMPLOYEE_ID + " TEXT, " + COLUMN_EMPLOYEE_NAME + " TEXT,"
            + COLUMN_EMPLOYEE_PHONE + " TEXT," + COLUMN_EMPLOYEE_PASSWORD + " TEXT, " + COLUMN_EMPLOYEE_POSISTION + "TEXT," +
            COLUMN_EMPLOYEE_WAGE + "DOUBLE," + COLUMN_EMPLOYEE_HOURS + "DOUBLE" + ")" )

    // drop table sql query
    private val DROP_EMPLOYEE_TABLE = "DROP TABLE IF EXISTS $TABLE_EMPLOYEE"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        //Drop User Table if exist
        db.execSQL(DROP_EMPLOYEE_TABLE)

        // Create tables again
        onCreate(db)

    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    fun getAllEmployees(): Int {

        // array of columns to fetch
        val columns = arrayOf(
            COLUMN_EMPLOYEE_ID,
            COLUMN_EMPLOYEE_NAME,
            COLUMN_EMPLOYEE_PHONE,
            COLUMN_EMPLOYEE_PASSWORD,
            COLUMN_EMPLOYEE_POSISTION,
            COLUMN_EMPLOYEE_WAGE,
            COLUMN_EMPLOYEE_HOURS
        )

        // sorting orders
        val sortOrder = "$COLUMN_EMPLOYEE_NAME ASC"
        val employeeList = ArrayList<Employee>()

        val db = this.readableDatabase

        // query the user table
        val cursor = db.query(
            TABLE_EMPLOYEE, //Table to query
            columns,            //columns to return
            null,     //columns for the WHERE clause
            null,  //The values for the WHERE clause
            null,      //group the rows
            null,       //filter by row groups
            sortOrder)         //The sort order
        if (cursor.moveToFirst()) {
            do {
                val employee = Employee(
                    id = cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_ID)),
                    name = cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_NAME)),
                    phoneNum = cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_PHONE)),
                    password = cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_PASSWORD)),
                    position = cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_POSISTION)),
                    wage = cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_WAGE)).toDouble(),
                    hours = cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_HOURS)).toDouble()
                )

                employeeList.add(employee)
            } while (cursor.moveToNext())
        }

        val cursorCount = cursor.count
        cursor.close()
        db.close()


        return cursorCount
    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    fun addEmployee(employee: Employee): Boolean {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_EMPLOYEE_ID, employee.id)
        values.put(COLUMN_EMPLOYEE_NAME, employee.name)
        values.put(COLUMN_EMPLOYEE_PHONE, employee.phoneNum)
        values.put(COLUMN_EMPLOYEE_PASSWORD, employee.password)
        values.put(COLUMN_EMPLOYEE_POSISTION, employee.position)
        values.put(COLUMN_EMPLOYEE_WAGE, employee.wage)
        values.put(COLUMN_EMPLOYEE_HOURS, employee.hours)


        // Inserting Row
        val success = db.insert(TABLE_EMPLOYEE, null, values)
        db.close()
        return (Integer.parseInt("$success") != -1)
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    fun updateEmployee(employee: Employee) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_EMPLOYEE_ID, employee.id)
        values.put(COLUMN_EMPLOYEE_NAME, employee.name)
        values.put(COLUMN_EMPLOYEE_PHONE, employee.phoneNum)
        values.put(COLUMN_EMPLOYEE_PASSWORD, employee.password)
        values.put(COLUMN_EMPLOYEE_POSISTION, employee.position)
        values.put(COLUMN_EMPLOYEE_WAGE, employee.wage)
        values.put(COLUMN_EMPLOYEE_HOURS, employee.hours)

        // updating row
        db.update(
            TABLE_EMPLOYEE, values, "$COLUMN_EMPLOYEE_ID = ?",
            arrayOf(employee.id))
        db.close()
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    fun deleteUser(employee: Employee) {

        val db = this.writableDatabase
        // delete user record by id
        db.delete(
            TABLE_EMPLOYEE, "$COLUMN_EMPLOYEE_ID = ?",
            arrayOf(employee.id))
        db.close()


    }

    /**
     * This method to check user exist or not
     *
     * @param phoneNum
     * @return true/false
     */
    fun checkUser(phoneNum: String): Boolean {

        // array of columns to fetch
        val columns = arrayOf(COLUMN_EMPLOYEE_ID)
        val db = this.readableDatabase

        // selection criteria
        val selection = "$COLUMN_EMPLOYEE_ID = ?"

        // selection argument
        val selectionArgs= arrayOf(phoneNum)

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        val cursor = db.query(
            TABLE_EMPLOYEE, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null)  //The sort order


        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0) {
            return true
        }

        return false
    }


    fun findEmployee(id: String): Boolean {
        val db = this.readableDatabase
        //val query = "SELECT * FROM $TABLE_EMPLOYEE WHERE $COLUMN_EMPLOYEE_PHONE LIKE $phoneNum"
        val query = "SELECT * FROM " + TABLE_EMPLOYEE + " WHERE " + COLUMN_EMPLOYEE_ID + " LIKE '" + id + "'"
        val cursor = db.rawQuery(query, null)

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0) {
            return true
        }
        return false

    }



    /**
     * This method to check user exist or not
     *
     * @param phoneNum
     * @param password
     * @return true/false
     */
    fun checkUser(phoneNum: String, password: String): Boolean {

        // array of columns to fetch
        val columns = arrayOf(COLUMN_EMPLOYEE_ID)

        val db = this.readableDatabase

        // selection criteria
        val selection = "$COLUMN_EMPLOYEE_PHONE = ? AND $COLUMN_EMPLOYEE_PASSWORD = ?"

        // selection arguments
        val selectionArgs = arrayOf(phoneNum, password)

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */

        val cursor = db.query(
            TABLE_EMPLOYEE, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null)  //The sort order


        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0) {
            return true
        }

        return false

    }

    fun getEmployeePosistion (phoneNum: String): String {
        // array of columns to fetch
        val columns = arrayOf(COLUMN_EMPLOYEE_PHONE)
        val db = this.readableDatabase

        // selection criteria
        val selection = "$COLUMN_EMPLOYEE_PHONE = ?"

        // selection argument
        val selectionArgs= arrayOf(phoneNum)

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */

        val cursor = db.query(
            TABLE_EMPLOYEE, //Table to query
            columns, //columns to return
            selection, //columns for the WHERE clause
            selectionArgs, //The values for the WHERE clause
            null,  //group the rows
            null, //filter by row groups
            null) //The sort order

        cursor?.moveToFirst()

        // prepare note object
        val employee = Employee(
            id = cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_ID)),
            name = cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_NAME)),
            phoneNum = cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_PHONE)),
            password = cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_PASSWORD)),
            position = cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_POSISTION)),
            wage = cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_WAGE)).toDouble(),
            hours = cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_HOURS)).toDouble()
        )

        // close the db connection
        cursor.close()

        return employee.position

    }


    companion object {

        // Database Version
        private val DATABASE_VERSION = 1

        // Database Name
        private val DATABASE_NAME = "EmployeeManager.db"

        // User table name
        private val TABLE_EMPLOYEE = "employee"

        // User Table Columns names
        private val COLUMN_EMPLOYEE_ID = "employee_id"
        private val COLUMN_EMPLOYEE_NAME = "employee_name"
        private val COLUMN_EMPLOYEE_PHONE = "employee_phone"
        private val COLUMN_EMPLOYEE_PASSWORD = "employee_password"
        private val COLUMN_EMPLOYEE_POSISTION = "employee_posistion"
        private val COLUMN_EMPLOYEE_WAGE = "employee_wage"
        private val COLUMN_EMPLOYEE_HOURS = "employee_hours"

    }

}