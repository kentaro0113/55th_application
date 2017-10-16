package com.chibafes.chibafes55;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shiho on 2017/08/31.
 */

// 0903_kimura:ActivityからFragmentへ変更、それに伴う調整
public class TimeTableActivity extends Fragment {

    private ListView listView1;
    private ListView listView2;
    private AlertDialog alertTimeTable = null;
    private LinearLayout viewTimeTable = null;

    private AdapterView.OnItemClickListener clickListenerTimeTable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_timetable, container, false);

        listView1= (android.widget.ListView) view.findViewById(R.id.list_timetable);
        listView2= (android.widget.ListView) view.findViewById(R.id.list_timetable2);

        /*
        String[] data = new String[(8)];
        for(int i = 0; i < data.length; ++i){
            data[i] ="好きな文字" + i ;
        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, data);
        listView1.setAdapter(arrayAdapter);
        */

        String sData = Commons.readString(getActivity(), "data_kikaku");
        try {
            JSONArray json = new JSONArray(sData);
            JSONObject json2 = new JSONObject(sData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TimeTableItem[] test = new TimeTableItem[6];
        for(int i = 0; i < 6; ++i) {
            test[i] = new TimeTableItem();
            test[i].setData((i+1) + ",団体名" + (i+1) + ",ここに概要がはいります," + (i+10) + ":00〜");
        }

        clickListenerTimeTable = new AdapterView.OnItemClickListener() {
            // 新着情報の項目をタップした時の処理
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // それぞれ文言を設定して表示する
                TextView textTitle = (TextView) viewTimeTable.findViewById(R.id.textName);
                textTitle.setText("団体名");
                ImageView imagePRCut = (ImageView) viewTimeTable.findViewById(R.id.imagePRCut);
                imagePRCut.setImageResource(getActivity().getResources().getIdentifier("dummy", "drawable", getActivity().getPackageName()));
                TextView textTime = (TextView) viewTimeTable.findViewById(R.id.textTime);
                textTime.setText("00:00〜");
                TextView textSummary = (TextView) viewTimeTable.findViewById(R.id.textSummary);
                textSummary.setText("概要");
                TextView textDetail = (TextView) viewTimeTable.findViewById(R.id.textDetail);
                textDetail.setText("PRコメント");
                alertTimeTable.show();
            }
        };

        TimeTableListAdapter arrayAdapterTimeTable = new TimeTableListAdapter(getActivity(), 0, test);
        listView1.setAdapter(arrayAdapterTimeTable);
        listView1.setOnItemClickListener(clickListenerTimeTable);
        listView2.setAdapter(arrayAdapterTimeTable);
        listView2.setOnItemClickListener(clickListenerTimeTable);
        //listView2.setAdapter(arrayAdapter);

        viewTimeTable = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.window_timetable, null);
        ImageButton buttonClose = (ImageButton) viewTimeTable.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            // 閉じるボタンを押した時の処理
            @Override
            public void onClick(View v) {
                alertTimeTable.dismiss();
            }
        });
        alertTimeTable = new AlertDialog.Builder(getActivity()).setView(viewTimeTable).create();

        return view;
    }


    class TimeTableItem {
        private int nNo;
        private String sName;
        private String sSummary;
        private String sTime;

        public TimeTableItem(){

        }

        public boolean setData(String sSource) {
            try {
                // 仮情報の設定
                String[] sBuf = sSource.split(",");
                nNo = Integer.parseInt(sBuf[0]);
                sName = sBuf[1];
                sSummary = sBuf[2];
                sTime = sBuf[3];
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        public int getNo() {
            return nNo;
        }
        public String getGroupName() {
            return sName;
        }
        public String getSummary() {
            return sSummary;
        }
        public String getTime() {
            return sTime;
        }

    }


    class TimeTableListAdapter extends ArrayAdapter<TimeTableItem> {
        private Context context;
        private LayoutInflater layoutInflater;

        public TimeTableListAdapter(Context context, int textViewResourceId, TimeTableItem[] objects) {
            super(context, textViewResourceId, objects);

            this.context = context;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TimeTableItem item = getItem(position);

            if (null == convertView) {
                convertView = layoutInflater.inflate(R.layout.listitem_timetable, null);
            }
            ImageView imagePRcut = (ImageView) convertView.findViewById(R.id.imagePRCut);
            imagePRcut.setImageResource(context.getResources().getIdentifier("dummy", "drawable", context.getPackageName()));
            TextView textName = (TextView) convertView.findViewById(R.id.textGroupName);
            textName.setText(item.getGroupName());
            TextView textSummary = (TextView) convertView.findViewById(R.id.textGroupSummary);
            textSummary.setText(item.getSummary());
            TextView textTime = (TextView) convertView.findViewById(R.id.textGroupTime);
            textTime.setText(item.getTime());

            return convertView;
        }
    }
}


