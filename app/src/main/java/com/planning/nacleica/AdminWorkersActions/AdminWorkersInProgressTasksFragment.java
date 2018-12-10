package com.planning.nacleica.AdminWorkersActions;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.planning.nacleica.AdminActions.AdminActivity;
import com.planning.nacleica.Database.DataBaseHelper;
import com.planning.nacleica.R;
import com.planning.nacleica.Tasks;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@SuppressLint("ValidFragment")
public class AdminWorkersInProgressTasksFragment extends Fragment {
    public List<Tasks> listOfAdminWorkersInProgTask = new ArrayList<>();
    RecyclerView adminRecyclerView;
    public FragmentActivity fragmentActivity = getActivity();
    AdminActivity activity;

    public AdminWorkersInProgressTasksFragment(AdminActivity activity,List<Tasks> listOfAdminWorkersInProgTask) {
        this.activity = activity;
        this.listOfAdminWorkersInProgTask = listOfAdminWorkersInProgTask;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment, container, false);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        listOfAdminWorkersInProgTask = dataBaseHelper.getWorkersInProgTasks();

        adminRecyclerView = rootView.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(fragmentActivity);
        adminRecyclerView.setLayoutManager(layoutManager);
        final AdminWorkersInProgRecyclerAdapter adapter = new AdminWorkersInProgRecyclerAdapter(activity, listOfAdminWorkersInProgTask);
        new Thread(new Runnable() {
            @Override
            public void run() {
                fragmentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adminRecyclerView.setAdapter(adapter);
                    }
                });
            }
        }).start();
        return rootView;
    }
}
