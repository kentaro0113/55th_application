package com.chibafes.chibafes55;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by steee on 2017/09/01.
 */


public class PageFragment extends Fragment
{
    private static final String PAGE = "PAGE";
    private OnFragmentInteractionListener onFragmentInteractionListener;

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
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        int page = getArguments().getInt(PAGE, 0);

        View view = inflater.inflate(R.layout.fragment_page, container, false);
        TextView textView =(TextView)view.findViewById(R.id.textView);
        textView.setText(String.valueOf(page));

        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            onFragmentInteractionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException();
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