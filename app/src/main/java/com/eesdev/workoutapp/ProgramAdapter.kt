package com.eesdev.workoutapp

import android.content.Context
import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eesdev.workoutapp.databinding.ProgramRecyclerRowBinding

class ProgramAdapter(val exerciseList : ArrayList<Exercise>,val email : String, val day : String, val context : Context) : RecyclerView.Adapter<ProgramAdapter.ProgramHolder>() {
    class ProgramHolder(val binding : ProgramRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramHolder {
        val binding = ProgramRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProgramHolder(binding)
    }

    override fun onBindViewHolder(holder: ProgramHolder, position: Int) {
        var exerciseName=exerciseList.get(position).exerciseName
        var exerciseSet=exerciseList.get(position).exerciseSet
        var exerciseWeight=exerciseList.get(position).exerciseWeight
        holder.binding.exerciseNameText.text=exerciseName
        holder.binding.exerciseSetText.text=exerciseSet
        holder.binding.exerciseWeightText.text=exerciseWeight

        holder.binding.deleteButton.setOnClickListener {
            val db = DatabaseHandler(holder.itemView.context,null)
            db.deleteProgram(exerciseList.get(position),globalEmail, globalDay,context)
        }
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context,ProgramDetail::class.java)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {

        return exerciseList.size
    }
}