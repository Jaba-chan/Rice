package ru.evgenykuzakov.rice.bnvFragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.evgenykuzakov.rice.databases.DAO.FoodDAO
import ru.evgenykuzakov.rice.data.FoodInfo
import ru.evgenykuzakov.rice.databases.FoodInfoDatabase
import ru.evgenykuzakov.rice.R

class StatisticsFragment: Fragment(R.layout.statistics_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tv1: TextView = view.findViewById(R.id.tv1)

        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val db = FoodInfoDatabase.getFoodInfoDatabase(requireActivity())
            val dao: FoodDAO = db!!.foodDao()
            val users: List<FoodInfo> = dao.getAll()
            tv1.text = users[0].producer
        }
    }


}