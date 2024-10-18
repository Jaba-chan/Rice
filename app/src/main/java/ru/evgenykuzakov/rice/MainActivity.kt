package ru.evgenykuzakov.rice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bnmMain: BottomNavigationView = findViewById(R.id.bnmMain)
        val navHostFragment =supportFragmentManager
            .findFragmentById(R.id.fcvMain) as NavHostFragment
        val navController = navHostFragment.navController

        bnmMain.setSelectedItemId(R.id.menuSportsmenStatistic)
        bnmMain.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menuPersonalSheet -> {
                    navController.navigate(R.id.personFragment)
                    true
                }
                R.id.menuSportsmenStatistic -> {
                    navController.navigate(R.id.statisticsFragment)
                    true
                }
                R.id.menuFood -> {
                    navController.navigate(R.id.foodFragment)
                    true
                }
                R.id.menuTraining -> {
                    navController.navigate(R.id.trainingFragment)
                    true
                }
                else -> false
            }
        }
    }
}