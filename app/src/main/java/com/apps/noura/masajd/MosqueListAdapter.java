package com.apps.noura.masajd;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Monirah on 14/12/17.
 */

//Used In Fragment to View List Information As Card View
public class MosqueListAdapter extends RecyclerView.Adapter {


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_mosque_item,parent,false);
        return new MosqueViewList(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MosqueViewList) holder).FillList(position);
    }


    @Override
    public int getItemCount() {

        return 15;
    }


    private class MosqueViewList extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Mosque List:

        private TextView mTextView;
        private TextView InfoTextView;
        private ImageView imageView;

        public MosqueViewList(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.MosqueName);
            InfoTextView = (TextView) view.findViewById(R.id.MosqueInfo);
            imageView = (ImageView) view.findViewById(R.id.MosqueImage);
            view.setOnClickListener(this);
        }

        public void FillList(int position) {

                  }

        @Override
        public void onClick(View view) {

        }
    }//end Inner Class
}
