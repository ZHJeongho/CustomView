package com.jeongho.mycustomview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by Jeongho on 2017/6/3.
 */

public class HorizontalScrollViewEx extends ViewGroup {

    private int mLastX;
    private int mLastY;

    private int mInterceptX;
    private int mInterceptY;

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

        //measureChildren(widthMeasureSpec, heightMeasureSpec);

        int width = 0;
        int height = 0;
        int childCount = getChildCount();
        System.out.println("childCount->>>" + childCount);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        widthSize -= getPaddingLeft();
        widthSize -= getPaddingRight();

        measureChildren(MeasureSpec.makeMeasureSpec(widthSize, widthMode), heightMeasureSpec);

        if (childCount == 0) {
            measureChildren(0, 0);
        }

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {

            int maxHeight = 0;
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                int childHeight = child.getMeasuredHeight();
                if (childHeight > maxHeight) {
                    maxHeight = childHeight;
                }

                int childWidth = child.getMeasuredWidth();
                width += childWidth;
            }
            //取子View中最高的为ViewGroup的高度
            height = maxHeight;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                int childWidth = child.getMeasuredWidth();
                width += childWidth;
            }

            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            int maxHeight = 0;
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                int childHeight = child.getMeasuredHeight();
                if (childHeight > maxHeight) {
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
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(left,
                    getTop() + getPaddingTop(),
                    left + child.getMeasuredWidth(),
                    getTop() + child.getMeasuredHeight() + getPaddingTop());
            left += child.getWidth();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //1.down    intercept = false
        //2.move   x > y ? intercept = true: intercept = false
        //3.up    intercept = false

        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mInterceptX;
                int deltaY = y - mInterceptY;

                if (Math.abs(deltaX) > Math.abs(deltaY)){
                    intercept = true;
                }else {
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
            default:
                break;
        }


        mInterceptX = x;
        mInterceptY = y;
        //        return super.onInterceptTouchEvent(ev);

        System.out.println("intercept -- >" + intercept);
        return intercept;
    }

    private int childIndex;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //intercept child touchEvent  拦截点击事件后调用onTouchEvent
        //1.down   stop animation
        //2.move   calculate the offset of x , and invoke scrollBy() to move the content of the View
        //3.up    calculate Xvelocity
        mVelocityTracker.addMovement(event);

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:


                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                //System.out.println("deltaX---->" + deltaX);
                scrollBy(-deltaX, 0);

                //((View)getParent()).scrollBy(-deltaX, 0);

                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();

                if (Math.abs(xVelocity) > 70) {
                    childIndex = xVelocity > 0 ? childIndex - 1 : childIndex + 1;
                } else {
                   // childIndex = xVelocity > 0 ? childIndex - 1 : childIndex + 1;
                }

                childIndex = Math.max(0, Math.min(childIndex, getChildCount() - 1));
                int dx = childIndex * getChildAt(0).getWidth() - getScrollX();
                smoothScrollTo(dx, 0);
                mVelocityTracker.clear();
                break;
            default:
                break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothScrollTo(int dx, int dy) {
        mScroller.startScroll(getScrollX(), 0, dx, 0, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        //true : the animation is not yet finished
        //false : the animation is  finished
        if (mScroller.computeScrollOffset()) {
            System.out.println("currX-->" + mScroller.getCurrX());
            System.out.println("currY-->" + mScroller.getCurrY());
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVelocityTracker.recycle();
    }
}
