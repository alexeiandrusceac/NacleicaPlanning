package com.planning.nacleica.workeractions;


import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class FragmentInProgressTask extends Fragment {
    public List<Tasks> listOfInProgressTask = new ArrayList<Tasks>();
    RecyclerView recyclerWorkerView;
    int idWorker;
    mainActivity workerActivity;

    public FragmentInProgressTask(mainActivity mainActivity, int idWorker) {

        this.idWorker = idWorker;
        this.workerActivity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View workerRootView = inflater.inflate(R.layout.worker_fragment, container, false);

        recyclerWorkerView = (RecyclerView) workerRootView.findViewById(R.id.workerRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(workerActivity);
        recyclerWorkerView.setLayoutManager(layoutManager);
        final WorkerProgTaskRecyclerViewAdapter adapter = new WorkerProgTaskRecyclerViewAdapter(workerActivity);
        new Thread(new Runnable() {
            @Override
            public void run() {
                workerActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerWorkerView.setAdapter(adapter);
                    }
                });
            }
        }).start();
        return workerRootView;
    }

}