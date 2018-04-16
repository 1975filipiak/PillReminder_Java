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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
