package com.eesdev.workoutapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eesdev.workoutapp.databinding.ExerciseRecyclerRowBinding

class ExerciseAdapter (val exerciseList : ArrayList<String>,val email : String,val day : String) : RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder>() {
    class ExerciseHolder(val binding : ExerciseRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {
        val binding = ExerciseRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ExerciseHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
        val exerciseName = exerciseList.get(position)
        holder.binding.exerciseText.text=exerciseName
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,AddExerciseActivity::class.java)
            intent.putExtra("name",exerciseName)
            intent.putExtra("email",email)
            intent.putExtra("day",day)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }
}