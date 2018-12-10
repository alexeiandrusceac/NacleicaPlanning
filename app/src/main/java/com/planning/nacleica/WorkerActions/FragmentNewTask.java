package com.planning.nacleica.WorkerActions;


import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.planning.nacleica.Database.DataBaseHelper;
import com.planning.nacleica.R;
import com.planning.nacleica.Tasks;
import com.planning.nacleica.WorkerRecyclerViewAdapter;

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
    ListView list;
    public int idWorker;
    public List<Tasks> listOfNewTask = new ArrayList<Tasks>();

    @SuppressLint("ValidFragment")
    public FragmentNewTask(int idWorker) {
       this.idWorker = idWorker;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment, container, false);
        noDataView = (TextView) rootView.findViewById(R.id.noTaskDataView);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        listOfNewTask = dataBaseHelper.getWorkerNewTask(idWorker);

        WorkerRecyclerViewAdapter adapter = new WorkerRecyclerViewAdapter(listOfNewTask);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return rootView;

    }

}