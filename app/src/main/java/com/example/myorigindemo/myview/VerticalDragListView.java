package com.example.myorigindemo.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;

public class VerticalDragListView extends FrameLayout {
    private ViewDragHelper mViewDragHelper;
    private View mDragView;
    private boolean mMenuisOpen=false;
    //后面菜单的高度
    private int mMenuHeight;
    public VerticalDragListView(@NonNull Context context) {
        this(context,null);
    }

    public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewDragHelper=ViewDragHelper.create(this,mCallBack);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount=getChildCount();
        if (childCount!=2){
            throw new RuntimeException("VerticalDragListView 只能包含两个子布局");
        }
        mDragView=getChildAt(1);
    }

    private ViewDragHelper.Callback mCallBack=new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return mDragView==child;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            if (top<=0){
                top=0;
            }
            if (top>=mMenuHeight){
                top=mMenuHeight;
            }
            return top;
        }

        /*@Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return left;
        }*/
        //手指松开的时候两者选其一，要么打开要么关闭
        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            if (mDragView.getTop()>mMenuHeight/2){
                mViewDragHelper.settleCapturedViewAt(0,mMenuHeight);
                mMenuisOpen=true;
            }else {
                mViewDragHelper.settleCapturedViewAt(0,0);
                mMenuisOpen=false;
            }
            invalidate();
        }
    };

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed){
            View menuView=getChildAt(0);
            mMenuHeight=menuView.getMeasuredHeight();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)){
            invalidate();
        }
    }
    private float mDownY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        /*if (mMenuisOpen){
            return true;
        }*/
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mViewDragHelper.processTouchEvent(ev);
                mDownY=ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY=ev.getY()-mDownY;
                if (moveY>0){
                    return !mMenuisOpen;
                }
                if (moveY<0){
                    return mMenuisOpen;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
