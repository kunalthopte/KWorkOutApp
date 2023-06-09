package com.example.k_workoutapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.k_workoutapp.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter( val items: ArrayList<ExerciseModel> ): RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {


    class  ViewHolder(binding: ItemExerciseStatusBinding): RecyclerView.ViewHolder(binding.root){
        val tvItem = binding.tvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ExerciseModel = items[position]
        holder.tvItem.text = model.getId().toString()

        when{
            model.getIsSelected() ->{
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.item_circular_color_accent_border_for_rv)
            }
            model.getIsCompleted() ->{
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.item_circular_color_accent_background)
            }
            else ->{
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.item_circcular_color_gray_background)
            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}