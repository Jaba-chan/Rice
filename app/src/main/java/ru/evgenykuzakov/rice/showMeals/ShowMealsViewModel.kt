package ru.evgenykuzakov.rice.showMeals

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.evgenykuzakov.rice.R
import ru.evgenykuzakov.rice.data.DatabaseNamesEnum
import ru.evgenykuzakov.rice.data.Heading
import ru.evgenykuzakov.rice.data.MealsTest
import ru.evgenykuzakov.rice.data.Nutrients
import ru.evgenykuzakov.rice.databases.MealsDatabase

class ShowMealsViewModel(val date: String, application: Application) : AndroidViewModel(application) {
    class ShowMealsViewModelFactory(
        private val date: String,
        private val application: Application
    ) : ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ShowMealsViewModel::class.java)) {
                return ShowMealsViewModel(date, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
    private val mealsDatabase = MealsDatabase.getAppDatabase(application)
    private val meals: MutableLiveData<MutableList<Any>?> = MutableLiveData()
    private var nutrients: MutableLiveData<Nutrients?> = MutableLiveData()
    private val _bannedPos = MutableLiveData<MutableList<Int>>(mutableListOf())
    val bannedPos: LiveData<MutableList<Int>> get() = _bannedPos

    fun addBannedPos(item: Int) {
        val currentList = _bannedPos.value ?: mutableListOf()
        currentList.add(item)
        _bannedPos.value = currentList
    }

    fun removeBannedPos(item: Int) {
        val currentList = _bannedPos.value ?: mutableListOf()
        currentList.remove(item)
        _bannedPos.value = currentList
    }
    var breakfastHeading = Heading(
            application.resources.getStringArray(R.array.meals)[0],
            DatabaseNamesEnum.BREAKFAST_DATABASE
    )
    var lunchHeading = Heading(
            application.resources.getStringArray(R.array.meals)[1],
            DatabaseNamesEnum.LUNCH_DATABASE
    )
    var dinnerHeading = Heading(
            application.resources.getStringArray(R.array.meals)[2],
            DatabaseNamesEnum.DINNER_DATABASE
    )
    var extraMealsHeading = Heading(
            application.resources.getStringArray(R.array.meals)[3],
            DatabaseNamesEnum.EXTRA_MEALS_DATABASE
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
    fun getNutrients(): LiveData<Nutrients?>{
        return nutrients
    }



    fun refreshList() {
        viewModelScope.launch {
            meals.value = (listOf(breakfastHeading) + getBreakfastMeals()
                        + listOf(lunchHeading) + getLunchMeals()
                        + listOf(dinnerHeading) + getDinnerMeals()
                        + listOf(extraMealsHeading) + getExtraMeals())
                        .toMutableList()
        }
    }

    fun remove(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            mealsDatabase!!.mealsDao().delete(id)
        }
    }

    fun insert(meal: MealsTest){
        viewModelScope.launch {
            mealsDatabase!!.mealsDao().insert(meal)
        }
    }

    fun update(meals: MutableList<Any>?) {
        viewModelScope.launch {
            var i = 0
            var j = 1
            var headind = DatabaseNamesEnum.LUNCH_DATABASE
            while (i < (meals?.size ?: 0)) {
                val item = meals?.get(i)
                if (item is Heading) {
                    headind = item.dbName
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