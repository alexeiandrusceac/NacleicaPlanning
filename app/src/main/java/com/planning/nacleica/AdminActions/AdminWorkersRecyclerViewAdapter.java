package com.planning.nacleica.AdminActions;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
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

import com.google.android.material.textfield.TextInputEditText;
import com.planning.nacleica.Database.DataBaseHelper;
import com.planning.nacleica.AuthActions.Worker;
import com.planning.nacleica.R;
import com.planning.nacleica.Title;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import static com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes;

public class AdminWorkersRecyclerViewAdapter extends RecyclerView.Adapter<AdminWorkersRecyclerViewAdapter.ViewHolder> {
    static List<Worker> dbWorkerList = new ArrayList<>();
    //static List<Worker> copyDbList;
    public DatePickerDialog datePicker;
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
    public CardView cardView;

    public AdminWorkersRecyclerViewAdapter(AdminWorkerActivity context, List<Worker> dbList, int user_id) {

        this.context = context;
        this.dbWorkerList = dbList;
        dataBaseHelper = DataBaseHelper.getInstance(context);
        this.idUser = user_id;
    }

    @NonNull
    @Override
    public AdminWorkersRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View adminWorkersListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adminworkers_content_main, parent, false);
        cardView = adminWorkersListView.findViewById(R.id.cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                int index = ((ViewGroup) (adminWorkersListView.getParent())).indexOfChild(adminWorkersListView);
                showDialogEditData(true, dbWorkerList.get(index), index);
            }
        });

        AdminWorkersRecyclerViewAdapter.ViewHolder viewHolder = new AdminWorkersRecyclerViewAdapter.ViewHolder(adminWorkersListView);
        return viewHolder;
    }

    private void showDialogEditData(final boolean shouldUpdate, final Worker worker, final int position) {
        final LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);

        final View view = layoutInflaterAndroid.inflate(R.layout.register_main, null);
        final Toolbar  editToolbar = view.findViewById(R.id.register_app_toolbar);


        editToolbar.setTitle("Editare angajat");
        editToolbar.setTitleTextAppearance(context, R.style.add_worker);

        final EditText nameView = view.findViewById(R.id.user_name_text);
        nameView.setText(String.valueOf(worker.Name));
        nameView.setTextColor(Color.BLACK);
        nameView.setHintTextColor(Color.RED);

        final EditText prenameView = view.findViewById(R.id.user_prename_text);
        prenameView.setText(String.valueOf(worker.Prename));
        prenameView.setTextColor(Color.BLACK);
        prenameView.setHintTextColor(Color.RED);

        final EditText birthView = context.utils.dateToEditText((TextInputEditText) view.findViewById(R.id.user_birth_text));
        birthView.setText(String.valueOf(worker.Birthday));
        birthView.setTextColor(Color.BLACK);
        birthView.setHintTextColor(Color.RED);

        final AppCompatSpinner titleSpinner = view.findViewById(R.id.user_title_text);
        titleSpinner.setAdapter(new ArrayAdapter<Title>(context, android.R.layout.simple_spinner_item, Title.values()));
        titleSpinner.setSelection(worker.Title - 1);

        final AppCompatImageView workerImageView = view.findViewById(R.id.userImage);
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


        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
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
                    worker.Title = ((Title) (titleSpinner.getSelectedItem())).getTitleIndex();
                    worker.Birthday = birthView.getText().toString();
                    worker.Password = passwordView.getText().toString();
                    worker.Image = context.utils.convertToByteArray(workerImageView);
                    updateData(dbWorkerList.get(position), position);
                }
            }

        });

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
        holder.title.setText(String.valueOf(Title.values()[dbWorkerList.get(position).Title - 1]));
        holder.image.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));

    }

    @Override
    public int getItemCount() {
        return dbWorkerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name, prename, birthday, title, idWorker;
        public AppCompatImageView image;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            name = itemLayoutView.findViewById(R.id.nameView);
            prename = itemLayoutView.findViewById(R.id.prenameView);
            birthday = itemLayoutView.findViewById(R.id.birthView);
            title = itemLayoutView.findViewById(R.id.titleView);
            //idWorker =  itemLayoutView.findViewById(R.id.idView);
            image = itemLayoutView.findViewById(R.id.workerImageView);

        }

    }

}
