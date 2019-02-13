package com.zimplifica.siteappandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.pdf417.encoder.BarcodeMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import android.graphics.Bitmap
import android.graphics.Color
import android.opengl.ETC1.getWidth
import android.opengl.ETC1.getHeight
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.zxing.common.BitMatrix
import com.zimplifica.siteappandroid.Models.Bill


class PaymentBill : AppCompatActivity() {

    lateinit var img : ImageView
    lateinit var img2 : ImageView
    lateinit var cardView: CardView
    lateinit var qrImage : ImageView
    lateinit var idTxt : TextView
    lateinit var descriptionTxt : TextView
    lateinit var amountTxt : TextView
    lateinit var userTxt : TextView
    lateinit var dateTxt : TextView
    lateinit var hourTxt : TextView
    lateinit var finishBtn : Button
    lateinit var divider : View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_bill)
        img = findViewById(R.id.imageView6)
        img2 = findViewById(R.id.imageView5)
        qrImage = findViewById(R.id.qr_img_generated)
        descriptionTxt = findViewById(R.id.textView10)
        amountTxt = findViewById(R.id.textView11)
        idTxt = findViewById(R.id.textView9)
        cardView = findViewById(R.id.cardView2)
        userTxt = findViewById(R.id.textView12)
        divider = findViewById(R.id.divider3)
        dateTxt = findViewById(R.id.textView13)
        hourTxt = findViewById(R.id.textView14)
        finishBtn = findViewById(R.id.button)
        cardView.elevation = 5F
        img.elevation = 10F
        img2.elevation = 10F
        divider.elevation = 8F
        val billObject = this.intent.getSerializableExtra("bill") as Bill
        idTxt.text = Html.fromHtml("<b>ID: </b>"+ billObject.uuid.toString())
        descriptionTxt.text = billObject.enterprise
        amountTxt.text = billObject.amount
        userTxt.text = billObject.user
        dateTxt.text = billObject.date
        hourTxt.text = billObject.hour

        generateQR(billObject.uuid.toString())

        finishBtn.setOnClickListener {
            finish()
        }

    }

    fun generateQR(message : String){
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix = multiFormatWriter.encode(message,BarcodeFormat.QR_CODE, 200, 200)
            /*
            val width = bitMatrix.width
            val height = bitMatrix.height

            val pixels = IntArray(width * height)
            for (y in 0 until height) {
                for (x in 0 until width) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = R.color.colorAccent
                    }
                }
            }
            val bitmapGen = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            bitmapGen.setPixels(pixels, 0, width, 0,0, width, height)*/

            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)

            qrImage.setImageBitmap(bitmap)
        }
        catch (e : WriterException){
            e.printStackTrace()
        }
    }
}
