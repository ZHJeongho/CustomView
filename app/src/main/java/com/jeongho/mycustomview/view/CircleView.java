package com.jeongho.mycustomview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.jeongho.mycustomview.R;

/**
 * Created by Jeongho_Lenovo on 2016/5/14.
 */
public class CircleView extends View implements Runnable {

    //上下文  获取自定义属性时用
    private Context mContext;

    private Paint mPaint;
    //圆的最大半径
    private int mMaxRadius;
    //边界宽度
    private int mBorderWidth;
    //圆的颜色
    private int mColor;
    //动画当前半径
    private int mCurentRadius;
    //动画是否进行
    public boolean isRun = true;

    private Scroller mScroller;
    /**
     * @param context
     */
    public CircleView(Context context) {
        this(context, null);
    }

    /**
     * 布局文件中引用时，调用该方法。需要用到attrs来解析布局文件中的属性
     *
     * @param context
     * @param attrs
     */
    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 自定义属性时调用该构造器
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttr(attrs);
        initPaint();
        //initListener();
        mScroller = new Scroller(mContext);
    }

    private void initPaint() {
        mPaint = new Paint();
        //设置抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setColor(mColor);

        int touchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
        System.out.println("touchSlop ---> " + touchSlop);
    }

    private void initAttr(AttributeSet attrs) {
        //自定义属性
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.CircleView);

        int count = a.getIndexCount();
        for (int index = 0; index < count; index++) {
            int attr = a.getIndex(index);
            switch (attr) {
                case R.styleable.CircleView_max_radius:
                    mMaxRadius = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                            50, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CircleView_circle_color:
                    mColor = a.getColor(attr, Color.LTGRAY);
                    break;
                case R.styleable.CircleView_circle_width:
                    mBorderWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                            2, getResources().getDisplayMetrics()));
                    break;
            }
        }
        a.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //1.解决padding不生效的问题
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int paddingTop = getPaddingTop();

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        canvas.drawCircle(width / 2 + paddingLeft, height / 2 + paddingTop, mCurentRadius, mPaint);
        Log.d("CV", "ondraw");
    }


    public void setPaintStyle(Paint.Style style) {
        mPaint.setStyle(style);
        invalidate();
    }


    @Override
    public void run() {
        while (isRun) {
            try {
                if (mCurentRadius < mMaxRadius) {
                    mCurentRadius += 10;
                } else {
                    mCurentRadius = 0;
                }
                //当前半径 > max    置max
                if (mCurentRadius > mMaxRadius){
                    mCurentRadius = mMaxRadius;
                }
                postInvalidate();
                Thread.sleep(40);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;

        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.AT_MOST:
                width = mMaxRadius * 2 + mBorderWidth;
                break;
        }

        // TODO: 2017/6/5 添加padding 否则View的宽高就没有考虑padding
        width += getPaddingLeft() + getPaddingRight();

        switch (heightMode){
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                height = mMaxRadius * 2 + mBorderWidth;
                break;
        }

        height += getPaddingTop() + getPaddingBottom();

        setMeasuredDimension(width, height);
    }

    public void smoothScrollTo(int destX, int destY){
        int scrollX = getScrollX();
        System.out.println(scrollX);
        int offset = destX - scrollX;
        mScroller.startScroll(scrollX, 0, offset, 0, 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        //super.computeScroll();
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("onTouchEvent");
        return super.onTouchEvent(event);
    }

    //停止或者动画
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }
}
