package com.apps.noura.masajd.Dawa;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noura Alsomaikhi on 12/31/2017.
 */

public class DawaSectionAdapter  extends FragmentPagerAdapter {


    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();



    public void AddFragment (Fragment fragment, String title){

        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);

    }




    public DawaSectionAdapter (FragmentManager fm) {
        super(fm);
    }


    @Override
    public CharSequence getPageTitle(int position) {


        return  mFragmentTitleList.get(position);
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {

        return mFragmentList.get(position);
    }


    @Override
    public int getCount() {

        //return Fragment List Size
        return mFragmentList.size();
    }
}

