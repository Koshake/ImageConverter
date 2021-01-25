package com.koshake1.imageconverter

import android.app.Application
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class App : Application() {
    private val cicerone: Cicerone<Router> by lazy {
        Cicerone.create()
    }

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    val router
        get() = cicerone.router

    val navigatorHolder
        get() = cicerone.navigatorHolder
}