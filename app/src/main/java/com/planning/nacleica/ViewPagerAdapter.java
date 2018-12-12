package com.planning.nacleica;

import android.content.Context;
import android.provider.ContactsContract;

import com.planning.nacleica.AdminActions.AdminActivity;
import com.planning.nacleica.AdminActions.AdminMaketTaskFragment;
import com.planning.nacleica.AdminActions.AdminNewTaskFragment;
import com.planning.nacleica.AdminWorkersActions.AdminWorkersDoneTasksFragment;
import com.planning.nacleica.AdminWorkersActions.AdminWorkersInProgressTasksFragment;
import com.planning.nacleica.AdminWorkersActions.AdminWorkersNewTasksFragment;
import com.planning.nacleica.Database.DataBaseHelper;
import com.planning.nacleica.WorkerActions.FragmentDoneTask;
import com.planning.nacleica.WorkerActions.FragmentInProgressTask;
import com.planning.nacleica.WorkerActions.FragmentNewTask;

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
                fragment = new AdminMaketTaskFragment((AdminActivity) context, null);

            }

        } else if (titleAccess != 4 && taskforadmin == false) {

            if (position == 0) {
                fragment = new AdminWorkersNewTasksFragment((AdminActivity) context, null);
            } else if (position == 1) {
                fragment = new AdminWorkersInProgressTasksFragment((AdminActivity) context, null);
            } else if (position == 2) {
                fragment = new AdminWorkersDoneTasksFragment((AdminActivity) context, null);
            }

        } else if (titleAccess == 4 && taskforadmin == false) {
            if (position == 0) {
                fragment = new FragmentNewTask((MainActivity) context, listOfWorkerNewTasks, idWorker);
            } else if (position == 1) {
                fragment = new FragmentInProgressTask((MainActivity) context, listOfWorkerProgTasks, idWorker);
            } else if (position == 2) {
                fragment = new FragmentDoneTask((MainActivity) context, listOfWorkerDoneTasks, idWorker);
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
                title = context.getResources().getText(R.string.client_Tasks) + "(" + ((AdminActivity)context).listOfAdminNewTasks.size()+ ")";
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
