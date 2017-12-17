package com.apps.noura.masajd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


///Fragment
public class MosqueImage extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;

        view = inflater.inflate(R.layout.fragment_mosque_image, container, false);
        Toast.makeText(getActivity(),"View Image Fragment",Toast.LENGTH_SHORT).show();

        return view;
    }


}
