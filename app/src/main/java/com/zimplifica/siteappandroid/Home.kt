package com.zimplifica.siteappandroid

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.zimplifica.siteappandroid.Models.Bill
import com.zimplifica.siteappandroid.utils.MyKeyboard
import java.io.Serializable
import java.util.*

class Home : AppCompatActivity() {

    lateinit var amount : TextView
    lateinit var payButton : Button
    lateinit var activityBtn : ImageButton
    private val default = "₡0"
    private var activityList = mutableListOf<Bill>()
    private val PAYMENT_REQUEST = 234

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        amount = findViewById(R.id.textView)
        val keyboard : MyKeyboard = findViewById(R.id.keyboard)
        activityBtn = findViewById(R.id.activity_home_btn)
        amount.setRawInputType(InputType.TYPE_CLASS_TEXT)
        amount.setTextIsSelectable(true)
        keyboard.setTextView(amount)

        payButton = findViewById(R.id.pay_btn_home)
        payButton.setOnClickListener {
            if(amount.text.toString() == default){
                val view = findViewById<View>(android.R.id.content)
                val snackBar = Snackbar.make(view,"Ingrese un Monto válido", Snackbar.LENGTH_LONG)
                snackBar.view.setOnClickListener {
                    snackBar.dismiss()
                }
                snackBar.show()
            }
            else{
                val value = amount.text.toString()
                val extra = value.substring(1)
                val intent = Intent(this,PaymentRequest::class.java)
                intent.putExtra("amount", extra)
                startActivityForResult(intent,PAYMENT_REQUEST)
            }

        }
        activityBtn.setOnClickListener {
            val intent = Intent(this, ActivityScreen::class.java)
            intent.putExtra("activityList", activityList as Serializable)
            startActivity(intent)
        }

        activityList.add( Bill(
            UUID.randomUUID(),"McDonalds La Tropicana",
            "Combo 3. Combo Agrandado. Papas Ext...","₡ 2.550,00","Francisco Córdoba Rojas",
            " 29/01/2019", "7:30pm"))
        activityList.add( Bill(
            UUID.randomUUID(),"Feria Del Agricultor",
            "","₡ 5.850,00","Francisco Córdoba Rojas",
            " 20/01/2019", "9:45pm"))
        activityList.add( Bill(
            UUID.randomUUID(),"Repuestos La Guacamaya",
            "","₡ 16.000,00","Francisco Córdoba Rojas",
            " 20/01/2019", "12:30pm"))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PAYMENT_REQUEST){
            if(resultCode == Activity.RESULT_OK){
                val result = data?.getSerializableExtra("bill") as Bill
                activityList.add(0,result)            }
        }
    }
}
