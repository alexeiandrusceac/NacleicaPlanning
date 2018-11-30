package com.planning.nacleica.AdminActions;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.planning.nacleica.AdminWorkersRecyclerViewAdapter;
import com.planning.nacleica.Database.DataBaseHelper;
import com.planning.nacleica.R;
import com.planning.nacleica.Title;
import com.planning.nacleica.WorkerActions.ValidationWorkerInputData;
import com.planning.nacleica.WorkerActions.Worker;
import com.planning.nacleica.WorkerActions.WorkerSession;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdminWorkerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //private final AppCompatActivity compatActivity = AdminWorkerActivity.this;
    private static final int RESULT_GALLERY_IMAGE = 1;
    private static final int RESULT_CAMERA_IMAGE = 0;
    Toolbar adminWorker_toolbar;
    DrawerLayout drawerLayout;
    private RecyclerView adminWorkerRecView;
    public TextView usr_name_nav;
    public AdminWorkersRecyclerViewAdapter adapterWorkers;
    private ActionBarDrawerToggle toggle;
    public TextView usr_pren_nav;
    public View view;
    byte[] userImage;
    private ByteArrayOutputStream baos = new ByteArrayOutputStream();
    private static AppCompatImageView nav_header_imageView;
    private static AppCompatImageView nav_userImage;
    String userPrename, userName;
    AppCompatSpinner titleSpinner;
    WorkerSession session;
    public TextInputEditText nameInputValue;
    public TextInputLayout nameInputLayout;
    public TextInputEditText prenameInputValue;
    public TextInputLayout prenameInputLayout;
    public TextInputEditText passwordInputValue;
    public LinearLayoutManager layoutWorkerManager;
    public TextInputLayout passwordInputLayout;
    public TextInputLayout workerTitleLayout;
    public AppCompatSpinner workerTitleSpinner;
    public TextInputEditText confPasswdInputValue;
    public TextInputLayout confPasswordInputLayout;
    public FloatingActionButton fab;
    int idUser;
    FrameLayout frameLayout;
    private View header;
    private NavigationView navigationView;
    LayoutInflater layoutInflater;
    private DataBaseHelper dbHelper;
    public AppCompatImageView workerImage;
    public ValidationWorkerInputData valUserData;
    public String workerBirth;
    public List<Worker> listOfWorkers;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_frame);
        initUI();
    }

    void initUI() {

        dbHelper = DataBaseHelper.getInstance(this);
        listOfWorkers = new ArrayList<>();
        session = new WorkerSession(getApplicationContext());
        valUserData = new ValidationWorkerInputData(AdminWorkerActivity.this);
        layoutInflater = (LayoutInflater) AdminWorkerActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        navigationView = (NavigationView) findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(this);
        header = (navigationView).getHeaderView(0);
        usr_name_nav = header.findViewById(R.id.usr_name_nav);
        usr_pren_nav = header.findViewById(R.id.usr_pren_nav);

        nav_header_imageView = header.findViewById(R.id.nav_header_imageView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        setupDrawer();

        Bundle b = getIntent().getExtras();
        userPrename = b.getString("Prename");
        userName = b.getString("Name");
        userImage = b.getByteArray("Image");
        idUser = b.getInt("Id");

        nav_header_imageView.setImageBitmap(BitmapFactory.decodeByteArray(userImage, 0, userImage.length));
        view = layoutInflater.inflate(R.layout.admin_worker_main, frameLayout);
        fab = view.findViewById(R.id.adminWorkerAdd);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) AdminWorkerActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View postMainView = layoutInflater.inflate(R.layout.register_main, null, false);
                final ByteArrayOutputStream baos = new ByteArrayOutputStream();

                final TextInputEditText workerName = postMainView.findViewById(R.id.user_name_text);
                final TextInputEditText workerPrename = postMainView.findViewById(R.id.user_prename_text);
                workerBirth = setDate(postMainView);//postMainView.findViewById(R.id.user_birth_text);
                final TextInputEditText workerPassword = postMainView.findViewById(R.id.user_pass_text);
                final AppCompatButton workerButton = postMainView.findViewById(R.id.register_button);
                workerButton.setVisibility(View.GONE);


                workerTitleSpinner = postMainView.findViewById(R.id.user_title_text);
                workerImage = postMainView.findViewById(R.id.userImage);
                workerTitleSpinner.setAdapter(new ArrayAdapter<Title>(AdminWorkerActivity.this, android.R.layout.simple_spinner_item, Title.values()));
                workerImage.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(AdminWorkerActivity.this, workerImage);
                        popupMenu.getMenuInflater().inflate(R.menu.photo_menu, popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @SuppressLint("NewApi")
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.take_photo:
                                        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                            requestPermissions(new String[]{Manifest.permission.CAMERA}, RESULT_CAMERA_IMAGE);
                                        } else {
                                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                            startActivityForResult(cameraIntent, RESULT_CAMERA_IMAGE);
                                        }

                                        return true;
                                    case R.id.choose_photo:
                                        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), RESULT_GALLERY_IMAGE);
                                        return true;
                                    case R.id.delete_photo:
                                        workerImage.setImageDrawable(getDrawable(R.drawable.ic_profile));
                                        return true;
                                }
                                Toast.makeText(
                                        AdminWorkerActivity.this,
                                        "Ati selectat: " + item.getTitle(),
                                        Toast.LENGTH_SHORT
                                ).show();
                                return true;
                            }
                        });

                        popupMenu.show();
                    }
                });

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminWorkerActivity.this).setView(postMainView).setCancelable(false).setPositiveButton(
                        "Inregistreaza", new DialogInterface.OnClickListener() {
                            @SuppressLint("ResourceType")
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                registerUser();

                            }
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
                        Bitmap bitmap = ((BitmapDrawable) workerImage.getDrawable()).getBitmap();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                        Worker worker = new Worker();
                        worker.Name = workerName.getText().toString();
                        worker.Prename = workerPrename.getText().toString();
                        worker.Title = workerTitleSpinner.getSelectedItemPosition();
                        worker.Image = baos.toByteArray();
                        worker.Birthday = workerBirth;
                        worker.Password = workerPassword.getText().toString();

                        dbHelper.registerNewWorker(getApplicationContext(),worker);
                        refreshListOfWorkers();
                        ad.dismiss();
                    }
                });
            }
        });
        adminWorker_toolbar = view.findViewById(R.id.adminWorker_toolbar);
        setSupportActionBar(adminWorker_toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adminWorker_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
        listOfWorkers = dbHelper.getWorkers();
        refreshListOfWorkers();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RESULT_CAMERA_IMAGE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 0);
            } else {

                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap photo = null;
        Uri selectedImage;
        switch (requestCode) {
            case RESULT_CAMERA_IMAGE:
                if (resultCode == RESULT_OK) {
                    photo = (Bitmap) data.getExtras().get("data");
                    workerImage.setImageBitmap(photo);
                }
                break;

            case RESULT_GALLERY_IMAGE:
                if (resultCode == RESULT_OK) {
                    selectedImage = data.getData();
                    try {
                        photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
                        workerImage.setImageBitmap(photo);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                break;

        }
    }

    private void setupDrawer() {
        toggle = new ActionBarDrawerToggle(AdminWorkerActivity.this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

                invalidateOptionsMenu();
            }
        };

        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(toggle);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Toast.makeText(AdminWorkerActivity.this, "Ati ajuns acasa", Toast.LENGTH_SHORT).show();
                Intent homeActivity = new Intent(getApplicationContext(), AdminActivity.class);
                homeActivity.putExtra("Image", userImage);
                homeActivity.putExtra("Prename", userPrename);
                homeActivity.putExtra("Name", userName);
                homeActivity.putExtra("Id", idUser);
                startActivity(homeActivity);

                break;
            case R.id.adminWorkers:
                Toast.makeText(AdminWorkerActivity.this, "Ati selectat ofertele dvs", Toast.LENGTH_SHORT).show();
                Intent postActivity = new Intent(getApplicationContext(), AdminWorkerActivity.class);
                setTitle(AdminWorkerActivity.this.getResources().getString(R.string.adminWorkers));
                postActivity.putExtra("Image", userImage);
                postActivity.putExtra("Prename", userPrename);
                postActivity.putExtra("Name", userName);
                postActivity.putExtra("Id", idUser);
                startActivity(postActivity);

                break;
            case R.id.logOut_button:
                session.logoutWorker();
                finish();
            default:
                return false;
        }
        return true;
    }

    public String setDate(View dialogView) {
        final Calendar cldr = Calendar.getInstance();

        final TextInputEditText dateinput = dialogView.findViewById(R.id.user_birth_text);
        dateinput.setInputType(InputType.TYPE_NULL);
        dateinput.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.O)
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                DatePickerDialog datepicker = new DatePickerDialog(AdminWorkerActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        dateinput.setText((((date < 10) ? "0" : "") + date) + " / " + (((month < 10) ? "0" : "") + (month + 1)) + " / " + year);
                    }
                }, year, month, day);
                datepicker.show();
            }
        });
        return dateinput.getText().toString();
    }

    private void registerUser() {
        if (!valUserData.textFilled(nameInputValue, nameInputLayout, getString(R.string.error_name))) {
            return;
        }
        if (!valUserData.textFilled(passwordInputValue, passwordInputLayout, getString(R.string.error_password))) {
            return;
        }
        if (!valUserData.isInputEditTextMatches(passwordInputValue, confPasswdInputValue,
                confPasswordInputLayout, getString(R.string.error_password_match))) {
            return;
        }

        if (!dbHelper.checkUserOnLogin(nameInputValue.getText().toString().trim())) {

            Bitmap bitmap = ((BitmapDrawable) workerImage.getDrawable()).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            Worker workerData = new Worker();
            workerData.Name = nameInputValue.getText().toString();
            workerData.Prename = prenameInputValue.getText().toString();
            workerData.Password = passwordInputValue.getText().toString();
            workerData.Image = baos.toByteArray();
            workerData.Birthday = workerBirth;
            workerData.Title = workerTitleSpinner.getSelectedItemPosition();
            dbHelper.registerNewWorker(getApplicationContext(),workerData);

            Snackbar.make(null, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            nameInputValue.setText(null);

        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(null, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }
    }

    public void refreshListOfWorkers() {
        adminWorkerRecView = view.findViewById(R.id.adminRecyclerView);
        adminWorkerRecView.setHasFixedSize(true);
        layoutWorkerManager = new LinearLayoutManager(AdminWorkerActivity.this);
        adminWorkerRecView.setLayoutManager(layoutWorkerManager);
        adapterWorkers = new AdminWorkersRecyclerViewAdapter(AdminWorkerActivity.this, listOfWorkers, idUser);
        adminWorkerRecView.setAdapter(adapterWorkers);
        adminWorkerRecView.refreshDrawableState();

    }
/*
    @Override
    public void onClick(View v) {

    }*/
}
