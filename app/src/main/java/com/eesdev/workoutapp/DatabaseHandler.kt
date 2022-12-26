package com.eesdev.workoutapp

import android.content.ContentValues
import android.content.Context
import android.content.Intent
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

    fun createTables() {
        val db = this.writableDatabase
        val query = ("CREATE TABLE IF NOT EXISTS program(id INTEGER PRIMARY KEY,name VARCHAR,email VARCHAR,day VARCHAR,sets VARCHAR,weight VARCHAR,reps VARCHAR)")
        val query1 = ("CREATE TABLE IF NOT EXISTS exercises(id INTEGER PRIMARY KEY, name VARCHAR, desc VARCHAR)")

        //db.execSQL(query2)
        db.execSQL(query)
        db.execSQL(query1)
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

    fun addExercise(exerciseMap : Map<String, String>){
        val db = this.writableDatabase
        val query = ("CREATE TABLE IF NOT EXISTS exercises(id INTEGER PRIMARY KEY,name VARCHAR, desc VARCHAR)")
        val values=ContentValues()
        var doesExist : Boolean
        db.execSQL(query)
        for(exercise in exerciseMap){
            values.put("name",exercise.key)
            values.put("desc",exercise.value)
            val c = db.rawQuery("select * from exercises where name =" + "\""+ exercise.key.trim() + "\"",null)
            c.moveToFirst()
            doesExist=c.count>0
            if(doesExist){
                //already exists
            }
            else{
                db.insert("exercises",null,values)
            }
            c.close()
        }
    }

    fun deleteProgram( exercise : Exercise , email : String, day : String, context : Context){
        val db = this.writableDatabase
        val query = "delete from program where email = ? and name = ? and day = ?"
        db.execSQL(query,arrayOf<String>(email,exercise.exerciseName,day))
        val intent = Intent(context,ProgramActivity::class.java)
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
        val exerciseRep=c.getColumnIndex("reps")
        while(c.moveToNext()){
            val exercise = Exercise(c.getString(exerciseIx),c.getString(exerciseSet),c.getString(exerciseWeight),c.getString(exerciseRep))
            println(c.getString(exerciseIx))
            exercises.add(exercise)
        }
        c.close()
        return exercises
    }


    fun getExerciseDesc(exerciseName: String): String
    {
        var text = "error"
        val db = this.writableDatabase
        val cursor = db.rawQuery("select * from exercises where name = \"" + exerciseName + "\"", null)
        val index = cursor.getColumnIndex("desc")
        while(cursor.moveToNext()){
            text = cursor.getString(index)
        }
        cursor.close()
        return text
    }

    fun addToProgram(exerciseName : String , emailAddress : String , dayInput : String,exerciseSet : String,exerciseWeight : String, exerciseRep: String){
        val db = this.writableDatabase
        val query = ("CREATE TABLE IF NOT EXISTS program(id INTEGER PRIMARY KEY,name VARCHAR,email VARCHAR,day VARCHAR,sets VARCHAR,weight VARCHAR, reps VARCHAR)")
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
        values.put("reps",exerciseRep)
        if(!hasExercise){
            println("girdi has exercise")
            db.insert("program",null,values)
        }
        cursor.close()
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
