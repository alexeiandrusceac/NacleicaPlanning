package com.planning.nacleica.AdminActions;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class AdminMaketTaskFragment extends Fragment {
    public List<Tasks> listOfAdminMaketTask = new ArrayList<>();
    RecyclerView adminRecyclerView;
    public FragmentActivity fragmentActivity = getActivity();
    public AdminActivity activity;

    public AdminMaketTaskFragment (AdminActivity activity)
    {
        this.activity = activity;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment, container, false);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        listOfAdminMaketTask = dataBaseHelper.getAdminMaketTask();

        adminRecyclerView = rootView.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        adminRecyclerView.setLayoutManager(layoutManager);
        final AdminMaketTaskRecyclerViewAdapter adapter = new AdminMaketTaskRecyclerViewAdapter(activity,listOfAdminMaketTask);
        new Thread(new Runnable() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
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
