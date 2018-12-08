package com.planning.nacleica.AdminActions;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
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

public class AdminNewTasksRecyclerViewAdapter extends RecyclerView.Adapter<AdminNewTasksRecyclerViewAdapter.ViewHolder> {
    TextView noAdminDataView;
    AdminActivity activity;
    List<Tasks> listOfTasks = new ArrayList<>();
    CardView cardView;
    DataBaseHelper dataBaseHelper;
    Utils utils;

    public AdminNewTasksRecyclerViewAdapter(AdminActivity context, List<Tasks> tasksList) {

        this.listOfTasks = tasksList;
        this.activity = context;
        dataBaseHelper = DataBaseHelper.getInstance(context);

    }

    @Override
    public AdminNewTasksRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list_item, parent, false);
        noAdminDataView = view.findViewById(R.id.noTaskDataView);
        noAdminDataView.setText(R.string.noNewTasks);
        cardView = view.findViewById(R.id.cardViewTask);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popupMenu = new PopupMenu(activity, view);
                popupMenu.getMenuInflater().inflate(R.menu.task_action, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NewApi")
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.maket_task:
                                LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View postMainTaskView = layoutInflater.inflate(R.layout.choose_worker, null, false);
                                final AppCompatSpinner workerSpinner = postMainTaskView.findViewById(R.id.worker_choose);
                                workerSpinner.setAdapter(new ArrayAdapter<Worker>(activity,android.R.layout.simple_spinner_item,dataBaseHelper.getWorkers(2)));

                                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity).setTitle("Atribuire la machetare").setView(view).setCancelable(false).setPositiveButton(
                                        "Atribuie", new DialogInterface.OnClickListener() {
                                            @SuppressLint("ResourceType")
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) { }
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
                                        listOfTasks.get(v.getId()).idWorker = ((Worker)workerSpinner.getSelectedItem()).workerID;
                                        listOfTasks.get(v.getId()).TaskState = 1;
                                        dataBaseHelper.updateData(activity, listOfTasks.get(v.getId()));
                                        view.refreshDrawableState();
                                        ad.dismiss();

                                    }
                                });

                                return true;
                            case R.id.edit_task:
                                showDialogEditData(true, listOfTasks.get(v.getId()), v.getId());
                                view.refreshDrawableState();
                                return true;
                        }

                        return true;
                    }
                });

                popupMenu.show();
            }
        });
        if (listOfTasks.size() > 0) {
            noAdminDataView.setVisibility(View.GONE);
        } else {
            noAdminDataView.setVisibility(View.VISIBLE);
        }
        AdminNewTasksRecyclerViewAdapter.ViewHolder viewHolder = new AdminNewTasksRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        byte[] array = listOfTasks.get(position).TaskImageBefore;
        holder.taskTitle.setText(listOfTasks.get(position).TaskName);
        holder.compPhone.setText(listOfTasks.get(position).TaskCompanyPhone);
        holder.compName.setText(listOfTasks.get(position).TaskCompany);
        holder.dateFrom.setText(listOfTasks.get(position).TaskPeriodFrom);
        holder.dateTo.setText(listOfTasks.get(position).TaskPeriodTo);
        holder.imageBefore.setImageBitmap(BitmapFactory.decodeByteArray(array,0,array.length));
        //holder.infoWorker.setText(getWorkerName(listOfTasks.get(position).idWorker));
    }

    private void showDialogEditData(final boolean shouldUpdate, final Tasks task, final int position) {
        final LayoutInflater layoutInflaterAndroid = LayoutInflater.from(activity);

        final View view = layoutInflaterAndroid.inflate(R.layout.new_task_main, null);

        final TextInputEditText compName = view.findViewById(R.id.comp_name_value);
        compName.setText(String.valueOf(task.TaskCompany));
        compName.setTextColor(Color.BLACK);
        compName.setHintTextColor(Color.RED);

        final TextInputEditText compPhone = view.findViewById(R.id.comp_phone_value);
        compPhone.setText(String.valueOf(task.TaskCompanyPhone));
        compPhone.setTextColor(Color.BLACK);
        compPhone.setHintTextColor(Color.RED);

        final TextInputEditText taskName = view.findViewById(R.id.task_name_value);
        taskName.setText(String.valueOf(task.TaskName));
        taskName.setTextColor(Color.BLACK);
        taskName.setHintTextColor(Color.RED);

        final TextInputEditText dateFrom = utils.dateToEditText((TextInputEditText) view.findViewById(R.id.date_from_value));
        dateFrom.setText(String.valueOf(task.TaskPeriodFrom));
        dateFrom.setTextColor(Color.BLACK);
        dateFrom.setHintTextColor(Color.RED);

        final TextInputEditText dateTo = utils.dateToEditText((TextInputEditText) view.findViewById(R.id.date_to_value));
        dateFrom.setText(String.valueOf(task.TaskPeriodTo));
        dateFrom.setTextColor(Color.BLACK);
        dateFrom.setHintTextColor(Color.RED);

        final AppCompatImageView imageBeforeView = view.findViewById(R.id.imageBeforeView);
        imageBeforeView.setImageBitmap(BitmapFactory.decodeByteArray(task.TaskImageBefore, 0, task.TaskImageBefore.length));
        imageBeforeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //context.imageView = imageBeforeView;
                utils.openImagePopupMenu(imageBeforeView);
            }
        });
       /* final EditText birthView = context.utils.dateToEditText((TextInputEditText) view.findViewById(R.id.user_birth_text));
        birthView.setText(String.valueOf(worker.Birthday));
        birthView.setTextColor(Color.BLACK);
        birthView.setHintTextColor(Color.RED);

        final AppCompatSpinner titleSpinner = view.findViewById(R.id.user_title_text);
        titleSpinner.setAdapter(new ArrayAdapter<Title>(context, android.R.layout.simple_spinner_item, Title.values()));
        titleSpinner.setSelection(worker.Title - 1);


        workerImageView.setImageBitmap(BitmapFactory.decodeByteArray(worker.Image, 0, worker.Image.length));
        workerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.imageView = workerImageView;
                context.utils.openImagePopupMenu(workerImageView);
            }
        });

        final EditText passwordView = view.findViewById(R.id.user_pass_text);
        passwordView.setText(String.valueOf(worker.Password));
        passwordView.setTextColor(Color.BLACK);
        passwordView.setHintTextColor(Color.RED);

        final AppCompatButton registerButton = view.findViewById(R.id.register_button);
        registerButton.setVisibility(View.GONE);

        final EditText confPasswordView = view.findViewById(R.id.user_confpass_text);
        confPasswordView.setText(String.valueOf(worker.Password));
        confPasswordView.setTextColor(Color.BLACK);
        confPasswordView.setHintTextColor(Color.RED);

*/
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(activity);
        alertDialogBuilderUserInput.setView(view);


        alertDialogBuilderUserInput
                .setCancelable(false)
                .setView(view)
                .setPositiveButton(shouldUpdate ? "Actualizeaza" : "Salveaza", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("Anuleaza",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(taskName.getText().toString())) {
                    Toast.makeText(activity, "Introduceti numele sarcinii!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(compName.getText().toString())) {
                    Toast.makeText(activity, "Introduceti numele companiei clientului!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(compPhone.getText().toString())) {
                    Toast.makeText(activity, "Introduceti nr de telefon a clientului!", Toast.LENGTH_SHORT).show();
                    return;
                } else if ((TextUtils.isEmpty(dateFrom.getText().toString())) && (TextUtils.isEmpty(dateTo.getText().toString()))) {
                    Toast.makeText(activity, "Introduceti perioada de realizare a sarcinii!", Toast.LENGTH_SHORT).show();
                    return;
                }

                alertDialog.dismiss();

                // verifica daca utilizatorul actualizeaza datele
                if (shouldUpdate && task != null) {
                    // actualizeaza datele
                    task.TaskName = taskName.getText().toString();
                    task.TaskCompany = compName.getText().toString();
                    task.TaskCompanyPhone = compPhone.getText().toString();
                    task.TaskPeriodFrom = dateFrom.getText().toString();
                    task.TaskPeriodTo = dateTo.getText().toString();
                    task.TaskImageBefore = utils.convertToByteArray(imageBeforeView);
                    updateData(task, position);
                }
            }

        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void updateData(Tasks tasks, int position) {
        dataBaseHelper.updateData(activity, tasks);
        listOfTasks.set(position, tasks);
        activity.refreshListOfAdminTasks();

    }

    @Override
    public int getItemCount() {
        return listOfTasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView compName, compPhone, infoWorker, taskTitle, dateFrom, dateTo;
        public AppCompatImageView imageBefore;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            compName = itemLayoutView.findViewById(R.id.comp_name);
            compPhone = itemLayoutView.findViewById(R.id.comp_phone);
            taskTitle = itemLayoutView.findViewById(R.id.task_title);
            dateFrom = itemLayoutView.findViewById(R.id.task_periodFrom);
            dateTo = itemLayoutView.findViewById(R.id.task_periodTo);
            infoWorker = itemLayoutView.findViewById(R.id.infoWorker);
            infoWorker.setVisibility(View.GONE);
            imageBefore = itemLayoutView.findViewById(R.id.imageBeforeView);

        }

    }
}
