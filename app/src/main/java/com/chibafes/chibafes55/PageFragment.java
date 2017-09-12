package com.chibafes.chibafes55;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by steee on 2017/09/01.
 */


public class PageFragment extends Fragment
{
    private static final String PAGE = "PAGE";
    private OnFragmentInteractionListener onFragmentInteractionListener;
    private AlertDialog alertSearch = null;
    private ScrollView viewSearch;
    private ListView listKikaku;

    public PageFragment() {}

    public static PageFragment newInstance(int page)
    {
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE, page);

        PageFragment pageFragment = new PageFragment();
        pageFragment.setArguments(bundle);

        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewSearch = (ScrollView) getActivity().getLayoutInflater().inflate(R.layout.activity_kikakusearch, null);
        ImageButton buttonClose = (ImageButton) viewSearch.findViewById(R.id.buttonSearchClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertSearch.dismiss();
            }
        });
        Button buttonExec = (Button) viewSearch.findViewById(R.id.buttonSearchExec);
        buttonExec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KikakuItem[] test = new KikakuItem[6];
                for(int i = 0; i < 6; ++i) {
                    test[i] = new KikakuItem();
                    test[i].setData((i+1) + ",団体名" + (i+1) + ",ここに概要がはいります,A-" + (i+1));
                }

                KikakuListAdapter arrayAdapterKikaku = new KikakuListAdapter(getActivity(), 0, test);
                listKikaku.setAdapter(arrayAdapterKikaku);
                alertSearch.dismiss();
            }
        });
        alertSearch = new AlertDialog.Builder(getActivity()).setView(viewSearch).create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        int page = getArguments().getInt(PAGE, 0);

        View view = null;
        switch(page) {
            case 1:
                view = inflater.inflate(R.layout.activity_kikakulist, container, false);
                ImageButton buttonSearch = (ImageButton) view.findViewById(R.id.buttonSearch);
                buttonSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertSearch.show();
                    }
                });
                listKikaku =  (ListView) view.findViewById(R.id.listSearchResult);
                break;
            case 2:
                view = inflater.inflate(R.layout.activity_kikakufav, container, false);
                break;
        }
        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            onFragmentInteractionListener = (OnFragmentInteractionListener) context;
        //} else {
        //    throw new RuntimeException();
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        onFragmentInteractionListener = null;
    }

    interface OnFragmentInteractionListener
    {
        void onFragmentInteraction(Uri uri);
    }
}

class KikakuItem {
    private int nNo;
    private String sName;
    private String sSummary;
    private String sPlace;

    public KikakuItem(){

    }

    public boolean setData(String sSource) {
        try {
            // 仮情報の設定
            String[] sBuf = sSource.split(",");
            nNo = Integer.parseInt(sBuf[0]);
            sName = sBuf[1];
            sSummary = sBuf[2];
            sPlace = sBuf[3];
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
    public String getPlace() {
        return sPlace;
    }

}


class KikakuListAdapter extends ArrayAdapter<KikakuItem> {
    private Context context;
    private LayoutInflater layoutInflater;

    public KikakuListAdapter(Context context, int textViewResourceId, KikakuItem[] objects) {
        super(context, textViewResourceId, objects);

        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        KikakuItem item = getItem(position);

        if (null == convertView) {
            convertView = layoutInflater.inflate(R.layout.listitem_kikaku, null);
        }
        ImageView imagePRcut = (ImageView) convertView.findViewById(R.id.imagePRCut);
        imagePRcut.setImageResource(context.getResources().getIdentifier("dummy", "drawable", context.getPackageName()));
        TextView textName = (TextView) convertView.findViewById(R.id.textGroupName);
        textName.setText(item.getGroupName());
        TextView textSummary = (TextView) convertView.findViewById(R.id.textGroupSummary);
        textSummary.setText(item.getSummary());
        TextView textPlace = (TextView) convertView.findViewById(R.id.textGroupPlace);
        textPlace.setText(item.getPlace());

        return convertView;
    }
}