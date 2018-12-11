package com.planning.nacleica.AdminWorkersActions;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.planning.nacleica.AdminActions.AdminActivity;
import com.planning.nacleica.Database.DataBaseHelper;
import com.planning.nacleica.R;
import com.planning.nacleica.Tasks;
import com.planning.nacleica.AuthActions.Worker;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

public class AdminWorkersDoneRecyclerAdapter extends RecyclerView.Adapter<AdminWorkersDoneRecyclerAdapter.ViewHolder> {
    TextView noAdminDataView;
    AdminActivity activity;
    List<Tasks> listsOfAdminWorkersDoneTasks = new ArrayList<>();
    DataBaseHelper dataBaseHelper;

    public AdminWorkersDoneRecyclerAdapter(AdminActivity context, List<Tasks> tasksList) {
        this.listsOfAdminWorkersDoneTasks = tasksList;
        this.activity = context;
        dataBaseHelper = DataBaseHelper.getInstance(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View adminWorkersDoneTasksListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list_item, parent, false);
        noAdminDataView = adminWorkersDoneTasksListView.findViewById(R.id.noTaskDataView);
        noAdminDataView.setText(R.string.noWorkerDoneTasks);

        if (listsOfAdminWorkersDoneTasks.size() > 0) {
            noAdminDataView.setVisibility(View.GONE);
        } else {
            noAdminDataView.setVisibility(View.VISIBLE);
        }
        AdminWorkersDoneRecyclerAdapter.ViewHolder viewHolder = new AdminWorkersDoneRecyclerAdapter.ViewHolder(adminWorkersDoneTasksListView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        byte[] array = listsOfAdminWorkersDoneTasks.get(position).TaskImageAfter;
        holder.taskTitle.setText(listsOfAdminWorkersDoneTasks.get(position).TaskName);
        holder.compPhone.setText(listsOfAdminWorkersDoneTasks.get(position).TaskCompanyPhone);
        holder.compName.setText(listsOfAdminWorkersDoneTasks.get(position).TaskCompany);
        holder.dateFrom.setText(listsOfAdminWorkersDoneTasks.get(position).TaskPeriodFrom);
        holder.dateTo.setText(listsOfAdminWorkersDoneTasks.get(position).TaskPeriodTo);
        holder.imageAfter.setImageBitmap(BitmapFactory.decodeByteArray(array, 0, array.length));
        holder.infoWorker.setText(new Worker().getWorkerName(listsOfAdminWorkersDoneTasks.get(position).idWorker));
    }

    @Override
    public int getItemCount() {
        return listsOfAdminWorkersDoneTasks.size();
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
