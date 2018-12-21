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

public class WorkerNewTaskRecyclerViewAdapter extends RecyclerView.Adapter<WorkerNewTaskRecyclerViewAdapter.ViewHolder> {
    TextView noDataView;

    CardView cardView;
    private int indexChild;
    DataBaseHelper dataBaseHelper;
    mainActivity context;
    List<Tasks> listOfWorkerNewTasks = new ArrayList<>();

    public WorkerNewTaskRecyclerViewAdapter(mainActivity context) {
        this.context = context;
        this.listOfWorkerNewTasks = context.listOfNewTasks;

    }

    @Override
    public WorkerNewTaskRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View workerNewTasksListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.worker_recycler_iew_list, parent, false);
        noDataView = workerNewTasksListView.findViewById(R.id.noTaskDataView);
        cardView = workerNewTasksListView.findViewById(R.id.cardViewTask);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexChild = ((ViewGroup) workerNewTasksListView.getParent()).indexOfChild(workerNewTasksListView);
                listOfWorkerNewTasks.get(indexChild).TaskState = 3;
                context.dbHelper.updateWorker(context,listOfWorkerNewTasks.get(indexChild));
                listOfWorkerNewTasks.remove(indexChild);

                context.refreshWorkerTasks();
            }
        });
        if (getItemCount() > 0) {
            noDataView.setVisibility(View.GONE);
        } else {
            noDataView.setVisibility(View.VISIBLE);
        }
        WorkerNewTaskRecyclerViewAdapter.ViewHolder viewHolder = new WorkerNewTaskRecyclerViewAdapter.ViewHolder(workerNewTasksListView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        byte[] array = listOfWorkerNewTasks.get(position).TaskImageAfter;
        holder.taskTitle.setText(listOfWorkerNewTasks.get(position).TaskName);
        holder.compName.setText(listOfWorkerNewTasks.get(position).TaskCompany);
        holder.dateFrom.setText(listOfWorkerNewTasks.get(position).TaskPeriodFrom);
        holder.dateTo.setText(listOfWorkerNewTasks.get(position).TaskPeriodTo);
        holder.imageAfter.setImageBitmap(BitmapFactory.decodeByteArray(array, 0, array.length));

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return listOfWorkerNewTasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView compName, compPhone, taskTitle, dateFrom, dateTo;
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
