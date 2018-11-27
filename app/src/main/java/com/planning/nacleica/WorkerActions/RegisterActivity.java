package com.planning.nacleica.WorkerActions;

/*
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;*/


import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.Toast;

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
import java.io.IOException;
import java.lang.reflect.Array;
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
    private int RESULT_LOAD_IMAGE = 1;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    public DatePickerDialog datepicker;
    private ByteArrayOutputStream baos = new ByteArrayOutputStream();
    public View photoView;
public String data;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);
        //getSupportActionBar().hide();
        initUI();
    }

    void initUI() {
        workerDBHelper = DataBaseHelper.getInstance(this);
        workerData = new Worker();
        scrollView = (NestedScrollView) findViewById(R.id.scroll);
        //final ImagePopup imagePopup = new ImagePopup(this);
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

        registerButton = (AppCompatButton) findViewById(R.id.register_button);


        // String[] stringArray = (String[])(Title.values());
        workerTitleSpinner.setAdapter(new ArrayAdapter<Title>(this, android.R.layout.simple_spinner_item, Title.values()));

        registerButton.setOnClickListener(this);
        valUserData = new ValidationWorkerInputData(compatActivity);
        data =  setDate();
        userImageValue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               /* LayoutInflater inflaterPhoto = (LayoutInflater)RegisterActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                photoView = inflaterPhoto.inflate(R.menu.photo_menu,null);
*/
                PopupMenu popupMenu = new PopupMenu(compatActivity, userImageValue);
                popupMenu.getMenuInflater().inflate(R.menu.photo_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NewApi")
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.take_photo:
                                if (checkSelfPermission(Manifest.permission.CAMERA)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                                            MY_CAMERA_PERMISSION_CODE);
                                } else {
                                    startActivityForResult(new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE), RESULT_LOAD_IMAGE);
                                }

                                return true;
                            case R.id.choose_photo:
                                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), RESULT_LOAD_IMAGE);
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
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // MenuInflater inflater = getMenuInflater();

        return true;
    }*/

   /* @SuppressLint("NewApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //case R.id.action_settings:
            //    return true;

            case R.id.take_photo:
                if (checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_CAMERA_PERMISSION_CODE);
                } else {
                    startActivityForResult(new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE), RESULT_LOAD_IMAGE);
                }
                return true;
            case R.id.choose_photo:
                startActivityForResult(new Intent(Intent.ACTION_PICK), RESULT_LOAD_IMAGE);
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }

    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            workerData.Birthday = /*userBirthdayValue.getText().toString();*/data ;
            workerData.Title = workerTitleSpinner.getSelectedItemPosition();
            workerDBHelper.registerNewWorker(workerData);
            Snackbar.make(scrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            nameInputValue.setText(null);

            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);
        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(scrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, RESULT_LOAD_IMAGE);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
        /*switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startActivityForResult(new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI), RESULT_LOAD_IMAGE);
                    //startActivityForResult(cameraIntent, RESULT_LOAD_IMAGE);

                } else {
                    Toast.makeText(this, "Please give your permission.", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }*/
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       /* if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            userImageValue.setImageBitmap(photo);
        }*/
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            //String[] filePathColumn = {MediaStore.Images.Media.DATA};
/*
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            /*String picturePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
            BitmapFactory.Options op = new BitmapFactory.Options();
            op.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath,op);
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);*/

            try {
                Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));

                userImageValue.setImageBitmap(bit);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));

            /*} catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/


            // cursor.close();


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
