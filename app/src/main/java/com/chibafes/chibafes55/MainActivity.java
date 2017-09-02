package com.chibafes.chibafes55;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

/**
 * Main Activity
 * Created by llrk on 2017/08/04.
 * 起動時に呼び出される画面
 */

public class MainActivity extends Activity implements HttpPostAsync.AsyncTaskCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // タイトルバーの表示設定：非表示にする
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // このActivityに関連づけるレイアウトの設定
        setContentView(R.layout.activity_main);

        // 仮：通信なしで次の画面へ遷移する処理へ
        checkRunState();

        // アンケートや情報などをネットワーク更新する処理を開始する
        HttpPostAsync postObject = new HttpPostAsync(this);
        postObject.execute(Statics.URL_UPDATE, "lastdate=" + Commons.readLong(this, "lastdate"));
    }

    // ネットワーク更新前処理
    public void preExecute() {
    }
    // ネットワーク更新後処理
    public void postExecute(String result, boolean bError) {
        if(result != null && !bError) {
            // 正常にデータが取得できた場合、更新処理行う

        }
        checkRunState();
    }
    // 通信中の処理
    public void progressUpdate(int progress) {
    }
    // 通信キャンセル時の処理
    public void cancel() {
        checkRunState();
    }

    private void checkRunState() {
        // アプリインストール後の初回起動かどうかのチェックを行う
        if(Commons.readInt(this, "Course") == Statics.NONE) {
            // 初回起動なら初回起動用の画面へ遷移する
            Intent intent = new Intent(MainActivity.this, FirstRunActivity.class);
            startActivity(intent);
            // 処理が終わったらこのActivityを破棄する
            finish();
        }
        else {
            // 初回起動でなければメインメニューへ遷移する
            Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
            startActivity(intent);
            // 処理が終わったらこのActivityを破棄する
            finish();
        }
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
