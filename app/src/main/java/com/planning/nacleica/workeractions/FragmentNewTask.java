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
public class FragmentNewTask extends Fragment {
    TextView noDataView;
    RecyclerView recyclerView;
    public int idWorker;
    public List<Tasks> listOfNewTask = new ArrayList<Tasks>();
    com.planning.nacleica.mainActivity mainActivity;
    @SuppressLint("ValidFragment")
    public FragmentNewTask(com.planning.nacleica.mainActivity mainActivity, List<Tasks> listOfNewTask, int idWorker) {
       this.idWorker = idWorker;
       this.mainActivity = mainActivity;
       this.listOfNewTask= listOfNewTask;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment, container, false);
        noDataView = (TextView) rootView.findViewById(R.id.noTaskDataView);

        WorkerNewTaskRecyclerViewAdapter adapter = new WorkerNewTaskRecyclerViewAdapter(mainActivity,listOfNewTask);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return rootView;

    }

}