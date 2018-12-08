package com.planning.nacleica.AdminActions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.planning.nacleica.Database.DataBaseHelper;
import com.planning.nacleica.R;
import com.planning.nacleica.Tasks;
import com.planning.nacleica.Utils;
import com.planning.nacleica.WorkerActions.Worker;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AdminMaketTaskRecyclerViewAdapter  extends RecyclerView.Adapter<AdminMaketTaskRecyclerViewAdapter.ViewHolder> {
    TextView noAdminDataView;
    AdminActivity activity;
    List<Tasks> listsOfMaketTasks = new ArrayList<>();
    CardView cardView;
    DataBaseHelper dataBaseHelper;
    Utils utils;

    public AdminMaketTaskRecyclerViewAdapter(AdminActivity context, List<Tasks> tasksList) {

        this.listsOfMaketTasks = tasksList;
        this.activity = context;
        dataBaseHelper = DataBaseHelper.getInstance(context);

    }


    @Override
    public AdminMaketTaskRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list_item, parent, false);
        noAdminDataView = view.findViewById(R.id.noTaskDataView);
        noAdminDataView.setText(R.string.noMaketTasks);
        cardView = view.findViewById(R.id.cardViewTask);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popupMenu = new PopupMenu(activity, view);
                popupMenu.getMenuInflater().inflate(R.menu.maket_task_action, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NewApi")
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.ready_task:
                                LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View postMainTaskView = layoutInflater.inflate(R.layout.choose_worker, null, false);
                                final AppCompatSpinner workerSpinner = postMainTaskView.findViewById(R.id.worker_choose);
                                workerSpinner.setAdapter(new ArrayAdapter<Worker>(activity,android.R.layout.simple_spinner_item,dataBaseHelper.getWorkers(4)));

                                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity).setTitle("Atribuire la prelucrare").setView(view).setCancelable(false).setPositiveButton(
                                        "Atribuie", new DialogInterface.OnClickListener() {
                                            @SuppressLint("ResourceType")
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {}
                                        }
                                ).setNegativeButton("Anulare", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                final AlertDialog ad = alertDialog.create();
                                ad.show();
                                ad.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                    @SuppressLint("ResourceType")
                                    @Override
                                    public void onClick(View v) {
                                        listsOfMaketTasks.get(v.getId()).idWorker = ((Worker)workerSpinner.getSelectedItem()).workerID;
                                        listsOfMaketTasks.get(v.getId()).TaskState = 1;
                                        dataBaseHelper.updateData(activity, listsOfMaketTasks.get(v.getId()));
                                        view.refreshDrawableState();
                                        /*listOfTasks.get(v.getId()).idWorker = ;
                                        listOfTasks.get(v.getId()).TaskState = 1;
                                        dataBaseHelper.updateData(activity, listOfTasks.get(v.getId()));
                                        view.refreshDrawableState();*/
                                        ad.dismiss();

                                    }
                                });

                                return true;
                            /*case R.id.edit_task:
                                //showDialogEditData(true, listOfTasks.get(v.getId()), v.getId());
                                view.refreshDrawableState();
                                return true;*/
                        }

                        return true;
                    }
                });

                popupMenu.show();
            }
        });

        if (listsOfMaketTasks.size() > 0) {
            noAdminDataView.setVisibility(View.GONE);
        } else {
            noAdminDataView.setVisibility(View.VISIBLE);
        }
        AdminMaketTaskRecyclerViewAdapter.ViewHolder viewHolder = new AdminMaketTaskRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminMaketTaskRecyclerViewAdapter.ViewHolder holder, int position) {
        byte[] array = listsOfMaketTasks.get(position).TaskImageAfter;
        holder.taskTitle.setText(listsOfMaketTasks.get(position).TaskName);
        holder.compPhone.setText(listsOfMaketTasks.get(position).TaskCompanyPhone);
        holder.compName.setText(listsOfMaketTasks.get(position).TaskCompany);
        holder.dateFrom.setText(listsOfMaketTasks.get(position).TaskPeriodFrom);
        holder.dateTo.setText(listsOfMaketTasks.get(position).TaskPeriodTo);
        holder.imageAfter.setImageBitmap(BitmapFactory.decodeByteArray(array, 0, array.length));
        holder.infoWorker.setText(new Worker().getWorkerName(listsOfMaketTasks.get(position).idWorker));
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    private void updateData(Tasks tasks, int position) {
        dataBaseHelper.updateData(activity, tasks);
        listsOfMaketTasks.set(position, tasks);
        activity.refreshListOfAdminTasks();

    }
    @Override
    public int getItemCount() {
        return listsOfMaketTasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView compName, compPhone, infoWorker, taskTitle, idWorker, dateFrom, dateTo;
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
