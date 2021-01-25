package com.koshake1.imageconverter.presenter

import com.koshake1.imageconverter.navigation.Screens
import com.koshake1.imageconverter.view.MainView
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

class MainPresenter(private val router: Router) : MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(Screens.FirstScreen())
    }
}