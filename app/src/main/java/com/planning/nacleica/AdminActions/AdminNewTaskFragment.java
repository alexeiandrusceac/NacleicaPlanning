package com.planning.nacleica.AdminActions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.planning.nacleica.R;

import com.planning.nacleica.Tasks;
import com.planning.nacleica.Database.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdminNewTaskFragment extends Fragment {
    public List<Tasks> listOfAdminTask = new ArrayList<>();
    TextView adminNoDataView;
    RecyclerView adminRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment, container, false);

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // adminNoDataView = (TextView) view.findViewById(R.id.adminNoDataView);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        listOfAdminTask = dataBaseHelper.getAdminNewTask();

        //String[] items = getResources().getStringArray(R.array.tab_B);
        AdminRecyclerViewAdapter adapter = new AdminRecyclerViewAdapter(listOfAdminTask);
        adminRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        adminRecyclerView.setLayoutManager(layoutManager);
        adminRecyclerView.setAdapter(adapter);

    }

}
