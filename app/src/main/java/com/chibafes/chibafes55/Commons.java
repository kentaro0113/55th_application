package com.chibafes.chibafes55;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.longevitysoft.android.xml.plist.PListXMLHandler;
import com.longevitysoft.android.xml.plist.PListXMLParser;
import com.longevitysoft.android.xml.plist.domain.PList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Commons
 * Created by llrk on 2017/02/05.
 * 汎用関数クラス
 */

public class Commons {
    // Androidバージョンコードを取得する
    public static int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        int versionCode = 0;
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch(Exception e) {
            //
        }

        return versionCode;
    }

    // 0～引数の値未満のランダムな正数を取得する
    public static int getRandom(int nMax) {
        return (int)(Math.random() * nMax);
    }

    // 文字列を保存する
    public static void writeString(Context context, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = pref.edit();
        e.putString(key, value);
        e.commit();
    }
    // int型整数を保存する
    public static void writeInt(Context context, String key, int value) {
        SharedPreferences pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = pref.edit();
        e.putInt(key, value);
        e.commit();
    }
    // long型整数を保存する
    public static void writeLong(Context context, String key, long value) {
        SharedPreferences pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = pref.edit();
        e.putLong(key, value);
        e.commit();
    }

    // 文字列を読み込む
    public static String readString(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        return pref.getString(key, null);
    }
    // int型整数を読み込む
    public static int readInt(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        return pref.getInt(key, Statics.NONE);
    }
    // long型整数を読み込む
    public static long readLong(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        return pref.getLong(key, Statics.NONE);
    }

    // 対象の画像の縦横幅を取得する
    public static PointF getImageSize(ImageView image) {
        Rect rect = image.getDrawable().getBounds();
        float scaleX = (float) image.getWidth() / (float) rect.width();
        float scaleY = (float) image.getHeight() / (float) rect.height();
        float scale = Math.min(scaleX, scaleY);
        float w = scale * rect.width();
        float h = scale * rect.height();
        return new PointF(w, h);
    }

    // 画面サイズを取得する
    public static Point getDisplaySize(Context context){
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point;
    }

    // Assetsフォルダ内のテキストを取得する
    public static String getAssetsText(Context context, String path) {
        InputStream is = null;
        BufferedReader br = null;
        String result = "";

        try {
            try {
                is = context.getAssets().open(path);
                br = new BufferedReader(new InputStreamReader(is));

                // １行ずつ読み込み、改行を付加する
                String str;
                while ((str = br.readLine()) != null) {
                    result += str + "\n";
                }
            } finally {
                if (is != null) is.close();
                if (br != null) br.close();
            }
        } catch (Exception e){
            // エラー発生時の処理
        }

        return result;
    }

    // Assetsフォルダ内のPlistファイルをパースして取得する
    public static PList getParsedPlist(Context context, String path) {
        PListXMLHandler handler = new PListXMLHandler();
        PListXMLParser parser = new PListXMLParser();
        parser.setHandler(handler);

        try {
            // assets フォルダの plist ファイルを開き、パースする
            parser.parse(context.getResources().getAssets().open(path));
        }
        catch (IllegalStateException e){
            // エラー処理を実装する
        }
        catch (IOException e){
            // エラー処理を実装する
        }
        PList plist = ((PListXMLHandler) parser.getHandler()).getPlist();
        return plist;
    }

    // ウェイトを行う
    public static void setWait(int nTime) {
        try {
            Thread.sleep(nTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 現在時間を指定したフォーマットの文字列で取得する
    public static String getTimeString(String formatString) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        return sdf.format(date);
    }

}