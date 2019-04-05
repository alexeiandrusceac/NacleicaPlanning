package com.planning.nacleica.adminactions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.planning.nacleica.authactions.WorkerSpinnerAdapter;
import com.planning.nacleica.database.DataBaseHelper;
import com.planning.nacleica.R;
import com.planning.nacleica.Tasks;
import com.planning.nacleica.authactions.Worker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AdminMaketTaskRecyclerViewAdapter extends RecyclerView.Adapter<AdminMaketTaskRecyclerViewAdapter.ViewHolder> {
    TextView noAdminDataView;
    adminActivity activity;
    List<Tasks> listsOfMaketTasks = new ArrayList<>();
    CardView cardView;
    DataBaseHelper dataBaseHelper;
    private int indexChild;
    List<Worker> workers = new ArrayList<>();
    public int idWorker;
    public Worker worker;
    public View adminMaketTasksListView;

    public AdminMaketTaskRecyclerViewAdapter(adminActivity context) {

        this.activity = context;
        this.listsOfMaketTasks = context.listOfAdminMakTasks;
        workers = activity.dbHelper.getWorkers();

    }

    @Override
    public AdminMaketTaskRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        adminMaketTasksListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list_item, parent, false);
        noAdminDataView = adminMaketTasksListView.findViewById(R.id.noTaskDataView);
        noAdminDataView.setText(R.string.noMaketTasks);
        cardView = adminMaketTasksListView.findViewById(R.id.cardViewTask);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popupMenu = new PopupMenu(activity, adminMaketTasksListView);
                popupMenu.getMenuInflater().inflate(R.menu.maket_task_action, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NewApi")
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.ready_task:
                                indexChild = ((ViewGroup) (adminMaketTasksListView.getParent())).indexOfChild(adminMaketTasksListView);
                                LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                final View postMainTaskView = layoutInflater.inflate(R.layout.choose_worker, null, false);
                                final AppCompatSpinner workerSpinner = postMainTaskView.findViewById(R.id.choose_worker);
                                workerSpinner.setAdapter(new WorkerSpinnerAdapter(activity, android.R.layout.simple_spinner_dropdown_item, workers));
                                workerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        worker = (Worker) parent.getItemAtPosition(position);
                                        idWorker = worker.workerID;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        worker = (Worker) parent.getSelectedItem();
                                        idWorker = worker.workerID;
                                    }
                                });
                                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity).setTitle("Atribuire la prelucrare").setView(postMainTaskView).setCancelable(false).setPositiveButton(
                                        "Atribuie", new DialogInterface.OnClickListener() {
                                            @SuppressLint("ResourceType")
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        }
                                ).setNegativeButton("Anulare", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                final AlertDialog adialog = alertDialog.create();
                                adialog.show();
                                adialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                    @SuppressLint("ResourceType")
                                    @Override
                                    public void onClick(View v) {
                                        listsOfMaketTasks.get(indexChild).idWorker = idWorker;
                                        listsOfMaketTasks.get(indexChild).TaskState = 2;
                                        activity.dbHelper.updateWorker(activity, listsOfMaketTasks.get(indexChild));
                                        listsOfMaketTasks.remove(indexChild);
                                        activity.refreshListOfAdminTasks();
                                        adialog.dismiss();
                                    }
                                });

                                return true;
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
        AdminMaketTaskRecyclerViewAdapter.ViewHolder viewHolder = new AdminMaketTaskRecyclerViewAdapter.ViewHolder(adminMaketTasksListView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminMaketTaskRecyclerViewAdapter.ViewHolder holder, int position) {
        byte[] array = listsOfMaketTasks.get(position).TaskImageAfter;
        int idWorker = listsOfMaketTasks.get(position).idWorker;
        holder.taskTitle.setText(listsOfMaketTasks.get(position).TaskName);
        holder.compPhone.setText(listsOfMaketTasks.get(position).TaskCompanyPhone);
        holder.compName.setText(listsOfMaketTasks.get(position).TaskCompany);
        holder.dateFrom.setText(listsOfMaketTasks.get(position).TaskPeriodFrom);
        holder.dateTo.setText(listsOfMaketTasks.get(position).TaskPeriodTo);
        holder.imageAfter.setImageBitmap(BitmapFactory.decodeByteArray(array, 0, array.length));

        for (Worker worker : workers)
        {
            if(worker.workerID == idWorker)
            {
                holder.infoWorker.setText(worker.getFullName());
                break;
             }

        }

    }

    @Override
    public long getItemId(int position) {
        return position;
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
