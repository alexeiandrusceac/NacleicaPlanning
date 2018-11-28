package com.planning.nacleica;

import com.planning.nacleica.AdminActions.AdminMaketTaskFragment;
import com.planning.nacleica.AdminActions.AdminNewTaskFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by alexei on 21-08-2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    public int titleAccess;

    public ViewPagerAdapter(FragmentManager fm, int title) {
        super(fm);
        this.titleAccess = title;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (titleAccess == 4) {
            if (position == 0) {
                fragment = new FragmentNewTask();
            } else if (position == 1) {
                fragment = new FragmentInProgressTask();
            } else if (position == 2) {
                fragment = new FragmentDoneTask();
            }
        } else {
            if (position == 0) {
                fragment = new AdminNewTaskFragment();
            } else if (position == 1) {
                fragment = new AdminMaketTaskFragment();
            }
        }
        return fragment;
    }

    @Override
    public int getCount() {


        return (titleAccess == 4) ?  3 : 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (titleAccess == 4) {
            if (position == 0) {
                title = "Sarcini noi";
            } else if (position == 1) {
                title = "Sarcini prelucrare";
            } else if (position == 2) {
                title = "Sarcini finisate";
            }
        }
        else {
            if (position == 0) {
                title = "Intilnire client";
            } else if (position == 1) {
                title = "Sarcina la machetare";
            }
        }
        return title;
    }
}
