package com.planning.nacleica.WorkerActions;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.planning.nacleica.AdminActions.AdminActivity;
import com.planning.nacleica.MainActivity;
import com.planning.nacleica.R;
import com.planning.nacleica.Database.DataBaseHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity compatActivity = LoginActivity.this;
    private NestedScrollView scrollView;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        getSupportActionBar().hide();
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

        // Initializarea Listeners
        loginButton.setOnClickListener(this);
        registerLink.setOnClickListener(this);

        valWorkerInput = new ValidationWorkerInputData(compatActivity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                checkUser();
                break;
            case R.id.registerlink:
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
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

            if (currWorker.Title != 4) {
                Intent mainActivityIntent = new Intent(getApplicationContext(), AdminActivity.class);
                mainActivityIntent.putExtra("Image", currWorker.Image);
                mainActivityIntent.putExtra("Name", currWorker.Name);
                mainActivityIntent.putExtra("Prename", currWorker.Prename);
                mainActivityIntent.putExtra("Id", currWorker.workerID);

                startActivity(mainActivityIntent);
                finish();
            } else {
                Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                mainActivityIntent.putExtra("Image", currWorker.Image);
                mainActivityIntent.putExtra("Name", currWorker.Name);
                mainActivityIntent.putExtra("Prename", currWorker.Prename);
                mainActivityIntent.putExtra("Id", currWorker.workerID);

                startActivity(mainActivityIntent);
                finish();
            }
        } else {
            Snackbar.make(scrollView, getString(R.string.error_name_password), Snackbar.LENGTH_LONG).show();
        }
    }
}
