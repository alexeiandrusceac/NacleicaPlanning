package com.planning.nacleica;

import android.content.Context;

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
    public int idWorker;
    public Context context;
    public ViewPagerAdapter(Context context,FragmentManager fm, int title, int idWorker) {
        super(fm);
        this.titleAccess = title;
        this.context = context;
        this.idWorker = idWorker;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        if (titleAccess == 4) {
            if (position == 0) {
                fragment = new FragmentNewTask(idWorker);
            } else if (position == 1) {
                fragment = new FragmentInProgressTask(idWorker);
            } else if (position == 2) {
                fragment = new FragmentDoneTask(idWorker);
            }
        } else {
            if (position == 0) {
                fragment = new  AdminNewTaskFragment();
            } else if (position == 1) {
                fragment = new AdminMaketTaskFragment();
            }

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return (titleAccess == 4) ? 3 : 2;
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
        } else {
            if (position == 0) {
                title = "Intilnire client";
            } else if (position == 1) {
                title = "Sarcina la machetare";
            }
        }
        return title;
    }
}
