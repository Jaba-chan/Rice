package ru.evgenykuzakov.rice.showMeals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import ru.evgenykuzakov.rice.R
import ru.evgenykuzakov.rice.data.DatabaseNamesEnum
import ru.evgenykuzakov.rice.data.Heading
import ru.evgenykuzakov.rice.data.MealsTest

class ShowMealsAdapter:
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        val VIEW_TYPE_MEAL = 1
        val VIEW_TYPE_HEADING = 2
        val VIEW_TYPE_FOTTER = 3
    }
    private var items: MutableList<Any>? = mutableListOf()
    private var onHeadingClickListener: ((Boolean, DatabaseNamesEnum, Int) -> Unit)? = null
    fun setOnHeadingClickListener(f: (Boolean, DatabaseNamesEnum, Int) -> Unit) {
        onHeadingClickListener = f
    }
    fun setMeals(items: MutableList<Any>?) {
        this.items = items
        notifyDataSetChanged()
    }
    fun setMealsWithoutNotify(items: MutableList<Any>?) {
        this.items = items
    }
    fun getMeals(): MutableList<Any>? {
        return items
    }


    class MealHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView
        val tvAmount: TextView

        init {
            tvName = itemView.findViewById(R.id.tvName)
            tvAmount = itemView.findViewById(R.id.tvAmount)
        }
    }

    class HeadingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHeadingOfMeal: TextView
        val ivHeadingOfMeal: ImageView
        val cardviewHeading: CardView
        val ivHeadingExpanding: ImageView

        init {
            tvHeadingOfMeal = itemView.findViewById(R.id.tvHeadingOfMeal)
            ivHeadingOfMeal = itemView.findViewById(R.id.ivHeadingOfMeal)
            cardviewHeading = itemView.findViewById(R.id.cardviewHeading)
            ivHeadingExpanding = itemView.findViewById(R.id.ivHeadingExpanding)
        }
    }

    class FooterHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvCalories = itemView.findViewById<TextView>(R.id.tvCalories)
        val tvProtein = itemView.findViewById<TextView>(R.id.tvProtein)
        val tvFats = itemView.findViewById<TextView>(R.id.tvFats)
        val tvCarbohydrates = itemView.findViewById<TextView>(R.id.tvCarbohydrates)
    }

    override fun getItemViewType(position: Int): Int {
        return when (items?.get(position)) {
            is MealsTest -> VIEW_TYPE_MEAL
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
                val holder = HeadingHolder(view)
                holder.cardviewHeading.setOnClickListener {
                    val pos = holder.adapterPosition
                    if (pos != NO_POSITION) {
                        val item = (items?.get(pos) as Heading)
                        item.isExpanded = !item.isExpanded
                        if (item.isExpanded){
                            holder.ivHeadingExpanding.setImageResource(R.drawable.expand_up_icon)
                        } else {
                            holder.ivHeadingExpanding.setImageResource(R.drawable.expand_down_icon)
                        }
                        onHeadingClickListener?.invoke(item.isExpanded,(items?.get(pos) as Heading).dbName, pos)
                    }
                }
                return holder
            }

            VIEW_TYPE_FOTTER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.nutrients_footer , parent, false)
                FooterHolder(view)
            }
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MealHolder -> {
                val item = items?.get(position) as MealsTest
                holder.tvName.text = item.name
                holder.tvAmount.text = item.amount.toString()
            }

            is HeadingHolder -> {
                val item = items?.get(position) as Heading
                holder.tvHeadingOfMeal.text = item.heading
                when(item.dbName){
                    DatabaseNamesEnum.BREAKFAST_DATABASE -> holder.ivHeadingOfMeal.setImageResource(R.drawable.breakfast_heading_icon)
                    DatabaseNamesEnum.LUNCH_DATABASE -> holder.ivHeadingOfMeal.setImageResource(R.drawable.lunch_heading_icon)
                    DatabaseNamesEnum.DINNER_DATABASE -> holder.ivHeadingOfMeal.setImageResource(R.drawable.dinner_heading_icon)
                    DatabaseNamesEnum.EXTRA_MEALS_DATABASE -> holder.ivHeadingOfMeal.setImageResource(R.drawable.extra_meals_heading_icon)
                }
            }
            is FooterHolder -> {

            }
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

}