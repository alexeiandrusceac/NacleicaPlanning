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

public class AdminWorkersInProgRecyclerAdapter extends RecyclerView.Adapter<AdminWorkersInProgRecyclerAdapter.ViewHolder>  {
    TextView noAdminDataView;
    adminActivity activity;
    List<Tasks> listOfAdminWorkersInProgTask = new ArrayList<>();
    DataBaseHelper dataBaseHelper;
    List<Worker> workers;
    public AdminWorkersInProgRecyclerAdapter(adminActivity activity, List<Tasks> tasksList)
    {
        dataBaseHelper = DataBaseHelper.getInstance(activity);
        this.listOfAdminWorkersInProgTask = tasksList;
        this.activity = activity;
        workers = activity.dbHelper.getWorkers();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View adminWorkersProgListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list_item, parent, false);
        noAdminDataView = adminWorkersProgListView.findViewById(R.id.noTaskDataView);
        noAdminDataView.setText(R.string.noWorkerInProgTasks);

        if (listOfAdminWorkersInProgTask.size() > 0) {
            noAdminDataView.setVisibility(View.GONE);
        } else {
            noAdminDataView.setVisibility(View.VISIBLE);
        }
        AdminWorkersInProgRecyclerAdapter.ViewHolder viewHolder = new AdminWorkersInProgRecyclerAdapter.ViewHolder(adminWorkersProgListView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        byte[] array = listOfAdminWorkersInProgTask.get(position).TaskImageAfter;
        holder.taskTitle.setText(listOfAdminWorkersInProgTask.get(position).TaskName);
        holder.compPhone.setText(listOfAdminWorkersInProgTask.get(position).TaskCompanyPhone);
        holder.compName.setText(listOfAdminWorkersInProgTask.get(position).TaskCompany);
        holder.dateFrom.setText(listOfAdminWorkersInProgTask.get(position).TaskPeriodFrom);
        holder.dateTo.setText(listOfAdminWorkersInProgTask.get(position).TaskPeriodTo);
        holder.imageAfter.setImageBitmap(BitmapFactory.decodeByteArray(array, 0, array.length));
        int idWorker = listOfAdminWorkersInProgTask.get(position).idWorker;
        for (Worker worker : workers) {
            if (worker.workerID == idWorker) {
                holder.infoWorker.setText(worker.getFullName());
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return listOfAdminWorkersInProgTask.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

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
