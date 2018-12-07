package com.planning.nacleica;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
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
import com.planning.nacleica.AdminActions.AdminActivity;
import com.planning.nacleica.AdminActions.AdminWorkerActivity;
import com.planning.nacleica.Database.DataBaseHelper;
import com.planning.nacleica.WorkerActions.WorkerSession;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;


public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
    public ViewPagerAdapter viewPagerAdapter;
    public ViewPager viewPager;
    private DataBaseHelper dbHelper;
    public FloatingActionButton fab;
    public TextView usr_name_nav;
    public TextView usr_pren_nav;
    private ActionBarDrawerToggle toggle;
    String userPrename, userName;
    private NavigationView navigationView;
    private static AppCompatImageView nav_header_imageView;
    private DrawerLayout drawerLayout;
    byte[] userImage;
    private View header;
    WorkerSession session;
    FrameLayout frameLayout;
    LayoutInflater layoutInflater;
    Toolbar toolbar;
    int idUser;
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_frame);
        initUI();
    }

    void initUI() {
        dbHelper = DataBaseHelper.getInstance(this);


        session = new WorkerSession(getApplicationContext());

        layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        view = layoutInflater.inflate(R.layout.activity_main, frameLayout);
        toolbar = view.findViewById(R.id.worker_toolbar);

        /*fab = view.findViewById(R.id.buttonFloating);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        TabLayout tabs = view.findViewById(R.id.sliding_tabs);
        viewPager = view.findViewById(R.id.viewPager);

        viewPagerAdapter = new ViewPagerAdapter(getApplicationContext(),getSupportFragmentManager(),4,idUser);
        viewPager.setAdapter(viewPagerAdapter);

        tabs.setupWithViewPager(viewPager);
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

    }
    private void setupDrawer() {
        toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
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
                Toast.makeText(MainActivity.this, "Ati ajuns acasa", Toast.LENGTH_SHORT).show();
                Intent homeActivity = new Intent(getApplicationContext(), MainActivity.class);
                homeActivity.putExtra("Image", userImage);
                homeActivity.putExtra("Prename", userPrename);
                homeActivity.putExtra("Name", userName);
                homeActivity.putExtra("Id", idUser);
                startActivity(homeActivity);

                break;
           /* case R.id.adminWorkers:
                Toast.makeText(MainActivity.this, "Ati selectat ofertele dvs", Toast.LENGTH_SHORT).show();
                Intent postActivity = new Intent(getApplicationContext(), AdminWorkerActivity.class);
                setTitle(AdminActivity.this.getResources().getString(R.string.adminWorkers));
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
