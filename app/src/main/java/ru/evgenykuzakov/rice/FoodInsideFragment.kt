package ru.evgenykuzakov.rice

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class FoodInsideFragment(private val info: String): Fragment(R.layout.food_inside_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabInfoText = view.findViewById<TextView>(R.id.tvBreakfast)
        val rvBreakfast = view.findViewById<RecyclerView>(R.id.rvBreakfast)
        val rvLunch = view.findViewById<RecyclerView>(R.id.rvLunch)
        val rvDinner = view.findViewById<RecyclerView>(R.id.rvDinner)
        val rvExtraMeal = view.findViewById<RecyclerView>(R.id.rvExtraMeal)

        val newText: String =  getString(R.string.breakfast) + " " + info
        tabInfoText.text = newText

        var list1: List<TestMeal> = listOf(TestMeal("Молоко", 100.0), TestMeal("Хлеб", 10.0), TestMeal("Яйца", 50.0))
        var list2: List<TestMeal> = listOf(TestMeal("Водка", 100.0), TestMeal("Пиво", 10.0), TestMeal("Вино", 50.0))

        rvBreakfast.adapter = FoodRecyclerViewAdapter(list1)
        rvLunch.adapter = FoodRecyclerViewAdapter(list2)
        rvDinner.adapter = FoodRecyclerViewAdapter(list1)
        rvExtraMeal.adapter = FoodRecyclerViewAdapter(list2)

        val swipeCallback = object : ItemTouchHelper.Callback(){
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(ItemTouchHelper.UP + ItemTouchHelper.DOWN, 0)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                TODO("Not yet implemented")
            }

        }
    }


}