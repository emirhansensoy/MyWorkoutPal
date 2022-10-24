package com.eesdev.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerButton = findViewById<Button>(R.id.registerButton)
        val emailInput = findViewById<EditText>(R.id.emailText)
        val loginInput = findViewById<EditText>(R.id.passwordText)
        val loginText = findViewById<TextView>(R.id.textViewLogin)

        registerButton.setOnClickListener{

            // below we have created
            // a new DBHelper class,
            // and passed context to it
            val db = DatabaseHandler(this, null)

            // creating variables for values
            // in name and age edit texts
            val emailText = emailInput.text.toString()
            val passwordText = loginInput.text.toString()

            // calling method to add
            // name to our database
            db.addUser(emailText, passwordText)

            // Toast to message on the screen
            Toast.makeText(this, "$emailText added to database", Toast.LENGTH_LONG).show()

            // at last, clearing edit texts
            emailInput.text.clear()
            loginInput.text.clear()

            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
            startActivity(intent)
        }

        loginText.setOnClickListener{
            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
            startActivity(intent)
        }

        /*
        printName.setOnClickListener{

            // creating a DBHelper class
            // and passing context to it
            val db = DatabaseHandler(this, null)

            // below is the variable for cursor
            // we have called method to get
            // all names from our database
            // and add to name text view
            val cursor = db.getUser()

            // moving the cursor to first position and
            // appending value in the text view
            cursor!!.moveToFirst()
            Name.append(cursor.getString(cursor.getColumnIndex(DatabaseHandler.EMAIL_COL)) + "\n")
            Age.append(cursor.getString(cursor.getColumnIndex(DatabaseHandler.PASSWORD_COl)) + "\n")

            // moving our cursor to next
            // position and appending values
            while(cursor.moveToNext()){
                Name.append(cursor.getString(cursor.getColumnIndex(DatabaseHandler.EMAIL_COL)) + "\n")
                Age.append(cursor.getString(cursor.getColumnIndex(DatabaseHandler.PASSWORD_COl)) + "\n")
            }

            // at last we close our cursor
            cursor.close()

        }
        */
    }
}