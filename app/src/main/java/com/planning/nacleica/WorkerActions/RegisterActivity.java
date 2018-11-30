package com.planning.nacleica.WorkerActions;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;

import android.app.DatePickerDialog;

import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.text.InputType;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.planning.nacleica.R;
import com.planning.nacleica.Title;
import com.planning.nacleica.Database.DataBaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity compatActivity = RegisterActivity.this;
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
    private ByteArrayOutputStream baos = new ByteArrayOutputStream();
    public View photoView;
    public String data;
    Intent loginActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);
        initUI();
    }

    void initUI() {
        loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
        valUserData = new ValidationWorkerInputData(compatActivity);
        workerDBHelper = DataBaseHelper.getInstance(this);

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
        userBirthdayValue = (TextInputEditText) findViewById(R.id.user_birth_text);
        data = setDate();
        registerButton = (AppCompatButton) findViewById(R.id.register_button);
        workerTitleSpinner.setAdapter(new ArrayAdapter<Title>(this, android.R.layout.simple_spinner_item, Title.values()));

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.register_app_toolbar);

        registerButton.setOnClickListener(this);
        // action_settings = findViewById(R.id.action_settings);
        userImageValue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(compatActivity, userImageValue);
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
                                userImageValue.setImageDrawable(getDrawable(R.drawable.ic_profile));
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

                popupMenu.show();
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

            Bitmap bitmap = ((BitmapDrawable) userImageValue.getDrawable()).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

            workerData.Name = nameInputValue.getText().toString();
            workerData.Prename = prenameInputValue.getText().toString();
            workerData.Password = passwordInputValue.getText().toString();
            workerData.Image = baos.toByteArray();
            workerData.Birthday = /*userBirthdayValue.getText().toString();*/data;
            workerData.Title = workerTitleSpinner.getSelectedItemPosition();
            workerDBHelper.registerNewWorker(getApplicationContext(),workerData);
            Snackbar.make(scrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            nameInputValue.setText(null);
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

                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 0);
            } else {

                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap photo = null;
        Uri selectedImage;
        switch (requestCode) {
            case RESULT_CAMERA_IMAGE:
                if (resultCode == RESULT_OK) {
                    photo = (Bitmap) data.getExtras().get("data");
                    userImageValue.setImageBitmap(photo);
                }
                break;

            case RESULT_GALLERY_IMAGE:
                if (resultCode == RESULT_OK) {
                    selectedImage = data.getData();
                    try {
                        photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
                        userImageValue.setImageBitmap(photo);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                break;

        }
    }


    /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            userImageValue.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }*/


    public String setDate() {
        final Calendar cldr = Calendar.getInstance();

        //final TextInputEditText dateinput = (TextInputEditText) findViewById(R.id.user_birth_text);
        userBirthdayValue.setInputType(InputType.TYPE_NULL);
        userBirthdayValue.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.O)
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                datepicker = new DatePickerDialog(compatActivity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        userBirthdayValue.setText((((date < 10) ? "0" : "") + date) + " / " + (((month < 10) ? "0" : "") + (month + 1)) + " / " + year);
                    }
                }, year, month, day);
                datepicker.show();
            }
        });
        return userBirthdayValue.getText().toString();
    }

}
