package com.planning.nacleica.authactions;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class WorkerSession {

    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared preferences mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    public static final String PREFER_NAME = "Reg";

    // All Shared Preferences Keys
    public static final String IS_WORKER_LOGIN = "IsWorkerLoggedIn";

    // Worker name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    //public static final String KEY_EMAIL = "Email";

    // password
    public static final String KEY_PASSWORD = "password";

    // Constructor
    public WorkerSession(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createWorkerLoginSession(String wName, String wPassword){
        // Storing login value as TRUE
        editor.putBoolean(IS_WORKER_LOGIN, true);

        // Storing name in preferences
        editor.putString(KEY_NAME, wName);
        // Storing email in preferences

        // Storing email in preferences
        editor.putString(KEY_PASSWORD,  wPassword);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method will check worker login status
     * If false it will redirect worker to login page
     * Else do anything
     * */
    public boolean checkLogin(){
        // Check login status
        if(!this.isWorkerLoggedIn()){

            // worker is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, loginActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        return false;
    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getWorkerDetails(){

        //Use hashmap to store worker credentials
        HashMap<String, String> worker = new HashMap<String, String>();

        // worker name
        worker.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // worker email id
        worker.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));


        return worker;
    }

    /**
     * Clear session details
     * */
    public void logoutWorker(){

        // Clearing all worker data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect worker to mainActivity
        Intent i = new Intent(_context, loginActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }


    // Check for login
    public boolean isWorkerLoggedIn(){
        return pref.getBoolean(IS_WORKER_LOGIN, false);
    }
}
