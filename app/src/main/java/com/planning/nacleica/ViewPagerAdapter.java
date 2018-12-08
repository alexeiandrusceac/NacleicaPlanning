package com.planning.nacleica;

import android.content.Context;

import com.planning.nacleica.AdminActions.AdminActivity;
import com.planning.nacleica.AdminActions.AdminMaketTaskFragment;
import com.planning.nacleica.AdminActions.AdminNewTaskFragment;
import com.planning.nacleica.AdminWorkersActions.AdminWorkersDoneTasksFragment;
import com.planning.nacleica.AdminWorkersActions.AdminWorkersInProgressTasksFragment;
import com.planning.nacleica.AdminWorkersActions.AdminWorkersNewTasksFragment;

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
    public boolean taskforadmin = false;

    public ViewPagerAdapter(Context context, boolean taskforadmin, FragmentManager fm, int title, int idWorker) {
        super(fm);
        this.titleAccess = title;
        this.context = context;
        this.idWorker = idWorker;
        this.taskforadmin = taskforadmin;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        // verifica daca este lucrator simplu
        if (titleAccess != 4 && taskforadmin == true) {

            if (position == 0) {
                fragment = new AdminNewTaskFragment((AdminActivity) context);
            } else if (position == 1) {
                fragment = new AdminMaketTaskFragment((AdminActivity) context);
            }

        } else if (titleAccess != 4 && taskforadmin == false) {

            if (position == 0) {
                fragment = new AdminWorkersNewTasksFragment((AdminActivity) context);
            } else if (position == 1) {
                fragment = new AdminWorkersInProgressTasksFragment((AdminActivity) context);
            } else if (position == 2) {
                fragment = new AdminWorkersDoneTasksFragment((AdminActivity) context);
            }

        } else {
            if (position == 0) {
                fragment = new FragmentNewTask(idWorker);
            } else if (position == 1) {
                fragment = new FragmentInProgressTask(idWorker);
            } else if (position == 2) {
                fragment = new FragmentDoneTask(idWorker);
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
        if (titleAccess != 4 && taskforadmin == true) {
            if (position == 0) {
                title = context.getResources().getText(R.string.client_Tasks) + "(" + ((AdminNewTaskFragment) getItem(position)).listOfAdminNewTask.size() + ")";
            } else if (position == 1) {
                title = context.getResources().getText(R.string.maket_Tasks) + "(" + ((AdminMaketTaskFragment) getItem(position)).listOfAdminMaketTask.size() + ")";
            }
        } else if (titleAccess != 4 && taskforadmin == false) {

            if (position == 0) {
                title = context.getResources().getText(R.string.new_Tasks) + "(" + ((AdminWorkersNewTasksFragment) getItem(position)).listOfAdminWorkersNewTask.size() + ")";
            } else if (position == 1) {
                title = context.getResources().getText(R.string.prel_Tasks) + "(" + ((AdminWorkersInProgressTasksFragment) getItem(position)).listOfAdminWorkersInProgTask.size() + ")";
            } else if (position == 2) {
                title = context.getResources().getText(R.string.done_Tasks) + "(" + ((AdminWorkersDoneTasksFragment) getItem(position)).listOfAdminWorkersDoneTask.size() + ")";
            }
        } else {
            if (position == 0) {
                title = context.getResources().getText(R.string.new_Tasks) + "(" + ((FragmentNewTask) getItem(position)).listOfNewTask.size() + ")";
            } else if (position == 1) {
                title = context.getResources().getText(R.string.prel_Tasks) + "(" + ((FragmentInProgressTask) getItem(position)).listOfInProgressTask.size() + ")";
            } else if (position == 2) {
                title = context.getResources().getText(R.string.done_Tasks) + "(" + ((FragmentDoneTask) getItem(position)).listOfDoneTask.size() + ")";
            }
        }
        return title;
    }
}
