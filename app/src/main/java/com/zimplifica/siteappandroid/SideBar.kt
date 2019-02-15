package com.zimplifica.siteappandroid

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator

class SideBar : AppCompatActivity() {

    lateinit var img : ImageView
    lateinit var imgButton : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_side_bar)
        img = findViewById(R.id.imageView9)
        imgButton = findViewById(R.id.imageView10)
        val drawable = TextDrawable.builder()
            .beginConfig()
            .width(100)
            .bold()
            .textColor(Color.BLACK)
            .height(100)
            .endConfig()
            .buildRound("FC", Color.WHITE)
        img.setImageDrawable(drawable)
        imgButton.setOnClickListener {
            finish()
        }
    }
}
