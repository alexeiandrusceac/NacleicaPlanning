package com.planning.nacleica;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.planning.nacleica.authactions.loginActivity;

import androidx.appcompat.app.AppCompatActivity;

public class loadingActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_activity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), loginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        },3000);

    }

}
