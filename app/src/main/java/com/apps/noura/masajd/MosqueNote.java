package com.apps.noura.masajd;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class MosqueNote extends Fragment {

    private Button button;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view =inflater.inflate(R.layout.fragment_mosque_note, container, false);

        button = (Button) view.findViewById(R.id.AddNewNote);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : Check Login

                final AlertDialog.Builder  Dialog= new AlertDialog.Builder(getContext());
                View DialogView = getLayoutInflater().inflate(R.layout.dialog_note_check_login,null);
                Button OkButoon =  (Button) DialogView.findViewById(R.id.Ok);
                Dialog.setView(DialogView);
                final AlertDialog dialog =Dialog.create();
                dialog.show();
                OkButoon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


            }
        });


        return view;
    }


}
