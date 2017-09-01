package com.chibafes.chibafes55;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

/**
 * Created by aki09 on 2017/09/01.
 */

public class SettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // タイトルバーの表示設定：非表示にする
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // このActivityに関連づけるレイアウトの設定
        setContentView(R.layout.activity_setting);
    }

    public void onClickButtonAbout(View view){
        new AlertDialog.Builder(this)
                    .setTitle(null)
                    .setMessage("ChibaFlier  Copyright © 第55回千葉大祭実行委員会事務局")
                    .setPositiveButton("OK",null)
                    .show();
    }
    public void onClickButtonToWeb(View view) {
        // Webサイトへ遷移する
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Statics.URL_CHIBAFES_WEB));
        startActivity(intent);
        // 処理が終わったらこのActivityを破棄する
        finish();
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
