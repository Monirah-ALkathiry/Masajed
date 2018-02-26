package com.apps.noura.masajd;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monirah on 10/12/17.
 */

// Used For Fragment View (Button)

public class MosqueSectoinsAdapter  extends FragmentPagerAdapter{

    //Keep Track Fragment And Fragment Title:
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();



    //Fun : Add Fragment To Fragment List:
    public void AddFragment (Fragment fragment, String title ){

        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);


    }//end AddFragment Fun



    //Default Constructor
    public MosqueSectoinsAdapter(FragmentManager fm)  {
        super(fm);
    }


     @Override
    public CharSequence getPageTitle(int position) {
        //return super.getPageTitle(position);

        return  mFragmentTitleList.get(position);
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        //Return Actual Fragment
        return mFragmentList.get(position);
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {

        //return Fragment List Size
        return mFragmentList.size();
    }
}
