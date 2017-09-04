package com.chibafes.chibafes55;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by shiho on 2017/08/31.
 */

// 0903_kimura:ActivityからFragmentへ変更、それに伴う調整
public class TimeTableActivity extends Fragment {

    private ListView listView1;
    private ListView listView2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_timetable, container, false);

        listView1= (android.widget.ListView) view.findViewById(R.id.list_timetable);
        //listView2= (android.widget.ListView) findViewById(R.id.list_timetable2);

        String[] data = new String[(8)];
        for(int i = 0; i < data.length; ++i){
            data[i] ="好きな文字" + i ;
        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, data);
        listView1.setAdapter(arrayAdapter);
        //listView2.setAdapter(arrayAdapter);

        return view;
    }
    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // タイトルバーの表示設定：非表示にする
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // このActivityに関連づけるレイアウトの設定
        setContentView(R.layout.activity_timetable);

        listView1= (android.widget.ListView) findViewById(R.id.list_timetable);
        //listView2= (android.widget.ListView) findViewById(R.id.list_timetable2);

        String[] data = new String[(8)];
        for(int i = 0; i < data.length; ++i){
            data[i] ="好きな文字" + i ;
        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listView1.setAdapter(arrayAdapter);
        //listView2.setAdapter(arrayAdapter);

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                // バックキー押下時の処理
                case KeyEvent.KEYCODE_BACK:
                    // バックキーを押しても何も起きないようにする
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
    */
}


