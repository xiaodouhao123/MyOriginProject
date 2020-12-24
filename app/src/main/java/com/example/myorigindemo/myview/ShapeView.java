package com.example.myorigindemo.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class ShapeView extends View {
    private Shape mCUrrentShape = Shape.Circle;
    private Paint mPaint;
    private Path mPath;

    public ShapeView(Context context) {
        this(context, null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.w("ShapeView", "getWidth==" + getWidth() + ",getHeight==" + getHeight() + ",getMeasureWidth==" + getMeasuredWidth() + ",getMeasureHeight==" + getMeasuredHeight());
        switch (mCUrrentShape) {
            case Circle://画圆形
                float centerX = getWidth() / 2;
                float centerY = getHeight() / 2;
                mPaint.setColor(Color.YELLOW);
                canvas.drawCircle(centerX, centerY, getMeasuredWidth()/2, mPaint);
                break;
            case Square://画正方形
                mPaint.setColor(Color.BLUE);
                canvas.drawRect(getWidth() / 2-getMeasuredWidth()/2, getHeight()/2-getMeasuredHeight()/2, getWidth() / 2+getMeasuredWidth()/2, getHeight()/2+getMeasuredHeight()/2, mPaint);
                break;
            case Triangle: //画三角形
                mPaint.setColor(Color.RED);
                if (mPath== null) {
                    mPath=new Path();
                    mPath.moveTo(getWidth()/2,0);
                    mPath.lineTo(getWidth()/2-getMeasuredWidth()/2, (float) ((getMeasuredHeight()/2)*Math.sqrt(3)));
                    mPath.lineTo(getWidth()/2+getMeasuredWidth()/2,(float) ((getMeasuredHeight()/2)*Math.sqrt(3)));
                    mPath.close();
                }
                canvas.drawPath(mPath,mPaint);
                break;
        }
    }

    public enum Shape {
        Circle, Square, Triangle
    }
    public void changeShape(){
        switch (mCUrrentShape) {
            case Circle://画圆形
                mCUrrentShape=Shape.Square;
                break;
            case Square://画正方形
                mCUrrentShape=Shape.Triangle;
                break;
            case Triangle: //画三角形
                mCUrrentShape=Shape.Circle;
                break;
        }
        invalidate();
    }
}
