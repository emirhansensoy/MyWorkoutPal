package com.eesdev.workoutapp

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Debug
import android.system.Os.uname




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

    fun addExercise(exerciseList : ArrayList<String>){
        val db = this.writableDatabase
        val query = ("CREATE TABLE IF NOT EXISTS exercises(id INTEGER PRIMARY KEY,name VARCHAR)")
        val values=ContentValues()
        var doesExist : Boolean
        db.execSQL(query)
        for(exercise in exerciseList){
            values.put("name",exercise)
            val c = db.rawQuery("select * from exercises where name =" + "\""+ exercise.trim() + "\"",null)
            c.moveToFirst()
            doesExist=c.count>0
            if(doesExist){
                //already exists
            }
            else{
                db.insert("exercises",null,values)
            }
        }

    }

    fun deleteProgram( exercise : Exercise , email : String, day : String, context : Context){
        val db = this.writableDatabase
        val query = "delete from program where email = ? and name = ? and day = ?"
        db.execSQL(query,arrayOf<String>(email,exercise.exerciseName,day))
        var intent = Intent(context,ProgramActivity::class.java)
        context.startActivity(intent)


    }

    fun getExercises(email : String, day : String) : ArrayList<Exercise>{
        val db = this.writableDatabase

        val query = "select * from program where email = ? and day = ?"
        val exercises = ArrayList<Exercise>()
        val c = db.rawQuery(query, arrayOf<String>(email, day) )
        val exerciseIx=c.getColumnIndex("name")
        val exerciseSet=c.getColumnIndex("sets")
        val exerciseWeight=c.getColumnIndex("weight")
        while(c.moveToNext()){
            val exercise = Exercise(c.getString(exerciseIx),c.getString(exerciseSet),c.getString(exerciseWeight))
            println(c.getString(exerciseIx))
            exercises.add(exercise)
        }
        return exercises
    }

    fun addToProgram(exerciseName : String , emailAddress : String , dayInput : String,exerciseSet : String,exerciseWeight : String ){
        val db = this.writableDatabase
        val query = ("CREATE TABLE IF NOT EXISTS program(id INTEGER PRIMARY KEY,name VARCHAR,email VARCHAR,day VARCHAR,sets VARCHAR,weight VARCHAR)")
        db.execSQL(query)
        val cursor = db.rawQuery("select * from program",null)
        val emailIx=cursor.getColumnIndex("email")
        val nameIx=cursor.getColumnIndex("name")
        val dayIx=cursor.getColumnIndex("day")
        var hasExercise = false
        while(cursor.moveToNext()){
            val email = cursor.getString(emailIx)
            val name = cursor.getString(nameIx)
            val day = cursor.getString(dayIx)

            if(exerciseName == name && emailAddress == email && dayInput == day){
                hasExercise = true
            }
        }
        val values = ContentValues()
        values.put("name",exerciseName)
        values.put("email",emailAddress)
        values.put("day",dayInput)
        values.put("sets",exerciseSet)
        values.put("weight",exerciseWeight)
        if(!hasExercise){
            println("girdi has exercise")
            db.insert("program",null,values)
        }
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
