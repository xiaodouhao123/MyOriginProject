package com.example.myorigindemo.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.myorigindemo.R;

public class ProgressBar extends View {
    private Paint mInnerPaint;
    private Paint mOuterPaint;
    private Paint mTextPaint;
    private int innerBackground= Color.RED;
    private int outerColor=Color.GREEN;
    private int progressTextColor=Color.RED;
    private int foundWidth=10;
    private int progressTextSize=15;
    private int mProgress=0;
    private int mMax=100;
    public ProgressBar(Context context) {
        this(context,null);
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.ProgressBar);
        innerBackground=array.getColor(R.styleable.ProgressBar_innerBackground,innerBackground);
        outerColor=array.getColor(R.styleable.ProgressBar_outerColor,outerColor);
        progressTextColor=array.getColor(R.styleable.ProgressBar_progressTextColor,progressTextColor);
        foundWidth=array.getDimensionPixelSize(R.styleable.ProgressBar_progressTextSize,foundWidth);
        progressTextSize=array.getDimensionPixelSize(R.styleable.ProgressBar_progressTextSize,progressTextSize);
        array.recycle();
        mInnerPaint=new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(innerBackground);
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setStrokeWidth(foundWidth);
        mOuterPaint=new Paint();
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setColor(outerColor);
        mOuterPaint.setStyle(Paint.Style.STROKE);
        mOuterPaint.setStrokeWidth(foundWidth);
        mTextPaint=new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(progressTextColor);
        mTextPaint.setTextSize(progressTextSize);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width,height)/2,Math.min(width,height)/2);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.w("ProgressBar","getWidth=="+getWidth()+",getMeasure=="+getMeasuredWidth());
        // 画内圆
        canvas.drawCircle(getMeasuredWidth()/2,getMeasuredWidth()/2,getMeasuredWidth()/2-foundWidth/2,mInnerPaint);
        //画外圆弧
        RectF rectF=new RectF(foundWidth/2,foundWidth/2,getMeasuredWidth()-foundWidth/2,getMeasuredHeight()-foundWidth/2);
        if (mProgress==0) return;
        float percent=(float)mProgress/mMax;
        canvas.drawArc(rectF,0,percent*360,false,mOuterPaint);
        //画文字
        String text=percent*100+"%";
        Rect textBounds=new Rect();
        mTextPaint.getTextBounds(text,0,text.length(),textBounds);
        Log.w("ProgressBar","textBounds.left=="+textBounds.left+"textBounds.right=="+textBounds.right+"textBounds.top=="+textBounds.top+"textBounds.bottom=="+textBounds.bottom);
        float x=getMeasuredWidth()/2-textBounds.width()/2;
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float dy=(fontMetrics.bottom-fontMetrics.top)/2-fontMetrics.bottom;
        Log.w("ProgressBar","fontMetrics.top=="+fontMetrics.top+"fontMetrics.bottom=="+fontMetrics.bottom);
        Log.w("ProgressBar","dy=="+dy+",textBounds.height()/2=="+textBounds.height()/2);
        //float y=getMeasuredHeight()/2+textBounds.height()/2;
        float y=getMeasuredHeight()/2+dy;
        canvas.drawText(text,x,y,mTextPaint);
    }
    public void setProgress(int progress){
        mProgress=progress;
        invalidate();
    }
}
