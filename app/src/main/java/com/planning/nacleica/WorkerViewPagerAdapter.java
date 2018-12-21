package com.planning.nacleica;

import android.content.Context;

import com.planning.nacleica.adminactions.AdminMaketTaskFragment;
import com.planning.nacleica.adminactions.AdminNewTaskFragment;
import com.planning.nacleica.adminactions.adminActivity;
import com.planning.nacleica.adminworkersactions.AdminWorkersDoneTasksFragment;
import com.planning.nacleica.adminworkersactions.AdminWorkersInProgressTasksFragment;
import com.planning.nacleica.adminworkersactions.AdminWorkersNewTasksFragment;
import com.planning.nacleica.database.DataBaseHelper;
import com.planning.nacleica.workeractions.FragmentDoneTask;
import com.planning.nacleica.workeractions.FragmentInProgressTask;
import com.planning.nacleica.workeractions.FragmentNewTask;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by alexei on 21-08-2015.
 */
public class WorkerViewPagerAdapter extends FragmentPagerAdapter {
    public int titleAccess;
    public int idWorker;
    public Context context;


    DataBaseHelper dataBaseHelper;

    public WorkerViewPagerAdapter(Context context, FragmentManager fm, int idWorker) {
        super(fm);
        this.context = context;
        this.idWorker = idWorker;

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        if (position == 0) {
            fragment = new FragmentNewTask((mainActivity) context, idWorker);
        } else if (position == 1) {
            fragment = new FragmentInProgressTask((mainActivity) context, idWorker);
        } else if (position == 2) {
            fragment = new FragmentDoneTask((mainActivity) context, idWorker);
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
        if (position == 0) {
            title = context.getResources().getText(R.string.new_Tasks) + "(" + ((mainActivity) context).listOfNewTasks.size() + ")";
        } else if (position == 1) {
            title = context.getResources().getText(R.string.prel_Tasks) + "(" + ((mainActivity) context).listOfInProgressTasks.size() + ")";
        } else if (position == 2) {
            title = context.getResources().getText(R.string.done_Tasks) + "(" + ((mainActivity) context).listOfDoneTasks.size() + ")";
        }

        return title;
    }
}
