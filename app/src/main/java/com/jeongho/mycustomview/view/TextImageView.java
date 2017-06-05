package com.jeongho.mycustomview.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Jeongho on 2016/7/12.
 */
public class TextImageView extends RelativeLayout{
    //图片区背景图片
    private Drawable mImageBackground;
    //文字区背景图片
    private Drawable mTextBackground;
    //文字标题
    private String mTitle;
    public TextImageView(Context context) {
        this(context, null);
    }

    public TextImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

//        TypedArray ta = context.obtainStyledAttributes(attrs)
    }
}
