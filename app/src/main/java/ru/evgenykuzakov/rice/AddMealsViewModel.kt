package ru.evgenykuzakov.rice

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddMealsViewModel(application: Application) : AndroidViewModel(application) {
    private val foodDatabase = FoodInfoDatabase.getFoodInfoDatabase(application)

    suspend fun findByName(name: String): List<FoodInfo> {
        return withContext(Dispatchers.IO) {
            foodDatabase!!.foodDao().findByName(name)
        }
    }
}

