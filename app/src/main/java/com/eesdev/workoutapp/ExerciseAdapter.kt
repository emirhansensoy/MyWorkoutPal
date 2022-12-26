package com.eesdev.workoutapp

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eesdev.workoutapp.databinding.ExerciseRecyclerRowBinding

class ExerciseAdapter (val exerciseMap : Map<String, String>,val email : String,val day : String) : RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder>() {
    class ExerciseHolder(val binding : ExerciseRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {
        val binding = ExerciseRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ExerciseHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
        val exerciseName = exerciseMap.keys.toList()[position]
        Log.d("msg", exerciseName)
        holder.binding.exerciseText.text=exerciseName
        holder.binding.imageView.setOnClickListener {
            val intent = Intent(holder.itemView.context,AddExerciseActivity::class.java)
            intent.putExtra("name",exerciseName)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return exerciseMap.size
    }
}