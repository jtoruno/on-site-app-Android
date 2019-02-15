package com.zimplifica.siteappandroid

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.zimplifica.siteappandroid.Models.Bill

class RequestBill : AppCompatActivity() {

    lateinit var userImg : ImageView
    lateinit var userTtxt : TextView
    lateinit var amountTxt : TextView
    lateinit var descriptionTxt : TextView
    lateinit var dateTxt : TextView
    lateinit var hourTxt : TextView
    lateinit var statusTxt : TextView
    lateinit var backBtn : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_bill)
        backBtn = findViewById(R.id.button2)
        userImg = findViewById(R.id.imageView8)
        userTtxt = findViewById(R.id.textView)
        amountTxt = findViewById(R.id.textView19)
        descriptionTxt = findViewById(R.id.textView21)
        dateTxt = findViewById(R.id.textView22)
        hourTxt = findViewById(R.id.textView23)
        statusTxt = findViewById(R.id.textView24)
        val bill = this.intent.getSerializableExtra("bill") as Bill
        backBtn.setOnClickListener {
            finish()
        }
        userTtxt.text = bill.enterprise
        amountTxt.text = bill.amount
        descriptionTxt.text = bill.description
        dateTxt.text = bill.date
        hourTxt.text = bill.hour
        thumbnailImg(bill.enterprise)
    }

    private fun thumbnailImg(name : String){
        val generator = ColorGenerator.MATERIAL
        val initials = name
            .split(' ')
            .mapNotNull { it.firstOrNull()?.toString() }
            .reduce { acc, s -> acc + s }
        val drawable = TextDrawable.builder()
            .beginConfig()
            .width(100)
            .height(100)
            .endConfig()
            .buildRound(initials,generator.getColor(initials))
        userImg.setImageDrawable(drawable)
    }
}
