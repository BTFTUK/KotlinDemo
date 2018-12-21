package com.example.rrks.kotlindemo.weight

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.TextView

class ColorfulTextView(context: Context?) : TextView(context) {
    private var mWidth = 0
    private var mHeight = 0
    private var redColor = Color.RED
    private var buleColor = Color.BLUE
    private var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mGradientPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mRectFbg: RectF = RectF()
    private var mRectF: RectF = RectF()
    private var mGradientWidth = 50
    private lateinit var mAnimator: ValueAnimator
    private lateinit var canvas: Canvas
    private lateinit var bitmap: Bitmap

    constructor(context: Context?, attrs: AttributeSet?) : this(context)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs)

    init {
        mPaint.color = redColor
        mPaint.style = Paint.Style.FILL
        mGradientPaint.style = Paint.Style.FILL
        mGradientPaint.color = buleColor
        val linearGradient: LinearGradient = LinearGradient(0f, 0f, 0f, mGradientWidth.toFloat(), buleColor, redColor, Shader.TileMode.CLAMP)
        mGradientPaint.shader = linearGradient
    }

    override fun onDraw(canvas: Canvas?) {
        drawBg(canvas)
        super.onDraw(canvas)
    }

    private fun drawBg(canvas: Canvas?) {
        canvas?.drawRoundRect(mRectFbg, mHeight.toFloat() / 2, mHeight.toFloat() / 2, mPaint)
        canvas?.drawBitmap(bitmap,0f,0f,mPaint)
//        canvas?.drawPoint(0f, 0f, mPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        mRectFbg.set(0f, 0f, mWidth.toFloat(), mHeight.toFloat())
        mAnimator = ValueAnimator.ofInt(0, mWidth - mGradientWidth)
        mAnimator.addUpdateListener {
            val left: Float = it.animatedValue as Float
            mRectF.set(left, 0f, left + mGradientWidth, mHeight.toFloat())
        }
        bitmap = Bitmap.createBitmap(mGradientWidth, mHeight, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
        canvas.drawRect(mRectF, mGradientPaint)

    }

}