package com.example.myorigindemo.myview;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.myorigindemo.R;

public class LetterSideBar extends View {
    private Paint mPaint;
    private int texDefaulttColor=Color.BLACK;
    private float textSize=20;
    private int textSelectedColor=Color.BLUE;
    private int mCurrentSelected=0;
    private String[] mLetters={"A","B","C","D","E","F",
                                "G","H","I","J","K","L",
                                "M","N","O","P","Q","R",
                                "S","T","U","V","W","X",
                                "Y","Z","#"};
    private int itemHeight;

    public LetterSideBar(Context context) {
        this(context,null);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.LetterSideBar);
        texDefaulttColor=array.getColor(R.styleable.LetterSideBar_texDefaulttColor,texDefaulttColor);
        textSelectedColor=array.getColor(R.styleable.LetterSideBar_textSelectedColor,textSelectedColor);
        textSize=array.getDimension(R.styleable.LetterSideBar_textSize,sp2px(textSize));
        array.recycle();
        initPaint();

    }

    private void initPaint() {
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(texDefaulttColor);
        mPaint.setTextSize(textSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int textWidth = (int) mPaint.measureText("A");
        //计算指定宽度=左右padding+字母的宽度
        int width=getPaddingLeft()+getPaddingRight()+textWidth;
        //高度可以直接获取
        int height=MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.w("LetterSideBar","getWidth="+getWidth()+",getMeasuredWidth="+getMeasuredWidth());
        itemHeight=(getHeight()-getPaddingBottom()-getPaddingTop())/mLetters.length;
        int y=getPaddingTop()+itemHeight/2;
        for (int i=0;i<mLetters.length;i++){
            Rect bounds=new Rect();
            if (i==mCurrentSelected){
                mPaint.setColor(Color.RED);
            }else {
                mPaint.setColor(texDefaulttColor);
            }
            mPaint.getTextBounds(mLetters[i],0,0,bounds);
            int textWidth = (int) mPaint.measureText(mLetters[i]);
            int x=getWidth()/2-textWidth/2;
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            float dy=(fontMetrics.bottom-fontMetrics.top)/2-fontMetrics.bottom;
            canvas.drawText(mLetters[i],x,y+dy,mPaint);
            y+=itemHeight;
        }
    }

    private float sp2px(float sp){
     return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.w("LetterSideBar","action=="+event.getAction());
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                int mSelected= (int) ((y-getPaddingTop())/itemHeight);
                if (mSelected<0) mSelected=0;
                if (mSelected>mLetters.length-1) mSelected=mLetters.length-1;
                Log.w("LetterSideBar","mSelected=="+mSelected);
                if (mCurrentSelected!=mSelected){
                    mCurrentSelected=mSelected;
                    if (mListener!=null) mListener.touch(mLetters[mCurrentSelected],true);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mListener!=null) mListener.touch(mLetters[mCurrentSelected],false);
                break;

        }
        return true;
    }
    private LetterTouchListener mListener;

    public void setmListener(LetterTouchListener mListener) {
        this.mListener = mListener;
    }

    public interface LetterTouchListener{
        void touch(String texr,boolean isTouch);
    }
}
