package com.planning.nacleica;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.planning.nacleica.Database.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentNewTask extends Fragment {
    TextView noDataView;
    RecyclerView recyclerView;
    ListView list;
    public List<Tasks> listOfNewTask = new ArrayList<Tasks>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment, container, false);
        //noDataView = (TextView) rootView.findViewById(R.id.noDataView);

        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        listOfNewTask = dataBaseHelper.getWorkerNewTask();

        WorkerRecyclerViewAdapter adapter = new WorkerRecyclerViewAdapter(listOfNewTask);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}