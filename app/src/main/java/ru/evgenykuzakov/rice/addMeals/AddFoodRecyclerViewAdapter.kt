package ru.evgenykuzakov.rice.addMeals

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import ru.evgenykuzakov.rice.data.FoodInfo
import ru.evgenykuzakov.rice.R
import ru.evgenykuzakov.rice.data.MealsTest
import ru.evgenykuzakov.rice.data.MeasurementEnum
import java.lang.Math.round

class AddFoodRecyclerViewAdapter(
    private var context: Context, private var items: List<FoodInfo>,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<AddFoodRecyclerViewAdapter.MealHolder>() {
    interface OnItemClickListener {
        fun onItemClick(position: Int, amount: Int, amountMultiplayer: Float)
    }

    inner class MealHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var chosenMeasurement: MeasurementEnum
        val defaultMeasurementArray: Array<String>
        val edAmount: TextInputLayout
        val acvAmount: AutoCompleteTextView
        val btAddToMeals: Button
        val tvFoodName: TextView
        val acvMeasurement: AutoCompleteTextView
        val tvCalories: TextView
        val tvCarbohydrates: TextView
        val tvFats: TextView
        val tvProtein: TextView
        private val llExtra: LinearLayout
        private val clHeadingContainer: ConstraintLayout
        private val ivExpanding: ImageView
        private val edMeasurement: TextInputLayout

        init {
            chosenMeasurement = MeasurementEnum.MEASUREMENT_GRAM
            defaultMeasurementArray =
                context.resources.getStringArray(R.array.default_measurement_amount)
            tvFoodName = itemView.findViewById(R.id.tvFoodName)
            btAddToMeals = itemView.findViewById(R.id.btAddToMeals)
            ivExpanding = itemView.findViewById(R.id.ivExpanding)
            llExtra = itemView.findViewById(R.id.llExtra)
            clHeadingContainer = itemView.findViewById(R.id.clHeadingContainer)
            acvMeasurement = itemView.findViewById(R.id.acvMeasurement)
            edMeasurement = itemView.findViewById(R.id.edMeasurement)
            edAmount = itemView.findViewById(R.id.edAmount)
            acvAmount = itemView.findViewById(R.id.acvAmount)
            tvCalories = itemView.findViewById(R.id.tvCalories)
            tvCarbohydrates = itemView.findViewById(R.id.tvCarbohydrates)
            tvFats = itemView.findViewById(R.id.tvFats)
            tvProtein = itemView.findViewById(R.id.tvProtein)


            btAddToMeals.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val meal = items[position]
                    val amount = edAmount.editText?.text.toString().toInt()
                    itemClickListener.onItemClick(
                        position,
                        amount,
                        getAmountMultiplayer(
                            meal.netWeight,
                            meal.numberOfServings ?: 1,
                           amount,
                            chosenMeasurement)
                    )
                }
            }
            ivExpanding.setOnClickListener {
                if (llExtra.visibility == View.VISIBLE) {
                    llExtra.visibility = View.GONE
                    ivExpanding.setImageResource(R.drawable.expand_down_icon)
                } else {
                    llExtra.visibility = View.VISIBLE
                    ivExpanding.setImageResource(R.drawable.expand_up_icon)
                }
            }
        }
    }


    fun setData(newData: List<FoodInfo>) {
        items = newData
        notifyDataSetChanged()
    }

    fun getData(): List<FoodInfo> {
        return items
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            MealHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.add_meal_item, parent, false)
        return MealHolder(view)

    }

    override fun onBindViewHolder(holder: MealHolder, position: Int) {
        fun setNutrients(item: FoodInfo, amountMultiplayer: Float){
            holder.tvCarbohydrates.text = String.format(
                context.getString(R.string.pattern_gram),
                (item.carbohydrates * amountMultiplayer))
            holder.tvProtein.text = String.format(
                context.getString(R.string.pattern_gram),
                (item.protein * amountMultiplayer))
            holder.tvFats.text = String.format(
                context.getString(R.string.pattern_gram),
                (item.fats * amountMultiplayer))
            holder.tvCalories.text = round((item.calories ?: 0) * amountMultiplayer).toString()
        }
        val item = items[position]
        holder.tvFoodName.text = item.name
        holder.acvAmount.setText(
            holder.defaultMeasurementArray[holder.chosenMeasurement.ordinal])
        val acvAdapterItems = context.resources.getStringArray(R.array.measurement)
        val adapter =
            ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, acvAdapterItems)
        holder.acvMeasurement.setAdapter(adapter)
        holder.acvMeasurement.setText(acvAdapterItems[0], false)
        var isAmountEditTextFocused = false
        val text = holder.acvAmount.text
        val amount = when(text.isNotEmpty()){
                true -> text.toString().toInt()
                false -> 0
        }
        val amountMultiplayer = getAmountMultiplayer(
            item.netWeight,
            item.numberOfServings ?: 0,
            amount,
            holder.chosenMeasurement)
        setNutrients(item, amountMultiplayer)

        holder.acvMeasurement.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                holder.acvMeasurement.showDropDown()
            } else {
                holder.acvMeasurement.dismissDropDown()
            }
        }
        holder.acvMeasurement.setOnClickListener {
            if (holder.acvMeasurement.isShown) {
                holder.acvMeasurement.dismissDropDown()
                holder.acvMeasurement.clearFocus()
            } else {
                holder.acvMeasurement.showDropDown()
            }
        }
        holder.acvMeasurement.setOnItemClickListener { _, _, pos, _ ->
            holder.chosenMeasurement = when(pos){
                0 -> MeasurementEnum.MEASUREMENT_GRAM
                1 -> MeasurementEnum.MEASUREMENT_PORTION
                else -> {throw Exception("Undefined measurement")}
            }
            holder.acvMeasurement.dismissDropDown()
            holder.acvMeasurement.clearFocus()
            holder.edAmount.editText?.setText(holder.defaultMeasurementArray[pos])
        }

        holder.acvAmount.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val inputMethodManager =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(holder.edAmount.windowToken, 0)
            }
        }
        holder.acvAmount.setOnClickListener {
            if (holder.acvAmount.isFocused) {
                holder.acvAmount.clearFocus()
            }
        }
        holder.acvAmount.doAfterTextChanged { text ->
            val amount = when(text?.isNotEmpty()){
                true -> text.toString().toInt()
                false -> 0
                null -> 0
            }
            val amountMultiplayer = getAmountMultiplayer(
                item.netWeight,
                item.numberOfServings ?: 0,
                amount,
                holder.chosenMeasurement)
            holder.btAddToMeals.isEnabled = checkAmountCorrectness(
                holder.acvAmount.text.toString())
                setNutrients(item, amountMultiplayer)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun checkAmountCorrectness(text: String): Boolean {
        return if (text.isNotEmpty()) {
            text[0] != '0'
        } else {
            false
        }
    }
    private fun getAmountMultiplayer(
        weight: Float,
        numberOfServings: Int,
        amount: Int,
        measurement: MeasurementEnum): Float {
        return when(measurement){
            MeasurementEnum.MEASUREMENT_PORTION ->
                (weight * 10) * amount / numberOfServings
            MeasurementEnum.MEASUREMENT_GRAM ->
               amount.toFloat() / MeasurementEnum.MEASUREMENT_GRAM.amount
        }
    }

}