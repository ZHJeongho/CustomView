package com.jeongho.mycustomview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.Toast;

/**
 * Created by Jeongho on 2017/6/3.
 */

public class HorizontalScrollViewEx extends ViewGroup {

    private int mLastX;
    private int mLastY;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    public HorizontalScrollViewEx(Context context) {
        this(context, null);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
        mVelocityTracker = VelocityTracker.obtain();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //1.measure child
        //2.calculate width and height
        //3.invoke setMeasuredDimension()

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int width = 0;
        int height = 0;
        int childCount = getChildCount();
        System.out.println("childCount->>>" + childCount);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (childCount == 0){
            measureChildren(0, 0);
        }

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){

            int maxHeight = 0;
            for (int i = 0; i < childCount; i++){
                View child = getChildAt(i);
                int childHeight = child.getMeasuredHeight();
                if (childHeight > maxHeight){
                    maxHeight = childHeight;
                }


                int childWidth = child.getMeasuredWidth();
                width += childWidth;
            }

            height = maxHeight;
        }else if (widthMode == MeasureSpec.AT_MOST){
            for (int i = 0; i < childCount; i++){
                View child = getChildAt(i);
                int childWidth = child.getMeasuredWidth();
                width += childWidth;
            }

            height = heightSize;
        }else if (heightMode == MeasureSpec.AT_MOST){
            int maxHeight = 0;
            for (int i = 0; i < childCount; i++){
                View child = getChildAt(i);
                int childHeight = child.getMeasuredHeight();
                if (childHeight > maxHeight){
                    maxHeight = childHeight;
                }
            }

            height = maxHeight;
            width = widthSize;
        }

        width += getPaddingLeft() + getPaddingRight();
        height += getPaddingTop() + getPaddingBottom();

        System.out.println("width->>" + width);
        System.out.println("height->>" + height);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //call child.layout(int l, int t, int r, int b)
        //note: left += child.width   累加每个child的宽度，水平排列

        int left = getPaddingLeft();
        for (int i = 0; i < getChildCount(); i++){
            View child = getChildAt(i);
            child.layout(left, getTop() + getPaddingTop(), left + child.getMeasuredWidth(), getTop() + child.getMeasuredHeight()  + getPaddingTop());
            left += child.getWidth();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //1.down intercept = false
        //2.move x > y ? intercept = true: intercept = false
        //3.up intercept = false

//        return super.onInterceptTouchEvent(ev);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //intercept child touchEvent  拦截点击事件后调用onTouchEvent
        //1.down   stop animation
        //2.move   calculate the offset of x , and invoke scrollBy() to move the content of the View
        //3.up    calculate Xvelocity
        mVelocityTracker.addMovement(event);

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                int deltaX = x - mLastX;
                System.out.println("move---------->" + deltaX);
                scrollBy(-deltaX, 0);
                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();

                 if (xVelocity > 50){
                     Toast.makeText(getContext(), "fanye", Toast.LENGTH_SHORT).show();

                 }else {
                     Toast.makeText(getContext(), "bufanye", Toast.LENGTH_SHORT).show();

                 }
                break;
            default:
                break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }

//    @Override
//    public void computeScroll() {
//        if (!mScroller.computeScrollOffset()){
//            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
//            postInvalidate();
//        }
//    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVelocityTracker.recycle();
    }
}
