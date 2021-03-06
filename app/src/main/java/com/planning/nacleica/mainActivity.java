package com.planning.nacleica;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
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
import com.planning.nacleica.database.DataBaseHelper;
import com.planning.nacleica.authactions.WorkerSession;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;


public class mainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public mainActivity compatMainActivity = mainActivity.this;
    public WorkerViewPagerAdapter viewPagerAdapter;
    public ViewPager viewPager;
    public DataBaseHelper dbHelper;
    public FloatingActionButton fab;
    public TextView usr_info_nav;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private static AppCompatImageView nav_header_imageView;
    private DrawerLayout drawerLayout;
    byte[] userImage;
    String userName, userPrename;
    private View header;
    WorkerSession session;
    FrameLayout frameLayout;
    LayoutInflater layoutInflater;
    Toolbar toolbar;
    int idUser;
    private View view;
    public List<Tasks> listOfNewTasks;
    public List<Tasks> listOfInProgressTasks;
    public List<Tasks> listOfDoneTasks;
    private Bundle bundleData;
BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker_main_frame);
        initUI();
        getBundleData();
    }

    void getBundleData() {
        bundleData = getIntent().getExtras();
        userPrename = bundleData.getString("Prename");
        userName = bundleData.getString("Name");
        userImage = bundleData.getByteArray("Image");
        idUser = bundleData.getInt("Id");
        usr_info_nav.setText(userName + " " + userPrename);
        nav_header_imageView.setImageBitmap(BitmapFactory.decodeByteArray(userImage, 0, userImage.length));
    }

    void initUI() {

        dbHelper = DataBaseHelper.getInstance(this);

        session = new WorkerSession(getApplicationContext());

        layoutInflater = (LayoutInflater) mainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        frameLayout = (FrameLayout) findViewById(R.id.worker_content_frame);
        navigationView = (NavigationView) findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(this);
        header = (navigationView).getHeaderView(0);
        usr_info_nav = header.findViewById(R.id.usr_info_nav);

        nav_header_imageView = header.findViewById(R.id.nav_header_imageView);

        Bundle bundleData = getIntent().getExtras();
        userPrename = bundleData.getString("Prename");
        userName = bundleData.getString("Name");
        userImage = bundleData.getByteArray("Image");
        idUser = bundleData.getInt("Id");
        usr_info_nav.setText(userName + " " + userPrename);
        nav_header_imageView.setImageBitmap(BitmapFactory.decodeByteArray(userImage, 0, userImage.length));

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        setupDrawer();

        view = layoutInflater.inflate(R.layout.activity_main, frameLayout);
        toolbar = view.findViewById(R.id.worker_toolbar);

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
        fillWorkerTasks();
        refreshWorkerTasks();
        broadcastReceiver = new BroadcastReceiver();
        registerNetworkBroadcast();

    }
    private void registerNetworkBroadcast() {
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(broadcastReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }

   public void refreshWorkerTasks() {
        TabLayout tabs = view.findViewById(R.id.worker_tab);
        viewPager = view.findViewById(R.id.workerViewPager);

        viewPagerAdapter = new WorkerViewPagerAdapter(compatMainActivity, getSupportFragmentManager(), idUser);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.refreshDrawableState();

        tabs.setupWithViewPager(viewPager);
        tabs.refreshDrawableState();
    }

    public void fillWorkerTasks() {
        listOfNewTasks = dbHelper.getWorkerNewTask(idUser);
        listOfInProgressTasks = dbHelper.getWorkerInProgressTask(idUser);
        listOfDoneTasks = dbHelper.getWorkerDoneTask(idUser);

    }

    private void setupDrawer() {
        toggle = new ActionBarDrawerToggle(compatMainActivity, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
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
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Toast.makeText(mainActivity.this, "Ati ajuns acasa", Toast.LENGTH_SHORT).show();
                Intent homeActivity = new Intent(mainActivity.this, mainActivity.class);
                homeActivity.putExtra("Image", userImage);
                homeActivity.putExtra("Prename", userPrename);
                homeActivity.putExtra("Name", userName);
                homeActivity.putExtra("Id", idUser);
                startActivity(homeActivity);

                break;
           /* case R.id.adminWorkers:
                Toast.makeText(mainActivity.this, "Ati selectat ofertele dvs", Toast.LENGTH_SHORT).show();
                Intent postActivity = new Intent(getApplicationContext(), adminWorkerActivity.class);
                setTitle(adminActivity.this.getResources().getString(R.string.adminWorkers));
                postActivity.putExtra("Image", userImage);
                postActivity.putExtra("Prename", userPrename);
                postActivity.putExtra("Name", userName);
                postActivity.putExtra("Id", idUser);
                startActivity(postActivity);

                break;*/
            case R.id.logOut_button:
                session.logoutWorker();
                finish();
            default:
                return false;
        }
        return true;
    }

}
