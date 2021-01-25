package com.koshake1.imageconverter.navigation

import com.koshake1.imageconverter.ui.FirstFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class FirstScreen() : SupportAppScreen() {
        override fun getFragment() = FirstFragment.newInstance()
    }
}