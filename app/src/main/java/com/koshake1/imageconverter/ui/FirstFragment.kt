package com.koshake1.imageconverter.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.koshake1.imageconverter.R
import com.koshake1.imageconverter.model.ImageModel
import com.koshake1.imageconverter.presenter.FirstPresenter
import com.koshake1.imageconverter.view.FirstView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_first.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


const val GALLERY_REQUEST = 1
const val TAG = "Tag"
const val REQUEST_CODE_READ = 66

class FirstFragment : MvpAppCompatFragment(), FirstView {

    companion object {
        fun newInstance() = FirstFragment()
    }

    private val presenter by moxyPresenter {
        FirstPresenter(this, ImageModel(), AndroidSchedulers.mainThread())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = View.inflate(context, R.layout.fragment_first, null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestPermissions(
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_CODE_READ
        )

        buttonOpen.setOnClickListener(View.OnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        })

        buttonConvert.setOnClickListener { presenter.convertJpgToPng() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GALLERY_REQUEST -> {
                presenter.imageSelected(data?.data.toString())
            }
        }
    }

    override fun setImage(path: String) {
        Log.d(TAG, " Image " + Uri.parse(path))
        image.setImageURI(Uri.parse(path))
    }

    override fun showInfo(info: String) {
        Toast.makeText(context, info, Toast.LENGTH_LONG).show()
    }
}