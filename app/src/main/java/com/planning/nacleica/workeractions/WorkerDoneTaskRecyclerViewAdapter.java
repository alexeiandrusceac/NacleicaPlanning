package com.planning.nacleica.workeractions;


import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.planning.nacleica.database.DataBaseHelper;
import com.planning.nacleica.mainActivity;
import com.planning.nacleica.R;
import com.planning.nacleica.Tasks;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class WorkerDoneTaskRecyclerViewAdapter extends RecyclerView.Adapter<WorkerDoneTaskRecyclerViewAdapter.ViewHolder> {
    TextView noDataView;
    List<Tasks> listOfWorkerDoneTasks = new ArrayList<>();
    CardView cardView;
    private int indexChild;
    DataBaseHelper dataBaseHelper;
    mainActivity context;
    public WorkerDoneTaskRecyclerViewAdapter(mainActivity context) {

        this.context = context;
        this.listOfWorkerDoneTasks = context.listOfDoneTasks;

    }

    @Override
    public WorkerDoneTaskRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View workerDoneTasksListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.worker_recycler_iew_list, parent, false);
        noDataView = workerDoneTasksListView.findViewById(R.id.noTaskDataView);
        cardView = workerDoneTasksListView.findViewById(R.id.cardViewTask);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexChild = ((ViewGroup) workerDoneTasksListView.getParent()).indexOfChild(workerDoneTasksListView);
                listOfWorkerDoneTasks.get(indexChild).TaskState = 4;
                context.dbHelper.updateData(context,listOfWorkerDoneTasks.get(indexChild));
                listOfWorkerDoneTasks.remove(indexChild);
                context.refreshWorkerTasks();
            }
        });
        if (getItemCount() > 0) {
            noDataView.setVisibility(View.GONE);
        } else {
            noDataView.setVisibility(View.VISIBLE);
        }
        WorkerDoneTaskRecyclerViewAdapter.ViewHolder viewHolder = new WorkerDoneTaskRecyclerViewAdapter.ViewHolder(workerDoneTasksListView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        byte[] array = listOfWorkerDoneTasks.get(position).TaskImageAfter;
        holder.taskTitle.setText(listOfWorkerDoneTasks.get(position).TaskName);
        holder.compName.setText(listOfWorkerDoneTasks.get(position).TaskCompany);
        holder.dateFrom.setText(listOfWorkerDoneTasks.get(position).TaskPeriodFrom);
        holder.dateTo.setText(listOfWorkerDoneTasks.get(position).TaskPeriodTo);
        holder.imageAfter.setImageBitmap(BitmapFactory.decodeByteArray(array, 0, array.length));

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return listOfWorkerDoneTasks.size();
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
