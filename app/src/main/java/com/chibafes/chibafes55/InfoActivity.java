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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private ListView tableNewsList;
    private AlertDialog alertInfo = null;
    private LinearLayout viewInfo = null;

    private InfoItem[] arrInfoItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_info, container, false);

        tableNewsList = (ListView) view.findViewById(R.id.tableNewsList);

        String sNews = Commons.readString(getContext(), "data_news");
        if(sNews != null) {
            try {
                JSONArray arrayNewsData = new JSONArray(sNews);
                arrInfoItem = new InfoItem[arrayNewsData.length()];
                for(int i = 0; i < arrayNewsData.length(); ++i) {
                    arrInfoItem[i] = new InfoItem();
                    arrInfoItem[i].setData(arrayNewsData.getJSONObject(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // 新着情報をリストに反映する
        InfoListAdapter arrayAdapterInfo = new InfoListAdapter(getActivity(), 0, arrInfoItem);
        tableNewsList.setAdapter(arrayAdapterInfo);
        tableNewsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // 新着情報の項目をタップした時の処理
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InfoItem item = arrInfoItem[position];

                // それぞれ文言を設定して表示する
                TextView textTitle = (TextView) viewInfo.findViewById(R.id.textTitle);
                textTitle.setText(item.getTitle());
                TextView textTime = (TextView) viewInfo.findViewById(R.id.textTime);
                textTime.setText(item.getTime());
                TextView textMessage = (TextView) viewInfo.findViewById(R.id.textMessage);
                textMessage.setText(item.getDetail());
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
    private JSONObject data;

    public InfoItem(){
        data = null;
    }

    public boolean setData(JSONObject data) {
        try {
            this.data = data;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public int getId() {
        try {
            return data.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public String getTitle() {
        try {
            return data.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
    public String getDetail() {
        try {
            return data.getString("detail");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
    public String getTime() {
        try {
            String sTime = data.getString("update_time");
            return sTime.substring(0, 9);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
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
        textTime.setText(item.getTime());

        return convertView;
    }
}

/*
        case BUTTON_INFO_COSPLAY:
        case BUTTON_INFO_GRANDPRIX:
        case BUTTON_INFO_GOODS:
        case BUTTON_INFO_KIKAKU:
            infoKikakuView = [AppModule getStoryboardView:@"InfoKikakuView"];
            [infoKikakuView setViewId:(int)button.tag parent:self];
            [self presentViewController:infoKikakuView animated:YES completion:nil];
            break;

 */