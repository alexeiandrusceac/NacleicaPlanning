package com.planning.nacleica.Database;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.planning.nacleica.Tasks;
import com.planning.nacleica.AuthActions.Worker;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by alexei.andrusceac on 19.03.2018.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    public static String TAG = "nacleica.md";
    public static String DATABASE_NAME = "NacleicaPlanning.db";
    /*Crearea tabelului cu Taskuri*/
    private static String TASK_TABLE = "TaskTable";
    private static String TASK_ID = "idTask";
    private static String TASK_WORKER_ID = "idWorker";
    private static String TASK_NAME = "TaskName";
    private static String TASK_COM_PHONE = "TaskPhone";
    private static String TASK_COM_NAME = "TaskComName";
    private static String TASK_PERIOD_FROM = "TaskPeriodFrom";
    private static String TASK_PERIOD_TO = "TaskPeriodTo";
    private static String TASK_STATE = "TaskState";
    private static String TASK_IMAGE_BEFORE = "TaskImageBefore";
    private static String TASK_IMAGE_AFTER = "TaskImageAfter";

    /*Crearea tabelului cu Taskuri*/

    /*Crearea tabelului cu Utilizatori*/
    private static String WORKER_TABLE = "Worker";
    private static String WORKER_ID = "idWorker";
    private static String WORKER_NAME = "FirstName";
    private static String WORKER_PRENAME = "LastName";
    private static String WORKER_PASSWORD = "Password";
    private static String WORKER_TITLE = "Title";
    private static String WORKER_IMAGE = "Image";
    private static String WORKER_BIRTHDAY = "Birthday";
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
            TASK_NAME + " TEXT," + TASK_COM_NAME + " TEXT," + TASK_COM_PHONE + " TEXT," + TASK_STATE + " INTEGER,  " + TASK_PERIOD_FROM + " TEXT, " + TASK_PERIOD_TO + " TEXT," + TASK_IMAGE_BEFORE + " BLOB, " + TASK_IMAGE_AFTER + " BLOB" + ")";

    private String CREATE_WORKER_TABLE = "CREATE TABLE " + WORKER_TABLE + "(" + WORKER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            WORKER_NAME + " TEXT," + WORKER_PRENAME + " TEXT, " + WORKER_PASSWORD + " TEXT, " + WORKER_TITLE + " INT," + WORKER_IMAGE + " BLOB," + WORKER_BIRTHDAY + " TEXT" + ")";

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

    public void updateData(Context context, Tasks tasks) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_NAME, tasks.TaskName);
        contentValues.put(TASK_IMAGE_BEFORE, tasks.TaskImageBefore);
        contentValues.put(TASK_IMAGE_AFTER, tasks.TaskImageAfter);
        contentValues.put(TASK_STATE, tasks.TaskState);
        contentValues.put(TASK_COM_NAME, tasks.TaskCompany);
        contentValues.put(TASK_COM_PHONE, tasks.TaskCompanyPhone);
        contentValues.put(TASK_PERIOD_FROM, tasks.TaskPeriodFrom);
        contentValues.put(TASK_PERIOD_TO, tasks.TaskPeriodTo);
        sqLiteDatabase.update(TASK_TABLE, contentValues, TASK_ID + "=?", new String[]{String.valueOf(tasks.idTask)});
        if (sqLiteDatabase != null) {
            Toast.makeText(context, "Informatia sa actualizat cu succes", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Nu sa actualizat informatia", Toast.LENGTH_SHORT).show();

        }
    }

    public void updateData(Context context, Worker worker) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WORKER_NAME, worker.Name);
        contentValues.put(WORKER_PRENAME, worker.Prename);
        contentValues.put(WORKER_BIRTHDAY, worker.Birthday);
        contentValues.put(WORKER_IMAGE, worker.Image);
        contentValues.put(WORKER_PASSWORD, worker.Password);
        contentValues.put(WORKER_TITLE, worker.Title);
        sqLiteDatabase.update(WORKER_TABLE, contentValues, WORKER_ID + "= ?", new String[]{String.valueOf(worker.workerID)});
        if (sqLiteDatabase != null) {
            Toast.makeText(context, "Informatia sa actualizat cu succes", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Nu sa actualizat informatia", Toast.LENGTH_SHORT).show();

        }
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

    public void registerNewWorker(Context context, Worker worker) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues workerValues = new ContentValues();
        workerValues.put(WORKER_NAME, worker.Name);
        workerValues.put(WORKER_PRENAME, worker.Prename);
        workerValues.put(WORKER_IMAGE, worker.Image);
        workerValues.put(WORKER_BIRTHDAY, worker.Birthday);
        workerValues.put(WORKER_PASSWORD, worker.Password);
        workerValues.put(WORKER_TITLE, worker.Title);
        long id = sqLiteDatabase.insert(WORKER_TABLE, null, workerValues);
        if (sqLiteDatabase != null) {
            Toast.makeText(context, String.format("Angajatul  cu numele " + worker.Name + " " + worker.Prename + " s-a inregistrat cu succes"), Toast.LENGTH_SHORT).show();
            Log.d(TAG, String.format("Angajatul  cu numele " + worker.Name + " " + worker.Prename + " s-a inregistrat cu succes"));
        } else {
            Toast.makeText(context, String.format("Angajatul  cu numele " + worker.Name + " " + worker.Prename + "nu s-a inregistrat"), Toast.LENGTH_SHORT).show();
            Log.d(TAG, String.format("Angajatul  cu numele " + worker.Name + " " + worker.Prename + "nu s-a inregistrat"));
        }
    }

    public void createNewTask(Context context, Tasks newTask) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_NAME, newTask.TaskName);
        contentValues.put(TASK_COM_NAME, newTask.TaskCompany);
        contentValues.put(TASK_COM_PHONE, newTask.TaskCompanyPhone);
        contentValues.put(TASK_STATE, newTask.TaskState);
        contentValues.put(TASK_PERIOD_FROM, newTask.TaskPeriodFrom);
        contentValues.put(TASK_PERIOD_TO, newTask.TaskPeriodTo);
        contentValues.put(TASK_IMAGE_AFTER, newTask.TaskImageAfter);
        contentValues.put(TASK_IMAGE_BEFORE, newTask.TaskImageBefore);
        long id = sqLiteDatabase.insert(TASK_TABLE, null, contentValues);
        if (sqLiteDatabase != null) {
            Toast.makeText(context, String.format("Sarcina  cu numele " + newTask.TaskName + " s-a creat cu succes"), Toast.LENGTH_SHORT).show();
            Log.d(TAG, String.format("Sarcina  cu numele " + newTask.TaskName + " s-a creat cu succes"));
        } else {
            Toast.makeText(context, String.format("Sarcina  cu numele " + newTask.TaskName + " nu s-a creat"), Toast.LENGTH_SHORT).show();
            Log.d(TAG, String.format("Sarcina  cu numele " + newTask.TaskName + " nu s-a creat"));
        }
    }
    public List<Worker> getWorkers(int title)
    {
        List<Worker> listDesigner  = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor curWorker = sqLiteDatabase.rawQuery("SELECT * from " + WORKER_TABLE + " Where "+ WORKER_TITLE + "=?",new String[]{String.valueOf(title)} );
        if (curWorker.moveToFirst()) {
            do {
                Worker worker = new Worker();
                worker.workerID = curWorker.getInt(curWorker.getColumnIndex(WORKER_ID));
                worker.Name = curWorker.getString(curWorker.getColumnIndex(WORKER_NAME));
                worker.Prename = curWorker.getString(curWorker.getColumnIndex(WORKER_PRENAME));
                worker.Password = curWorker.getString(curWorker.getColumnIndex(WORKER_PASSWORD));
                worker.Title = curWorker.getInt(curWorker.getColumnIndex(WORKER_TITLE));
                worker.Image = curWorker.getBlob(curWorker.getColumnIndex(WORKER_IMAGE));
                worker.Birthday = curWorker.getString(curWorker.getColumnIndex(WORKER_BIRTHDAY));
                listDesigner.add(worker);
            } while (curWorker.moveToNext());

        }
        curWorker.close();
        return listDesigner;
    }
    public List<Worker> getWorkers() {

        List<Worker> listOfWorkers = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor curWorker = sqLiteDatabase.rawQuery("SELECT * from " + WORKER_TABLE, null);
        if (curWorker.moveToFirst()) {
            do {
                Worker worker = new Worker();
                worker.workerID = curWorker.getInt(curWorker.getColumnIndex(WORKER_ID));
                worker.Name = curWorker.getString(curWorker.getColumnIndex(WORKER_NAME));
                worker.Prename = curWorker.getString(curWorker.getColumnIndex(WORKER_PRENAME));
                worker.Password = curWorker.getString(curWorker.getColumnIndex(WORKER_PASSWORD));
                worker.Title = curWorker.getInt(curWorker.getColumnIndex(WORKER_TITLE));
                worker.Image = curWorker.getBlob(curWorker.getColumnIndex(WORKER_IMAGE));
                worker.Birthday = curWorker.getString(curWorker.getColumnIndex(WORKER_BIRTHDAY));
                listOfWorkers.add(worker);
            } while (curWorker.moveToNext());

        }
        curWorker.close();
        return listOfWorkers;

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
                worker.Password = curWorker.getString(curWorker.getColumnIndex(WORKER_PASSWORD));
                worker.Title = curWorker.getInt(curWorker.getColumnIndex(WORKER_TITLE));
                worker.Image = curWorker.getBlob(curWorker.getColumnIndex(WORKER_IMAGE));
                worker.Birthday = curWorker.getString(curWorker.getColumnIndex(WORKER_BIRTHDAY));

            } while (curWorker.moveToNext());

        }
        curWorker.close();
        return worker;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TASK_TABLE);
        db.execSQL(CREATE_WORKER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL(DROP_TASK_TABLE);
            db.execSQL(DROP_WORKER_TABLE);
        }
    }

    public List<Tasks> getAdminNewTask() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Tasks> listOfAdminNewTask = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TASK_TABLE + " where " + TASK_STATE + " =? ", new String[]{String.valueOf(0)});
        if (cursor.moveToFirst()) {
            do {
                Tasks newdata = new Tasks();
                newdata.idTask = cursor.getInt(cursor.getColumnIndex(TASK_ID));
                newdata.idWorker = cursor.getInt(cursor.getColumnIndex(TASK_WORKER_ID));
                newdata.TaskCompany = cursor.getString(cursor.getColumnIndex(TASK_COM_NAME));
                newdata.TaskCompanyPhone = cursor.getString(cursor.getColumnIndex(TASK_COM_PHONE));
                newdata.TaskName = cursor.getString(cursor.getColumnIndex(TASK_NAME));
                newdata.TaskPeriodFrom = cursor.getString(cursor.getColumnIndex(TASK_PERIOD_FROM));
                newdata.TaskPeriodTo = cursor.getString(cursor.getColumnIndex(TASK_PERIOD_TO));
                newdata.TaskState = cursor.getInt(cursor.getColumnIndex(TASK_STATE));
                newdata.TaskImageBefore = cursor.getBlob(cursor.getColumnIndex(TASK_IMAGE_BEFORE));
                newdata.TaskImageAfter = cursor.getBlob(cursor.getColumnIndex(TASK_IMAGE_AFTER));

                listOfAdminNewTask.add(newdata);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return listOfAdminNewTask;

    }

    public List<Tasks> getAdminMaketTask() {
        SQLiteDatabase db = getReadableDatabase();
        List<Tasks> listOfAdminMaketTask = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TASK_TABLE + " where " + TASK_STATE + " =?", new String[]{String.valueOf(1)});
        if (cursor.moveToFirst()) {
            do {
                Tasks newMaketTask = new Tasks();
                newMaketTask.idTask = cursor.getInt(cursor.getColumnIndex(TASK_ID));
                newMaketTask.idWorker = cursor.getInt(cursor.getColumnIndex(TASK_WORKER_ID));
                newMaketTask.TaskCompany = cursor.getString(cursor.getColumnIndex(TASK_COM_NAME));
                newMaketTask.TaskCompanyPhone = cursor.getString(cursor.getColumnIndex(TASK_COM_PHONE));
                newMaketTask.TaskName = cursor.getString(cursor.getColumnIndex(TASK_NAME));
                newMaketTask.TaskPeriodFrom = cursor.getString(cursor.getColumnIndex(TASK_PERIOD_FROM));
                newMaketTask.TaskPeriodTo = cursor.getString(cursor.getColumnIndex(TASK_PERIOD_TO));
                newMaketTask.TaskState = cursor.getInt(cursor.getColumnIndex(TASK_STATE));
                newMaketTask.TaskImageBefore = cursor.getBlob(cursor.getColumnIndex(TASK_IMAGE_BEFORE));
                newMaketTask.TaskImageAfter = cursor.getBlob(cursor.getColumnIndex(TASK_IMAGE_AFTER));

                listOfAdminMaketTask.add(newMaketTask);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return listOfAdminMaketTask;
    }

    public List<Tasks> getWorkerNewTask(int idWorker) {
        SQLiteDatabase db = getReadableDatabase();
        List<Tasks> listOfNewTask = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TASK_TABLE + " where " + TASK_STATE + " =? " + " AND " + TASK_WORKER_ID + "=?", new String[]{String.valueOf(2), String.valueOf(idWorker)});
        if (cursor.moveToFirst()) {
            do {
                Tasks newdata = new Tasks();
                newdata.idTask = cursor.getInt(cursor.getColumnIndex(TASK_ID));
                newdata.idWorker = cursor.getInt(cursor.getColumnIndex(TASK_WORKER_ID));
                newdata.TaskCompany = cursor.getString(cursor.getColumnIndex(TASK_COM_NAME));
                newdata.TaskCompanyPhone = cursor.getString(cursor.getColumnIndex(TASK_COM_PHONE));
                newdata.TaskName = cursor.getString(cursor.getColumnIndex(TASK_NAME));
                newdata.TaskPeriodFrom = cursor.getString(cursor.getColumnIndex(TASK_PERIOD_FROM));
                newdata.TaskPeriodTo = cursor.getString(cursor.getColumnIndex(TASK_PERIOD_TO));
                newdata.TaskState = cursor.getInt(cursor.getColumnIndex(TASK_STATE));
                newdata.TaskImageBefore = cursor.getBlob(cursor.getColumnIndex(TASK_IMAGE_BEFORE));
                newdata.TaskImageAfter = cursor.getBlob(cursor.getColumnIndex(TASK_IMAGE_AFTER));

                listOfNewTask.add(newdata);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return listOfNewTask;
    }
    public List<Tasks> getWorkersDoneTasks()
    {
        SQLiteDatabase db = getReadableDatabase();
        List<Tasks> listOfWorkerDoneTask = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TASK_TABLE + " where " + TASK_STATE + " =?", new String[]{String.valueOf(4)});
        if (cursor.moveToFirst()) {
            do {
                Tasks newdata = new Tasks();
                newdata.idTask = cursor.getInt(cursor.getColumnIndex(TASK_ID));
                newdata.idWorker = cursor.getInt(cursor.getColumnIndex(TASK_WORKER_ID));
                newdata.TaskCompany = cursor.getString(cursor.getColumnIndex(TASK_COM_NAME));
                newdata.TaskCompanyPhone = cursor.getString(cursor.getColumnIndex(TASK_COM_PHONE));
                newdata.TaskName = cursor.getString(cursor.getColumnIndex(TASK_NAME));
                newdata.TaskPeriodFrom = cursor.getString(cursor.getColumnIndex(TASK_PERIOD_FROM));
                newdata.TaskPeriodTo = cursor.getString(cursor.getColumnIndex(TASK_PERIOD_TO));
                newdata.TaskState = cursor.getInt(cursor.getColumnIndex(TASK_STATE));
                newdata.TaskImageBefore = cursor.getBlob(cursor.getColumnIndex(TASK_IMAGE_BEFORE));
                newdata.TaskImageAfter = cursor.getBlob(cursor.getColumnIndex(TASK_IMAGE_AFTER));

                listOfWorkerDoneTask.add(newdata);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return listOfWorkerDoneTask;
    }
    public List<Tasks> getWorkersInProgTasks() {
        SQLiteDatabase db = getReadableDatabase();
        List<Tasks> listOfWorkerProgTask = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TASK_TABLE + " where " + TASK_STATE + " =?", new String[]{String.valueOf(3)});
        if (cursor.moveToFirst()) {
            do {
                Tasks newdata = new Tasks();
                newdata.idTask = cursor.getInt(cursor.getColumnIndex(TASK_ID));
                newdata.idWorker = cursor.getInt(cursor.getColumnIndex(TASK_WORKER_ID));
                newdata.TaskCompany = cursor.getString(cursor.getColumnIndex(TASK_COM_NAME));
                newdata.TaskCompanyPhone = cursor.getString(cursor.getColumnIndex(TASK_COM_PHONE));
                newdata.TaskName = cursor.getString(cursor.getColumnIndex(TASK_NAME));
                newdata.TaskPeriodFrom = cursor.getString(cursor.getColumnIndex(TASK_PERIOD_FROM));
                newdata.TaskPeriodTo = cursor.getString(cursor.getColumnIndex(TASK_PERIOD_TO));
                newdata.TaskState = cursor.getInt(cursor.getColumnIndex(TASK_STATE));
                newdata.TaskImageBefore = cursor.getBlob(cursor.getColumnIndex(TASK_IMAGE_BEFORE));
                newdata.TaskImageAfter = cursor.getBlob(cursor.getColumnIndex(TASK_IMAGE_AFTER));

                listOfWorkerProgTask.add(newdata);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return listOfWorkerProgTask;
    }

    public List<Tasks> getWorkersNewTasks() {
        SQLiteDatabase db = getReadableDatabase();
        List<Tasks> listOfWorkerNewTask = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TASK_TABLE + " where " + TASK_STATE + " =?", new String[]{String.valueOf(2)});
        if (cursor.moveToFirst()) {
            do {
                Tasks newdata = new Tasks();
                newdata.idTask = cursor.getInt(cursor.getColumnIndex(TASK_ID));
                newdata.idWorker = cursor.getInt(cursor.getColumnIndex(TASK_WORKER_ID));
                newdata.TaskCompany = cursor.getString(cursor.getColumnIndex(TASK_COM_NAME));
                newdata.TaskCompanyPhone = cursor.getString(cursor.getColumnIndex(TASK_COM_PHONE));
                newdata.TaskName = cursor.getString(cursor.getColumnIndex(TASK_NAME));
                newdata.TaskPeriodFrom = cursor.getString(cursor.getColumnIndex(TASK_PERIOD_FROM));
                newdata.TaskPeriodTo = cursor.getString(cursor.getColumnIndex(TASK_PERIOD_TO));
                newdata.TaskState = cursor.getInt(cursor.getColumnIndex(TASK_STATE));
                newdata.TaskImageBefore = cursor.getBlob(cursor.getColumnIndex(TASK_IMAGE_BEFORE));
                newdata.TaskImageAfter = cursor.getBlob(cursor.getColumnIndex(TASK_IMAGE_AFTER));

                listOfWorkerNewTask.add(newdata);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return listOfWorkerNewTask;
    }

    public List<Tasks> getWorkerInProgressTask(int idWorker) {
        SQLiteDatabase db = getReadableDatabase();
        List<Tasks> listOfInProgressTask = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TASK_TABLE + " where " + TASK_STATE + " =?" + " AND " + TASK_WORKER_ID + "=?", new String[]{String.valueOf(3), String.valueOf(idWorker)});
        if (cursor.moveToFirst()) {
            do {
                Tasks newdata = new Tasks();
                newdata.idTask = cursor.getInt(cursor.getColumnIndex(TASK_ID));
                newdata.idWorker = cursor.getInt(cursor.getColumnIndex(TASK_WORKER_ID));
                newdata.TaskCompany = cursor.getString(cursor.getColumnIndex(TASK_COM_NAME));
                newdata.TaskCompanyPhone = cursor.getString(cursor.getColumnIndex(TASK_COM_PHONE));
                newdata.TaskName = cursor.getString(cursor.getColumnIndex(TASK_NAME));
                newdata.TaskPeriodFrom = cursor.getString(cursor.getColumnIndex(TASK_PERIOD_FROM));
                newdata.TaskPeriodTo = cursor.getString(cursor.getColumnIndex(TASK_PERIOD_TO));
                newdata.TaskState = cursor.getInt(cursor.getColumnIndex(TASK_STATE));
                newdata.TaskImageBefore = cursor.getBlob(cursor.getColumnIndex(TASK_IMAGE_BEFORE));
                newdata.TaskImageAfter = cursor.getBlob(cursor.getColumnIndex(TASK_IMAGE_AFTER));

                listOfInProgressTask.add(newdata);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return listOfInProgressTask;
    }

    public List<Tasks> getWorkerDoneTask(int idWorker) {
        SQLiteDatabase db = getReadableDatabase();
        List<Tasks> listOfDoneTask = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TASK_TABLE + " where " + TASK_STATE + " =?" + " AND " + TASK_WORKER_ID + "=?", new String[]{String.valueOf(4), String.valueOf(idWorker)});
        if (cursor.moveToFirst()) {
            do {
                Tasks newdata = new Tasks();
                newdata.idTask = cursor.getInt(cursor.getColumnIndex(TASK_ID));
                newdata.idWorker = cursor.getInt(cursor.getColumnIndex(TASK_WORKER_ID));
                newdata.TaskCompany = cursor.getString(cursor.getColumnIndex(TASK_COM_NAME));
                newdata.TaskCompanyPhone = cursor.getString(cursor.getColumnIndex(TASK_COM_PHONE));
                newdata.TaskName = cursor.getString(cursor.getColumnIndex(TASK_NAME));
                newdata.TaskPeriodFrom = cursor.getString(cursor.getColumnIndex(TASK_PERIOD_FROM));
                newdata.TaskPeriodTo = cursor.getString(cursor.getColumnIndex(TASK_PERIOD_TO));
                newdata.TaskState = cursor.getInt(cursor.getColumnIndex(TASK_STATE));
                newdata.TaskImageBefore = cursor.getBlob(cursor.getColumnIndex(TASK_IMAGE_BEFORE));
                newdata.TaskImageAfter = cursor.getBlob(cursor.getColumnIndex(TASK_IMAGE_AFTER));

                listOfDoneTask.add(newdata);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return listOfDoneTask;
    }
}
