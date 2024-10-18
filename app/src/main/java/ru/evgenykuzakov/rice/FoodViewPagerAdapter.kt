package ru.evgenykuzakov.rice

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class FoodViewPagerAdapter(private val fragments: List<Fragment>, fragment: Fragment):
    FragmentStateAdapter(fragment){
    override fun getItemCount(): Int {
        return fragments.size
    }
    override fun createFragment(position: Int): Fragment {
        return  fragments[position]
    }
}