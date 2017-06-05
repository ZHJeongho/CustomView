package com.jeongho.mycustomview.activity;

import android.animation.ValueAnimator;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.jeongho.mycustomview.R;
import com.jeongho.mycustomview.view.CircleView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    private Button mFillBtn;
    private Button mFillAndStrokeBtn;
    private Button mStartBtn;
    private Button mStopBtn;
    private Button mStrokeBtn;

    private CircleView mCircleView;
    private Thread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }

    private void initListener() {
        mFillBtn.setOnClickListener(this);
        mFillAndStrokeBtn.setOnClickListener(this);
        mStrokeBtn.setOnClickListener(this);
        mStartBtn.setOnClickListener(this);
        mStopBtn.setOnClickListener(this);
        mCircleView.setOnClickListener(this);
        mCircleView.setOnTouchListener(this);
    }

    private void initView() {
        mFillBtn = (Button) findViewById(R.id.btn_fill);
        mFillAndStrokeBtn = (Button) findViewById(R.id.btn_fill_and_stroke);
        mStrokeBtn = (Button) findViewById(R.id.btn_stroke);
        mStartBtn = (Button) findViewById(R.id.btn_start);
        mStopBtn = (Button) findViewById(R.id.btn_stop);

        mCircleView = (CircleView) findViewById(R.id.cv);

        mThread = new Thread(mCircleView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_fill:
                //设置画笔为填充
//                mCircleView.setPaintStyle(Paint.Style.FILL);
                mCircleView.scrollBy(100, 0);
                break;
            case R.id.btn_fill_and_stroke:
                //设置画笔为填充并且描边
                mCircleView.setPaintStyle(Paint.Style.FILL_AND_STROKE);
                break;
            case R.id.btn_stroke:
                //设置画笔为描边
                //mCircleView.setPaintStyle(Paint.Style.STROKE);

                //1.scroll滑动
                //mCircleView.smoothScrollTo(500, 100);
                //2.属性动画
                //ObjectAnimator.ofFloat(mCircleView, "translationX", 0, 100).setDuration(100).start();
                //3.布局参数
                //ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mCircleView.getLayoutParams();
                //params.leftMargin += 100;
                //mCircleView.requestLayout();
                //4.动画
                final int startX = 0;
                final int deltaX = 100;
                ValueAnimator animator = ValueAnimator.ofInt(0, 1).setDuration(1000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float fraction = animation.getAnimatedFraction();
                        mCircleView.scrollTo((int) (startX + fraction * deltaX), 0);
                    }
                });
                animator.start();
                break;
            case R.id.btn_start:
                startAnimation(true);
                break;
            case R.id.btn_stop:
                startAnimation(false);
                break;
            case R.id.cv:

                System.out.println("onClick");

                if (mCircleView.isRun){
                    startAnimation(false);
                }else {
                    startAnimation(true);
                }
                break;
        }
    }

    public void startAnimation(boolean isStart){
        if (isStart){
            mCircleView.isRun = isStart;
            if (mThread == null){
                mThread = new Thread(mCircleView);
            }
            if (!mThread.isAlive()){
                mThread.start();
            }
        }else {
            //关闭线程
            mCircleView.isRun = isStart;
            if (mThread != null){
                mThread = null;
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        System.out.println("onTouch");
        return false;
    }
}
