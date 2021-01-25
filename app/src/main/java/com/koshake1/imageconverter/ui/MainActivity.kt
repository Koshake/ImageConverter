package com.koshake1.imageconverter.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.koshake1.imageconverter.App
import com.koshake1.imageconverter.R
import com.koshake1.imageconverter.presenter.MainPresenter
import com.koshake1.imageconverter.view.MainView
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainActivity : MvpAppCompatActivity(), MainView {
    private val navigatorHolder = App.instance.navigatorHolder
    private val navigator = SupportAppNavigator(this, supportFragmentManager, R.id.container)

    private val presenter by moxyPresenter {
        MainPresenter(App.instance.router)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}