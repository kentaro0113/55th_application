package com.chibafes.chibafes55;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    private AlertDialog alertInfo = null;
    private LinearLayout viewInfo = null;

    private InfoItem[] arrInfoItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_info, container, false);

        listview2 = (ListView) view.findViewById(R.id.listview2);

        int nCount = Commons.readInt(getActivity(), "category_count" + Statics.DATA_CATEGORY_INFO);
        arrInfoItem = new InfoItem[nCount];
        // 新着情報を読み込む
        // [フォーマット]
        // 1,[No],[件名],[本文],[時間]
        // ・新着情報はcategory_count1の件数分をNoの降順で読み込む
        // ・所定のフォーマットに当てはまらない記事は読み込まない
        int h = 0;
        for(int i = nCount - 1; i >= 0; --i) { // 降順にするためにデクリメントにする
            arrInfoItem[i] = new InfoItem();
            while(h < Statics.LIMIT_COUNT_NO) {
                String sBuf = Commons.readString(getActivity(), "data" + Statics.DATA_CATEGORY_INFO + "_" + h);
                ++h;
                if(arrInfoItem[i].setData(sBuf)) {
                    break;
                }
            }
        }

        // 新着情報をリストに反映する
        InfoListAdapter arrayAdapterInfo = new InfoListAdapter(getActivity(), 0, arrInfoItem);
        listview2.setAdapter(arrayAdapterInfo);
        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // 新着情報の項目をタップした時の処理
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InfoItem item = arrInfoItem[position];

                // それぞれ文言を設定して表示する
                TextView textTitle = (TextView) viewInfo.findViewById(R.id.textTitle);
                textTitle.setText(item.getNo() + ". " + item.getTitle());
                TextView textTime = (TextView) viewInfo.findViewById(R.id.textTime);
                textTime.setText(item.getTime("M/d h:m:s"));
                TextView textMessage = (TextView) viewInfo.findViewById(R.id.textMessage);
                textMessage.setText(item.getMessage());
                alertInfo.show();
            }
        });
        // 新着情報の詳細ウィンドウビューの設定
        viewInfo = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.window_info_detail, null);
        ImageButton buttonClose = (ImageButton) viewInfo.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            // 閉じるボタンを押した時の処理
            @Override
            public void onClick(View v) {
                alertInfo.dismiss();
            }
        });
        alertInfo = new AlertDialog.Builder(getActivity()).setView(viewInfo).create();

        return view;
    }
}

// 新着情報管理クラス
class InfoItem {
    private int nNo;            // 管理番号
    private String sTitle;      // タイトル
    private String sMessage;    // 本文
    private String sTime;       // 登録時間

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
    public String getTime(String sFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(sFormat);
        return sdf.format(new Date(Long.parseLong(sTime) * 1000));
    }

}

// 新着情報リスト用アダプタ
class InfoListAdapter extends ArrayAdapter<InfoItem> {
    private Context context;
    private LayoutInflater layoutInflater;

    public InfoListAdapter(Context context, int textViewResourceId, InfoItem[] objects) {
        super(context, textViewResourceId, objects);

        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // 一覧には見出しと時間だけ表示する
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InfoItem item = getItem(position);

        if (null == convertView) {
            convertView = layoutInflater.inflate(R.layout.listitem_info, null);
        }

        TextView textTitle = (TextView) convertView.findViewById(R.id.textTitle);
        TextView textTime = (TextView) convertView.findViewById(R.id.textTime);
        textTitle.setText(item.getTitle());
        textTime.setText(item.getTime("M/d h:m:s"));

        return convertView;
    }
}