package ru.evgenykuzakov.rice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION

class AddFoodRecyclerViewAdapter(private var items: List<FoodInfo>) : RecyclerView.Adapter<AddFoodRecyclerViewAdapter.MealHolder>() {
    class MealHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView
        val tvAmount: TextView

        init {
            tvName = itemView.findViewById<TextView>(R.id.tvName)
            tvAmount = itemView.findViewById<TextView>(R.id.tvAmount)
        }
    }
    fun setData(newData: List<FoodInfo>) {
        items = newData
        notifyDataSetChanged() // Обновляет данные в RecyclerView
    }

    private var OnItemClickListener: ((Int) -> Unit)? = null
    fun setOnClickListener(f: (Int) -> Unit) {OnItemClickListener = f}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            MealHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.meal_item, parent, false)
        val holder = MealHolder(view)
        view.setOnClickListener{
            val pos = holder.adapterPosition
            if (pos != NO_POSITION){
                OnItemClickListener?.invoke(pos)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: MealHolder, position: Int) {
        val item = items?.get(position)
        if (item != null) {
            holder.tvName.text = item.name
            holder.tvAmount.text = item.netWeight.toString()
        }

    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

}