package com.example.myorigindemo.myview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import javax.net.ssl.SSLEngineResult

class LockPatternView :View{
    private var mIsInit=false
    //外圆的半径
    private var mDotRadius=0;
    //画笔
    private var mLinePaint:Paint?=null
    private var mPressedPaint:Paint?=null
    private var mErrorPaint:Paint?=null
    private var mNormalPaint:Paint?=null
    private var mArowPaint:Paint?=null
    //颜色
    private val mOuterPressedColor=0xff8cbad8.toInt()
    private val mInnerPressedColor=0xff0596f6.toInt()
    private val mOuterNormalColor=0xffd9d9d9.toInt()
    private val mInnerNormalColor=0xff929292.toInt()
    private val mOuterErrorColor=0xff0901032.toInt()
    private val mInnerErrorColor=0xffea0945.toInt()
    private var mPoints:Array<Array<Point?>>=Array(3){Array<Point?>(3,{null})}
    constructor(context:Context):super(context)
    constructor(context: Context,attrs: AttributeSet):super(context,attrs)
    constructor(context: Context,attrs: AttributeSet,defStyleAttr:Int):super(context,attrs,defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        if (!mIsInit){
            initDot()
            initPaint()
            mIsInit=true
        }
    }

    private fun initPaint() {
        //线的画笔
        mLinePaint= Paint()
        mLinePaint!!.color=mInnerPressedColor
        mLinePaint!!.style=Paint.Style.STROKE
        mLinePaint!!.isAntiAlias=true
        mLinePaint!!.strokeWidth=(mDotRadius/9).toFloat()
        //按下的画笔
        mPressedPaint= Paint()
        mPressedPaint!!.style=Paint.Style.STROKE
        mPressedPaint!!.isAntiAlias=true
        mPressedPaint!!.strokeWidth=(mDotRadius/6).toFloat()
        //错误的画笔
        mErrorPaint= Paint()
        mErrorPaint!!.style=Paint.Style.STROKE
        mErrorPaint!!.isAntiAlias=true
        mErrorPaint!!.strokeWidth=(mDotRadius/6).toFloat()
        //默认的画笔
        mNormalPaint= Paint()
        mNormalPaint!!.style=Paint.Style.STROKE
        mNormalPaint!!.isAntiAlias=true
        mNormalPaint!!.strokeWidth=(mDotRadius/9).toFloat()
        //箭头的画笔
        mArowPaint= Paint()
        mArowPaint!!.color=mInnerPressedColor
        mArowPaint!!.style=Paint.Style.STROKE
        mArowPaint!!.isAntiAlias=true
    }

    private fun initDot() {
        mPoints[0][0]=Point(,0)
    }
    class Point(var centerX:Int,var centerY:Int,var index:Int){
        private val STATUS_NORMAL=1
        private val STATUS_PRESSED=2
        private val STATUS_ERROR=3
        private var status=STATUS_NORMAL


    }
}