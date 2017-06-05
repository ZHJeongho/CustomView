package com.jeongho.mycustomview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.jeongho.mycustomview.R;

/**
 * Created by Jeongho_Lenovo on 2016/5/17.
 */
public class TopBar extends ViewGroup{

    private Context mContext;

    private String mTitle;
    private int mTitleColor;
    private int mTitleSize;

    private int mLeftColor;
    private String mLeftText;
    private Drawable mLeftBg;

    private String mRightText;
    private int mRightColor;
    private Drawable mRightBg;

    public TopBar(Context context) {
        this(context, null);
    }

    public TopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.TopBar, defStyleAttr, 0);
        int count = ta.getIndexCount();
        for (int i = 0; i < count; i++){
            int attr = ta.getIndex(i);
            switch (attr){
                case R.styleable.TopBar_title:
                    mTitle = ta.getString(attr);
                    break;
                case R.styleable.TopBar_titleColor:
                    mTitleColor = ta.getColor(attr, Color.YELLOW);
                    break;
                case R.styleable.TopBar_titleTextSize:
                    mTitleSize = ta.getDimensionPixelSize(attr, 10);
                    break;
                case R.styleable.TopBar_leftText:
                    mLeftText = ta.getString(attr);
                    break;
                case R.styleable.TopBar_leftTextBackground:
                    mLeftBg = ta.getDrawable(attr);
                    break;
                case R.styleable.TopBar_leftTextColor:
                    mLeftColor = ta.getColor(attr, Color.YELLOW);
                    break;
                case R.styleable.TopBar_rightText:
                    mRightText = ta.getString(attr);
                    break;
                case R.styleable.TopBar_rightTextBackground:
                    mRightBg = ta.getDrawable(attr);
                    break;
                case R.styleable.TopBar_rightTextColor:
                    mRightColor = ta.getColor(attr, Color.YELLOW);
                    break;
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
