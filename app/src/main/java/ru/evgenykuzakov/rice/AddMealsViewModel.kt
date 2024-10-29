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
import java.util.Date

class AddMealsViewModel(application: Application) : AndroidViewModel(application) {
    private val foodDatabase = FoodInfoDatabase.getFoodInfoDatabase(application)

    suspend fun findByName(name: String): List<FoodInfo>?{
        return withContext(Dispatchers.IO) {
            foodDatabase!!.foodDao().findByName(name)
        }
    }
}

