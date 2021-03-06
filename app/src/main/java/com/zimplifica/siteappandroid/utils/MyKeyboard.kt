package com.zimplifica.siteappandroid.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.inputmethod.InputConnection
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.util.SparseArray
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.zimplifica.siteappandroid.R
import org.fabiomsr.moneytextview.MoneyTextView


class MyKeyboard @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {

    // keyboard keys (buttons)
    private var mButton1: Button? = null
    private var mButton2: Button? = null
    private var mButton3: Button? = null
    private var mButton4: Button? = null
    private var mButton5: Button? = null
    private var mButton6: Button? = null
    private var mButton7: Button? = null
    private var mButton8: Button? = null
    private var mButton9: Button? = null
    private var mButton0: Button? = null
    private var mButtonDelete: ImageButton? = null
    private var mButtonEnter: Button? = null

    // This will map the button resource id to the String value that we want to
    // input when that button is clicked.
    private var keyValues = SparseArray<String>()

    // Our communication link to the EditText
    private var inputConnection: InputConnection? = null

    private var textView : TextView? = null

    private var moneytext : MoneyTextView? = null

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {

        // initialize buttons
        LayoutInflater.from(context).inflate(R.layout.keyboard, this, true)
        mButton1 = findViewById(R.id.button_1)
        mButton2 = findViewById(R.id.button_2)
        mButton3 = findViewById(R.id.button_3)
        mButton4 = findViewById(R.id.button_4)
        mButton5 = findViewById(R.id.button_5)
        mButton6 = findViewById(R.id.button_6)
        mButton7 = findViewById(R.id.button_7)
        mButton8 = findViewById(R.id.button_8)
        mButton9 = findViewById(R.id.button_9)
        mButton0 = findViewById(R.id.button_0)
        mButtonDelete = findViewById(R.id.button_delete)
        mButtonEnter = findViewById(R.id.button_enter)

        // set button click listeners
        mButton1!!.setOnClickListener(this)
        mButton2!!.setOnClickListener(this)
        mButton3!!.setOnClickListener(this)
        mButton4!!.setOnClickListener(this)
        mButton5!!.setOnClickListener(this)
        mButton6!!.setOnClickListener(this)
        mButton7!!.setOnClickListener(this)
        mButton8!!.setOnClickListener(this)
        mButton9!!.setOnClickListener(this)
        mButton0!!.setOnClickListener(this)
        mButtonDelete!!.setOnClickListener(this)
        //mButtonEnter!!.setOnClickListener(this)

        // map buttons IDs to input strings
        keyValues.put(R.id.button_1, "1")
        keyValues.put(R.id.button_2, "2")
        keyValues.put(R.id.button_3, "3")
        keyValues.put(R.id.button_4, "4")
        keyValues.put(R.id.button_5, "5")
        keyValues.put(R.id.button_6, "6")
        keyValues.put(R.id.button_7, "7")
        keyValues.put(R.id.button_8, "8")
        keyValues.put(R.id.button_9, "9")
        keyValues.put(R.id.button_0, "0")
        //keyValues.put(R.id.button_enter, "\n")
    }

    override fun onClick(v: View) {
        
        /*
        // do nothing if the InputConnection has not been set yet
        if (inputConnection == null) return

        // Delete text or input key value
        // All communication goes through the InputConnection
        if (v.id === R.id.button_delete) {
            val selectedText = inputConnection!!.getSelectedText(0)
            if (TextUtils.isEmpty(selectedText)) {
                // no selection, so delete previous character
                inputConnection!!.deleteSurroundingText(1, 0)
            } else {
                // delete the selection
                inputConnection!!.commitText("", 1)
            }
        } else {
            val value = keyValues.get(v.getId())
            inputConnection!!.commitText(value, 1)
        }*/
        /*
        val vibratorService =  context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            vibratorService.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
        }else{
            vibratorService.vibrate(100)
        }*/

        val scale = AnimationUtils.loadAnimation(context, R.anim.btn_anim)
        v.startAnimation(scale)

        val default = "₡0"
        /*
        if(textView == null) return
        var ss = textView?.text ?: ""
        if(v.id === R.id.button_delete){
            if(ss.toString()!=default){
                if(ss.toString().length == 2){
                    textView?.text = default
                }
                else{
                    val newVal = ss.subSequence(0, ss.length-1)
                    textView?.text = newVal
                }
            }
        }
        else{
            if (ss.toString()==default){
                textView?.text = "₡" + keyValues.get(v.id)
            }else{
                val value = keyValues.get(v.id)
                val newVal = ss.toString() + value
                textView?.text = newVal
            }
        }*/
        val vibratorService =  context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (moneytext == null) return
        var float = moneytext?.amount!!.toInt()
        val string = float.toString()
        if(v.id === R.id.button_delete){
            if (string.length == 1){
                moneytext?.amount = 0F
            }
            else{
                val newVal = string.subSequence(0, string.length-1)
                moneytext?.amount = newVal.toString().toFloat()
            }
        }
        else{
            if(string.length==6){
                vibratorService.vibrate(80)
                return
            }
            if(float == 0){
                moneytext?.amount = keyValues.get(v.id).toFloat()
            }
            else{
                val value = string + keyValues.get(v.id)
                moneytext?.amount = value.toFloat()
            }
        }


    }

    // The activity (or some parent or controller) must give us
    // a reference to the current EditText's InputConnection
    fun setInputConnection(ic: InputConnection) {
        this.inputConnection = ic
    }

    fun setTextView(txt : TextView){
        this.textView = txt
    }

    fun setMoneyTextView(item : MoneyTextView){
        this.moneytext = item
    }
}// constructors