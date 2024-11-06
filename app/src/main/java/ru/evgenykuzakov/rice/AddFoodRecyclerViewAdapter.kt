package ru.evgenykuzakov.rice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class AddFoodRecyclerViewAdapter(private var items: List<FoodInfo>,
    private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<AddFoodRecyclerViewAdapter.MealHolder>() {
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    inner class MealHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var isExpanded: Boolean
        val clHeadingContainer: ConstraintLayout
        val clAmountContainer: ConstraintLayout
        val clMeasurementContainer: ConstraintLayout
        val btAddToMeals: Button
        val tvFoodName: TextView
        val ivExpanding: ImageView

        init {
            isExpanded = false
            tvFoodName = itemView.findViewById(R.id.tvFoodName)
            btAddToMeals = itemView.findViewById(R.id.btAddToMeals)
            ivExpanding = itemView.findViewById(R.id.ivExpanding)
            clHeadingContainer = itemView.findViewById(R.id.clHeadingContainer)
            clAmountContainer = itemView.findViewById(R.id.clAmountContainer)
            clMeasurementContainer = itemView.findViewById(R.id.clMeasurementContainer)
            btAddToMeals.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(position)
                }
            }
            clHeadingContainer.setOnClickListener{
                if (isExpanded){
                    clAmountContainer.visibility = View.GONE
                    clMeasurementContainer.visibility = View.GONE
                    btAddToMeals.visibility = View.GONE
                    ivExpanding.setImageResource(R.drawable.expand_circle_down_icon)
                    isExpanded = false
                } else {
                    clAmountContainer.visibility = View.VISIBLE
                    clMeasurementContainer.visibility = View.VISIBLE
                    ivExpanding.setImageResource(R.drawable.expand_circle_up_icon)
                    btAddToMeals.visibility = View.VISIBLE
                    isExpanded = true
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
        val view = inflater.inflate(R.layout.add_meal_item, parent, false)
        return MealHolder(view)

    }

    override fun onBindViewHolder(holder: MealHolder, position: Int) {
        val item = items[position]
        holder.tvFoodName.text = item.name
    }

    override fun getItemCount(): Int {
        return items.size
    }

}