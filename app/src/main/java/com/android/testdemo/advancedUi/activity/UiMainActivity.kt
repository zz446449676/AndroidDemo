package com.android.testdemo.advancedUi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.android.testdemo.R

class UiMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ui_main)
        val china_svg = findViewById<Button>(R.id.china_svg)

        china_svg.setOnClickListener {
            val intent = Intent(this, ChinaSVGActivity::class.java)
            startActivity(intent)
        }
    }
}