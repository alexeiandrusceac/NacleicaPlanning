package com.planning.nacleica;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;

import static android.app.Activity.RESULT_OK;
import static androidx.core.app.ActivityCompat.requestPermissions;
import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.checkSelfPermission;

public class Utils implements IUtils {
    public Context context;
    public DatePickerDialog datepicker;
    public static final int RESULT_GALLERY_IMAGE = 1;
    public static final int RESULT_CAMERA_IMAGE = 0;
    public Activity activity;

    public Utils(Context context) {
        this.context = context;
        this.activity = (Activity) context;
    }

    public byte[] convertToByteArray(AppCompatImageView imageView) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        bitmap.compress(Bitmap.CompressFormat.WEBP, 0, baos);
        return baos.toByteArray();
    }

    public void openImagePopupMenu(final AppCompatImageView userImageValue) {

        PopupMenu popupMenu = new PopupMenu(activity, userImageValue);
        popupMenu.getMenuInflater().inflate(R.menu.photo_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NewApi")
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.take_photo:
                        if (checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, RESULT_CAMERA_IMAGE);
                        } else {
                            startActivityForResult(activity, new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE), RESULT_CAMERA_IMAGE, null);
                        }

                        return true;
                    case R.id.choose_photo:
                        startActivityForResult(activity, new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), RESULT_GALLERY_IMAGE, null);
                        return true;
                    case R.id.delete_photo:
                        userImageValue.setImageDrawable(context.getDrawable(R.drawable.ic_profile2));
                        return true;
                }
                Toast.makeText(
                        context,
                        "Ati selectat: " + item.getTitle(),
                        Toast.LENGTH_SHORT
                ).show();
                return true;
            }
        });

        popupMenu.show();

    }

     @SuppressLint("NewApi")
     @RequiresApi(api = Build.VERSION_CODES.M)
     @Override
     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         activity.onRequestPermissionsResult(requestCode,permissions,grantResults);
         if (requestCode == RESULT_CAMERA_IMAGE) {

             if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                 Toast.makeText(activity, "camera permission granted", Toast.LENGTH_LONG).show();

                 startActivityForResult(activity, new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE), 0,null);
             } else {

                 Toast.makeText(activity, "camera permission denied", Toast.LENGTH_LONG).show();
             }

         }
     }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Bitmap getBitmap(Uri selectedImage) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(selectedImage));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            ExifInterface exifInterface = new ExifInterface(context.getContentResolver().openInputStream(selectedImage));
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

            int rotation = 0;
            if (orientation == 6) rotation = 90;
            else if (orientation == 3) rotation = 180;
            else if (orientation == 8) rotation = 270;

            // Rotate Image if Necessary
            if (rotation != 0) {
                // Create Matrix
                Matrix matrix = new Matrix();
                matrix.postRotate(rotation);

                // Rotate Bitmap
                Bitmap rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                bitmap.recycle();
                bitmap = rotated;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public TextInputEditText dateToEditText(final TextInputEditText dateinput) {
        final Calendar cldr = Calendar.getInstance();
        //TextInputEditText date = dateinput ;
        if (dateinput != null) {
            dateinput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);

                    datepicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                            dateinput.setText((((date < 10) ? "0" : "") + date) + " / " + (((month < 10) ? "0" : "") + (month + 1)) + " / " + year);
                        }
                    }, year, month, day);
                    datepicker.show();
                }
            });
        }
        return dateinput;
    }
    public static Activity getActivity(){

        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);

            Map<Object, Object> activities = (Map<Object, Object>) activitiesField.get(activityThread);
            if (activities == null)
                return null;

            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Activity activity = (Activity) activityField.get(activityRecord);
                    return activity;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

}
