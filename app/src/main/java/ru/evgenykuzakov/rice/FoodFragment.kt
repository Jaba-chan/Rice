package ru.evgenykuzakov.rice

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.Date

class FoodFragment: Fragment(R.layout.food_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pager = view.findViewById<ViewPager2>(R.id.vp2DaysOfTheWeek)
        val tabs = view.findViewById<TabLayout>(R.id.tlDaysOfTheWeek)

        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        var currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        if (currentDayOfWeek == 1){
            currentDayOfWeek = 6
        } else {
            currentDayOfWeek -= 2
        }
        calendar[Calendar.DAY_OF_WEEK] = Calendar.MONDAY

        val weekDates = mutableListOf<Date>()
        for (i in 0..6) {
            weekDates.add(calendar.time)
            calendar.add(Calendar.DAY_OF_WEEK, 1)
            Log.e("fdfff",weekDates[i].toString())
        }

        val vp2Adapter = FoodViewPagerAdapter(listOf(
            FoodInsideFragment(weekDates[0]),
            FoodInsideFragment(weekDates[1]),
            FoodInsideFragment(weekDates[2]),
            FoodInsideFragment(weekDates[3]),
            FoodInsideFragment(weekDates[4]),
            FoodInsideFragment(weekDates[5]),
            FoodInsideFragment(weekDates[6]),
        ), this)
        pager.adapter = vp2Adapter
        pager.isUserInputEnabled = false
        pager.setCurrentItem(currentDayOfWeek, false)
        TabLayoutMediator(tabs, pager){ tab, position ->
            when(position){
                0 ->  tab.text = resources.getString(R.string.abbreviation_monday)
                1 ->  tab.text = resources.getString(R.string.abbreviation_tuesday)
                2 ->  tab.text = resources.getString(R.string.abbreviation_wednesday)
                3 ->  tab.text = resources.getString(R.string.abbreviation_thursday)
                4 ->  tab.text = resources.getString(R.string.abbreviation_friday)
                5 ->  tab.text = resources.getString(R.string.abbreviation_saturday)
                6 ->  tab.text = resources.getString(R.string.abbreviation_sunday)
            }
        }.attach()


    }
}