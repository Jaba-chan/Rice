package ru.evgenykuzakov.rice

import android.app.Activity
import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FoodInsideFragment(private val daysOfTheWeek: Date) : Fragment(R.layout.food_inside_fragment) {
    lateinit var model: ShowMealsViewModel
    lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvMain = view.findViewById<RecyclerView>(R.id.rvMain)
        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fabAdd)
        val adapter = ShowMealsRecyclerViewAdapter()

        val navHostFragment = requireActivity().supportFragmentManager
            .findFragmentById(R.id.fcvMain) as NavHostFragment
        val navController = navHostFragment.navController

        model = ViewModelProvider(this)[ShowMealsViewModel::class.java]
        model.setDate(getDate(daysOfTheWeek))


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

                return if (viewType == ShowMealsRecyclerViewAdapter.VIEW_TYPE_MEAL) {
                    makeMovementFlags(
                        ItemTouchHelper.UP + ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT
                    )
                } else {
                    makeMovementFlags(0,0)
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
                    if (target.itemViewType == ShowMealsRecyclerViewAdapter.VIEW_TYPE_MEAL) {
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

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                val item = adapter.getMeals()?.removeAt(pos)
                adapter.notifyItemRemoved(pos)
                model.remove((item as MealsTest).id, adapter.getMeals())
            }

        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvMain)

        fabAdd.setOnClickListener{
            val intent = Intent(requireActivity(), AddMealsActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        model.refreshList()
    }

    fun getDate(date: Date): String{
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
    }
}

