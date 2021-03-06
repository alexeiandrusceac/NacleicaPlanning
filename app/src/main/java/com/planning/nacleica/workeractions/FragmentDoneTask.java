package com.planning.nacleica.workeractions;


import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.planning.nacleica.R;
import com.planning.nacleica.Tasks;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.planning.nacleica.mainActivity;

@SuppressLint("ValidFragment")
public class FragmentDoneTask extends Fragment {
    TextView noDataView;
    int idWorker;
    RecyclerView recyclerView;

    mainActivity activity;
    public FragmentDoneTask(com.planning.nacleica.mainActivity mainActivity, int idWorker) {

        this.idWorker = idWorker;
        this.activity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.worker_fragment, container, false);
        noDataView = (TextView) rootView.findViewById(R.id.noTaskDataView);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.workerRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        final WorkerDoneTaskRecyclerViewAdapter adapter = new WorkerDoneTaskRecyclerViewAdapter(activity);
        new Thread(new Runnable() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        }).start();
        return rootView;


    }

}