package com.eesdev.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.eesdev.workoutapp.databinding.ActivityAddExerciseBinding

class AddExerciseActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddExerciseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddExerciseBinding.inflate(layoutInflater)
        val view = binding.root
        super.onCreate(savedInstanceState)
        setContentView(view)
        binding.textExerciseName.text=intent.getStringExtra("name")
        val intent = Intent(this@AddExerciseActivity,ProgramActivity::class.java)

        val db = DatabaseHandler(this,null)
        binding.buttonAdd.setOnClickListener {
            db.addToProgram(binding.textExerciseName.text.toString(),globalEmail!!, globalDay!!,binding.inputSet.text.toString(),binding.inputWeight.text.toString())
            startActivity(intent)
        }
        binding.button.setOnClickListener {
            startActivity(intent)
        }


    }

    override fun onBackPressed() {
        //back button disabled
    }


}