package com.planning.nacleica.AdminActions;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.planning.nacleica.R;
import com.planning.nacleica.Tasks;
import com.planning.nacleica.TextItemViewHolder;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by anupamchugh on 05/10/16.
 */

public class AdminRecyclerViewAdapter extends RecyclerView.Adapter<TextItemViewHolder> {
    TextView noAdminDataView;
    List<Tasks> items;

    public AdminRecyclerViewAdapter(List<Tasks> items) {
        this.items = items;
    }

    @Override
    public TextItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list_item, parent, false);
        noAdminDataView = view.findViewById(R.id.noDataView);

        if (items.size() > 0) {
            noAdminDataView.setVisibility(View.GONE);
        } else {
            noAdminDataView.setVisibility(View.VISIBLE);
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
