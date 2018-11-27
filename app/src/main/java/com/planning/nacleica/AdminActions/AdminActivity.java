package com.planning.nacleica.AdminActions;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.planning.nacleica.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.admin_main);


    }
}
