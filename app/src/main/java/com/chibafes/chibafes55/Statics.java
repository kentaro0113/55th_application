package com.chibafes.chibafes55;

/**
 * Statics
 * Created by llrk on 2017/08/05.
 * 定数を記述するファイル
 */

public class Statics {
    public static final String URL_UPDATE       = "http://demo.chibafes.com/appli/php/55th/update.php";
    public static final String URL_ENQUETE      = "http://demo.chibafes.com/appli/php/55th/enquete.php";
    public static final String URL_CHIBAFES_WEB = "http://chibafes.com/";

    public static final int NONE                = -1;
    public static final int LIMIT_COUNT_NO      = 99999; // 各リストの最大index値（保険）

    public static final int TYPE_SELECT         = 1; // アンケート：選択式
    public static final int TYPE_TEXT           = 2; // アンケート：記述式

    public static final int DATA_CATEGORY_INFO      = 1; // データカテゴリ：新着情報
    public static final int DATA_CATEGORY_KIKAKU    = 2; // データカテゴリ：企画
    public static final int DATA_CATEGORY_STAGE     = 3; // データカテゴリ：ステージ
    public static final int DATA_CATEGORY_STREET    = 4; // データカテゴリ：ストパ
}
