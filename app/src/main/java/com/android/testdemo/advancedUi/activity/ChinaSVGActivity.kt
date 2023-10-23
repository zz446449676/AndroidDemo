package com.android.testdemo.advancedUi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.testdemo.R

class ChinaSVGActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_china_svgactivity)
        title = "中国地图"
    }
}