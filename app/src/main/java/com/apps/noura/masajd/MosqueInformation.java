package com.apps.noura.masajd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Monirah on 13/12/17.
 */

//View Mosque Information (Fragment)
public class MosqueInformation extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;

        view = inflater.inflate(R.layout.fragment_mosque_information, container, false);

        Toast.makeText(getActivity(),"Information Fragment",Toast.LENGTH_SHORT).show();
        return view;
    }
}
