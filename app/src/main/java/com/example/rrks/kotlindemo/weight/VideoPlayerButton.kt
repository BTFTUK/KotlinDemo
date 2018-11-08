package com.example.rrks.kotlindemo.weight

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

class VideoPlayerButton(context: Context?) : View(context) {
    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mPath: Path = Path()
    private var mAnimator: ValueAnimator = ValueAnimator.ofFloat(0f, 1f)
    private var mWidth = 0
    private var mHeight = 0
    private var mDrawLeft: Float = 0f
    private var mDrawRight: Float = 0f
    private var mDrawTop: Float = 0f
    private var mDrawBottom: Float = 0f
    private var mCenterPoint = Point(0, 0)
    val size = dp2px(14f)
    private var isPause = false
    private var pathEffect3f: PathEffect = CornerPathEffect(dp2px(3f))
    private var pathEffect1f: PathEffect = CornerPathEffect(dp2px(1f))
    var percent: Float = 0f
    private lateinit var mPathPointUP: Point
    private lateinit var mPathPointDown: Point

    private lateinit var mPathPoint1: Point
    private lateinit var mPathPoint2: Point
    private lateinit var mPathPoint3: Point
    private lateinit var mPathPoint4: Point
    private var offSet:Float = 3f

    constructor(context: Context?, attrs: AttributeSet?) : this(context)

    constructor (context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs)

    init {
        mPaint.color = Color.WHITE
        mPaint.style = Paint.Style.FILL
        mPaint.strokeWidth = 1f
        mPaint.strokeJoin = Paint.Join.ROUND

        mAnimator.addUpdateListener {
            percent = it.animatedValue as Float
            invalidate()
        }

        setOnClickListener {
            isPause = !isPause
            mAnimator.start()

        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        mCenterPoint.set(w / 2, h / 2)
        mDrawLeft = mCenterPoint.x - size / 2 + offSet
        mDrawRight = mCenterPoint.x + size / 2 + offSet
        mDrawTop = mCenterPoint.y - size / 2 - 6
        mDrawBottom = mCenterPoint.y + size / 2 + 6

        mPathPointUP = getPathPoint(Point(mDrawLeft.toInt(), mDrawTop.toInt()),
                Point(mDrawRight.toInt(), mCenterPoint.y), 0.5f)
        mPathPointDown = getPathPoint(Point(mDrawLeft.toInt(), mDrawBottom.toInt()),
                Point(mDrawRight.toInt(), mCenterPoint.y), 0.5f)

        mPathPoint1 = Point((mDrawLeft + dp2px(4f)).toInt(), mDrawTop.toInt())
        mPathPoint2 = Point((mDrawLeft + dp2px(4f)).toInt(), mDrawBottom.toInt())

        mPathPoint3 = Point((mDrawRight - offSet - dp2px(4f)).toInt(), mDrawTop.toInt())
        mPathPoint4 = Point((mDrawRight - offSet - dp2px(4f)).toInt(), mDrawBottom.toInt())

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.parseColor("#aa000000")
        canvas?.drawCircle(mCenterPoint.x.toFloat(), mCenterPoint.y.toFloat(), dp2px(23f), mPaint)

        if (isPause) {
            mPaint.pathEffect = pathEffect1f

            mPaint.style = Paint.Style.FILL
            mPaint.color = Color.WHITE
            mPath.moveTo(mDrawLeft, mDrawTop)
            mPath.lineTo(mDrawLeft, mDrawBottom)

            lineToHH(mPathPointDown, mPathPoint2, percent, mPath)
            lineToHH(mPathPointUP, mPathPoint1, percent, mPath)
            mPath.close()

            mPath.moveTo(mDrawRight - offSet * percent, mCenterPoint.y + (mDrawTop - mCenterPoint.y) * percent)
            mPath.lineTo(mDrawRight - offSet * percent, mCenterPoint.y + (mDrawBottom - mCenterPoint.y) * percent)

            lineToHH(mPathPointDown, mPathPoint4, percent, mPath)
            lineToHH(mPathPointUP, mPathPoint3, percent, mPath)
            mPath.close()

            canvas?.drawPath(mPath, mPaint)
            mPath.reset()

        } else {
            mPaint.pathEffect = pathEffect3f

            mPaint.style = Paint.Style.FILL
            mPaint.color = Color.WHITE
            mPath.moveTo(mDrawLeft, mDrawTop)
            mPath.lineTo(mDrawLeft, mDrawBottom)

            lineToHH(mPathPoint2, mPathPointDown, percent, mPath)
            lineToHH(mPathPoint1, mPathPointUP, percent, mPath)
            mPath.close()

            //右边
//            mPath.moveTo(mDrawRight - 5 * percent, mCenterPoint.y + (mDrawTop - mCenterPoint.y) * percent)
//            mPath.lineTo(mDrawRight - 5 * percent, mCenterPoint.y + (mDrawBottom - mCenterPoint.y) * percent)

            mPath.moveTo(mDrawRight - offSet * percent, mDrawTop + (mCenterPoint.y - mDrawTop) * percent)
            mPath.lineTo(mDrawRight - offSet * percent, mDrawBottom + (mCenterPoint.y - mDrawBottom) * percent)

            lineToHH(mPathPoint4, mPathPointDown, percent, mPath)
            lineToHH(mPathPoint3, mPathPointUP, percent, mPath)
            mPath.close()

            if (percent == 1f) {
                mPath.reset()
                mPath.moveTo(mDrawLeft, mDrawTop)
                mPath.lineTo(mDrawLeft, mDrawBottom)
                mPath.lineTo(mDrawRight, mCenterPoint.y.toFloat())
                mPath.close()
                canvas?.drawPath(mPath, mPaint)
                mPath.reset()
            }

            canvas?.drawPath(mPath, mPaint)
            mPath.reset()
        }

    }

    private fun dp2px(dp: Float): Float {
//        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
//                Resources.getSystem().displayMetrics)
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    }

    /**
     * 根据两个点得到两点线段的百分比，来得到当前点
     * y = kx+b
     */
    private fun getPathPoint(pointS: Point?, pointE: Point?, percent: Float): Point {
        return if (pointS != null && pointE != null) {
            val x = (pointS.x + (pointE.x - pointS.x) * percent).toInt()
            val y = (pointS.y + (pointE.y - pointS.y) * percent).toInt()
            return Point(x, y)
        } else {
            Point(0, 0)
        }
    }

    private fun lineToHH(pointS: Point?, pointE: Point?, percent: Float, path: Path) {
        if (pointS != null && pointE != null) {
            val x = (pointS.x + (pointE.x - pointS.x) * percent)
            val y = (pointS.y + (pointE.y - pointS.y) * percent)
            path.lineTo(x, y)
        }
    }
}