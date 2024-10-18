package ru.evgenykuzakov.rice

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FoodFragment: Fragment(R.layout.food_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pager = view.findViewById<ViewPager2>(R.id.vp2DaysOfTheWeek)
        val tabs = view.findViewById<TabLayout>(R.id.tlDaysOfTheWeek)

        val vp2Adapter = FoodViewPagerAdapter(listOf(
            FoodInsideFragment(resources.getString(R.string.abbreviation_monday)),
            FoodInsideFragment(resources.getString(R.string.abbreviation_tuesday)),
            FoodInsideFragment(resources.getString(R.string.abbreviation_wednesday)),
            FoodInsideFragment(resources.getString(R.string.abbreviation_thursday)),
            FoodInsideFragment(resources.getString(R.string.abbreviation_friday)),
            FoodInsideFragment(resources.getString(R.string.abbreviation_saturday)),
            FoodInsideFragment(resources.getString(R.string.abbreviation_sunday)),
        ), this)
        pager.adapter = vp2Adapter

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