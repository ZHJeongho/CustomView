package com.jeongho.mycustomview.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeongho.mycustomview.R;

import java.util.LinkedList;

/**
 * Created by Jeongho_Lenovo on 2016/5/16.
 */
public class HomeActivity extends Activity implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private LinkedList<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initList();
        initView();
        mListView.setAdapter(mAdapter);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.lv);
        mListView.setOnItemClickListener(this);
    }

    private void initList() {
        mList = new LinkedList<String>();
        mList.add("CircleView");
        mList.add("HorizontalScroll");
    }

    private BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_home, parent, false);
                holder = new ViewHolder();
                holder.tvContent = (TextView) convertView.findViewById(R.id.tv_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvContent.setText(mList.get(position));
            return convertView;
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        Class<?> TargetClass = null;
        switch (mList.get(position)) {
            case "CircleView":
                TargetClass = MainActivity.class;
                break;
            case "HorizontalScroll":
                TargetClass = HorizontalScrollActivity.class;
                break;
            default:
                break;
        }
        if (TargetClass != null){
            intent.setClass(this, TargetClass);
            startActivity(intent);
        }
    }

    public class ViewHolder {
        public TextView tvContent;
    }
}
