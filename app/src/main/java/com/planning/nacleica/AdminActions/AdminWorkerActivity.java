package com.planning.nacleica.AdminActions;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.util.SizeF;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.planning.nacleica.AdminWorkersActions.AdminWorkersInProgressTasksFragment;
import com.planning.nacleica.Database.DataBaseHelper;
import com.planning.nacleica.R;
import com.planning.nacleica.Title;
import com.planning.nacleica.Utils;
import com.planning.nacleica.AuthActions.ValidationWorkerInputData;
import com.planning.nacleica.AuthActions.Worker;
import com.planning.nacleica.AuthActions.WorkerSession;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.Size;
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
    private final AppCompatActivity compatAdminWorkerActivity = AdminWorkerActivity.this;
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
    WorkerSession session;
    public TextInputEditText nameInputValue;
    public TextInputLayout nameInputLayout;
    public TextInputEditText prenameInputValue;
    public TextInputEditText passwordInputValue;
    public LinearLayoutManager layoutWorkerManager;
    public TextInputLayout passwordInputLayout;
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
    public Utils utils;
    public AppCompatImageView imageView;
    public TextView noAdminWorkersView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_frame);
        initUI();
    }

    void initUI() {
        utils = new Utils(compatAdminWorkerActivity);
        dbHelper = DataBaseHelper.getInstance(this);
        listOfWorkers = new ArrayList<>();
        session = new WorkerSession(getApplicationContext());
        valUserData = new ValidationWorkerInputData(compatAdminWorkerActivity);
        layoutInflater = (LayoutInflater) compatAdminWorkerActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        noAdminWorkersView = view.findViewById(R.id.noWorkers);
        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) compatAdminWorkerActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View postMainView = layoutInflater.inflate(R.layout.register_main, null, false);
                final Toolbar registerToolbar = postMainView.findViewById(R.id.register_app_toolbar);
                final TextInputEditText workerName = postMainView.findViewById(R.id.user_name_text);
                final TextInputEditText workerPrename = postMainView.findViewById(R.id.user_prename_text);
                final TextInputEditText workerBirth = utils.dateToEditText((TextInputEditText) postMainView.findViewById(R.id.user_birth_text));
                final TextInputEditText workerPassword = postMainView.findViewById(R.id.user_pass_text);
                final AppCompatButton workerButton = postMainView.findViewById(R.id.register_button);
                workerButton.setVisibility(View.GONE);

                int[] array= {android.R.attr.text};
                TypedArray typedArray = obtainStyledAttributes(R.style.add_worker,array);
                registerToolbar.setTitle(typedArray.getString(0));
                registerToolbar.setTitleTextAppearance(compatAdminWorkerActivity, R.style.add_worker);

                workerTitleSpinner = postMainView.findViewById(R.id.user_title_text);
                workerTitleSpinner.setAdapter(new ArrayAdapter<Title>(compatAdminWorkerActivity, android.R.layout.simple_spinner_item, Title.values()));
                workerImage = postMainView.findViewById(R.id.userImage);
                workerImage.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        imageView = workerImage;
                        utils.openImagePopupMenu(workerImage);
                    }
                });

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(compatAdminWorkerActivity)
                        .setView(postMainView)

                        .setCancelable(false).setPositiveButton(
                                "Inregistreaza", new DialogInterface.OnClickListener() {
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

                final AlertDialog ad = alertDialog.create();
                ad.show();
                ad.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onClick(View v) {

                        Worker worker = new Worker();
                        worker.Name = workerName.getText().toString();
                        worker.Prename = workerPrename.getText().toString();
                        worker.Title = ((Title) (workerTitleSpinner.getSelectedItem())).getTitleIndex();
                        worker.Image = utils.convertToByteArray(workerImage);
                        worker.Birthday = workerBirth.getText().toString();
                        worker.Password = workerPassword.getText().toString();
                        dbHelper.registerNewWorker(getApplicationContext(), worker);
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
        //listOfWorkers = dbHelper.getWorkers();
        refreshListOfWorkers();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new AlertDialog.Builder(compatAdminWorkerActivity)
                .setTitle("Sunteti siguri?")
                .setMessage("Sunteti sigur ca doriti sa iesiti din aplicatie?")
                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(1);
                    }
                })
                .setNegativeButton("Nu",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RESULT_CAMERA_IMAGE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(compatAdminWorkerActivity, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 0);
            } else {

                Toast.makeText(compatAdminWorkerActivity, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap photo = null;
        switch (requestCode) {
            case RESULT_CAMERA_IMAGE:
                if (resultCode == RESULT_OK) {
                    photo = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(photo);
                }
                break;

            case RESULT_GALLERY_IMAGE:
                if (resultCode == RESULT_OK) {
                    imageView.setImageBitmap(utils.getBitmap(data.getData()));
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
                Toast.makeText(compatAdminWorkerActivity, "Sunteti gata de lucru?", Toast.LENGTH_SHORT).show();
                Intent homeActivity = new Intent(getApplicationContext(), AdminActivity.class);
                homeActivity.putExtra("Image", userImage);
                homeActivity.putExtra("Prename", userPrename);
                homeActivity.putExtra("Name", userName);
                homeActivity.putExtra("Id", idUser);
                startActivity(homeActivity);

                break;
            case R.id.adminWorkers:
                Toast.makeText(compatAdminWorkerActivity, "Ati selectat" + compatAdminWorkerActivity.getResources().getString(R.string.adminWorkers), Toast.LENGTH_SHORT).show();
                Intent postActivity = new Intent(getApplicationContext(), AdminWorkerActivity.class);
                setTitle(compatAdminWorkerActivity.getResources().getString(R.string.adminWorkers));
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

    /*
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

                Worker workerData = new Worker();
                workerData.Name = nameInputValue.getText().toString();
                workerData.Prename = prenameInputValue.getText().toString();
                workerData.Password = passwordInputValue.getText().toString();
                workerData.Image = utils.convertToByteArray(workerImage);
                workerData.Birthday = workerBirth;
                workerData.Title = workerTitleSpinner.getSelectedItemPosition();

                dbHelper.registerNewWorker(getApplicationContext(), workerData);
                refreshListOfWorkers();
                Snackbar.make(null, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
                nameInputValue.setText(null);

            } else {
                // Snack Bar to show error message that record already exists
                Snackbar.make(null, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
            }
        }
    */
    public void refreshListOfWorkers() {
        listOfWorkers = dbHelper.getWorkers();
        adminWorkerRecView = view.findViewById(R.id.adminRecyclerView);
        adminWorkerRecView.setHasFixedSize(true);
        layoutWorkerManager = new LinearLayoutManager(compatAdminWorkerActivity);
        adminWorkerRecView.setLayoutManager(layoutWorkerManager);
        adapterWorkers = new AdminWorkersRecyclerViewAdapter(AdminWorkerActivity.this, listOfWorkers, idUser);
        adminWorkerRecView.setAdapter(adapterWorkers);
        showEmptyDataTextView();
        adminWorkerRecView.refreshDrawableState();

    }

    private void showEmptyDataTextView() {

        if (adapterWorkers.getItemCount() > 0) {
            noAdminWorkersView.setVisibility(View.GONE);
        } else {
            noAdminWorkersView.setVisibility(View.VISIBLE);
        }
    }
/*
    @Override
    public void onClick(View v) {

    }*/
}
