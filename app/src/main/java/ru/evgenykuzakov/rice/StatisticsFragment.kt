package ru.evgenykuzakov.rice

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.room.Database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StatisticsFragment: Fragment(R.layout.statistics_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tv1: TextView = view.findViewById(R.id.tv1)

        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val db = AppDatabase.getAppDatabase(requireActivity())
            val dao: FoodDAO = db!!.foodDao()
            dao.insertAll(FoodInfo(2, "ddffd", "dff", "dfd", "dffdsaaf",
                3, "fdf", 100, 3, 3.0,4.0, 4.6))
            val users: List<FoodInfo> = dao.getAll()
            tv1.text = users.get(0).producer
        }
    }


}