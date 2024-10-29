package ru.evgenykuzakov.rice

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates


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
        val spChooseMeals = findViewById<Spinner>(R.id.spChooseMeals)
        val edSearch = findViewById<EditText>(R.id.edSearch)

        date = intent.getStringExtra(DATE) ?: ""
        adapter = AddFoodRecyclerViewAdapter(emptyList(), this)
        rvAddMeals.adapter = adapter

        addMealsViewModel = ViewModelProvider(this)[AddMealsViewModel::class.java]
        showMealsViewModel = ViewModelProvider(this)[ShowMealsViewModel::class.java]
        showMealsViewModel.setDate(date)

        spChooseMeals.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                chosenMeal = spChooseMeals.selectedItemPosition
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { spChooseMeals.setSelection(0) }
        }



        edSearch.doAfterTextChanged {
            CoroutineScope(Dispatchers.IO).launch {
                val query = edSearch.text.toString()
                val data = if (query.isNotBlank()) {
                    addMealsViewModel.findByName(query) ?: emptyList()
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


    }

    override fun onItemClick(position: Int) {
        val data = adapter.getData()[position]

        val pos = when(chosenMeal){
            0 -> intent.getIntExtra(BREAKFAST_LAST_POSITION, 0)
            1 -> intent.getIntExtra(LUNCH_LAST_POSITION, 0)
            2 -> intent.getIntExtra(DINNER_LAST_POSITION, 0)
            3 -> intent.getIntExtra(EXTRA_MEALS_LAST_POSITION, 0)
            else -> -10
        }
        val meal = MealsTest(0,
            data.name,
            data.netWeight,
            DatabaseNamesEnum.entries[chosenMeal],
            pos + 1,
            date,
            data.calories, data.protein, data.fats, data.carbohydrates)
        showMealsViewModel.insert(meal)
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}