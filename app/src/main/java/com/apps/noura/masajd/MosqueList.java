package com.apps.noura.masajd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MosqueList extends Fragment  {
    // the fragment initialization

    private static final String TAG = "MosqueLIST";

    //-----------------------------------------------------------------


    public MosqueList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable  ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view;

        view = inflater.inflate(R.layout.fragment_mosque_list, container, false);

        //Create Recycle View:
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.MosqueRecyclerView);

        MosqueListAdapter mosqueListAdapter = new MosqueListAdapter();
        recyclerView.setAdapter(mosqueListAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        return  view;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }//end On create


}

