package com.eesdev.workoutapp

import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.eesdev.workoutapp.databinding.ActivityProgramBinding

class ProgramActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProgramBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgramBinding.inflate(layoutInflater)
        val day = intent.getStringExtra("day")

        setDay(day!!,binding)

        val view = binding.root
        setContentView(view)
        val email = intent.getStringExtra("email")

        val buttonAddExercise = findViewById<Button>(R.id.buttonAddExercise)
        buttonAddExercise.setOnClickListener {
            val intent = Intent(this@ProgramActivity,ExerciseActivity::class.java)
            intent.putExtra("email",email)
            intent.putExtra("day",day)
            startActivity(intent)
        }
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
