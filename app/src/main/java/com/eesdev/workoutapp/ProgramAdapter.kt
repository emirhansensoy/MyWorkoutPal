package com.eesdev.workoutapp

import android.content.Context
import android.content.Intent
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
        val exerciseName=exerciseList.get(position).exerciseName
        val exerciseSet=exerciseList.get(position).exerciseSet
        val exerciseWeight=exerciseList.get(position).exerciseWeight
        val exerciseRep=exerciseList.get(position).exerciseRep
        holder.binding.exerciseNameText.text=exerciseName
        holder.binding.exerciseSetText.text=exerciseSet
        holder.binding.exerciseWeightText.text=exerciseWeight
        holder.binding.exerciseRepText.text=exerciseRep

        holder.binding.deleteButton.setOnClickListener {
            val db = DatabaseHandler(holder.itemView.context,null)
            db.deleteProgram(exerciseList.get(position),globalEmail, globalDay,context)
        }
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context,ProgramDetail::class.java)
            intent.putExtra("exerciseName", holder.binding.exerciseNameText.text.toString())
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {

        return exerciseList.size
    }
}