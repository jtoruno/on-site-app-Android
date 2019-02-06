package com.zimplifica.siteappandroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.zimplifica.siteappandroid.utils.MyKeyboard

class Home : AppCompatActivity() {

    lateinit var amount : TextView
    lateinit var payButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        amount = findViewById(R.id.textView)
        val keyboard : MyKeyboard = findViewById(R.id.keyboard)
        amount.setRawInputType(InputType.TYPE_CLASS_TEXT)
        amount.setTextIsSelectable(true)
        keyboard.setTextView(amount)

        payButton = findViewById(R.id.pay_btn_home)
        payButton.setOnClickListener {
            val intent = Intent(this,PaymentRequest::class.java)
            startActivity(intent)
        }

    }
}
