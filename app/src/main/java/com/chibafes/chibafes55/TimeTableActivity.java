package com.chibafes.chibafes55;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
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
import android.widget.Button;
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
    private static final int BUTTON_TIMETABLE_TAB_DAY1 = 0;
    private static final int BUTTON_TIMETABLE_TAB_DAY2 = 1;
    private static final int BUTTON_TIMETABLE_TAB_DAY3 = 2;
    private static final int BUTTON_TIMETABLE_TAB_DAY4 = 3;
    private static final int BUTTON_TIMETABLE_TAB_CHECK = 4;

    private TextView viewNoFavorite;
    private Button buttonTabDay1;
    private Button buttonTabDay2;
    private Button buttonTabDay3;
    private Button buttonTabDay4;
    private Button buttonTabCheck;
    private ListView listView1;
    private ListView listView2;
    private AlertDialog alertTimeTable = null;
    private LinearLayout viewTimeTable = null;
    private LinearLayout viewTableBase;


    private AdapterView.OnItemClickListener clickListenerTimeTable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_timetable, container, false);

        // タブのボタンサイズを調整
        buttonTabDay1 = (Button) view.findViewById(R.id.buttonTabDay1);
        buttonTabDay2 = (Button) view.findViewById(R.id.buttonTabDay2);
        buttonTabDay3 = (Button) view.findViewById(R.id.buttonTabDay3);
        buttonTabDay4 = (Button) view.findViewById(R.id.buttonTabDay4);
        buttonTabCheck = (Button) view.findViewById(R.id.buttonTabCheck);

        Point displaySize = Commons.getDisplaySize(getContext());
        int nTabImageWidth = (displaySize.x - 56) / 5;
        int nTabImageHeight = nTabImageWidth * 4 / 7;

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) buttonTabDay1.getLayoutParams();
        params.width = nTabImageWidth;
        params.height = nTabImageHeight;
        buttonTabDay1.setLayoutParams(params);

        params = (LinearLayout.LayoutParams) buttonTabDay2.getLayoutParams();
        params.width = nTabImageWidth;
        params.height = nTabImageHeight;
        buttonTabDay2.setLayoutParams(params);

        params = (LinearLayout.LayoutParams) buttonTabDay3.getLayoutParams();
        params.width = nTabImageWidth;
        params.height = nTabImageHeight;
        buttonTabDay3.setLayoutParams(params);

        params = (LinearLayout.LayoutParams) buttonTabDay4.getLayoutParams();
        params.width = nTabImageWidth;
        params.height = nTabImageHeight;
        buttonTabDay4.setLayoutParams(params);

        params = (LinearLayout.LayoutParams) buttonTabCheck.getLayoutParams();
        params.width = nTabImageWidth;
        params.height = nTabImageHeight;
        buttonTabCheck.setLayoutParams(params);

        buttonTabDay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setList(BUTTON_TIMETABLE_TAB_DAY1);
            }
        });
        buttonTabDay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setList(BUTTON_TIMETABLE_TAB_DAY2);
            }
        });
        buttonTabDay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setList(BUTTON_TIMETABLE_TAB_DAY3);
            }
        });
        buttonTabDay4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setList(BUTTON_TIMETABLE_TAB_DAY4);
            }
        });
        buttonTabCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setList(BUTTON_TIMETABLE_TAB_CHECK);
            }
        });

        viewTableBase = (LinearLayout) view.findViewById(R.id.viewTableBase);
        viewNoFavorite = (TextView) view.findViewById(R.id.viewNoFavorite);

        // リスト
        listView1= (android.widget.ListView) view.findViewById(R.id.list_timetable);
        listView2= (android.widget.ListView) view.findViewById(R.id.list_timetable2);


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

        setList(BUTTON_TIMETABLE_TAB_DAY1);

        return view;
    }

    private void setList(int nextList) {
        buttonTabDay1.setAlpha(0.25f);
        buttonTabDay2.setAlpha(0.25f);
        buttonTabDay3.setAlpha(0.25f);
        buttonTabDay4.setAlpha(0.25f);
        buttonTabCheck.setAlpha(0.25f);
        viewNoFavorite.setVisibility(View.GONE);
        switch(nextList) {
            case BUTTON_TIMETABLE_TAB_DAY1:
                buttonTabDay1.setAlpha(1.0f);
                viewTableBase.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTabDay1));
                break;
            case BUTTON_TIMETABLE_TAB_DAY2:
                buttonTabDay2.setAlpha(1.0f);
                viewTableBase.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTabDay2));
                break;
            case BUTTON_TIMETABLE_TAB_DAY3:
                buttonTabDay3.setAlpha(1.0f);
                viewTableBase.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTabDay3));
                break;
            case BUTTON_TIMETABLE_TAB_DAY4:
                buttonTabDay4.setAlpha(1.0f);
                viewTableBase.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTabDay4));
                break;
            case BUTTON_TIMETABLE_TAB_CHECK:
                buttonTabCheck.setAlpha(1.0f);
                viewTableBase.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTabCheck));
                break;
        }
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
            TextView textTime = (TextView) convertView.findViewById(R.id.textGroupTime);
            textTime.setText(item.getTime());

            return convertView;
        }
    }
}


