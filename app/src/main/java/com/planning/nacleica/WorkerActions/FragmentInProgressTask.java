package com.planning.nacleica.WorkerActions;


import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.planning.nacleica.MainActivity;
import com.planning.nacleica.R;
import com.planning.nacleica.Tasks;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@SuppressLint("ValidFragment")
public class FragmentInProgressTask extends Fragment {
    public List<Tasks> listOfInProgressTask = new ArrayList<Tasks>();
    RecyclerView recyclerView;
    int idWorker;
    MainActivity mainActivity;
    public FragmentInProgressTask(MainActivity mainActivity,List<Tasks> listOfInProgressTask, int idWorker) {
        this.listOfInProgressTask = listOfInProgressTask;
        this.idWorker = idWorker;
        this.mainActivity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment, container, false);

        WorkerProgTaskRecyclerViewAdapter adapter = new WorkerProgTaskRecyclerViewAdapter(mainActivity,listOfInProgressTask);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

}