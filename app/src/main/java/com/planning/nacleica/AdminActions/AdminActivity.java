package com.planning.nacleica.AdminActions;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.planning.nacleica.Database.DataBaseHelper;
import com.planning.nacleica.R;
import com.planning.nacleica.Tasks;
import com.planning.nacleica.Utils;
import com.planning.nacleica.ViewPagerAdapter;
import com.planning.nacleica.AuthActions.WorkerSession;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public AppCompatActivity compatAdminActivity = AdminActivity.this;
    public ViewPagerAdapter adminViewPagerAdapter, adminWorkerViewPageAdapter;
    public ViewPager adminViewPager, adminWorkerViewPager;
    public FloatingActionButton fab;
    private DataBaseHelper dbHelper;
    private ActionBarDrawerToggle toggle;
    String userPrename, userName;
    private static final int RESULT_GALLERY_IMAGE = 1;
    private static final int RESULT_CAMERA_IMAGE = 0;
    private NavigationView navigationView;
    private static AppCompatImageView nav_header_imageView;
    private DrawerLayout drawerLayout;
    byte[] userImage;
    public TextView usr_name_nav;
    public TextView usr_pren_nav;
    public View view;
    int idUser;

    private View header;
    WorkerSession session;
    FrameLayout frameLayout;
    LayoutInflater layoutInflater;
    Toolbar toolbar;
    public AppCompatImageView imageView;
    public Utils utils;
    private TabLayout tabsAdmin;
    private TabLayout tabsWorker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_frame);
        initUI();
    }

    void initUI() {
        dbHelper = DataBaseHelper.getInstance(this);
        utils = new Utils(compatAdminActivity);
        session = new WorkerSession(getApplicationContext());
        layoutInflater = (LayoutInflater) compatAdminActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.getHeaderView(0);
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


        view = layoutInflater.inflate(R.layout.admin_main, frameLayout);
        fab = view.findViewById(R.id.adminWorkerFab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTask();
            }
        });
        toolbar = view.findViewById(R.id.main_app_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
        refreshListOfAdminTasks();
    }

    void createTask() {
        LayoutInflater layoutInflater = (LayoutInflater) AdminActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View postMainTaskView = layoutInflater.inflate(R.layout.new_task_main, null, false);

        final AppCompatImageView imageBeforeView = postMainTaskView.findViewById(R.id.imageBeforeView);
        final TextInputEditText nameTaskText = postMainTaskView.findViewById(R.id.task_name_value);
        final TextInputEditText nameCompText = postMainTaskView.findViewById(R.id.comp_name_value);
        final TextInputEditText dateFromText = utils.dateToEditText((TextInputEditText) postMainTaskView.findViewById(R.id.date_from_value));
        final TextInputEditText dateToText = utils.dateToEditText((TextInputEditText) postMainTaskView.findViewById(R.id.date_to_value));
        final TextInputEditText compPhoneText = postMainTaskView.findViewById(R.id.comp_phone_value);

        imageBeforeView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imageView = imageBeforeView;
                utils.openImagePopupMenu(imageBeforeView);
            }
        });

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(compatAdminActivity).setView(postMainTaskView).setCancelable(false).setPositiveButton(
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

                Tasks task = new Tasks();
                task.TaskName = nameTaskText.getText().toString();
                task.TaskState = 0;
                task.TaskCompany = nameCompText.getText().toString();
                task.TaskCompanyPhone = compPhoneText.getText().toString();
                task.TaskPeriodFrom = dateFromText.getText().toString();
                task.TaskPeriodTo = dateToText.getText().toString();
                task.TaskImageBefore = utils.convertToByteArray(imageBeforeView);
                task.TaskImageAfter = utils.convertToByteArray(imageBeforeView);

                dbHelper.createNewTask(getApplicationContext(), task);
                refreshListOfAdminTasks();
                ad.dismiss();

            }
        });

    }

    private void setupDrawer() {
        toggle = new ActionBarDrawerToggle(AdminActivity.this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
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
                Toast.makeText(AdminActivity.this, "Ati ajuns acasa", Toast.LENGTH_SHORT).show();
                Intent homeActivity = new Intent(getApplicationContext(), AdminActivity.class);
                homeActivity.putExtra("Image", userImage);
                homeActivity.putExtra("Prename", userPrename);
                homeActivity.putExtra("Name", userName);
                homeActivity.putExtra("Id", idUser);
                startActivity(homeActivity);

                break;
            case R.id.adminWorkers:
                Toast.makeText(AdminActivity.this, "Ati selectat monitorizarea angajatilor", Toast.LENGTH_SHORT).show();
                Intent postActivity = new Intent(getApplicationContext(), AdminWorkerActivity.class);
                setTitle(AdminActivity.this.getResources().getString(R.string.adminWorkers));
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

    public void refreshListOfAdminTasks() {

        tabsAdmin = view.findViewById(R.id.admin_tab);
        tabsWorker = view.findViewById(R.id.adminWorker_tab);
        adminViewPager = view.findViewById(R.id.adminViewPager);
        adminWorkerViewPager = view.findViewById(R.id.adminWorkerViewPager);

        adminViewPagerAdapter = new ViewPagerAdapter(compatAdminActivity, true, getSupportFragmentManager(), 0, idUser);
        adminViewPager.setAdapter(adminViewPagerAdapter);
        adminViewPager.refreshDrawableState();
        tabsAdmin.setupWithViewPager(adminViewPager);
        tabsAdmin.refreshDrawableState();
        adminWorkerViewPageAdapter = new ViewPagerAdapter(compatAdminActivity, false, getSupportFragmentManager(), 0, idUser);
        adminWorkerViewPager.setAdapter(adminWorkerViewPageAdapter);
        adminWorkerViewPager.refreshDrawableState();
        tabsWorker.setupWithViewPager(adminWorkerViewPager);
        tabsWorker.refreshDrawableState();

    }
}
