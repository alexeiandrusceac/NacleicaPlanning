package com.planning.nacleica.WorkerActions;


import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.planning.nacleica.Database.DataBaseHelper;
import com.planning.nacleica.MainActivity;
import com.planning.nacleica.R;
import com.planning.nacleica.Tasks;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class WorkerProgTaskRecyclerViewAdapter extends RecyclerView.Adapter<WorkerProgTaskRecyclerViewAdapter.ViewHolder> {
    TextView noDataView;
    List<Tasks> listOfWorkerProgTasks = new ArrayList<>();
    CardView cardView;
    private int indexChild;
    DataBaseHelper dataBaseHelper;
    MainActivity context;
    public WorkerProgTaskRecyclerViewAdapter(MainActivity context, List<Tasks> listTasks) {
        this.listOfWorkerProgTasks = listTasks;
        this.context = context;
        dataBaseHelper = DataBaseHelper.getInstance(context);
    }

    @Override
    public WorkerProgTaskRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View workerProgTasksListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list_item, parent, false);
        noDataView = workerProgTasksListView.findViewById(R.id.noTaskDataView);
        cardView = workerProgTasksListView.findViewById(R.id.cardViewTask);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexChild = ((ViewGroup) workerProgTasksListView.getParent()).indexOfChild(workerProgTasksListView);
                listOfWorkerProgTasks.get(indexChild).TaskState = 4;
                dataBaseHelper.updateData(context,listOfWorkerProgTasks.get(indexChild));
                workerProgTasksListView.refreshDrawableState();
            }
        });
        if (getItemCount() > 0) {
            noDataView.setVisibility(View.GONE);
        } else {
            noDataView.setVisibility(View.VISIBLE);
        }
        WorkerProgTaskRecyclerViewAdapter.ViewHolder viewHolder = new WorkerProgTaskRecyclerViewAdapter.ViewHolder(workerProgTasksListView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        byte[] array = listOfWorkerProgTasks.get(position).TaskImageAfter;
        holder.taskTitle.setText(listOfWorkerProgTasks.get(position).TaskName);
        holder.compName.setText(listOfWorkerProgTasks.get(position).TaskCompany);
        holder.dateFrom.setText(listOfWorkerProgTasks.get(position).TaskPeriodFrom);
        holder.dateTo.setText(listOfWorkerProgTasks.get(position).TaskPeriodTo);
        holder.imageAfter.setImageBitmap(BitmapFactory.decodeByteArray(array, 0, array.length));

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return listOfWorkerProgTasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView compName, taskTitle, dateFrom, dateTo;
        public AppCompatImageView imageAfter;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            compName = itemLayoutView.findViewById(R.id.comp_name);
            taskTitle = itemLayoutView.findViewById(R.id.task_title);
            dateFrom = itemLayoutView.findViewById(R.id.task_periodFrom);
            dateTo = itemLayoutView.findViewById(R.id.task_periodTo);
            imageAfter = itemLayoutView.findViewById(R.id.imageView);

        }


    }
}
