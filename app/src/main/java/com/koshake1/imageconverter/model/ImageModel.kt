package com.koshake1.imageconverter.model

import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.net.toUri
import com.koshake1.imageconverter.App
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception
import java.util.*

const val TAG = "log"

class ImageModel {
    companion object {
        const val PNG_FILE = "converted.png"
        const val QUALITY = 50
    }

    var imageToConvertPath: String? = null

    fun getFilePath(): Observable<File> {
        var file: File? = null
        val state = Environment.getExternalStorageState()
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            try {
                file =
                    App.instance.applicationContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        } else if (state.equals(Environment.MEDIA_SHARED)) {
            try {
                file = App.instance.filesDir
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
        val path = file?.let { File(file, "/Task") }
        path?.mkdirs()
        Log.d(TAG, "Create $path");
        return Observable.just(path)
    }

    fun convertJpgToPng(file: File) = Completable.create { emitter ->
        convertToPng(file).let {
            if (it) {
                emitter.onComplete()
            } else {
                emitter.onError(RuntimeException("Error while converting to Png"))
                return@create
            }
        }
    }

    private fun convertToPng(file: File): Boolean {
        var out: OutputStream? = null
        val toConvert = File(convertUriToPath(imageToConvertPath))
        val bitMap = BitmapFactory.decodeFile(toConvert.absolutePath)
        val result = File(file, PNG_FILE)
        if (!result.exists()) {
            result.createNewFile()
        }
        return try {
            out = FileOutputStream(result)
            bitMap.compress(Bitmap.CompressFormat.PNG, QUALITY, out)
            out.flush()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            out?.close()
        }
    }

    private fun convertUriToPath(path: String?): String? {
        var cursor: Cursor? = null
        return try {
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            cursor = path?.let {
                App.instance.applicationContext?.contentResolver?.query(
                    it?.toUri(),
                    projection,
                    null,
                    null,
                    null
                )
            }
            val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor?.moveToFirst()
            columnIndex?.let { cursor?.getString(it) }
        } finally {
            cursor?.close()
        }
    }
}
