package ru.evgenykuzakov.rice.showMeals

import android.app.Activity
import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.evgenykuzakov.rice.R
import ru.evgenykuzakov.rice.RecyclerViewSwipeDecorator
import ru.evgenykuzakov.rice.addMeals.AddMealsActivity
import ru.evgenykuzakov.rice.data.DatabaseNamesEnum
import ru.evgenykuzakov.rice.data.Heading
import ru.evgenykuzakov.rice.data.MealsTest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class FoodInsideFragment(private val date: Date) : Fragment(R.layout.food_inside_fragment) {
    lateinit var model: ShowMealsViewModel
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    model.refreshList()
                }

            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvMain = view.findViewById<RecyclerView>(R.id.rvMain)
        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fabAdd)
        val pbWaitForMealsData = view.findViewById<ProgressBar>(R.id.pbWaitForMealsData)

        pbWaitForMealsData.visibility = View.VISIBLE
        model = ViewModelProvider(this,
            ShowMealsViewModel.ShowMealsViewModelFactory(
                getDate(date),
                requireActivity().application))[ShowMealsViewModel::class.java]
        model.refreshList()
        val adapter = ShowMealsAdapter(requireContext())

        adapter.setOnHeadingClickListener { isExpanded, database, startPos ->
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    when(database){
                        DatabaseNamesEnum.BREAKFAST_DATABASE ->
                            model.breakfastHeading.isExpanded = !model.breakfastHeading.isExpanded
                        DatabaseNamesEnum.LUNCH_DATABASE ->
                            model.lunchHeading.isExpanded = !model.lunchHeading.isExpanded
                        DatabaseNamesEnum.DINNER_DATABASE ->
                            model.dinnerHeading.isExpanded = !model.dinnerHeading.isExpanded
                        DatabaseNamesEnum.EXTRA_MEALS_DATABASE ->
                            model.extraMealsHeading.isExpanded = !model.extraMealsHeading.isExpanded
                    }
                    model.refreshList()
                    adapter.notifyDataSetChanged()
                    model.setBannedPos(adapter.getPositionsForViewType() ?: emptyList())
                }
            }
        }


        model.getMeals().observe(viewLifecycleOwner) {meals ->
            adapter.setMeals(meals)
            pbWaitForMealsData.visibility = View.GONE
        }

        rvMain.adapter = adapter
        rvMain.overScrollMode = View.OVER_SCROLL_NEVER

        val callback = object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val pos = viewHolder.absoluteAdapterPosition
                val viewType = adapter.getItemViewType(pos)

                return if (viewType == ShowMealsAdapter.VIEW_TYPE_MEAL) {
                    makeMovementFlags(
                        ItemTouchHelper.UP + ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT
                    )
                } else {
                    makeMovementFlags(0, 0)
                }
            }


            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val toPos = target.absoluteAdapterPosition
                val fromPos = viewHolder.absoluteAdapterPosition
                val checkedBannedItem = adapter.getMeals()?.get(toPos)
                if (checkedBannedItem is Heading){
                    if (toPos > fromPos){
                        if (!checkedBannedItem.isExpanded) {
                            return true
                        }
                    } else {
                        if (model.bannedPos.value?.contains(toPos - 1) == true){
                            return true
                        }
                    }
                }
                if (toPos > 0 && toPos <= adapter.itemCount - 2) {
                    val item = adapter.getMeals()?.removeAt(fromPos)
                    if (item != null) {
                        adapter.getMeals()?.add(toPos, item)
                    }
                    adapter.notifyItemMoved(fromPos, toPos)
                    model.update(adapter.getMeals())
                    model.setBannedPos(adapter.getPositionsForViewType() ?: emptyList())
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
                val cornerRadius =
                    requireContext().resources.getDimension(R.dimen.meal_corner_radius)
                val indent =
                    requireContext().resources.getDimension(R.dimen.swipe_delete_indent)
                RecyclerViewSwipeDecorator.Builder(
                    c, recyclerView, viewHolder,
                    dX, dY, actionState, isCurrentlyActive
                )
                    .addBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.color_swap_delete_container
                        )
                    )
                    .addActionIcon(R.drawable.delete_icon)
                    .setActionIconTint(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.color_swap_delete_icon
                        )
                    )
                    .addCornerRadius(0, cornerRadius.toInt())
                    .setSwipeLeftBackgroundMargin(0, indent, 0f, 0f, 0f)
                    .create()
                    .decorate()

                val position = viewHolder.absoluteAdapterPosition
                val itemCount = recyclerView.adapter?.itemCount ?: 0
                val bottomLimit = recyclerView.findViewHolderForAdapterPosition(itemCount - 2)?.itemView?.bottom
                    ?: recyclerView.height
                val newDY = when {
                    position <= 1 -> Math.max(dY, 0f)
                    position > 1 -> Math.min(
                        dY,
                        (bottomLimit - viewHolder.itemView.bottom.toFloat())
                    )

                    else -> dY
                }

                super.onChildDraw(
                    c, recyclerView, viewHolder, dX, newDY, actionState, isCurrentlyActive
                )
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.absoluteAdapterPosition
                val item = adapter.getMeals()?.removeAt(pos)
                adapter.notifyDataSetChanged()
                model.remove((item as MealsTest).id)
                model.setBannedPos(adapter.getPositionsForViewType() ?: emptyList())
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvMain)

        fabAdd.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                val breakfastLastPosition = model.getBreakfastLastPosition()
                val lunchLastPosition = model.getLunchLastPosition()
                val dinnerLastPosition = model.getDinnerLastPosition()
                val extraLastPosition = model.getExtraLastPosition()

                withContext(Dispatchers.Main) {
                    val intent = AddMealsActivity.newIntent(
                        requireActivity(),
                        getDate(date),
                        breakfastLastPosition,
                        lunchLastPosition,
                        dinnerLastPosition,
                        extraLastPosition
                    )
                    resultLauncher.launch(intent)
                }
            }
        }
    }


    private fun getDate(date: Date): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
    }

}