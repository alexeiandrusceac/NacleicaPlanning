package com.planning.nacleica.adminactions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.planning.nacleica.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@SuppressLint("ValidFragment")
public class AdminMaketTaskFragment extends Fragment {
    RecyclerView adminMaketTaskRecyclerView;
    adminActivity activity;

    public AdminMaketTaskFragment (Context activity)
    {
        this.activity = (adminActivity) activity;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment, container, false);

        adminMaketTaskRecyclerView = rootView.findViewById(R.id.recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        adminMaketTaskRecyclerView.setLayoutManager(layoutManager);
        final AdminMaketTaskRecyclerViewAdapter adapter = new AdminMaketTaskRecyclerViewAdapter(activity);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ((AppCompatActivity) activity).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adminMaketTaskRecyclerView.setAdapter(adapter);
                    }
                });
            }
        }).start();
        return rootView;
    }
}
