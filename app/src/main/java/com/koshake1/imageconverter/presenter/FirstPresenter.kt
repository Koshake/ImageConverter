package com.koshake1.imageconverter.presenter

import android.util.Log
import com.koshake1.imageconverter.model.ImageModel
import com.koshake1.imageconverter.ui.TAG
import com.koshake1.imageconverter.view.FirstView
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter


class FirstPresenter(private val view: FirstView,
                     private val model: ImageModel,
                     private val mainScheduler: Scheduler
) :
    MvpPresenter<FirstView>() {

    fun imageSelected(path: String) {
        viewState.setImage(path)
        model.imageToConvertPath = path
    }

    fun convertJpgToPng() {
        Log.d(TAG, "Presenter convert")
        model.getFilePath()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .subscribe({
                model.convertJpgToPng(it)
                    .observeOn(mainScheduler)
                    .subscribe(
                        { view.showInfo("Converted to Png and saved to ${it.absolutePath}!") },
                        { it.message?.let { it1 -> view.showInfo(it1) } }
                    )
            },
                { it.message?.let { it1 -> view.showInfo(it1) } })
    }
}