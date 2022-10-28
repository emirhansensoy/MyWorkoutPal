package com.eesdev.workoutapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = DatabaseHandler(this,null)
        val signUpText = findViewById<TextView>(R.id.textViewSignUp)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val emailText = findViewById<EditText>(R.id.emailText)
        val passwordText = findViewById<EditText>(R.id.passwordText)

        // if sign up button is clicked, go to register activity
        signUpText.setOnClickListener{
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        // if login button is clicked, check if the user exists then go to program activity
        loginButton.setOnClickListener{
            val email: String = emailText.text.toString().trim()
            val pass: String = passwordText.text.toString().trim()

            if (email == "" || pass == "") {
                Toast.makeText(this@MainActivity, "Fill all the fields", Toast.LENGTH_SHORT).show()
            }
            else if (!email.isValidEmail()) {
                Toast.makeText(this@MainActivity, "Please enter a correct email", Toast.LENGTH_SHORT).show()
            }
            else if (!isValidPassword(passwordText.text.toString())) {
                Toast.makeText(this@MainActivity, "Please enter a correct password", Toast.LENGTH_SHORT).show()
            }
            else {
                // check email and password if they exist/equal in the database - if yes login to the app
                val userPassCheckResult: Boolean = db.validateUser(email, pass)
                // user successfully logged in
                if (userPassCheckResult) {
                    val intent = Intent(this@MainActivity, ProgramActivity::class.java)
                    intent.putExtra("email",email)
                    intent.putExtra("day","Monday")
                    startActivity(intent)
                }
                else {
                    Toast.makeText(this@MainActivity, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                }
            }

            //db.clearDatabase("user")
        }
    }

    private fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    private fun isValidPassword(password: String): Boolean {
        if (password.length < 8) return false
        if (password.firstOrNull { it.isDigit() } == null) return false
        if (password.filter { it.isLetter() }.firstOrNull { it.isUpperCase() } == null) return false
        if (password.filter { it.isLetter() }.firstOrNull { it.isLowerCase() } == null) return false
        if (password.firstOrNull { !it.isLetterOrDigit() } == null) return false

        return true
    }


}