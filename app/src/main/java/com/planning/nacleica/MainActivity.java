package com.planning.nacleica;




import android.os.Bundle;

import android.view.View;
import android.widget.TextView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.planning.nacleica.database.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


public class MainActivity extends AppCompatActivity {
    public ViewPagerAdapter viewPagerAdapter;
    public ViewPager viewPager;
    public FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    void initUI() {
        TabLayout tabs = (TabLayout) findViewById(R.id.sliding_tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        fab = (FloatingActionButton) findViewById(R.id.buttonFloating);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        fillNewTask();
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        tabs.setupWithViewPager(viewPager);
    }

    public void fillNewTask()
    {

    }
}
