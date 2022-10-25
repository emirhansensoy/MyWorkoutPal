package com.eesdev.workoutapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + EMAIL_COL + " TEXT PRIMARY KEY, " +
                PASSWORD_COl + " TEXT" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    // This method is for adding data in our database
    fun addUser(email : String, password : String ){
        val values = ContentValues()
        values.put(EMAIL_COL, email)
        values.put(PASSWORD_COl, password)

        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun validateUser(email: String ,password: String): Boolean
    {
        val db = this.writableDatabase
        val c = db.rawQuery("select * from user where email =" + "\""+ email.trim() + "\""+" and password="+ "\""+ password.trim() + "\"", null)
        c.moveToFirst()
        val i = c.count
        c.close()
        return i > 0
    }

    fun doesExist(email: String): Boolean
    {
        val db = this.writableDatabase
        val c = db.rawQuery("select * from user where email =" + "\""+ email.trim() + "\"", null)
        c.moveToFirst()
        val i = c.count
        c.close()
        return i > 0
    }

    fun clearDatabase(TABLE_NAME: String) {
        val db = this.readableDatabase
        val clearDBQuery = "DELETE FROM $TABLE_NAME"
        db.execSQL(clearDBQuery)
    }

    companion object{
        private val DATABASE_NAME = "workoutapp"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "user"
        val EMAIL_COL = "email"
        val PASSWORD_COl = "password"
    }
}
