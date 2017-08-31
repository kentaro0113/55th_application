package com.chibafes.chibafes55;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by steee on 2017/08/31.
 */

public class InfoActivity extends Activity {

    private ListView listview1;
    private ListView listview2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // タイトルバーの表示設定：非表示にする
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // このActivityに関連づけるレイアウトの設定
        setContentView(R.layout.activity_info);

        listview1 = (ListView) findViewById(R.id.listview1);
        listview2 = (ListView) findViewById(R.id.listview2);

        String[] data = new String[8];
        for(int i = 0; i < data.length; i++){
            data[i] = "info" + i;
        }

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listview1.setAdapter(arrayAdapter1);
        listview2.setAdapter(arrayAdapter1);

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
}
