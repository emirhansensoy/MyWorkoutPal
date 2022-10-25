package com.eesdev.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
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
        val passwordInput = findViewById<EditText>(R.id.passwordText)
        val loginText = findViewById<TextView>(R.id.textViewLogin)

        registerButton.setOnClickListener{
            val db = DatabaseHandler(this, null)

            val emailText = emailInput.text.toString().trim()
            val passwordText = passwordInput.text.toString().trim()

            if (emailText == "" || passwordText == "") {
                Toast.makeText(this@RegisterActivity, "Fill all the fields", Toast.LENGTH_SHORT).show()
            }
            else if (!emailText.isValidEmail()) {
                Toast.makeText(this@RegisterActivity, "Please enter a correct email", Toast.LENGTH_SHORT).show()
            }
            else if (!isValidPassword(passwordText)) {
                Toast.makeText(this@RegisterActivity, "Please enter a correct password", Toast.LENGTH_SHORT).show()
            }
            else if (db.doesExist(emailText)) {
                Toast.makeText(this@RegisterActivity, "This user already exists", Toast.LENGTH_SHORT).show()
            }
            else {
                db.addUser(emailText, passwordText)
                Toast.makeText(this, "$emailText added to database", Toast.LENGTH_LONG).show()
                val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }

        loginText.setOnClickListener{
            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
            startActivity(intent)
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