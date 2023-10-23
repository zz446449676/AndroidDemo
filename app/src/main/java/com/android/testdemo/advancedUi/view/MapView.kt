package com.android.testdemo.advancedUi.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.Toast
import androidx.core.graphics.PathParser
import com.android.testdemo.R
import com.android.testdemo.Util.ViewHelper
import com.android.testdemo.advancedUi.model.ProvinceItem
import org.w3c.dom.Element
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

class MapView : View {
    private lateinit var context : Context

    private val MODEL_PARSER_SUCCESS = 1000

    // 省份数据结构
    private var provinceList = ArrayList<ProvinceItem>()

    private val paint = Paint()

    // 地图初始化时的总大小
    private var totalRect = RectF()

    // 地图缩放比例
    private var scale = 1.0f

    // 画布平移距离
    private var translateX = 0f
    private var translateY = 0f

    // 缩放手势监听
    private lateinit var scaleGestureDetector: ScaleGestureDetector

    // 拖拽手势监听
    private lateinit var dragGestureDetector: GestureDetector

    private val colorArray = intArrayOf(Color.parseColor("#75BDE0"), Color.parseColor("#78D1D2"), Color.parseColor("#97DBAE"), Color.parseColor("#CDE4AD"))

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            if (msg.what == MODEL_PARSER_SUCCESS) {
                val totalSize = provinceList.size
                for (index in 0 until totalSize) {
                    // 对颜色进行选择
                    provinceList[index].setDrawColor(colorArray[index % 4])
                }
                scale = (width / totalRect.width())
                // 设置地图在y方向上的位置
                translateY = ViewHelper.dp2px(context, 120f).toFloat()
                invalidate()
            }
        }
    }

    private fun init(context: Context) {
        this.context = context
        paint.isAntiAlias = true

        // 子线程中进行加载解析 SVG 数据
        Thread {
            // 载入 svg 数据流
            var inputStream = context.resources.openRawResource(R.raw.chinahigh)
            // 解析出省份结构
            provinceList = parserDom2Model(inputStream)
            // 通过 handler 切换到主线程，更新ui
            val message = Message.obtain()
            message.what = MODEL_PARSER_SUCCESS
            handler.sendMessage(message)
        }.start()

        // 设置缩放监听器, 用于对地图的缩放
        scaleGestureDetector = ScaleGestureDetector(context, object :
            ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                val newScale = scale * detector.scaleFactor
                if (newScale >= 0.8 && newScale < 4.5) {
                    scale = newScale
                    invalidate()
                }
                return true
            }
        })

        // 设置拖拽监听器，对地图进行拖拽
        dragGestureDetector = GestureDetector(context, object :
            GestureDetector.SimpleOnGestureListener() {
            override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
                translateX -= distanceX
                translateY -= distanceY
                invalidate()
                return true
            }
        })
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY))
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas != null) {
            val list = provinceList
            canvas.save()
            canvas.scale(scale, scale)
            canvas.translate(translateX, translateY)
            for (index in 0 until list.size) {
                list[index].drawItem(canvas, paint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            scaleGestureDetector.onTouchEvent(event)
            dragGestureDetector.onTouchEvent(event)
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    handleTouch(event.x, event.y)
                }
                MotionEvent.ACTION_MOVE -> {
                }
                MotionEvent.ACTION_UP -> {
                }
            }
        }
        return true
    }

    // 处理点击事件，突出颜色和显示名称
    private fun handleTouch(x: Float, y: Float) {
        val list = provinceList
        var item: ProvinceItem?
        if (list != null) {
            for (index in 0 until list.size) {
                item = list[index]
                if (item.isTouch(x/scale - translateX, y/scale - translateY)) {
                    val select = if (item.isSelected) "取消选择" else "选择"
                    // 如果已经选中，则取消选中状态
                    item.setIsSelected(!item.isSelected)
                    Toast.makeText(context, "您${select}了 ${item.provinceName} ", Toast.LENGTH_SHORT).show()
                    invalidate()
                    return
                }
            }
        }
    }

    // 解析 xml，把 xml 解析成 province 数据 model
    private fun parserDom2Model(inputStream: InputStream) : ArrayList<ProvinceItem> {
        val modelList = ArrayList<ProvinceItem>()
        try {
            // 用四个顶点来确定 view 的大小
            var left = -1.0f
            var top = -1.0f
            var right = -1.0f
            var bottom = -1.0f

            // 获取 DocumentBuilder 实例
            val builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            // 获取解析输入流，得到 Document 实例
            val doc = builder.parse(inputStream)
            // 得到根 root 元素
            val rootElement = doc.documentElement
            // 得到 path 标签元素
            val items = rootElement.getElementsByTagName("path")

            // 开始解析元素
            for (index in 0 until items.length) {
                val element = items.item(index) as Element
                val path = PathParser.createPathFromPathData(element.getAttribute("android:pathData"))
                val provinceItem = ProvinceItem(path)
                provinceItem.setProvinceName(element.getAttribute("android:name"))
                modelList.add(provinceItem)

                // 用 rect 来生成一个矩形来容纳 path，用于确定每个item的占位大小,最终可以确定出整个地图的大小
                val rect = RectF()
                path.computeBounds(rect, true)
                // 返回较小的值
                left = if (left == -1.0f) rect.left else left.coerceAtMost(rect.left)
                // 返回较大的值
                right = if (right == -1.0f) rect.right else right.coerceAtLeast(rect.right)
                top = if (top == -1.0f) rect.top else top.coerceAtMost(rect.top)
                bottom = if (bottom == -1.0f) rect.bottom else bottom.coerceAtLeast(rect.bottom)
                totalRect = RectF(left, top, right, bottom)
            }
        } catch (_ : Exception) {}
        return modelList
    }
}