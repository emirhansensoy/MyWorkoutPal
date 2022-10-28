package com.eesdev.workoutapp

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
        val email=intent.getStringExtra("email")
        val day = intent.getStringExtra("day")
        val db = DatabaseHandler(this,null)
        binding.buttonAdd.setOnClickListener {
            db.addToProgram(binding.textExerciseName.text.toString(),email!!,day!!,binding.inputSet.text.toString(),binding.inputWeight.text.toString())
        }

    }


}