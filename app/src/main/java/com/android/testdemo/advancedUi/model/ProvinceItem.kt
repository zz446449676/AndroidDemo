package com.android.testdemo.advancedUi.model

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Region

open class ProvinceItem(val path: android.graphics.Path) {
    // item颜色
    private var _drawColor: Int = 0
    // 公共的getter方法
    val drawColor: Int
        get() = _drawColor
    // 公共的setter方法
    fun setDrawColor(value: Int) {
        _drawColor = value
    }

    // 省份名字
    private var _provinceName: String = ""
    val provinceName: String
        get() = _provinceName
    fun setProvinceName(value: String) {
        _provinceName = value
    }

    // 是否已经选中
    private var _isSelected: Boolean = false
    val isSelected: Boolean
        get() = _isSelected
    fun setIsSelected(value: Boolean) {
        _isSelected = value
    }

    // 绘制Item
    fun drawItem(canvas: Canvas, paint: Paint) {
        if (isSelected) {
            // 选中时, 填充
            paint.apply {
                // 填充
                clearShadowLayer()
                strokeWidth = 1F
                style = Paint.Style.FILL
                color = Color.parseColor("#FF445F")
            }
            canvas.drawPath(path, paint)
        } else {
            paint.apply {
                strokeWidth = 2F
                color = Color.BLACK
                style = Paint.Style.FILL
                setShadowLayer(8F, 0F, 0F, -0x1)
            }
            canvas.drawPath(path, paint)

            paint.apply {
                clearShadowLayer()
                color = drawColor
                style = Paint.Style.FILL
                strokeWidth = 2F
            }
            canvas.drawPath(path, paint)
        }
    }

    // 根据点击屏幕的坐标 x,y 判断是否为当前的ProvinceItem
    fun isTouch(x: Float, y: Float) : Boolean {
        // 创建一个矩形对象，用于存储路径的边界信息。
        val rectF = RectF()
        // 计算路径的边界信息，并将结果存储到矩形对象中。第二个参数表示是否包括控制点，这里设置为`true`表示包括控制点。
        path.computeBounds(rectF, true)
        // 创建一个区域对象，用于存储路径的区域信息。
        val region = Region()
        // 将路径转换为区域，并将结果存储到区域对象中。第二个参数表示区域的范围，这里使用矩形对象的左上角和右下角坐标作为区域的范围。
        region.setPath(path, Region((rectF.left).toInt(), rectF.top.toInt(), rectF.right.toInt(), rectF.bottom.toInt()))
        return region.contains((x).toInt(), (y).toInt())
    }
}