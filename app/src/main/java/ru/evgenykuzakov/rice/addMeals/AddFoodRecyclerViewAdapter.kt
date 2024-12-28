package ru.evgenykuzakov.rice.addMeals

import android.content.Context
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
import ru.evgenykuzakov.rice.data.MeasurementEnum

class AddFoodRecyclerViewAdapter(
    private var context: Context, private var items: List<FoodInfo>,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<AddFoodRecyclerViewAdapter.MealHolder>() {
    interface OnItemClickListener {
        fun onItemClick(position: Int, amount: Int, amountMultiplayer: Double)
    }

    inner class MealHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var isExpanded: Boolean
        var chosenMeasurement: MeasurementEnum
        val defaultMeasurementArray: Array<String>
        private val llExtra: LinearLayout
        private val clHeadingContainer: ConstraintLayout
        val btAddToMeals: Button
        val tvFoodName: TextView
        val acvMeasurement: AutoCompleteTextView
        private val ivExpanding: ImageView
        private val edMeasurement: TextInputLayout
        val edAmount: TextInputLayout
        val acvAmount: AutoCompleteTextView

        init {
            isExpanded = false
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
            edAmount.editText?.setText(defaultMeasurementArray[chosenMeasurement.ordinal])

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
                if (isExpanded) {
                    llExtra.visibility = View.GONE
                    ivExpanding.setImageResource(R.drawable.expand_down_icon)
                    isExpanded = false
                } else {
                    llExtra.visibility = View.VISIBLE
                    ivExpanding.setImageResource(R.drawable.expand_up_icon)
                    isExpanded = true
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
        val item = items[position]
        holder.tvFoodName.text = item.name

        val acvAdapterItems = context.resources.getStringArray(R.array.measurement)
        val adapter =
            ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, acvAdapterItems)
        holder.acvMeasurement.setAdapter(adapter)
        holder.acvMeasurement.setText(acvAdapterItems[0], false)
        var isDropdownShown = false
        var isAmountEditTextFocused = false

        holder.acvMeasurement.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                holder.acvMeasurement.showDropDown()
                isDropdownShown = true
            } else {
                holder.acvMeasurement.dismissDropDown()
                isDropdownShown = false
            }
        }
        holder.acvMeasurement.setOnClickListener {
            if (isDropdownShown) {
                holder.acvMeasurement.dismissDropDown()
                holder.acvMeasurement.clearFocus()
                isDropdownShown = false
            } else {
                holder.acvMeasurement.showDropDown()
                isDropdownShown = true
            }
            isDropdownShown = !isDropdownShown
        }
        holder.acvMeasurement.setOnItemClickListener { _, _, position, _ ->
            holder.chosenMeasurement = when(position){
                0 -> MeasurementEnum.MEASUREMENT_GRAM
                1 -> MeasurementEnum.MEASUREMENT_PORTION
                else -> {throw Exception("Undefined measurement")}
            }
            holder.acvMeasurement.dismissDropDown()
            holder.acvMeasurement.clearFocus()
            holder.edAmount.editText?.setText(holder.defaultMeasurementArray[position])
            isDropdownShown = false
        }

        holder.acvAmount.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                isAmountEditTextFocused = true
            } else {
                isAmountEditTextFocused = false
                val inputMethodManager =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(holder.edAmount.windowToken, 0)
            }
        }
        holder.acvAmount.setOnClickListener {
            isAmountEditTextFocused = !isAmountEditTextFocused
            if (!isAmountEditTextFocused) {
                holder.acvAmount.clearFocus()
            }
        }
        holder.acvAmount.doAfterTextChanged {
            holder.btAddToMeals.isEnabled = checkAmountCorrectness(holder.acvAmount.text.toString())
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
        weight: Double,
        numberOfServings: Int,
        amount: Int,
        measurement: MeasurementEnum): Double {
        return when(measurement){
            MeasurementEnum.MEASUREMENT_PORTION ->
               weight * amount / numberOfServings
            MeasurementEnum.MEASUREMENT_GRAM ->
               amount / MeasurementEnum.MEASUREMENT_GRAM.amount
        }
    }

}