package com.planning.nacleica;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.planning.nacleica.AdminActions.AdminWorkerActivity;
import com.planning.nacleica.Database.DataBaseHelper;
import com.planning.nacleica.WorkerActions.Worker;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;

public class AdminWorkersRecyclerViewAdapter extends RecyclerView.Adapter<AdminWorkersRecyclerViewAdapter.ViewHolder> {
    static List<Worker> dbWorkerList;
    //static List<Worker> copyDbList;
    //private DatePickerDialog datePicker;
    private int idHotel;
    private boolean showContent = true;
    private DataBaseHelper dataBaseHelper;
    static AdminWorkerActivity context;
    private int idUser;
    //private List<Rooms> listOfRooms;
    /*private int selectedSpinnerItem;
    private String selectedSpinnerType;
    private int selectedSpinnerId;*/
    private LinearLayout roomLabelLayout;
    /*private Spinner roomType;
    private TextView roomNumberText;
    private EditText roomPriceText;*/
    private LinearLayout linearLayout;
    public Worker worker;

    public AdminWorkersRecyclerViewAdapter(AdminWorkerActivity context, List<Worker> dbList, int user_id) {
        this.dbWorkerList = new ArrayList<>();
        //this.listOfRooms = new ArrayList<Rooms>();
        this.context = context;
        this.dbWorkerList = dbList;
        /*copyDbList = new ArrayList<Hotels>();
        copyDbList.addAll(dbList);*/
        dataBaseHelper = DataBaseHelper.getInstance(context);
        this.idUser = user_id;
    }

