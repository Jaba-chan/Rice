package ru.evgenykuzakov.rice

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodInsideViewModel(application: Application) : AndroidViewModel(application) {
    private val mealsDatabase = MealsDatabase.getAppDatabase(application)
    private var wasChanged: MutableLiveData<Boolean> = MutableLiveData(false)
    fun getWasChanged(): LiveData<Boolean>{
        return wasChanged
    }
    fun setWasChanged(value: Boolean){
        wasChanged.postValue(value)
    }
    suspend fun getCalories(week: List<String>): List<Int>{
        return  withContext(Dispatchers.IO)
        {
            val calories: MutableList<Int> = mutableListOf()
            for (i in week) {
                calories.add(mealsDatabase!!.mealsDao().getCalories(i))
            }
            calories
        }
    }
}

