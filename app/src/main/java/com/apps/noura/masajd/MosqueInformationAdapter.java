package com.apps.noura.masajd;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monirah on 13/12/17.
 */

public class MosqueInformationAdapter extends FragmentPagerAdapter{

    //Keep Track for Fragment and Fragment Title:
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    //Fun : Add Fragment To Fragment List:
    public void AddFragment (Fragment fragment, String title){

        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);

    }//end AddFragment Fun

    //default constructor:
    public MosqueInformationAdapter(FragmentManager fm){

        super(fm);
    }

    //Return Page Title
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
       // return super.getPageTitle(position);
        return  mFragmentTitleList.get(position);

    }
//-----------------------End Return Page Title--------------------------------
    @Override
    public Fragment getItem(int position) {

        //return actual Fragment:
        return mFragmentList.get(position);

    }


    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
