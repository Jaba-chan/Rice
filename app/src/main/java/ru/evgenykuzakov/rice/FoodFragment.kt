package ru.evgenykuzakov.rice

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.properties.Delegates

class FoodFragment : Fragment(R.layout.food_fragment) {
    private var currentDayOfWeek by Delegates.notNull<Int>()
    private lateinit var today: Date
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btChoseDate = view.findViewById<ImageButton>(R.id.btChoseDate)
        val pager = view.findViewById<ViewPager2>(R.id.vp2DaysOfTheWeek)
        val tabs = view.findViewById<TabLayout>(R.id.tlDaysOfTheWeek)
        val tvHeadingDate = view.findViewById<TextView>(R.id.tvHeadingDate)

        val model = ViewModelProvider(requireActivity())[FoodInsideViewModel::class.java]

        val calendar = Calendar.getInstance()
        today = calendar.time
        currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        var weekDates = getWeekDates(calendar)

        btChoseDate.setOnClickListener {
            showDateDialog { selectedDate ->
                currentDayOfWeek = selectedDate.get(Calendar.DAY_OF_WEEK)
                weekDates = getWeekDates(selectedDate)
                pager.adapter = initViewPager(weekDates)
                pager.setCurrentItem(currentDayOfWeek, false)
                setTabLayoutMediator(model, tabs, pager, weekDates)
                setOnDateHeading(tvHeadingDate, weekDates[currentDayOfWeek])
            }
        }

        pager.adapter = initViewPager(weekDates)
        pager.isUserInputEnabled = false
        pager.setCurrentItem(currentDayOfWeek, false)
        setOnDateHeading(tvHeadingDate, weekDates[currentDayOfWeek])
        setTabLayoutMediator(model, tabs, pager, weekDates)

        model.getWasChanged().observe(viewLifecycleOwner) {
            val pos = tabs.selectedTabPosition
            CoroutineScope(Dispatchers.IO).launch {
                val iconId = getTabLayoutIcon(model.getCalories(getStringDate(weekDates))[pos], weekDates[pos])
                withContext(Dispatchers.Main) {
                    tabs.getTabAt(pos)?.setIcon(iconId)
                }
            }
        }
        tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val pos = tab?.position
                setOnDateHeading(tvHeadingDate, weekDates[pos!!])
                CoroutineScope(Dispatchers.IO).launch {
                    val iconId = getTabLayoutIcon(model.getCalories(getStringDate(weekDates))[pos], weekDates[pos])
                    withContext(Dispatchers.Main) {
                        tabs.getTabAt(pos)?.setIcon(iconId)

                    }
                }
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val pos = tab?.position
                CoroutineScope(Dispatchers.IO).launch {
                    val iconId = getTabLayoutIcon(model.getCalories(getStringDate(weekDates))[pos!!], weekDates[pos])
                    withContext(Dispatchers.Main) {
                        tabs.getTabAt(pos)?.setIcon(iconId)
                    }
                }
            }
        })

    }

    private fun setTabLayoutMediator(model: FoodInsideViewModel, tabs: TabLayout,
                                     pager: ViewPager2, weekDates: List<Date>) {
        TabLayoutMediator(tabs, pager) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.abbreviation_monday)
                1 -> tab.text = resources.getString(R.string.abbreviation_tuesday)
                2 -> tab.text = resources.getString(R.string.abbreviation_wednesday)
                3 -> tab.text = resources.getString(R.string.abbreviation_thursday)
                4 -> tab.text = resources.getString(R.string.abbreviation_friday)
                5 -> tab.text = resources.getString(R.string.abbreviation_saturday)
                6 -> tab.text = resources.getString(R.string.abbreviation_sunday)
            }
            CoroutineScope(Dispatchers.IO).launch {
                val iconId = getTabLayoutIcon(model.getCalories(getStringDate(weekDates))[position], weekDates[position])
                withContext(Dispatchers.Main) {
                    tab.setIcon(iconId)
                }
            }

        }.attach()
    }


    private fun showDateDialog(onDateSelected: (Calendar) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog =
            DatePickerDialog(requireActivity(), { _, selectedYear, selectedMonth, selectedDay ->
                calendar.set(Calendar.YEAR, selectedYear)
                calendar.set(Calendar.MONTH, selectedMonth)
                calendar.set(Calendar.DAY_OF_MONTH, selectedDay)

                onDateSelected(calendar)
            }, year, month, day)

        datePickerDialog.show()
    }

    private fun getWeekDates(calendar: Calendar): List<Date> {
        if (currentDayOfWeek == 1) {
            currentDayOfWeek = 6
        } else {
            currentDayOfWeek -= 2
        }
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, -1)
        }

        val weekDates = mutableListOf<Date>()
        for (i in 0..6) {
            weekDates.add(calendar.time)
            calendar.add(Calendar.DAY_OF_WEEK, 1)
        }
        return weekDates
    }

    private fun initViewPager(weekDates: List<Date>): FoodViewPagerAdapter {
        val vp2Adapter = FoodViewPagerAdapter(
            listOf(
                FoodInsideFragment(weekDates[0]),
                FoodInsideFragment(weekDates[1]),
                FoodInsideFragment(weekDates[2]),
                FoodInsideFragment(weekDates[3]),
                FoodInsideFragment(weekDates[4]),
                FoodInsideFragment(weekDates[5]),
                FoodInsideFragment(weekDates[6]),
            ), this
        )
        return vp2Adapter
    }

    private fun getStringDate(weekDates: List<Date>): List<String> {
        val stringDates: MutableList<String> = mutableListOf()
        for (i in weekDates) {
            stringDates.add(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(i))
        }
        return stringDates
    }

    private fun getTabLayoutIcon(calories: Int, date: Date): Int {
        return when {
            compareDatesByDayMonthYear(date, today) -> R.drawable.today_circle_icon
            date.after(today) -> R.drawable.empty_circle_icon
            calories == 0 && date.before(today) -> R.drawable.x_circle_icon
            calories != 0 && date.before(today) -> R.drawable.check_circle_icon
            else -> -1
        }
    }

    private fun compareDatesByDayMonthYear(date1: Date, date2: Date): Boolean {
        val calendar1 = Calendar.getInstance().apply { time = date1 }
        val calendar2 = Calendar.getInstance().apply { time = date2 }
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
    }

    private fun setOnDateHeading(tv: TextView, date: Date){
        tv.text = formatDateToHeadingText(date)
    }

    private fun formatDateToHeadingText(date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date

        val dayOfWeekFormatter = SimpleDateFormat("EEEE", Locale.getDefault())
        val dayOfWeek = dayOfWeekFormatter.format(date).replaceFirstChar { it.uppercase() }

        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val monthFormatter = SimpleDateFormat("MMMM", Locale.getDefault())
        val month = monthFormatter.format(date).replaceFirstChar { it.uppercase() }

        return "$dayOfWeek, $dayOfMonth $month"
    }

}