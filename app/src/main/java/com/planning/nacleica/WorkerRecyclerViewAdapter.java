package com.planning.nacleica;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by anupamchugh on 05/10/16.
 */

public class WorkerRecyclerViewAdapter extends RecyclerView.Adapter<TextItemViewHolder> {
    TextView noDataView;
    List<Tasks> items;

    public WorkerRecyclerViewAdapter(List<Tasks> items) {
        this.items = items;
    }

    @Override
    public TextItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list_item, parent, false);
        noDataView = view.findViewById(R.id.noDataView);
        if (items.size() > 0) {
            noDataView.setVisibility(View.GONE);
        } else {
            noDataView.setVisibility(View.VISIBLE);
        }
        return new TextItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TextItemViewHolder holder, int position) {

        holder.bind(items);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
