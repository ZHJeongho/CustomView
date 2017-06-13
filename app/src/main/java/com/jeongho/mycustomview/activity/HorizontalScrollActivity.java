package com.jeongho.mycustomview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.jeongho.mycustomview.R;
import com.jeongho.mycustomview.view.HorizontalScrollViewEx;

/**
 * Created by Jeongho on 2017/6/5.
 */

public class HorizontalScrollActivity extends AppCompatActivity {

    private Button mButton;
    private HorizontalScrollViewEx mHorizontalScrollViewEx;

    private Button mButtonTest;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal);
        mButton = (Button) findViewById(R.id.btn);
        mButtonTest = (Button) findViewById(R.id.btn_test);


        mHorizontalScrollViewEx = (HorizontalScrollViewEx) findViewById(R.id.hori);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //getScrollX  水平方向的偏移量  都是用(0,0)减去当前的View左上角坐标？
                //向右滑动  getScrollX 为负
                //向左滑动  getScrollX 为正


                //System.out.println("" + mHorizontalScrollViewEx.getScrollX());
                try{
                    mHorizontalScrollViewEx.scrollTo(400, 0);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }


            }
        });

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mHorizontalScrollViewEx.addView(new Button(HorizontalScrollActivity.this), params);
        mButtonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HorizontalScrollActivity.this, "aaaaa", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
