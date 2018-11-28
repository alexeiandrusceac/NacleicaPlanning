package com.planning.nacleica;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.planning.nacleica.WorkerActions.LoginActivity;
import com.planning.nacleica.WorkerActions.RegisterActivity;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_activity);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        },3000);

    }

}
