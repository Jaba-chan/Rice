package ru.evgenykuzakov.rice

import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class FoodInsideFragment(private val info: String) : Fragment(R.layout.food_inside_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvMain = view.findViewById<RecyclerView>(R.id.rvMain)

        var list1: MutableList<Any> =
            mutableListOf(TestMeal("Молоко", 100.0), TestMeal("Хлеб", 10.0), TestMeal("Яйца", 50.0))
        var list2: MutableList<Any> =
            mutableListOf(TestMeal("Водка", 100.0), TestMeal("Пиво", 10.0), TestMeal("Вино", 50.0))
        var clist =
            (listOf(Heading(resources.getString(R.string.breakfast))) + list1 + list2).toMutableList()
        val adapter = FoodRecyclerViewAdapter(clist)
        rvMain.adapter = adapter

        val callback = object : ItemTouchHelper.Callback() {

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val pos = viewHolder.adapterPosition
                val viewType = adapter.getItemViewType(pos)
                return if (viewType == FoodRecyclerViewAdapter.VIEW_TYPE_MEAL) {
                    makeMovementFlags(ItemTouchHelper.UP + ItemTouchHelper.DOWN, 0)
                } else {
                    makeMovementFlags(0, 0)
                }
            }


            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPos = viewHolder.adapterPosition
                val toPos = target.adapterPosition
                if (toPos > 0) {
                    val item = adapter.items.removeAt(fromPos)
                    adapter.items.add(toPos, item)
                    adapter.notifyItemMoved(fromPos, toPos)
                    return true
                }
                return false
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val position = viewHolder.adapterPosition
                val itemCount = recyclerView.adapter?.itemCount ?: 0
                val newDY = when {
                    position <= 1 -> Math.max(dY, 0f)
                    position >= itemCount - 1 -> Math.min(dY, (recyclerView.height - viewHolder.itemView.bottom).toFloat())
                    else -> dY
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, newDY, actionState, isCurrentlyActive)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvMain)
    }


}