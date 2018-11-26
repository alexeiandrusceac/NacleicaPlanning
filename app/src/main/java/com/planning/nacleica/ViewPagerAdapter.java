package com.planning.nacleica;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by Priyabrat on 21-08-2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new FragmentNewTask();
        }
        else if (position == 1)
        {
            fragment = new FragmentInProgressTask();
        }
        else if (position == 2)
        {
            fragment = new FragmentDoneTask();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Sarcini noi";
        }
        else if (position == 1)
        {
            title = "Sarcini prelucrare";
        }
        else if (position == 2)
        {
            title = "Sarcini finisate";
        }
        return title;
    }
}
