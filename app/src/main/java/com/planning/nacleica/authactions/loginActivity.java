package com.planning.nacleica.authactions;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.planning.nacleica.BaseActivity;
import com.planning.nacleica.BroadcastReceiver;
import com.planning.nacleica.KeyboardUtils;
import com.planning.nacleica.adminactions.adminActivity;
import com.planning.nacleica.mainActivity;
import com.planning.nacleica.R;
import com.planning.nacleica.database.DataBaseHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {
    long  touchDownTime = 0;
    private final AppCompatActivity compatActivity = loginActivity.this;
    private NestedScrollView scrollView;
    private TextView networkView;
    private TextInputLayout nameLayout;
    private TextInputLayout passwordLayout;
    private TextInputEditText nameInputEditText;
    private TextInputEditText passwordInputEditText;
    private AppCompatButton loginButton;
    private TextView registerLink;
    private ValidationWorkerInputData valWorkerInput;
    private DataBaseHelper workerDBHelper;
    private AppBarLayout appBarLayout;
    private TextView orRegister;
    // private Toolbar loginToolbar;
    WorkerSession session;
    BroadcastReceiver myBroadCastReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        //getSupportActionBar().hide();
        initUI();

    }

    void initUI() {
        workerDBHelper = DataBaseHelper.getInstance(this);
        session = new WorkerSession(getApplicationContext());
        ///Initializarea obiectelor din activity
        scrollView = (NestedScrollView) findViewById(R.id.scroll);
        nameLayout = (TextInputLayout) findViewById(R.id.user_name_layout);
        passwordLayout = (TextInputLayout) findViewById(R.id.password_layout);
        nameInputEditText = (TextInputEditText) findViewById(R.id.user_name_text);
        passwordInputEditText = (TextInputEditText) findViewById(R.id.password_Input);
        orRegister = (TextView) findViewById(R.id.orRegister);
        loginButton = (AppCompatButton) findViewById(R.id.login_button);
        registerLink = (TextView) findViewById(R.id.registerlink);
        myBroadCastReceiver = new BroadcastReceiver();

        //CoordinatorLayout layout = (CoordinatorLayout) findViewById(R.id.coordinatorlayout);
        // Initializarea Listeners
        loginButton.setOnClickListener(this);
        registerLink.setOnClickListener(this);

        valWorkerInput = new ValidationWorkerInputData(compatActivity);
        myBroadCastReceiver = new BroadcastReceiver();
        registerNetworkBroadcast();

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    private void registerNetworkBroadcast() {
        registerReceiver(myBroadCastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(myBroadCastReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    /*@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        // Checks whether a hardware keyboard is available
        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
            Toast.makeText(this, "keyboard visible", Toast.LENGTH_SHORT).show();
        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            Toast.makeText(this, "keyboard hidden", Toast.LENGTH_SHORT).show();
        }
    }*/


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                checkUser();
                break;
            case R.id.registerlink:
                Intent intentRegister = new Intent(getApplicationContext(), registerActivity.class);
                startActivity(intentRegister);
                finish();
                break;
        }
    }

    private void checkUser() {
        if (!valWorkerInput.nameValidating(nameInputEditText, nameLayout, getString(R.string.error_name)))
            return;

        if (!valWorkerInput.textFilled(passwordInputEditText, passwordLayout, getString(R.string.error_password)))
            return;

        if (workerDBHelper.checkUserOnLogin(nameInputEditText.getText().toString().trim(), passwordInputEditText.getText().toString().trim())) {

            Worker currWorker = workerDBHelper.getWorker(nameInputEditText.getText().toString(), passwordInputEditText.getText().toString());

            session.createWorkerLoginSession(nameInputEditText.getText().toString(), passwordInputEditText.getText().toString());

            if (currWorker != null) {
                if (currWorker.Title != 4) {
                    Intent mainActivityIntent = new Intent(getApplicationContext(), adminActivity.class);
                    mainActivityIntent.putExtra("Image", currWorker.Image);
                    mainActivityIntent.putExtra("Name", currWorker.Name);
                    mainActivityIntent.putExtra("Prename", currWorker.Prename);
                    mainActivityIntent.putExtra("Id", currWorker.workerID);

                    startActivity(mainActivityIntent);
                    finish();
                } else {
                    Intent mainActivityIntent = new Intent(getApplicationContext(), mainActivity.class);
                    mainActivityIntent.putExtra("Image", currWorker.Image);
                    mainActivityIntent.putExtra("Name", currWorker.Name);
                    mainActivityIntent.putExtra("Prename", currWorker.Prename);
                    mainActivityIntent.putExtra("Id", currWorker.workerID);

                    startActivity(mainActivityIntent);
                    finish();
                }
            }
        } else {
            Snackbar.make(scrollView, getString(R.string.error_name_password), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }
}
