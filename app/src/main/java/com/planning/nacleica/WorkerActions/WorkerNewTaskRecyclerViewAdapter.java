package com.planning.nacleica.WorkerActions;


import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.planning.nacleica.AuthActions.Worker;
import com.planning.nacleica.Database.DataBaseHelper;
import com.planning.nacleica.MainActivity;
import com.planning.nacleica.R;
import com.planning.nacleica.Tasks;
import com.planning.nacleica.TextItemViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class WorkerNewTaskRecyclerViewAdapter extends RecyclerView.Adapter<WorkerNewTaskRecyclerViewAdapter.ViewHolder> {
    TextView noDataView;
    List<Tasks> listOfWorkerNewTasks;
    CardView cardView;
    private int indexChild;
    DataBaseHelper dataBaseHelper;
    MainActivity context;
    public WorkerNewTaskRecyclerViewAdapter(MainActivity context, List<Tasks> listTasks) {
        this.listOfWorkerNewTasks = listTasks;
        this.context = context;
        dataBaseHelper = DataBaseHelper.getInstance(context);
    }

    @Override
    public WorkerNewTaskRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View workerNewTasksListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list_item, parent, false);
        noDataView = workerNewTasksListView.findViewById(R.id.noTaskDataView);
        cardView = workerNewTasksListView.findViewById(R.id.cardViewTask);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexChild = ((ViewGroup) workerNewTasksListView.getParent()).indexOfChild(workerNewTasksListView);
                listOfWorkerNewTasks.get(indexChild).TaskState = 3;
                dataBaseHelper.updateData(context,listOfWorkerNewTasks.get(indexChild));
                workerNewTasksListView.refreshDrawableState();
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
