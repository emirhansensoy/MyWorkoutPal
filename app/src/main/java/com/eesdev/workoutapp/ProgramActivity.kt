package com.eesdev.workoutapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eesdev.workoutapp.databinding.ActivityProgramBinding

var globalDay : String = "Monday"

class ProgramActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProgramBinding
    private lateinit var exerciseList : ArrayList<Exercise>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgramBinding.inflate(layoutInflater)

        exerciseList=ArrayList()
        val db = DatabaseHandler(this,null)
        exerciseList=db.getExercises(globalEmail!!,globalDay!!)
        setDay(globalDay!!,binding)

        val view = binding.root
        setContentView(view)


        val buttonSali=binding.buttonSali
        buttonSali.setOnClickListener {
            dayButton("Tuesday",binding,globalEmail!!,this@ProgramActivity)

        }
        binding.buttonPzt.setOnClickListener {
            dayButton("Monday",binding,globalEmail!!,this@ProgramActivity)

        }
        binding.buttonCars.setOnClickListener {
            dayButton("Wednesday",binding,globalEmail!!,this@ProgramActivity)
        }
        binding.buttonPers.setOnClickListener {
            dayButton("Thursday",binding,globalEmail!!,this@ProgramActivity)

        }
        binding.buttonCuma.setOnClickListener {
            dayButton("Friday",binding,globalEmail!!,this@ProgramActivity)
        }
        binding.buttonCmrts.setOnClickListener {
            dayButton("Saturday",binding,globalEmail!!,this@ProgramActivity)

        }
        binding.buttonPzr.setOnClickListener {
            dayButton("Sunday",binding,globalEmail!!,this@ProgramActivity)
        }
        val buttonAddExercise = findViewById<Button>(R.id.buttonAddExercise)
        buttonAddExercise.setOnClickListener {
            val intent = Intent(this@ProgramActivity,ExerciseActivity::class.java)
            startActivity(intent)
        }
        binding.recyclerProgram.layoutManager=LinearLayoutManager(this)
        binding.recyclerProgram.adapter=ProgramAdapter(exerciseList,globalEmail!!,globalDay!!,this@ProgramActivity)
    }
    override fun onBackPressed() {
        //disable back button
    }


}

    fun setDay(day : String,binding: ActivityProgramBinding){

        when(day){
            "Monday" -> {
                binding.buttonPzt.isSelected=true
                // recycler view list
            }
            "Tuesday" -> {
                binding.buttonSali.isSelected=true
            }
            "Wednesday" -> {
                binding.buttonCars.isSelected=true
            }
            "Thursday" -> {
                binding.buttonPers.isSelected=true
            }
            "Friday" -> {
                binding.buttonCuma.isSelected=true
            }
            "Saturday" -> {
                binding.buttonCmrts.isSelected=true
            }
            "Sunday" -> {
                binding.buttonPzr.isSelected=true
            }
        }
    }
    fun dayButton(day : String, binding : ActivityProgramBinding, email : String, context : Context ){
        val intent = Intent(context,ProgramActivity::class.java)
        globalDay = day
        context.startActivity(intent)
    }
