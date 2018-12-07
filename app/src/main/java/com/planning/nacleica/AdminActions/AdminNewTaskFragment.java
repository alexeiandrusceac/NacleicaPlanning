package com.planning.nacleica.AdminActions;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.planning.nacleica.R;
import com.planning.nacleica.Tasks;
import com.planning.nacleica.Database.DataBaseHelper;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdminNewTaskFragment extends Fragment {
    public List<Tasks> listOfAdminNewTask = new ArrayList<>();
    public FragmentActivity fragmentActivity = getActivity();
    RecyclerView adminRecyclerView;
    public AdminNewTaskFragment(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment, container, false);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        listOfAdminNewTask = dataBaseHelper.getAdminNewTask();

        adminRecyclerView = rootView.findViewById(R.id.recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(fragmentActivity);
        adminRecyclerView.setLayoutManager(layoutManager);
        final AdminNewTasksRecyclerViewAdapter adapter = new AdminNewTasksRecyclerViewAdapter(listOfAdminNewTask);
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
