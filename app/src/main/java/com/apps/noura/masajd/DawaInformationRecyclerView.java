package com.apps.noura.masajd;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Noura Alsomaikhi on 1/10/2018.
 */

class DawaInformationRecyclerView extends RecyclerView.Adapter<DawaInformationRecyclerView.DawaViewInformationList> {

    Intent intent;

    public DawaInformationRecyclerView(Intent intent) {
        this.intent = intent;
    }

    @Override
    public DawaInformationRecyclerView.DawaViewInformationList onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dawa_information_list, parent, false);

        return new DawaInformationRecyclerView.DawaViewInformationList(view);
    }

    @Override
    public void onBindViewHolder(DawaInformationRecyclerView.DawaViewInformationList holder, int position) {
// Dawa Activity information :
        // #1 Office
        String DawaOffice;
        DawaOffice = intent.getStringExtra("DawaOffice");
        holder.DawaOffice.setText(intent.getStringExtra("DawaOffice"));

        //#2 Date DawaActivityDateH
        String DawaReqDateH;
        holder.DawaActivityDateH.setText(intent.getStringExtra("DawaActivityDateH"));

        //#3 Time  DawaActivityTime
        holder.DawaActivityTime.setText(intent.getStringExtra("DawaActivityTime"));

        //#4 Days  DawaActivityRepDays
        holder.DawaActivityRepDays.setText(intent.getStringExtra("DawaActivityRepDays"));

        //#5 Daiah name FirstName + FatherName + GrandFatherName + FamilyName
        String FirstName, FatherName, GrandFatherName, FamilyName , Name;
        FirstName = intent.getStringExtra("FirstName");
        FatherName = intent.getStringExtra("FatherName");
        GrandFatherName = intent.getStringExtra("GrandFatherName");
        FamilyName = intent.getStringExtra("FamilyName");
        Name = FirstName+" "+FatherName+" "+GrandFatherName+ " " +FamilyName;
        holder.Name.setText(Name);

        //#6 language DawaActivLanguage
        holder.DawaActivLanguage.setText(intent.getStringExtra("DawaActivLanguage"));

        //#7 WomenPlaceAvailability
        String WomenPlaceAvailability;
        WomenPlaceAvailability = intent.getStringExtra("WomenPlaceAvailability");
        System.out.println("(:(:(:(" + WomenPlaceAvailability);
       int WA = Integer.parseInt(WomenPlaceAvailability);
        if (WA == 0) {
            holder.WomenPlaceAvailability.setText("غير متوفر");
        } else if (WA == 1) {
            holder.WomenPlaceAvailability.setText("متوفر");
        }




    }

    @Override
    public int getItemCount() {
        return 1;

    }

    public class DawaViewInformationList extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView DawaOffice;
        private TextView DawaActivityDateH;
        private TextView DawaActivityTime;
        private TextView DawaActivityRepDays;
        private TextView Name;
        private TextView DawaActivLanguage;
        private TextView WomenPlaceAvailability;
        private LinearLayout linearLayout;


        public DawaViewInformationList(View itemView) {
            super(itemView);

            DawaOffice = (TextView) itemView.findViewById(R.id.DawaOffice);
            DawaActivityDateH = (TextView) itemView.findViewById(R.id.DawaActivityDateH);
            DawaActivityTime = (TextView) itemView.findViewById(R.id.DawaActivityTime);
            DawaActivityRepDays = (TextView) itemView.findViewById(R.id.DawaActivityRepDays);
            Name = (TextView) itemView.findViewById(R.id.Name);
            DawaActivLanguage = (TextView) itemView.findViewById(R.id.DawaActivLanguage);
            WomenPlaceAvailability = (TextView) itemView.findViewById(R.id.WomenPlaceAvailability);
            //linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutInfo);


        }

        @Override
        public void onClick(View view) {

        }
    }
}