package com.chibafes.chibafes55;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.longevitysoft.android.xml.plist.domain.Dict;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steee on 2017/08/31.
 */

// 0903_kimura:ActivityからFragmentへ変更、それに伴う調整
public class InfoActivity extends Fragment {
    public static final int INFO_INDEX_NO      = 1;
    public static final int INFO_INDEX_TITLE   = 2;
    public static final int INFO_INDEX_MESSAGE = 3;
    public static final int INFO_INDEX_TIME    = 4;

    private ListView listview2;

    private InfoItem[] arrInfoItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_info, container, false);

        listview2 = (ListView) view.findViewById(R.id.listview2);

        int nCount = Commons.readInt(getActivity(), "category_count" + Statics.DATA_CATEGORY_INFO);
        InfoItem[] arrInfoItem = new InfoItem[nCount];
        // 新着情報を読み込む
        // [フォーマット]
        // 1,[No],[件名],[本文],[時間]
        // ・新着情報はcategory_count1の件数分をNoの降順で読み込む
        // ・所定のフォーマットに当てはまらない記事は読み込まない
        int h = 0;
        for(int i = nCount - 1; i >= 0; --i) {
            arrInfoItem[i] = new InfoItem();
            while(h < Statics.LIMIT_COUNT_NO) {
                String sBuf = Commons.readString(getActivity(), "data" + Statics.DATA_CATEGORY_INFO + "_" + h);
                ++h;
                if(arrInfoItem[i].setData(sBuf)) {
                    break;
                }
            }
        }

        InfoListAdapter arrayAdapterInfo = new InfoListAdapter(getActivity(), 0, arrInfoItem);
        listview2.setAdapter(arrayAdapterInfo);

        return view;
    }
}

class InfoItem {
    private int nNo;
    private String sTitle;
    private String sMessage;
    private String sTime;

    public InfoItem(){

    }

    public boolean setData(String sSource) {
        try {
            String[] arrBuf = sSource.split(",");
            nNo = Integer.parseInt(arrBuf[InfoActivity.INFO_INDEX_NO]);
            sTitle = arrBuf[InfoActivity.INFO_INDEX_TITLE];
            sMessage = arrBuf[InfoActivity.INFO_INDEX_MESSAGE];
            sTime = arrBuf[InfoActivity.INFO_INDEX_TIME];
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public int getNo() {
        return nNo;
    }
    public String getTitle() {
        return sTitle;
    }
    public String getMessage() {
        return sMessage;
    }
    public String getTime() {
        return sTime;
    }

}


class InfoListAdapter extends ArrayAdapter<InfoItem> {
    private Context context;
    private LayoutInflater layoutInflater;

    public InfoListAdapter(Context context, int textViewResourceId, InfoItem[] objects) {
        super(context, textViewResourceId, objects);

        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InfoItem item = getItem(position);

        if (null == convertView) {
            convertView = layoutInflater.inflate(R.layout.listitem_info, null);
        }

        TextView textTitle = (TextView) convertView.findViewById(R.id.textTitle);
        TextView textTime = (TextView) convertView.findViewById(R.id.textTime);
        textTitle.setText(item.getTitle());
        textTime.setText(item.getTime());

        return convertView;
    }
}