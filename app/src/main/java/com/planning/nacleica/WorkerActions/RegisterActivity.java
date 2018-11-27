package com.planning.nacleica.WorkerActions;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.planning.nacleica.R;
import com.planning.nacleica.Title;
import com.planning.nacleica.Database.DataBaseHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private final AppCompatActivity compatActivity = RegisterActivity.this;
    private NestedScrollView scrollView;
    private TextInputEditText nameInputValue;
    private TextInputLayout nameInputLayout;
    private TextInputEditText prenameInputValue;
    private TextInputLayout prenameInputLayout;
    private TextInputEditText emailInputValue;
    private TextInputLayout emailInputLayout;
    private TextInputEditText passwordInputValue;

    private TextInputLayout passwordInputLayout;
    private TextInputLayout workerTitleLayout;
    private Spinner workerTitleSpinner;
    private TextInputEditText confPasswdInputValue;
    private TextInputLayout confPasswordInputLayout;
    private AppCompatButton registerButton;
    private ValidationWorkerInputData valUserData;
    private DataBaseHelper workerDBHelper;
    private Worker workerData = new Worker();
    private ImageView userImage;
    private int RESULT_LOAD_IMAGE = 1;

    public RegisterActivity() {
    }
    //private ByteArrayOutputStream baos = new ByteArrayOutputStream();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        getSupportActionBar().hide();

        workerDBHelper = DataBaseHelper.getInstance(this);

        scrollView = (NestedScrollView) findViewById(R.id.scroll);

        nameInputLayout = (TextInputLayout) findViewById(R.id.user_name_layout);
        prenameInputLayout = (TextInputLayout) findViewById(R.id.user_prename_layout);
        passwordInputLayout = (TextInputLayout) findViewById(R.id.user_pass_layout);
        confPasswordInputLayout = (TextInputLayout) findViewById(R.id.user_confpass_layout);

        nameInputValue = (TextInputEditText) findViewById(R.id.user_name_text);
        prenameInputValue = (TextInputEditText) findViewById(R.id.user_prename_text);
        workerTitleSpinner = (Spinner)findViewById(R.id.user_title_text);
        passwordInputValue = (TextInputEditText) findViewById(R.id.user_pass_text);
        confPasswdInputValue = (TextInputEditText) findViewById(R.id.user_confpass_text);
        /*emailInputValue = (TextInputEditText) findViewById(R.id.user_email_text);
        emailInputLayout = (TextInputLayout) findViewById(R.id.user_email_layout);*/

         registerButton = (AppCompatButton) findViewById(R.id.register_button);
        workerTitleSpinner.setAdapter(new ArrayAdapter<Title>(this, android.R.layout.simple_spinner_item, Title.values()));

        registerButton.setOnClickListener(this);
        valUserData = new ValidationWorkerInputData(compatActivity);

        /*userImage = (ImageView) findViewById(R.id.userImage);
        userData = new User();
        userImage.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });*/
    }

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

            workerData.Name = nameInputValue.getText().toString();
            workerData.Prename = prenameInputValue.getText().toString();

            workerData.Title = workerTitleSpinner.getSelectedItem().toString();
            //workerData.Title = (String)();
            //userData.Email = emailInputValue.getText().toString();
            workerData.Password = passwordInputValue.getText().toString();
           /* Bitmap bitmap = ((BitmapDrawable) userImage.getDrawable()).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            userData.Image = baos.toByteArray();*/
            workerDBHelper.registerNewWorker(workerData);

            Snackbar.make(scrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            //emailInputValue.setText(null);
            nameInputValue.setText(null);

            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);
        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(scrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            userImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }*/

    }
}
