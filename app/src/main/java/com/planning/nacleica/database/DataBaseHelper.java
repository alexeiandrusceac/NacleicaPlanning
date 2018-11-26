package com.planning.nacleica.database;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.planning.nacleica.Tasks;
import com.planning.nacleica.UserActions.Worker;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * Created by alexei.andrusceac on 19.03.2018.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "NacleicaPlanning.db";
    /*Crearea tabelului cu Taskuri*/
    private static String TASK_TABLE = "TaskTable";
    private static String TASK_ID = "idTask";
    private static String TASK_WORKER_ID = "idWorker";
    private static String TASK_NAME = "TaskName";
    private static String TASK_DESCRIPTION = "TaskDescription";
    private static String TASK_PERIOD_FROM = "TaskPeriodFrom";
    private static String TASK_PERIOD_TO = "TaskPeriodTo";
    private static String TASK_STATE = "TaskState";
    /*Crearea tabelului cu Taskuri*/

    /*Crearea tabelului cu Utilizatori*/
    private static String WORKER_TABLE = "Worker";
    private static String WORKER_ID = "idWorker";
    private static String WORKER_NAME = "FirstName";
    private static String WORKER_PRENAME = "LastName";
    private static String WORKER_PASSWORD = "Password";
    private static String WORKER_TITLE = "Title";
    /*Crearea tabelului cu Utilizatori*/
    private static DataBaseHelper databaseHelper;
    private static final int SCHEMA = 1;

    public static synchronized DataBaseHelper getInstance(Context context) {
        if (databaseHelper == null) {
            databaseHelper = new DataBaseHelper(context.getApplicationContext());
        }
        return databaseHelper;
    }

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }


    @SuppressLint("NewApi")
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private String CREATE_TASK_TABLE = "CREATE TABLE " + TASK_TABLE + "(" + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK_WORKER_ID + " INTEGER, " +
            TASK_NAME + " TEXT," + TASK_DESCRIPTION + " TEXT," + TASK_STATE + " INTEGER,  " + TASK_PERIOD_FROM + " TEXT, " + TASK_PERIOD_TO + " TEXT" + ")";

    private String CREATE_USER_TABLE = "CREATE TABLE " + WORKER_TABLE + "(" + WORKER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            WORKER_NAME + " TEXT," + WORKER_PRENAME + " TEXT, " + WORKER_PASSWORD + " TEXT, " + WORKER_TITLE + " TEXT" + ")";

    private String DROP_TASK_TABLE = "DROP TABLE IF EXISTS " + TASK_TABLE;
    private String DROP_WORKER_TABLE = "DROP TABLE IF EXISTS " + WORKER_TABLE;

    public boolean checkUserOnLogin(String name, String password) {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(WORKER_TABLE,
                new String[]{WORKER_ID}, WORKER_NAME + " = ? " + " AND " + WORKER_PASSWORD + " = ? ", new String[]{name, password}, null, null, WORKER_ID);

        if (cursor.getCount() > 0) {
            return true;
        }
        cursor.close();
        return false;
    }

    public boolean checkUserOnLogin(String name) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(WORKER_TABLE,
                new String[]{WORKER_ID}, WORKER_NAME + " = ?", new String[]{name},
                null, null, WORKER_ID);
        int count = cursor.getCount();
        cursor.close();

        if (count > 0) {
            return true;
        }
        return false;
    }

    public void registerNewWorker(Worker worker) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues workerValues = new ContentValues();
        workerValues.put(WORKER_NAME, worker.Name);
        workerValues.put(WORKER_PRENAME, worker.Prename);
        // values.put(USER_EMAIL, user.Email);
        workerValues.put(WORKER_PASSWORD, worker.Password);
        workerValues.put(WORKER_TITLE, worker.Title);
        long id = sqLiteDatabase.insert(WORKER_TABLE, null, workerValues);

        Log.d(TAG, String.format("Angajatul  cu numele " + WORKER_NAME + " " + WORKER_PRENAME + " s-a inregistrat cu succes"));
    }

    public Worker getWorker(String workerName, String password) {
        Worker worker = new Worker();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor curWorker = sqLiteDatabase.rawQuery("SELECT * from " + WORKER_TABLE + " WHERE " +
                WORKER_NAME + " =? " + " AND " + WORKER_PASSWORD + " = ? ", new String[]{workerName, password});
        if (curWorker.moveToFirst()) {
            do {

                worker.workerID = curWorker.getInt(curWorker.getColumnIndex(WORKER_ID));
                worker.Name = curWorker.getString(curWorker.getColumnIndex(WORKER_NAME));
                worker.Prename = curWorker.getString(curWorker.getColumnIndex(WORKER_PRENAME));
                //user.Email = curWorker.getString(curWorker.getColumnIndex(USER_EMAIL));
                worker.Password = curWorker.getString(curWorker.getColumnIndex(WORKER_PASSWORD));
                worker.Title = curWorker.getString(curWorker.getColumnIndex(WORKER_TITLE));

            } while (curWorker.moveToNext());

        }
        curWorker.close();
        return worker;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TASK_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL(DROP_TASK_TABLE);
            db.execSQL(DROP_WORKER_TABLE);
        }
    }

    public List<Tasks> getTask() {
        SQLiteDatabase db = getReadableDatabase();
        List<Tasks> listOfNewTask = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TASK_TABLE ,null/*" where " + TASK_STATE + " =?", new String[]{String.valueOf(newTask)}*/);
        if (cursor.moveToFirst()) {
            do {
                Tasks newdata = new Tasks();
                newdata.idTask = cursor.getInt(cursor.getColumnIndex(TASK_ID));
                newdata.idWorker = cursor.getInt(cursor.getColumnIndex(TASK_WORKER_ID));
                newdata.TaskDescription = cursor.getString(cursor.getColumnIndex(TASK_DESCRIPTION));
                newdata.TaskName = cursor.getString(cursor.getColumnIndex(TASK_NAME));
                newdata.TaskPeriodFrom = cursor.getString(cursor.getColumnIndex(TASK_PERIOD_FROM));
                newdata.TaskPeriodTo = cursor.getString(cursor.getColumnIndex(TASK_PERIOD_TO));
                newdata.TaskState = cursor.getString(cursor.getColumnIndex(TASK_STATE));
                listOfNewTask.add(newdata);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return listOfNewTask;
    }
}
