package com.planning.nacleica.authactions;

import android.app.DatePickerDialog;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;

import android.graphics.Bitmap;

import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.planning.nacleica.BroadcastReceiver;
import com.planning.nacleica.R;
import com.planning.nacleica.Title;
import com.planning.nacleica.database.DataBaseHelper;
import com.planning.nacleica.Utils;

import androidx.appcompat.widget.Toolbar;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;

public class registerActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity compatRegisterActivity = registerActivity.this;
    private NestedScrollView scrollView;
    private TextInputEditText nameInputValue;
    private TextInputLayout nameInputLayout;
    private TextInputEditText prenameInputValue;
    private TextInputLayout prenameInputLayout;
    private TextInputEditText passwordInputValue;

    private TextInputLayout passwordInputLayout;
    private TextInputLayout workerTitleLayout;
    private AppCompatSpinner workerTitleSpinner;
    private TextInputEditText confPasswdInputValue;
    private TextInputLayout confPasswordInputLayout;
    private AppCompatButton registerButton;
    private ValidationWorkerInputData valUserData;
    private DataBaseHelper workerDBHelper;
    private Worker workerData;
    private AppCompatImageView userImageValue;
    private TextInputLayout userBirthdayLayout;
    private TextInputEditText userBirthdayValue;
    private static final int RESULT_GALLERY_IMAGE = 1;
    private static final int RESULT_CAMERA_IMAGE = 0;
    public DatePickerDialog datepicker;
    public String data;
    Intent loginActivity;
    public Utils utils;
    BroadcastReceiver myBroadCastReceiver;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);
        initUI();
    }

    void initUI() {
        loginActivity = new Intent(compatRegisterActivity, loginActivity.class);
        valUserData = new ValidationWorkerInputData(compatRegisterActivity);
        workerDBHelper = DataBaseHelper.getInstance(compatRegisterActivity);
        utils = new Utils(compatRegisterActivity);
        workerData = new Worker();
        scrollView = (NestedScrollView) findViewById(R.id.scroll);
        nameInputLayout = (TextInputLayout) findViewById(R.id.user_name_layout);
        prenameInputLayout = (TextInputLayout) findViewById(R.id.user_prename_layout);
        passwordInputLayout = (TextInputLayout) findViewById(R.id.user_pass_layout);
        confPasswordInputLayout = (TextInputLayout) findViewById(R.id.user_confpass_layout);
        nameInputValue = (TextInputEditText) findViewById(R.id.user_name_text);
        prenameInputValue = (TextInputEditText) findViewById(R.id.user_prename_text);
        workerTitleSpinner = (AppCompatSpinner) findViewById(R.id.user_title_text);
        passwordInputValue = (TextInputEditText) findViewById(R.id.user_pass_text);
        confPasswdInputValue = (TextInputEditText) findViewById(R.id.user_confpass_text);
        userImageValue = (AppCompatImageView) findViewById(R.id.userImage);
        userBirthdayLayout = (TextInputLayout) findViewById(R.id.user_birth_layout);
        userBirthdayValue = utils.dateToEditText((TextInputEditText)findViewById(R.id.user_birth_text));

        registerButton = (AppCompatButton) findViewById(R.id.register_button);
        workerTitleSpinner.setAdapter(new ArrayAdapter<Title>(compatRegisterActivity, android.R.layout.simple_spinner_dropdown_item, Title.values()));

        Toolbar toolbar = (Toolbar) findViewById(R.id.register_app_toolbar);
        registerButton.setOnClickListener(this);
        userImageValue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                utils.openImagePopupMenu(userImageValue);
               /* PopupMenu popupMenu = new PopupMenu(compatActivity, userImageValue);
                popupMenu.getMenuInflater().inflate(R.menu.photo_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NewApi")
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.take_photo:
                                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                    requestPermissions(new String[]{Manifest.permission.CAMERA}, RESULT_CAMERA_IMAGE);
                                } else {
                                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(cameraIntent, RESULT_CAMERA_IMAGE);
                                }

                                return true;
                            case R.id.choose_photo:
                                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), RESULT_GALLERY_IMAGE);
                                return true;
                            case R.id.delete_photo:
                                userImageValue.setImageDrawable(getDrawable(R.drawable.ic_profile2));
                                return true;
                        }
                        Toast.makeText(
                                compatActivity,
                                "Ati selectat: " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();
                        return true;
                    }
                });

                popupMenu.show();*/
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(loginActivity);
                finish();
            }
        });
        myBroadCastReceiver = new BroadcastReceiver();
        registerNetworkBroadcast();

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_example, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.register_worker:
                registerUser();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_worker:
                registerUser();
                break;
            case R.id.register_button:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        if (!valUserData.textFilled(nameInputValue, nameInputLayout, getString(R.string.error_name))) {
            return;
        }
        if (!valUserData.textFilled(passwordInputValue, passwordInputLayout, getString(R.string.error_password))) {
            return;
        }
        if (!valUserData.isInputEditTextMatches(passwordInputValue, confPasswdInputValue,
                confPasswordInputLayout, getString(R.string.error_password_match))) {
            return;
        }

        if (!workerDBHelper.checkUserOnLogin(nameInputValue.getText().toString().trim())) {
            workerData.Name = nameInputValue.getText().toString();
            workerData.Prename = prenameInputValue.getText().toString();
            workerData.Password = passwordInputValue.getText().toString();
            workerData.Image = utils.convertToByteArray(userImageValue);
            workerData.Birthday = userBirthdayValue.getText().toString();
            workerData.Title = ((Title) (workerTitleSpinner.getSelectedItem())).getTitleIndex();
            workerDBHelper.registerNewWorker(getApplicationContext(), workerData);
            Snackbar.make(scrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            startActivity(loginActivity);
            finish();
        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(scrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RESULT_CAMERA_IMAGE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(compatRegisterActivity, "Permisiunea camerei este acceptata", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 0);
            } else {

                Toast.makeText(compatRegisterActivity, "Permisiunea camerei este refuzata", Toast.LENGTH_LONG).show();
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap photo = null;

        switch (requestCode) {
            case RESULT_CAMERA_IMAGE:
                if (resultCode == RESULT_OK) {
                    photo = (Bitmap) data.getExtras().get("data");
                    userImageValue.setImageBitmap(photo);
                }
                break;

            case RESULT_GALLERY_IMAGE:
                if (resultCode == RESULT_OK) {
                    userImageValue.setImageBitmap(utils.getBitmap(data.getData()));
                }
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }
}
