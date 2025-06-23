package ru.evgenykuzakov.rice

import android.app.Application
import org.koin.android.ext.koin.androidContext
import ru.evgenykuzakov.rice.di.initKoin

class RiceApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin{
            androidContext(this@RiceApp)
        }
    }
}