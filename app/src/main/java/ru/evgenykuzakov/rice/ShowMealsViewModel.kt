package ru.evgenykuzakov.rice

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowMealsViewModel(application: Application) : AndroidViewModel(application) {
    private val mealsDatabase = MealsDatabase.getAppDatabase(application)
    private var meals: MutableLiveData<MutableList<Any>?> = MutableLiveData()
    private lateinit var date: String

    private val breakfastHeading = listOf(
        Heading(
            application.resources.getString(R.string.breakfast),
            DatabaseNamesEnum.BREAKFAST_DATABASE
        )
    )
    private val lunchHeading = listOf(
        Heading(
            application.resources.getString(R.string.lunch),
            DatabaseNamesEnum.LUNCH_DATABASE
        )
    )
    private val dinnerHeading = listOf(
        Heading(
            application.resources.getString(R.string.dinner),
            DatabaseNamesEnum.DINNER_DATABASE
        )
    )
    private val extraMealsHeading = listOf(
        Heading(
            application.resources.getString(R.string.extra_meals),
            DatabaseNamesEnum.EXTRA_MEALS_DATABASE
        )
    )

    private suspend fun getBreakfastMeals(): List<MealsTest> {
        return withContext(Dispatchers.IO) {
            mealsDatabase!!.mealsDao().getBreakfastData(date = date)
        }
    }

    private suspend fun getLunchMeals(): List<MealsTest> {
        return withContext(Dispatchers.IO) {
            mealsDatabase!!.mealsDao().getLunch(date = date)
        }
    }

    private suspend fun getDinnerMeals(): List<MealsTest> {
        return withContext(Dispatchers.IO) {
            mealsDatabase!!.mealsDao().getDinnerData(date = date)
        }
    }

    private suspend fun getExtraMeals(): List<MealsTest> {
        return withContext(Dispatchers.IO) {
            mealsDatabase!!.mealsDao().getExtraMealsData(date = date)
        }
    }

    fun getMeals(): LiveData<MutableList<Any>?> {
        return meals
    }
    fun setDate(date: String){
        this.date = date
    }

    private fun init() {
        mealsDatabase!!.mealsDao().insert(
            MealsTest(0, "Молоко", 100.0, DatabaseNamesEnum.BREAKFAST_DATABASE, 2, "2024-10-26"),
            MealsTest(0, "Хлеб", 10.0, DatabaseNamesEnum.BREAKFAST_DATABASE, 3, "2024-10-26"),
            MealsTest(0, "Яйца", 50.0, DatabaseNamesEnum.BREAKFAST_DATABASE, 4, "2024-10-25"),
            MealsTest(0, "Резиновый хуй", 100.0, DatabaseNamesEnum.LUNCH_DATABASE, 6, "2024-10-26"),
            MealsTest(0, "!!!!", 10.0, DatabaseNamesEnum.LUNCH_DATABASE, 7, "2024-10-26"),
            MealsTest(0, "?????", 50.0, DatabaseNamesEnum.LUNCH_DATABASE, 8, "2024-10-25"),
            MealsTest(0, "qqqqq", 100.0, DatabaseNamesEnum.DINNER_DATABASE, 10, "2024-10-26"),
            MealsTest(0, "fffff", 10.0, DatabaseNamesEnum.DINNER_DATABASE, 11, "2024-10-26"),
            MealsTest(0, "dddddd", 50.0, DatabaseNamesEnum.DINNER_DATABASE, 12, "2024-10-23"),
            MealsTest(0, "1111", 100.0, DatabaseNamesEnum.EXTRA_MEALS_DATABASE, 14, "2024-10-23"),
            MealsTest(0, "222", 10.0, DatabaseNamesEnum.EXTRA_MEALS_DATABASE, 15, "2024-10-26"),
            MealsTest(0, "4444", 50.0, DatabaseNamesEnum.EXTRA_MEALS_DATABASE, 16, "2024-10-26")
        )
    }

    fun refreshList() {
        CoroutineScope(Dispatchers.IO).launch {
            meals.postValue(
                (breakfastHeading + getBreakfastMeals()
                        + lunchHeading + getLunchMeals()
                        + dinnerHeading + getDinnerMeals()
                        + extraMealsHeading + getExtraMeals()).toMutableList()
            )
        }
    }

    fun remove(id: Int, meals: MutableList<Any>?){
        CoroutineScope(Dispatchers.IO).launch {
            mealsDatabase!!.mealsDao().delete(id)
            var i = 0
            while (i < (meals?.size ?: 0)) {
                val item = meals?.get(i)
                if (item is MealsTest) {
                    mealsDatabase.mealsDao().updateItem(item.copy(position = i + 1))
                }
                i++
            }
        }
    }

    fun update(meals: MutableList<Any>?) {
        CoroutineScope(Dispatchers.IO).launch {
            var i = 0
            var headind = DatabaseNamesEnum.BREAKFAST_DATABASE
            while (i < (meals?.size ?: 0)) {
                val item = meals?.get(i)
                if (item is Heading) {
                    headind = item.DB_NAme
                }
                if (item is MealsTest) {
                    mealsDatabase!!.mealsDao().updateItem(item.copy(position = i + 1, database = headind))
                }
                i++
            }
        }
    }

}

