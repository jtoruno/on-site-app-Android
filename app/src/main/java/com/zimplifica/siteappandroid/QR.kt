package com.zimplifica.siteappandroid

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.zxing.Result
import com.zimplifica.siteappandroid.utils.ContactsAdapter
import me.dm7.barcodescanner.zxing.ZXingScannerView

class QR : AppCompatActivity(), ZXingScannerView.ResultHandler {
    lateinit var ScannerVIew : ZXingScannerView
    private val PERMISSIONS_REQUEST_CAMERA = 100
    override fun handleResult(p0: Result?) {
        Log.e("QR", p0.toString())
        if (p0!=null){
            Log.e("QR", p0.text)
            val qrResult = p0.text
            val returnIntent = Intent()
            returnIntent.putExtra("qr",qrResult )
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
        else{
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)
        request()
    }

    fun request () {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CAMERA),
                    PERMISSIONS_REQUEST_CAMERA)
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }else{
            ScannerVIew = ZXingScannerView(this)
            setContentView(ScannerVIew)
            ScannerVIew.setResultHandler(this)
            ScannerVIew.startCamera()
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST_CAMERA -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    ScannerVIew = ZXingScannerView(this)
                    setContentView(ScannerVIew)
                    ScannerVIew.setResultHandler(this)
                    ScannerVIew.startCamera()
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.



                } else {
                    Toast.makeText(this,"No se dispone de permisos", Toast.LENGTH_LONG).show()
                    finish()
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }
    }

}
