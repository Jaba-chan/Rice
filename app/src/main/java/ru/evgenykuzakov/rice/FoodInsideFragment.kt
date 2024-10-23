package ru.evgenykuzakov.rice

import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodInsideFragment(private val info: String) : Fragment(R.layout.food_inside_fragment) {
    lateinit var model: MealsViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvMain = view.findViewById<RecyclerView>(R.id.rvMain)

        model = ViewModelProvider(this)[MealsViewModel::class.java]

        val adapter = MealsRecyclerViewAdapter()

        model.getMeals().observe(viewLifecycleOwner) {
            CoroutineScope(Dispatchers.Main).launch {
                adapter.setMeals(model.getMeals().value?.toMutableList())
            }
        }

        rvMain.adapter = adapter

        val callback = object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val pos = viewHolder.adapterPosition
                val viewType = adapter.getItemViewType(pos)

                return if (viewType == MealsRecyclerViewAdapter.VIEW_TYPE_MEAL) {
                    makeMovementFlags(
                        ItemTouchHelper.UP + ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT
                    )
                } else {
                    makeMovementFlags(0, ItemTouchHelper.LEFT)
                }
            }


            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val toPos = target.adapterPosition
                val fromPos = viewHolder.adapterPosition
                if (toPos > 0 && toPos <= adapter.itemCount) {
                    val item = adapter.getMeals()?.removeAt(fromPos)
                    if (item != null){
                        adapter.getMeals()?.add(toPos, item)
                    }
                    adapter.notifyItemMoved(fromPos, toPos)
                    if (target.itemViewType == MealsRecyclerViewAdapter.VIEW_TYPE_MEAL) {
                        model.update(adapter.getMeals())
                    }
                    return  true
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
                    position >= itemCount - 1 -> Math.min(
                        dY,
                        (recyclerView.height - viewHolder.itemView.bottom).toFloat()
                    )

                    else -> dY
                }

                super.onChildDraw(
                    c, recyclerView, viewHolder, dX, newDY, actionState, isCurrentlyActive
                )
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvMain)

    }

    override fun onResume() {
        super.onResume()
        model.refreshList()
    }
}

