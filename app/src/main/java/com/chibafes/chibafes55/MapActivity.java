package com.chibafes.chibafes55;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.longevitysoft.android.xml.plist.domain.Array;
import com.qozix.tileview.TileView;

/**
 * Created by aki09 on 2017/09/08.
 */

public class MapActivity extends Fragment {
    private Array arraySpotList = null;
    private TileView imageMap = null;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_map, container, false);
        setMapInfo(view);

        return view;
    }


    private void setMapInfo(View view) {
        String newImage = null;
        int imageWidth = 0;
        int imageHeight = 0;

        LinearLayout viewMapBase = (LinearLayout) view.findViewById(R.id.viewMapBase);
        if(imageMap != null) {
            viewMapBase.removeView(imageMap);
            imageMap.destroy();
            imageMap = null;
        }

        imageMap = new TileView(getActivity());
        imageMap.setBackgroundColor(Color.WHITE);
        imageMap.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        viewMapBase.addView(imageMap);

        newImage = "map_nishichiba";
        imageWidth = 2000;
        imageHeight = 962;

        imageMap.setScaleLimits(0.1f, 3.0f);
        imageMap.setSize(imageWidth, imageHeight);

        imageMap.addDetailLevel(1.000f, newImage + "/1000/%d_%d.png");
        imageMap.addDetailLevel(0.500f, newImage + "/500/%d_%d.png");
        imageMap.addDetailLevel(0.250f, newImage + "/250/%d_%d.png");
        imageMap.addDetailLevel(0.125f, newImage + "/125/%d_%d.png");

        // スポット情報を取得する
        /*
        PList path = Commons.getParsedPlist(this, "SpotList.plist");
        Array list = (Array) path.getRootElement();
        arraySpotList = (Array) list.get(nNewMap);

        String[] data = new String[arraySpotList.size()];
        for(int i = 0;i < arraySpotList.size(); ++i) {
            Dict dict = (Dict) arraySpotList.get(i);
            data[i] = dict.getConfiguration("name").getValue();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        ListView listSpot = (ListView)findViewById(R.id.listSpot);
        listSpot.setAdapter(arrayAdapter);

        listSpot.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dict dic = (Dict) arraySpotList.get(position);

                for(int i = 0; i < imageMap.getMarkerLayout().getChildCount(); ++i) {
                    ImageView imageView = (ImageView) imageMap.getMarkerLayout().getChildAt(i);
                    if((dic.getConfiguration("name").getValue()).equals(((Dict)imageView.getTag()).getConfiguration("name").getValue())){
                        imageMap.moveToMarker(imageView, true);
                        break;
                    }
                }
            }
        });

        // スポットにボタンを配置する
        for (int i = 0; i < arraySpotList.size(); ++i) {
            Dict dic = (Dict) arraySpotList.get(i);
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.pin);
            imageView.setTag(dic);
            imageView.setOnClickListener(markerClickListener);
            imageMap.addMarker(imageView, dic.getConfigurationInteger("x").getValue(), dic.getConfigurationInteger("y").getValue(), -0.5f, -1.0f);
        }
        */

        imageMap.setScale(0.3f);

    }
}
