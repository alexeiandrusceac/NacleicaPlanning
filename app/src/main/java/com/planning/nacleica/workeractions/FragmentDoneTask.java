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


@SuppressLint("ValidFragment")
public class FragmentDoneTask extends Fragment {
    TextView noDataView;
    int idWorker;
    RecyclerView recyclerView;
    public List<Tasks> listOfDoneTask = new ArrayList<Tasks>();
    com.planning.nacleica.mainActivity mainActivity;
    public FragmentDoneTask(com.planning.nacleica.mainActivity mainActivity, List<Tasks> listOfDoneTask, int idWorker) {
        this.listOfDoneTask = listOfDoneTask;
        this.idWorker = idWorker;
        this.mainActivity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment, container, false);
        noDataView = (TextView) rootView.findViewById(R.id.noTaskDataView);

        WorkerDoneTaskRecyclerViewAdapter adapter = new WorkerDoneTaskRecyclerViewAdapter(mainActivity,listOfDoneTask);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return rootView;


    }

}