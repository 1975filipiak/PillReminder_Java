package com.pum2018.pillreminder_java.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MedicineDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "medicine.db";

    public MedicineDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + MedicineContract.Medicine.TABLE_NAME  + " ("
                + MedicineContract.Medicine._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MedicineContract.Medicine.COLUMN_MEDICINE_NAME + " TEXT NOT NULL, "
                + MedicineContract.Medicine.COLUMN_MEDICINE_TYPE + " INTEGER NOT NULL, "
                + MedicineContract.Medicine.COLUMN_MEDICINE_QUANTITY + " INTEGER NOT NULL DEFAULT 0);";
        db.execSQL(SQL_CREATE_ENTRIES);

        String SQL_CREATE_ENTRIES_REPORT_TAKINGS = "CREATE TABLE " + MedicineContract.ReportTaking.TABLE_NAME  + " ("
                + MedicineContract.ReportTaking._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MedicineContract.ReportTaking.COLUMN_RTT_KEY_DATE + " TEXT NOT NULL, "
                + MedicineContract.ReportTaking.COLUMN_RTT_KEY_MEDICINE_NAME + " TEXT NOT NULL, "
                + MedicineContract.ReportTaking.COLUMN_RTT_KEY_PLANNED_TIME + " TEXT NOT NULL, "
                + MedicineContract.ReportTaking.COLUMN_RTT_KEY_TAKING_TIME + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_ENTRIES_REPORT_TAKINGS);

        String SQL_CREATE_ENTRIES_PLAN_TAKINGS = "CREATE TABLE " + MedicineContract.PlanTaking.TABLE_NAME + " ("
                + MedicineContract.PlanTaking._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MedicineContract.PlanTaking.TPT_KEY_HOUR + " INTEGER, "
                + MedicineContract.PlanTaking.TPT_KEY_MINUTE + " INTEGER, "
                + MedicineContract.PlanTaking.TPT_KEY_MEDICINE_ID + " INTEGER NOT NULL, "
                + MedicineContract.PlanTaking.TPT_KEY_DOSE + " INTEGER, "
                + MedicineContract.PlanTaking.TPT_KEY_DAY_SUN + " INTEGER DEFAULT 0, "
                + MedicineContract.PlanTaking.TPT_KEY_DAY_MON + " INTEGER DEFAULT 0, "
                + MedicineContract.PlanTaking.TPT_KEY_DAY_TUE + " INTEGER DEFAULT 0, "
                + MedicineContract.PlanTaking.TPT_KEY_DAY_WED + " INTEGER DEFAULT 0, "
                + MedicineContract.PlanTaking.TPT_KEY_DAY_THU + " INTEGER DEFAULT 0, "
                + MedicineContract.PlanTaking.TPT_KEY_DAY_FRI + " INTEGER DEFAULT 0, "
                + MedicineContract.PlanTaking.TPT_KEY_DAY_SAT + " INTEGER DEFAULT 0, "
                + "FOREIGN KEY (" + MedicineContract.PlanTaking.TPT_KEY_MEDICINE_ID + ") REFERENCES " + MedicineContract.Medicine.TABLE_NAME + " (" + MedicineContract.Medicine._ID + "));";



        db.execSQL(SQL_CREATE_ENTRIES_PLAN_TAKINGS);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
