package com.planning.nacleica.AdminActions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.planning.nacleica.Database.DataBaseHelper;
import com.planning.nacleica.MainActivity;
import com.planning.nacleica.R;
import com.planning.nacleica.Title;
import com.planning.nacleica.ViewPagerAdapter;
import com.planning.nacleica.WorkerActions.Worker;
import com.planning.nacleica.WorkerActions.WorkerSession;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public ViewPagerAdapter adminViewPagerAdapter, adminWorkerViewPageAdapter;
    public ViewPager adminViewPager, adminWorkerViewPager;
    public FloatingActionButton fab;
    private DataBaseHelper dbHelper;
    private ActionBarDrawerToggle toggle;
    String userPrename, userName;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_frame);
        initUI();
    }

    void initUI() {
        dbHelper = DataBaseHelper.getInstance(this);


        session = new WorkerSession(getApplicationContext());

        layoutInflater = (LayoutInflater) AdminActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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


        view = layoutInflater.inflate(R.layout.admin_main, frameLayout);
        fab = view.findViewById(R.id.adminWorkerFab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTask();
            }
        });
        toolbar = view.findViewById(R.id.main_app_toolbar);



        TabLayout tabs = view.findViewById(R.id.admin_tab);
        TabLayout tabsWorker = view.findViewById(R.id.adminWorker_tab);
        adminViewPager = view.findViewById(R.id.adminViewPager);
        adminWorkerViewPager = view.findViewById(R.id.adminWorkerViewPager);

        adminViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0,idUser);
        adminViewPager.setAdapter(adminViewPagerAdapter);
        tabs.setupWithViewPager(adminViewPager);

        adminWorkerViewPageAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 4,idUser);
        adminWorkerViewPager.setAdapter(adminWorkerViewPageAdapter);
        tabsWorker.setupWithViewPager(adminWorkerViewPager);

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
    void createTask()
    {
        LayoutInflater layoutInflater = (LayoutInflater) AdminActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View postMainTaskView = layoutInflater.inflate(R.layout.new_task_main, null, false);

        final TextInputEditText nameTaskText = postMainTaskView.findViewById(R.id.nameTaskText);
        final TextInputEditText workerPrename = postMainTaskView.findViewById(R.id.user_prename_text);
        final TextInputEditText workerBirth = utils.dateToEditText((TextInputEditText) postMainView.findViewById(R.id.user_birth_text));
        final TextInputEditText workerPassword = postMainTaskView.findViewById(R.id.user_pass_text);
        final AppCompatButton workerButton = postMainTaskView.findViewById(R.id.register_button);
        workerButton.setVisibility(View.GONE);


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

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(compatAdminWorkerActivity).setTitle("Adaugarea angajatului").setView(postMainView).setCancelable(false).setPositiveButton(
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
                //registerUser();
            }
        });
    }
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
                Toast.makeText(AdminActivity.this, "Ati selectat ofertele dvs", Toast.LENGTH_SHORT).show();
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
}
