package com.planning.nacleica;

import android.content.Context;

import com.planning.nacleica.adminactions.adminActivity;
import com.planning.nacleica.adminactions.AdminMaketTaskFragment;
import com.planning.nacleica.adminactions.AdminNewTaskFragment;
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
public class ViewPagerAdapter extends FragmentPagerAdapter {
    public int titleAccess;
    public int idWorker;
    public Context context;
    public boolean taskforadmin = false;

    DataBaseHelper dataBaseHelper;
    public List<Tasks>  listOfWorkerNewTasks, listOfWorkerProgTasks, listOfWorkerDoneTasks = new ArrayList<>();

    public ViewPagerAdapter(Context context, boolean taskforadmin, FragmentManager fm, int title, int idWorker) {
        super(fm);
        this.titleAccess = title;
        this.context = context;
        this.idWorker = idWorker;
        this.taskforadmin = taskforadmin;

        dataBaseHelper = DataBaseHelper.getInstance(context);
        if (titleAccess != 4) {

        } else {
            listOfWorkerNewTasks = dataBaseHelper.getWorkerNewTask(idWorker);
            listOfWorkerDoneTasks = dataBaseHelper.getWorkerDoneTask(idWorker);
            listOfWorkerProgTasks = dataBaseHelper.getWorkerInProgressTask(idWorker);
        }

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        // verifica daca este lucrator simplu
        if (titleAccess != 4 && taskforadmin == true) {

            if (position == 0) {
                fragment = new AdminNewTaskFragment(context);

            } else if (position == 1) {
                fragment = new AdminMaketTaskFragment(context);

            }

        } else if (titleAccess != 4 && taskforadmin == false) {

            if (position == 0) {
                fragment = new AdminWorkersNewTasksFragment((adminActivity) context, null);
            } else if (position == 1) {
                fragment = new AdminWorkersInProgressTasksFragment((adminActivity) context, null);
            } else if (position == 2) {
                fragment = new AdminWorkersDoneTasksFragment((adminActivity) context, null);
            }

        } else if (titleAccess == 4 && taskforadmin == false) {
            if (position == 0) {
                fragment = new FragmentNewTask((mainActivity) context, listOfWorkerNewTasks, idWorker);
            } else if (position == 1) {
                fragment = new FragmentInProgressTask((mainActivity) context, listOfWorkerProgTasks, idWorker);
            } else if (position == 2) {
                fragment = new FragmentDoneTask((mainActivity) context, listOfWorkerDoneTasks, idWorker);
            }
        }

        return fragment;
    }

    @Override
    public int getCount() {
        int count = 0;
        if (titleAccess != 4 && taskforadmin == true)
            count = 2;
        else if ((titleAccess != 4 && taskforadmin == false) || (titleAccess == 4 && taskforadmin == false))
            count = 3;

        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;

        if (titleAccess != 4 && taskforadmin == true) {
            if (position == 0) {
                title = context.getResources().getText(R.string.client_Tasks) + "(" + ((adminActivity)context).listOfAdminNewTasks.size()+ ")";
            } else if (position == 1) {
                title = context.getResources().getText(R.string.maket_Tasks) + "(" + /*listOfAdminMakTasks.size() + */")";
            }
        } else if (titleAccess != 4 && taskforadmin == false) {

            if (position == 0) {
                title = context.getResources().getText(R.string.new_Tasks) + "(" + /*listOfAdminWorkNewTasks.size() +*/ ")";
            } else if (position == 1) {
                title = context.getResources().getText(R.string.prel_Tasks) + "(" /*+ listOfAdminWorkProgTasks.size()*/ + ")";
            } else if (position == 2) {
                title = context.getResources().getText(R.string.done_Tasks) + "(" /*+ listOfAdminWorkDoneTasks.size()*/ + ")";
            }
        } else {
            if (position == 0) {
                title = context.getResources().getText(R.string.new_Tasks) + "(" + listOfWorkerNewTasks.size() + ")";
            } else if (position == 1) {
                title = context.getResources().getText(R.string.prel_Tasks) + "(" + listOfWorkerProgTasks.size() + ")";
            } else if (position == 2) {
                title = context.getResources().getText(R.string.done_Tasks) + "(" + listOfWorkerDoneTasks.size() + ")";
            }
        }
        return title;
    }
}
