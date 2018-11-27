package com.planning.nacleica.AdminActions;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.planning.nacleica.R;
import com.planning.nacleica.Tasks;
import com.planning.nacleica.TextItemViewHolder;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by anupamchugh on 05/10/16.
 */

public class AdminRecyclerViewAdapter extends RecyclerView.Adapter<TextItemViewHolder> {

    List<Tasks> items;

    public AdminRecyclerViewAdapter(List<Tasks> items) {
        this.items = items;
    }

    @Override
    public TextItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list_item, parent, false);
        return new TextItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TextItemViewHolder holder, int position) {
        holder.bind(String.valueOf(items.get(position)));
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