/*


@interface TimeTableView : UIViewController<UITableViewDelegate, UITableViewDataSource> {
    IBOutlet UITableView*   tableTimeTable;
    IBOutlet UIView*        viewNoCheck;

    NSArray*            arrayTimeTable;
    NSArray*            arrayData;
    NSMutableArray*     arrayCheck;

    int                 nCurrentList;
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];

    NSArray* arraySource = [NSArray arrayWithArray:[AppModule readData:@"data_kikaku"]];
    NSMutableArray* array = [NSMutableArray array];
    for(int i = 0; i < [arraySource count]; ++i) {
        NSDictionary *dic = [arraySource objectAtIndex:i];
        if([[dic objectForKey:@"type_id"] intValue] == 3 || [[dic objectForKey:@"type_id"] intValue] == 4) {
            [array addObject:dic];
        }
    }
    arrayData = [NSArray arrayWithArray:array];

    NSString* path = [[NSBundle mainBundle] pathForResource:@"TimeTable" ofType:@"plist"];
    arrayTimeTable = [NSArray arrayWithContentsOfFile:path];

}

- (void)setList:(int)nextList {
    if(nextList == BUTTON_TIMETABLE_TAB_CHECK) {
        [buttonCheck setAlpha:1.0f];
        [tableTimeTable setBackgroundColor:[UIColor colorWithRed:(251.0f / 255.0f) green:(255.0f / 255.0f) blue:(199.0f / 255.0f) alpha:1.0f]];
        arrayCheck = [NSMutableArray arrayWithArray:[AppModule readData:@"timetable_check"]];
        if(arrayCheck == nil || [arrayCheck count] <= 0) {
            [viewNoCheck setHidden:NO];
        }
    }
    nCurrentList = nextList;
    [tableTimeTable reloadData];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if([arrayData count] <= 0) {
        return 0;
    }
    if(nCurrentList == BUTTON_TIMETABLE_TAB_CHECK) {
        int nCount = 0;
        for(int i = 0; i < [arrayCheck count]; ++i) {
            NSDictionary *dic = [arrayCheck objectAtIndex:i];
            if([[dic objectForKey:@"array_index"] intValue] % 2 == section) {
                nCount++;
            }
        }
        return nCount;
    }

    return [[arrayTimeTable objectAtIndex:nCurrentList * 2 + section] count];
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"TimeTableCell"];

    AsyncImageView* image = (AsyncImageView*)[cell viewWithTag:1];
    UILabel* labelTime = (UILabel*)[cell viewWithTag:2];
    UILabel* labelName = (UILabel*)[cell viewWithTag:3];

    NSDictionary *targetTimeTable;
    int nDay = 0;
    if(nCurrentList == BUTTON_TIMETABLE_TAB_CHECK) {
        int nCount = -1;
        NSDictionary *dic;
        for(int i = 0; i < [arrayCheck count]; ++i) {
            dic = [arrayCheck objectAtIndex:i];
            if([[dic objectForKey:@"array_index"] intValue] % 2 == indexPath.section) {
                nCount++;
                if(indexPath.row == nCount) {
                    nDay = [[dic objectForKey:@"array_index"] intValue] / 2;
                    break;
                }
            }
        }
        NSArray *array = [arrayTimeTable objectAtIndex:[[dic objectForKey:@"array_index"] intValue]];
        for(int i = 0; i < [array count]; ++i) {
            if([[[array objectAtIndex:i] objectForKey:@"time"] intValue] == [[dic objectForKey:@"time_value"] intValue]) {
                targetTimeTable = [array objectAtIndex:i];
                break;
            }
        }
    }
    else {
        targetTimeTable = [[arrayTimeTable objectAtIndex:nCurrentList * 2 + indexPath.section] objectAtIndex:indexPath.row];
        nDay = nCurrentList;
    }
    NSUInteger nTargetIndex = [[arrayData valueForKey:@"id"] indexOfObject:[NSString stringWithFormat:@"%@", [targetTimeTable objectForKey:@"id"]]];
    NSDictionary *targetData = [arrayData objectAtIndex:nTargetIndex];

    [labelTime setText:[NSString stringWithFormat:@"11/%d %d:%02d〜", nDay + 2, [[targetTimeTable objectForKey:@"time"] intValue] / 100, [[targetTimeTable objectForKey:@"time"] intValue] % 100]];
    [labelName setText:[targetData objectForKey:@"name"]];
    // PRカット
    if([targetData objectForKey:@"image"] == nil || [[targetData objectForKey:@"image"] length] <= 0) {
        [image setImage:[UIImage imageNamed:@"no_image"]];
    }
    else {
        [image loadImage:URL_IMAGES name:[targetData objectForKey:@"image"]];
    }

    return cell;
}

// In a xib-based application, navigation from a table can be handled in -tableView:didSelectRowAtIndexPath:
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    TimeTableDetailView* detailView = [[TimeTableDetailView alloc] initWithNibName:@"TimeTableDetailView" bundle:nil];
    int nArrayIndex = 0;
    NSDictionary *targetTimeTable;
    if(nCurrentList == BUTTON_TIMETABLE_TAB_CHECK) {
        int nCount = -1;
        NSDictionary *dic;
        for(int i = 0; i < [arrayCheck count]; ++i) {
            dic = [arrayCheck objectAtIndex:i];
            if([[dic objectForKey:@"array_index"] intValue] % 2 == indexPath.section) {
                nCount++;
                if(indexPath.row == nCount) {
                    nArrayIndex = [[dic objectForKey:@"array_index"] intValue];
                    break;
                }
            }
        }
        NSArray *array = [arrayTimeTable objectAtIndex:[[dic objectForKey:@"array_index"] intValue]];
        for(int i = 0; i < [array count]; ++i) {
            if([[[array objectAtIndex:i] objectForKey:@"time"] intValue] == [[dic objectForKey:@"time_value"] intValue]) {
                targetTimeTable = [array objectAtIndex:i];
                break;
            }
        }
    }
    else {
        nArrayIndex = nCurrentList * 2 + (int)indexPath.section;
        targetTimeTable = [[arrayTimeTable objectAtIndex:nArrayIndex] objectAtIndex:indexPath.row];
    }
    NSUInteger nTargetIndex = [[arrayData valueForKey:@"id"] indexOfObject:[NSString stringWithFormat:@"%@", [targetTimeTable objectForKey:@"id"]]];
    NSDictionary *targetData = [arrayData objectAtIndex:nTargetIndex];
    [detailView setInfo:targetData parent:self arrayIndex:nArrayIndex time:[[targetTimeTable objectForKey:@"time"] intValue]];
    [self presentPopupViewController:detailView animationType:MJPopupViewAnimationSlideBottomBottom];
}


-(void)setCheckData {
    if(nCurrentList == BUTTON_TIMETABLE_TAB_CHECK) {
        [self setList:BUTTON_TIMETABLE_TAB_CHECK];
    }
}







@interface TimeTableDetailView : UIViewController {
    IBOutlet UILabel*           labelName;
    IBOutlet UIScrollView*      scrollBase;
    IBOutlet UILabel*           labelTime;
    IBOutlet AsyncImageView*       imagePRCut;
    IBOutlet UILabel*           labelSummary;
    IBOutlet UIImageView*       imageGenre1;
    IBOutlet UIImageView*       imageGenre2;
    IBOutlet UILabel*           labelDetail;
    IBOutlet UIButton*          buttonNice;
    IBOutlet UIButton*          buttonCheck;

    NSMutableDictionary*        dicInfo;
    TimeTableView*              timeTableView;
    NSMutableArray*             arrayCheck;
    BOOL                        bCheck;
}




- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];

    arrayCheck = [NSMutableArray arrayWithArray:[AppModule readData:@"timetable_check"]];
    if(arrayCheck == nil) {
        arrayCheck = [NSMutableArray array];
    }
    bCheck = ([self checkCheckd] != -1);
    [self setCheckIcon];

    [labelName setText:[dicInfo objectForKey:@"name"]];             // 名前
    [labelName setAdjustsFontSizeToFitWidth:YES];
    [labelTime setText:[dicInfo objectForKey:@"time"]];             // 日時
    [labelSummary setText:[dicInfo objectForKey:@"summary"]];       // 概要
    [labelSummary sizeToFit];
    [labelDetail setText:[dicInfo objectForKey:@"detail"]];         // 詳細
    [labelDetail sizeToFit];

    // PRカット
    if([dicInfo objectForKey:@"image"] == nil) {
        [imagePRCut setImage:[UIImage imageNamed:@"no_image"]];
    }
    else {
        [imagePRCut loadImage:URL_IMAGES name:[dicInfo objectForKey:@"image"]];
    }

    // ジャンルアイコン
    [imageGenre1 setImage:[UIImage imageNamed:[NSString stringWithFormat:@"icon_genre%d", [[dicInfo objectForKey:@"genre1"] intValue]]]];
    if([[dicInfo objectForKey:@"genre1"] intValue] == 0) {
        [imageGenre2 setHidden:YES];
    }
    else {
        [imageGenre2 setHidden:NO];
        [imageGenre2 setImage:[UIImage imageNamed:[NSString stringWithFormat:@"icon_genre%d", [[dicInfo objectForKey:@"genre2"] intValue]]]];
    }

}

- (void)setInfo:(NSDictionary*)dic parent:(TimeTableView*)parentView arrayIndex:(int)nArrayIndex time:(int)nTime {
    dicInfo = [NSMutableDictionary dictionaryWithDictionary:dic];
    [dicInfo setObject:[NSString stringWithFormat:@"11/%d %d:%02d〜", (nArrayIndex / 2) + 2, nTime / 100, nTime % 100] forKey:@"time"];
    [dicInfo setObject:[NSNumber numberWithInt:nArrayIndex] forKey:@"array_index"];
    [dicInfo setObject:[NSNumber numberWithInt:nTime] forKey:@"time_value"];
    timeTableView = parentView;
}

- (IBAction)selector:(id)sender {
    UIButton* button = (UIButton*)sender;

    switch(button.tag) {
        case BUTTON_TIMETALBE_CHECK: {
            bCheck = !bCheck;
            [self setCheckIcon];
            int nInsertTime = [[dicInfo objectForKey:@"array_index"] intValue] * 10000 + [[dicInfo objectForKey:@"time_value"] intValue];
            if(bCheck) {
                int i;
                for(i = 0; i < [arrayCheck count]; ++i) {
                    NSDictionary *dic = [arrayCheck objectAtIndex:i];
                    int nTargetTime = [[dic objectForKey:@"array_index"] intValue] * 10000 + [[dic objectForKey:@"time_value"] intValue];
                    if(nInsertTime < nTargetTime) {
                        break;
                    }
                }
                [arrayCheck insertObject:dicInfo atIndex:i];

                // 通知の作成
                UIUserNotificationType types = UIUserNotificationTypeSound | UIUserNotificationTypeAlert;
                UIUserNotificationSettings *mySettings = [UIUserNotificationSettings settingsForTypes:types categories:nil];
                [[UIApplication sharedApplication] registerUserNotificationSettings:mySettings];

                NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
                [dateFormatter setDateFormat:@"yyyy/MM/dd HH:mm:ss"];
                NSDate *targetDate = [dateFormatter dateFromString:[NSString stringWithFormat:@"2017/11/%02d %02d:%02d:00", (2 + [[dicInfo objectForKey:@"array_index"] intValue] / 2), [[dicInfo objectForKey:@"time_value"] intValue] / 100,  [[dicInfo objectForKey:@"time_value"] intValue] % 100]];
                targetDate = [targetDate initWithTimeInterval:-15 * 60 sinceDate:targetDate];

                UILocalNotification *notification = [[UILocalNotification alloc] init];
                notification.fireDate = targetDate;
                notification.timeZone = [NSTimeZone defaultTimeZone];
                notification.soundName = UILocalNotificationDefaultSoundName;
                if([[dicInfo objectForKey:@"array_index"] intValue] % 2 == 0) {
                    notification.alertBody = [NSString stringWithFormat:@"まもなく「%@」のステージがはじまります！", [dicInfo objectForKey:@"name"]];
                }
                else {
                    notification.alertBody = [NSString stringWithFormat:@"まもなく「%@」のストリートパフォーマンスがはじまります！", [dicInfo objectForKey:@"name"]];
                }
                notification.userInfo = [NSDictionary dictionaryWithObject:[NSString stringWithFormat:@"%d", nInsertTime] forKey:@"id"];
                [[UIApplication sharedApplication] scheduleLocalNotification:notification];
            }
            else {
                NSUInteger nTargetIndex = [self checkCheckd];
                [arrayCheck removeObjectAtIndex:nTargetIndex];
                // 通知の削除
                for(UILocalNotification *notification in [[UIApplication sharedApplication]     scheduledLocalNotifications]) {
                    if([[notification.userInfo objectForKey:@"id"] intValue] == nInsertTime) {
                        [[UIApplication sharedApplication] cancelLocalNotification:notification];
                    }
                }
            }
            [AppModule writeData:arrayCheck key:@"timetable_check"];
            [timeTableView setCheckData];
        }
            break;

    }
}

- (void)setCheckIcon {
    if(bCheck) {
        [buttonCheck setImage:[UIImage imageNamed:@"icon_favo_on"] forState:UIControlStateNormal];
    }
    else {
        [buttonCheck setImage:[UIImage imageNamed:@"icon_favo"] forState:UIControlStateNormal];
    }
}

- (int)checkCheckd {
    for(int i = 0; i < [arrayCheck count]; ++i) {
        NSDictionary* dic = [arrayCheck objectAtIndex:i];
        if([[dic objectForKey:@"array_index"] intValue] == [[dicInfo objectForKey:@"array_index"] intValue]
           && [[dic objectForKey:@"time_value"] intValue] == [[dicInfo objectForKey:@"time_value"] intValue]) {
            return i;
        }
    }
    return -1;
}

 */
