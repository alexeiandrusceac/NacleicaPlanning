package com.planning.nacleica;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.planning.nacleica.Database.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FragmentDoneTask extends Fragment {
    TextView noDataView;
    RecyclerView recyclerView;
    public List<Tasks> listOfDoneTask = new ArrayList<Tasks>();

    /*   public List<Tasks> listOfCancelTask = new ArrayList<Tasks>();
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment, container, false);
        return rootView;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        noDataView = (TextView) view.findViewById(R.id.noDataView);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        listOfDoneTask = dataBaseHelper.getWorkerDoneTask();

        if (listOfDoneTask.size() > 0) {
            noDataView.setVisibility(View.GONE);
            WorkerRecyclerViewAdapter adapter = new WorkerRecyclerViewAdapter(listOfDoneTask);
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        } else {
            noDataView.setVisibility(View.GONE);
        }
    }
}