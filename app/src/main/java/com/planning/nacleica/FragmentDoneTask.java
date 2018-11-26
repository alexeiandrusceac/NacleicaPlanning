package com.planning.nacleica;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.planning.nacleica.database.DataBaseHelper;

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

        /**The below code was when the ListView was used in place of RecyclerView. **/

        /*View view = inflater.inflate(R.layout.fragment_list, container, false);

        list = (ListView) view.findViewById(R.id.list);
        ArrayList stringList= new ArrayList();

        stringList.add("Item 3A");
        stringList.add("Item 3B");
        stringList.add("Item 3C");
        stringList.add("Item 3D");
        stringList.add("Item 3E");
        stringList.add("Item 3F");
        stringList.add("Item 3G");
        stringList.add("Item 3H");
        stringList.add("Item 3I");
        stringList.add("Item 3J");
        stringList.add("Item 3K");
        stringList.add("Item 3L");
        stringList.add("Item 3M");
        stringList.add("Item 3N");
        stringList.add("Item 3O");
        stringList.add("Item 3P");
        stringList.add("Item 3Q");
        stringList.add("Item 3R");
        stringList.add("Item 3S");
        stringList.add("Item 3T");
        stringList.add("Item 3U");
        stringList.add("Item 3V");
        stringList.add("Item 3W");
        stringList.add("Item 3X");
        stringList.add("Item 3Y");
        stringList.add("Item 3Z");

        CustomAdapter adapter = new CustomAdapter(stringList,getActivity());
        list.setAdapter(adapter);

        return view;*/
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        noDataView = (TextView) view.findViewById(R.id.noDataView);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());

        listOfDoneTask = dataBaseHelper.getTask();
        if (listOfDoneTask.size() > 0) {
            noDataView.setVisibility(View.GONE);

            RecyclerViewAdapter adapter = new RecyclerViewAdapter(listOfDoneTask);
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        } else {
            noDataView.setVisibility(View.GONE);
        }
    }
}