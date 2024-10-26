package ru.evgenykuzakov.rice

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddMealsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_meals_activity)
        val rvAddMeals = findViewById<RecyclerView>(R.id.rvAddMeals)
        val edSearch = findViewById<EditText>(R.id.edSearch)
        val model = ViewModelProvider(this)[AddMealsViewModel::class.java]

        edSearch.doAfterTextChanged {
            CoroutineScope(Dispatchers.IO).launch {
                val query = edSearch.text.toString()
                val data = model.findByName("$query%") ?: emptyList()
                withContext(Dispatchers.Main) {
                    if (rvAddMeals.adapter == null) {
                        val adapter = AddFoodRecyclerViewAdapter(data)
                        rvAddMeals.adapter = adapter
                    } else {
                        (rvAddMeals.adapter as AddFoodRecyclerViewAdapter).setData(data)
                    }
                }
            }

        }
        rvAddMeals.setOnClickListener{

        }
    }
}