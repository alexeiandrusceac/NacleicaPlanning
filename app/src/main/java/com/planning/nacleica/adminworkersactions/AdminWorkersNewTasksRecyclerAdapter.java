package com.planning.nacleica.adminworkersactions;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.planning.nacleica.adminactions.adminActivity;
import com.planning.nacleica.authactions.Worker;
import com.planning.nacleica.database.DataBaseHelper;
import com.planning.nacleica.R;
import com.planning.nacleica.Tasks;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

public class AdminWorkersNewTasksRecyclerAdapter extends RecyclerView.Adapter<AdminWorkersNewTasksRecyclerAdapter.ViewHolder> {
    TextView noAdminDataView;
    adminActivity activity;
    List<Tasks> listsOfNewTasks = new ArrayList<>();
    DataBaseHelper dataBaseHelper;
    List<Worker> workers;

    public AdminWorkersNewTasksRecyclerAdapter(adminActivity context, List<Tasks> tasksList) {

        this.listsOfNewTasks = tasksList;
        this.activity = context;
//        /dataBaseHelper = DataBaseHelper.getInstance(context);
        workers = activity.dbHelper.getWorkers();
    }


    @NonNull
    @Override
    public AdminWorkersNewTasksRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View adminWorkersNewTasksListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list_item, parent, false);
        noAdminDataView = adminWorkersNewTasksListView.findViewById(R.id.noTaskDataView);
        noAdminDataView.setText(R.string.noWorkerNewTasks);

        if (getItemCount() > 0) {
            noAdminDataView.setVisibility(View.GONE);
        } else {
            noAdminDataView.setVisibility(View.VISIBLE);
        }
        AdminWorkersNewTasksRecyclerAdapter.ViewHolder viewHolder = new AdminWorkersNewTasksRecyclerAdapter.ViewHolder(adminWorkersNewTasksListView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminWorkersNewTasksRecyclerAdapter.ViewHolder holder, int position) {
        byte[] array = listsOfNewTasks.get(position).TaskImageAfter;
        holder.taskTitle.setText(listsOfNewTasks.get(position).TaskName);
        holder.compPhone.setText(listsOfNewTasks.get(position).TaskCompanyPhone);
        holder.compName.setText(listsOfNewTasks.get(position).TaskCompany);
        holder.dateFrom.setText(listsOfNewTasks.get(position).TaskPeriodFrom);
        holder.dateTo.setText(listsOfNewTasks.get(position).TaskPeriodTo);
        holder.imageAfter.setImageBitmap(BitmapFactory.decodeByteArray(array, 0, array.length));
        int idWorker = listsOfNewTasks.get(position).idWorker;
        for (Worker worker : workers) {
            if (worker.workerID == idWorker) {
                holder.infoWorker.setText(worker.getFullName());
                break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return listsOfNewTasks.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView compName, compPhone, infoWorker, taskTitle, dateFrom, dateTo;
        public AppCompatImageView imageAfter;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            compName = itemLayoutView.findViewById(R.id.comp_name);
            compPhone = itemLayoutView.findViewById(R.id.comp_phone);
            infoWorker = itemLayoutView.findViewById(R.id.infoWorker);
            taskTitle = itemLayoutView.findViewById(R.id.task_title);
            dateFrom = itemLayoutView.findViewById(R.id.task_periodFrom);
            dateTo = itemLayoutView.findViewById(R.id.task_periodTo);
            imageAfter = itemLayoutView.findViewById(R.id.imageView);

        }


    }
}
