package ru.evgenykuzakov.rice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodRecyclerViewAdapter(val meals: List<TestMeal>): RecyclerView.Adapter<FoodRecyclerViewAdapter.FoodHolder>(){
    class FoodHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvName: TextView
        val tvAmount: TextView
        init {
            tvName = itemView.findViewById<TextView>(R.id.tvName)
            tvAmount = itemView.findViewById<TextView>(R.id.tvAmount)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.meal_item, parent,false)
        return FoodHolder(view)
    }

    override fun onBindViewHolder(holder: FoodHolder, position: Int) {
        holder.tvName.text = meals[position].name
        holder.tvAmount.text = meals[position].amount.toString()
    }

    override fun getItemCount(): Int {
        return  meals.size
    }
}