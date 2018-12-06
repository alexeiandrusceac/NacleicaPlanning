package com.planning.nacleica.AdminActions;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.planning.nacleica.Database.DataBaseHelper;
import com.planning.nacleica.R;
import com.planning.nacleica.Tasks;
import com.planning.nacleica.TextItemViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

public class AdminRecyclerViewAdapter extends RecyclerView.Adapter<AdminRecyclerViewAdapter.ViewHolder> {
    TextView noAdminDataView;
    Context context;
    List<Tasks> listOfTasks = new ArrayList<>();
    //DataBaseHelper dataBaseHelper;

    public AdminRecyclerViewAdapter(Context context, List<Tasks> tasksList) {
        this.context = context;
        this.listOfTasks = tasksList;
        //dataBaseHelper = DataBaseHelper.getInstance(context);
    }

    @Override
    public AdminRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list_item, parent, false);
        noAdminDataView = view.findViewById(R.id.noDataView);

        if (listOfTasks.size() > 0) {
            noAdminDataView.setVisibility(View.GONE);
        } else {
            noAdminDataView.setVisibility(View.VISIBLE);
        }
        AdminRecyclerViewAdapter.ViewHolder viewHolder = new AdminRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;/*new TextItemViewHolder(view);*/
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.taskTitle.setText(listOfTasks.get(position).TaskName);
        holder.compPhone.setText(listOfTasks.get(position).TaskCompanyPhone);
        holder.compName.setText(listOfTasks.get(position).TaskCompany);
        holder.dateFrom.setText(listOfTasks.get(position).TaskPeriodFrom);
        holder.dateTo.setText(listOfTasks.get(position).TaskPeriodTo);
        // holder.infoWorker.setText(getWorkerName(listOfTasks.get(position).idWorker));


    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return listOfTasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView compName, compPhone, infoWorker, taskTitle, idWorker, dateFrom, dateTo;
        public AppCompatImageView imageBefore;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            compName = itemLayoutView.findViewById(R.id.comp_name);
            compPhone = itemLayoutView.findViewById(R.id.comp_phone);
            //infoWorker = itemLayoutView.findViewById(R.id.infoWorker);

            taskTitle = itemLayoutView.findViewById(R.id.task_title);
            dateFrom = itemLayoutView.findViewById(R.id.task_periodFrom);
            dateTo = itemLayoutView.findViewById(R.id.task_periodTo);

            //idWorker =  itemLayoutView.findViewById(R.id.idView);
            // imageBefore = itemLayoutView.findViewById(R.id.workerImageView);

        }

    }
}
