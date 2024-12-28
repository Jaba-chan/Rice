package ru.evgenykuzakov.rice.addMeals

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.evgenykuzakov.rice.data.DatabaseNamesEnum
import ru.evgenykuzakov.rice.data.MealsTest
import ru.evgenykuzakov.rice.R
import ru.evgenykuzakov.rice.data.MeasurementEnum
import ru.evgenykuzakov.rice.data.Nutrients
import ru.evgenykuzakov.rice.showMeals.ShowMealsViewModel
import kotlin.math.round


class AddMealsActivity: AppCompatActivity(), AddFoodRecyclerViewAdapter.OnItemClickListener {
    private var chosenMeal: Int = 0
    private lateinit var adapter: AddFoodRecyclerViewAdapter
    private lateinit var showMealsViewModel: ShowMealsViewModel
    private lateinit var addMealsViewModel: AddMealsViewModel
    private lateinit var date: String

    companion object{
        private val DATE = "date"
        private val BREAKFAST_LAST_POSITION = "breakfastLastPosition"
        private val LUNCH_LAST_POSITION = "lunchLastPosition"
        private val DINNER_LAST_POSITION = "dinnerLastPosition"
        private val EXTRA_MEALS_LAST_POSITION = "extraMealsLastPosition"

        fun newIntent(context: Context,
                      date: String,
                      breakfastLastPosition: Int,
                      lunchLastPosition: Int,
                      dinnerLastPosition: Int,
                      extraMealsLastPosition: Int): Intent
        {
            val intent = Intent(context, AddMealsActivity::class.java)
            intent.putExtra(DATE, date)
            intent.putExtra(BREAKFAST_LAST_POSITION, breakfastLastPosition)
            intent.putExtra(LUNCH_LAST_POSITION, lunchLastPosition)
            intent.putExtra(DINNER_LAST_POSITION, dinnerLastPosition)
            intent.putExtra(EXTRA_MEALS_LAST_POSITION, extraMealsLastPosition)
            return intent
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_meals_activity)

        val rvAddMeals = findViewById<RecyclerView>(R.id.rvAddMeals)
        val acvChooseMeals = findViewById<AutoCompleteTextView>(R.id.acvChooseMeals)
        val edSearch = findViewById<EditText>(R.id.edSearch)
        val cvSearch = findViewById<MaterialCardView>(R.id.cvSearch)
        val ibClear = findViewById<ImageButton>(R.id.ibClear)
        val btBackToMeals = findViewById<ImageButton>(R.id.btBackToMeals)

        date = intent.getStringExtra(DATE) ?: ""
        adapter = AddFoodRecyclerViewAdapter(this, emptyList(), this)
        rvAddMeals.adapter = adapter
        rvAddMeals.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))


        addMealsViewModel = ViewModelProvider(this)[AddMealsViewModel::class.java]
        showMealsViewModel = ViewModelProvider(this)[ShowMealsViewModel::class.java]
        showMealsViewModel.setDate(date)

        val items = resources.getStringArray(R.array.meals)
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, items)
        acvChooseMeals.setAdapter(adapter)
        acvChooseMeals.setText(items[chosenMeal], false)
        acvChooseMeals.setOnItemClickListener{ _, _, position, _ ->
            chosenMeal = position
        }


        edSearch.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                ibClear.visibility = View.VISIBLE
                val typedValue = TypedValue()
                theme.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)
                val colorPrimary = typedValue.data
                cvSearch.strokeColor = colorPrimary
            } else {
                val typedValue = TypedValue()
                theme.resolveAttribute(com.google.android.material.R.attr.colorSurfaceContainerHighest, typedValue, true)
                val colorSurfaceContainerHighest = typedValue.data
                cvSearch.strokeColor = colorSurfaceContainerHighest
            }
        }

        edSearch.doAfterTextChanged {
            CoroutineScope(Dispatchers.IO).launch {
                val query = edSearch.text.toString()
                val data = if (query.isNotBlank()) {
                    addMealsViewModel.findByName(query)
                } else {
                    emptyList()
                }
                withContext(Dispatchers.Main) {
                    if (rvAddMeals.adapter != null) {
                        val newAdapter = (rvAddMeals.adapter as AddFoodRecyclerViewAdapter)
                        newAdapter.setData(data)
                    }
                }
            }
        }

        ibClear.setOnClickListener{
            edSearch.clearFocus()
            edSearch.setText("")
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(edSearch.windowToken, 0)
            ibClear.visibility = View.GONE
        }

        btBackToMeals.setOnClickListener {
            val intent = Intent()
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }
    }

    override fun onItemClick(position: Int, amountMultiplayer: Double) {
        val data = adapter.getData()[position]

        val pos = when(chosenMeal){
            0 -> intent.getIntExtra(BREAKFAST_LAST_POSITION, 0)
            1 -> intent.getIntExtra(LUNCH_LAST_POSITION, 0)
            2 -> intent.getIntExtra(DINNER_LAST_POSITION, 0)
            3 -> intent.getIntExtra(EXTRA_MEALS_LAST_POSITION, 0)
            else -> throw Exception("Undefined meal database")
        }
        val meal = MealsTest(0,
            data.id,
            data.name,
            amountMultiplayer,
            DatabaseNamesEnum.entries[chosenMeal],
            pos + 1,
            date,
            data.calories, data.protein, data.fats, data.carbohydrates)
        showMealsViewModel.insert(meal)
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun getNutrientsByWeight(weight: Double, measurement: MeasurementEnum, meal: Nutrients): Nutrients{

        return when(measurement){
            MeasurementEnum.MEASUREMENT_PORTION -> Nutrients(
                round(meal.calories.toDouble() * weight / MeasurementEnum.MEASUREMENT_PORTION.amount).toInt(),
                meal.protein * weight / MeasurementEnum.MEASUREMENT_PORTION.amount,
                meal.fats * weight / MeasurementEnum.MEASUREMENT_PORTION.amount,
                meal.carbohydrates * weight / MeasurementEnum.MEASUREMENT_PORTION.amount)
            MeasurementEnum.MEASUREMENT_GRAM -> Nutrients(
                round(meal.calories.toDouble() * weight / MeasurementEnum.MEASUREMENT_PORTION.amount).toInt(),
                meal.protein * weight / MeasurementEnum.MEASUREMENT_PORTION.amount,
                meal.fats * weight / MeasurementEnum.MEASUREMENT_PORTION.amount,
                meal.carbohydrates * weight / MeasurementEnum.MEASUREMENT_PORTION.amount)
        }
    }
}