    @NonNull
    @Override
    public AdminWorkersRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adminworkers_content_main, null);

        itemLayoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialogEditData(true, dbWorkerList.get(0), 0);
            }
        });
        /*reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hotelTitle = ((TextView) itemLayoutView.findViewById(R.id.titleView)).getText().toString();

                idHotel = Integer.parseInt(((TextView) itemLayoutView.findViewById(R.id.idView)).getText().toString());
                showReservationDialog(hotelTitle);
            }
        });*/


        AdminWorkersRecyclerViewAdapter.ViewHolder viewHolder = new AdminWorkersRecyclerViewAdapter.ViewHolder(itemLayoutView);
        return viewHolder;
    }

    private void showDialogEditData(final boolean shouldUpdate, final Worker worker, final int position) {
        final LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        final View view;
        final EditText nameView;
        final EditText prenameView;
        final EditText birthView;
        final EditText titleView;
        final AppCompatSpinner titleSpinner;
        final AppCompatImageView workerImageView;
        final String date;
        final EditText passwordView;
        final EditText confPasswordView;

        view = layoutInflaterAndroid.inflate(R.layout.register_main, null);
        date = context.setDate(view);
        nameView = view.findViewById(R.id.user_name_text);
        nameView.setText(String.valueOf(worker.Name));
        nameView.setTextColor(Color.BLACK);
        nameView.setHintTextColor(Color.RED);

        prenameView = view.findViewById(R.id.user_prename_text);
        prenameView.setText(String.valueOf(worker.Prename));
        prenameView.setTextColor(Color.BLACK);
        prenameView.setHintTextColor(Color.RED);

        birthView = view.findViewById(R.id.user_birth_text);
        birthView.setText(date);
        birthView.setTextColor(Color.BLACK);
        birthView.setHintTextColor(Color.RED);

        passwordView = view.findViewById(R.id.user_pass_text);
        passwordView.setText(date);
        passwordView.setTextColor(Color.BLACK);
        passwordView.setHintTextColor(Color.RED);

        confPasswordView = view.findViewById(R.id.user_confpass_text);
        confPasswordView.setText(date);
        confPasswordView.setTextColor(Color.BLACK);
        confPasswordView.setHintTextColor(Color.RED);

        titleSpinner = view.findViewById(R.id.user_title_text);
        titleSpinner.setAdapter(new ArrayAdapter<Title>(context, android.R.layout.simple_spinner_item, Title.values()));
        //workerImageView = view.findViewById(R.id.userImage);
       /* titleView = view.findViewById(R.id.user_title_view);
        titleView.setText(String.valueOf(worker.Title));
*/
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
        alertDialogBuilderUserInput.setView(view);

        //checkExternalStorage();

        if (shouldUpdate && worker != null) {

            nameView.setText(String.valueOf(worker.Name));
            nameView.setTextColor(Color.BLACK);
        }
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

                if (TextUtils.isEmpty(nameView.getText().toString())) {
                    Toast.makeText(context, "Introduceti numele angajatorului!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(prenameView.getText().toString())) {
                    Toast.makeText(context, "Introduceti prenumele angajatorului!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(birthView.getText().toString())) {
                    Toast.makeText(context, "Introduceti ziua de nastere!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(passwordView.getText().toString())) {
                    Toast.makeText(context, "Introduceti parola noua!", Toast.LENGTH_SHORT).show();
                    return;

                } else if (TextUtils.isEmpty(confPasswordView.getText().toString())) {
                    Toast.makeText(context, "Introduceti parola noua de confirmare!", Toast.LENGTH_SHORT).show();
                    return;

                }

                alertDialog.dismiss();

                // verifica daca utilizatorul actualizeaza datele
                if (shouldUpdate && worker != null) {
                    // actualizeaza datele
                    worker.Name = nameView.getText().toString();
                    worker.Prename = prenameView.getText().toString();
                    worker.Birthday = date;//birthView.getText().toString();//(outputText[0] == null ? 0 : Integer.parseInt(outputText[0]));
                    worker.Password = passwordView.getText().toString();

                    updateData(dbWorkerList.get(position), position);
                    //calculateSumTotal();

                }
            }

        });

    /*} else

    {
        view = layoutInflaterAndroid.inflate(R.layout.data_dialog_input, null);
        input = view.findViewById(R.id.inputText);
        input.setText(String.valueOf(inOut.INPUT));
        input.setTextColor(Color.BLACK);
        input.setHintTextColor(Color.RED);
        dateInputSet = view.findViewById(R.id.dateText);
        dateInputSet.setText(String.valueOf(inOut.DATE));
        dateInputSet.setTextColor(Color.BLACK);
        dateInputSet.setHintTextColor(Color.RED);
        dateInput = setDate(view);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MonitorizareMainActivity.this);
        alertDialogBuilderUserInput.setView(view);

        checkExternalStorage();

        if (shouldUpdate && inOut != null) {

            dateInput.setText(String.valueOf(inOut.DATE));

            dateInput.setTextColor(Color.BLACK);
        }
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

                inputText[0] = ((EditText) (view.findViewById(R.id.inputText))).getText().toString();
                if (TextUtils.isEmpty(inputText[0])) {
                    Toast.makeText(MonitorizareMainActivity.this, "Introduceti cit ati primit!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(dateInput.getText().toString())) {
                    Toast.makeText(MonitorizareMainActivity.this, "Introduceti data primirii!", Toast.LENGTH_SHORT).show();
                    return;
                }

                alertDialog.dismiss();

                // verifica daca utilizatorul actualizeaza datele
                if (shouldUpdate && inOut != null) {
                    // actualizeaza datele
                    inOut.DATE = dateInput.getText().toString();
                    inOut.INPUT = (inputText[0] == null ? 0 : Integer.parseInt(inputText[0]));
                    inOut.OUTPUT = 0;

                    updateData(listOfNewData.get(position), position);
                    calculateSumTotal();

                }
            }

        });*/
    }

    private void updateData(Worker worker, int position) {
        dataBaseHelper.updateData(context, worker);
        dbWorkerList.set(position, worker);
        context.refreshListOfWorkers();
        //AdminWorkersRecyclerViewAdapter.ViewHolder viewHolder = new AdminWorkersRecyclerViewAdapter.ViewHolder(itemLayoutView);
        /*context.setDataAdapter(new AdminWorkersRecyclerViewAdapter(context, tableHelper.getData(listOfNewData)));
        tb.refreshDrawableState();
        calculateSumTotal();*/

    }

    @Override
    public void onBindViewHolder(@NonNull AdminWorkersRecyclerViewAdapter.ViewHolder holder, int position) {
        byte[] byteArray = dbWorkerList.get(position).Image;
        holder.name.setText(dbWorkerList.get(position).Name);
        holder.prename.setText(dbWorkerList.get(position).Prename);
        holder.birthday.setText(dbWorkerList.get(position).Birthday);
       // holder.idWorker.setText(String.valueOf(dbWorkerList.get(position).workerID));
        holder.image.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
       /* holder.phone.setText(dbList.get(position).Phone);
        holder.city.setText(dbList.get(position).City);
        holder.idHotel.setText(String.valueOf(dbList.get(position).Id_Hotel));
    */
    }

    @Override
    public int getItemCount() {
        return dbWorkerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name, prename, birthday, idWorker;
        public AppCompatImageView image;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            name =  itemLayoutView.findViewById(R.id.nameView);
            prename = itemLayoutView.findViewById(R.id.prenameView);
            birthday = itemLayoutView.findViewById(R.id.birthView);
            //idWorker =  itemLayoutView.findViewById(R.id.idView);
            image =itemLayoutView.findViewById(R.id.workerImageView);

        }

    }

}
