package ru.evgenykuzakov.rice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodRecyclerViewAdapter(val items: MutableList<Any>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        companion object {
            val VIEW_TYPE_MEAL = 1
            val VIEW_TYPE_HEADING = 2
        }


    class MealHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView
        val tvAmount: TextView
        init {
            tvName = itemView.findViewById<TextView>(R.id.tvName)
            tvAmount = itemView.findViewById<TextView>(R.id.tvAmount)
        }
    }

    class HeadingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHeadingOfMeal: TextView
        init {
            tvHeadingOfMeal = itemView.findViewById<TextView>(R.id.tvHeadingOfMeal)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is TestMeal -> VIEW_TYPE_MEAL
            is Heading -> VIEW_TYPE_HEADING
            else -> throw IllegalArgumentException("Unknown item type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_MEAL -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.meal_item, parent, false)
                MealHolder(view)
            }

            VIEW_TYPE_HEADING -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.heading_item, parent, false)
                HeadingHolder(view)
            }

            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MealHolder -> {
                val item = items[position] as TestMeal
                holder.tvName.text = item.name
                holder.tvAmount.text = item.amount.toString()
            }

            is HeadingHolder -> {
                val item = items[position] as Heading
                holder.tvHeadingOfMeal.text = item.heading
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}