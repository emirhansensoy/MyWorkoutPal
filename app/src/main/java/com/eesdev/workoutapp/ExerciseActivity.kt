package com.eesdev.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.recyclerview.widget.LinearLayoutManager
import com.eesdev.workoutapp.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private lateinit var binding : ActivityExerciseBinding
    private lateinit var exerciseList : ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        val email=intent.getStringExtra("email")
        val day = intent.getStringExtra("day")
        val db = DatabaseHandler(this,null)
        exerciseList = arrayListOf("Bicep Curl","Skull Crusher","Push Ups","Pull Ups","Chin-ups","Dips","Preacher Curl","Triceps Extension","Romanian Deadlift","Hamstring Curl","Leg Press","Leg Extension","Chest Fly","Face Pull","Dumbell Press","Incline Dumbell Press","Seated Cable Row","Lateral Raise","Barbell Row","Dumbell Curl","Overhead Press","Conventional Deadlift","Sumo Deadlift","Low Bar Squat","High Bar Squat","Incline Bench Press","Bench Press")
        db.addExercise(exerciseList)
        //db.deleteProgram()
        db.addToProgram("Skull Crusher","sevdet@hotmail.com","Monday","5","10")
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        val view = binding.root
        super.onCreate(savedInstanceState)
        setContentView(view)
        binding.recyclerExercises.layoutManager=LinearLayoutManager(this)
        binding.recyclerExercises.adapter = ExerciseAdapter(exerciseList,email!!,day!!)
    }
}