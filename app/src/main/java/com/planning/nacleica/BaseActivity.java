package com.planning.nacleica;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    @Override
    public void setContentView(int resId) {

        LinearLayout screenRootView = new LinearLayout(this);
        screenRootView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        screenRootView.setOrientation(LinearLayout.VERTICAL);

        // Create your top view here
        View topView = new View(this); // Replace this topview with your view

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View screenView = inflater.inflate(resId, null);
        topView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //You will get onclick here of your topview in whatever screen it is clicked
            }
        });

        screenRootView.addView(topView);
        screenRootView.addView(screenView);

        super.setContentView(screenRootView);
    }
}
