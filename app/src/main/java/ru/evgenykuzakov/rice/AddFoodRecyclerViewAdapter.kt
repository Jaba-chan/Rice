package ru.evgenykuzakov.rice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AddFoodRecyclerViewAdapter(private var items: List<FoodInfo>,
    private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<AddFoodRecyclerViewAdapter.MealHolder>() {
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    inner class MealHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView
        val tvAmount: TextView

        init {
            tvName = itemView.findViewById<TextView>(R.id.tvName)
            tvAmount = itemView.findViewById<TextView>(R.id.tvAmount)
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(position)
                }
            }
        }
    }
    fun setData(newData: List<FoodInfo>) {
        items = newData
        notifyDataSetChanged()
    }
    fun getData(): List<FoodInfo>{
        return items
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            MealHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.meal_item, parent, false)
        return MealHolder(view)

    }

    override fun onBindViewHolder(holder: MealHolder, position: Int) {
        val item = items[position]
        holder.tvName.text = item.name
        holder.tvAmount.text = item.netWeight.toString()

    }

    override fun getItemCount(): Int {
        return items.size
    }

}