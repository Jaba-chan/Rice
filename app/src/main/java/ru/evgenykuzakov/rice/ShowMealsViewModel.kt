package ru.evgenykuzakov.rice

import android.app.Application
import android.util.Log
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
            application.resources.getStringArray(R.array.meals)[0],
            DatabaseNamesEnum.BREAKFAST_DATABASE
        )
    )
    private val lunchHeading = listOf(
        Heading(
            application.resources.getStringArray(R.array.meals)[1],
            DatabaseNamesEnum.LUNCH_DATABASE
        )
    )
    private val dinnerHeading = listOf(
        Heading(
            application.resources.getStringArray(R.array.meals)[2],
            DatabaseNamesEnum.DINNER_DATABASE
        )
    )
    private val extraMealsHeading = listOf(
        Heading(
            application.resources.getStringArray(R.array.meals)[3],
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
            mealsDatabase!!.mealsDao().getLunchData(date = date)
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

    suspend fun getBreakfastLastPosition(): Int {
        return withContext(Dispatchers.IO) {
            mealsDatabase!!.mealsDao().getBreakfastLastPosition(date = date)
        }
    }

    suspend fun getLunchLastPosition(): Int {
        return withContext(Dispatchers.IO) {
            mealsDatabase!!.mealsDao().getLunchLastPosition(date = date)
        }
    }

    suspend fun getDinnerLastPosition(): Int {
        return withContext(Dispatchers.IO) {
            mealsDatabase!!.mealsDao().getDinnerLastPosition(date = date)
        }
    }

    suspend fun getExtraLastPosition(): Int {
        return withContext(Dispatchers.IO) {
            mealsDatabase!!.mealsDao().getExtraMealsLastPosition(date = date)
        }
    }

    fun getMeals(): LiveData<MutableList<Any>?> {
        return meals
    }
    fun setDate(date: String){
        this.date = date
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
        }
    }

    fun insert(meal: MealsTest){
        CoroutineScope(Dispatchers.IO).launch {
            mealsDatabase!!.mealsDao().insert(meal)
        }
    }

    suspend fun getNutrients(date: String): Int{
        return withContext(Dispatchers.IO) {
            mealsDatabase!!.mealsDao().getNutrients(date)
        }
    }

    fun update(meals: MutableList<Any>?) {
        CoroutineScope(Dispatchers.IO).launch {
            var i = 0
            var j = 1
            var headind = DatabaseNamesEnum.LUNCH_DATABASE
            while (i < (meals?.size ?: 0)) {
                val item = meals?.get(i)
                if (item is Heading) {
                    headind = item.DB_NAme
                    Log.e("dfffff", item.DB_NAme.toString())
                    j = 1
                }
                if (item is MealsTest) {
                    mealsDatabase!!.mealsDao().updateItem(item.copy(position = j, database = headind))
                    j++
                }
                i++

            }
        }
    }

}

