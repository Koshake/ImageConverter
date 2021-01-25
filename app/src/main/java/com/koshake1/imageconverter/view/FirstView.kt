package com.koshake1.imageconverter.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface FirstView : MvpView {
    fun setImage(path: String)
    fun showInfo(info: String)
}