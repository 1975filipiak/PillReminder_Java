package com.pum2018.pillreminder_java.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class MedicineProvider extends ContentProvider{

    private static final int MEDICINE = 100;
    private static final int MEDICINE_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(MedicineContract.CONTENT_AUTHORITY,MedicineContract.PATH_MEDICINE,MEDICINE);
        sUriMatcher.addURI(MedicineContract.CONTENT_AUTHORITY,MedicineContract.PATH_MEDICINE + "/#",MEDICINE_ID);
    }

    MedicineDBHelper dbHelper;
    public static final String LOG_TAG = MedicineContract.class.getSimpleName();

    @Override
    public boolean onCreate() {
        dbHelper = new MedicineDBHelper(getContext());
        return true;
    }

    @Nullable
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case MEDICINE:
                cursor = database.query(MedicineContract.Medicine.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case MEDICINE_ID:
                selection = MedicineContract.Medicine._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = database.query(MedicineContract.Medicine.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MEDICINE:
                return insertMedicine(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertMedicine(Uri uri, ContentValues values) {
        // Check that the name is not null
        String name = values.getAsString(MedicineContract.Medicine.COLUMN_MEDICINE_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Medicine requires a name");
        }

        // Check that the type is valid
        Integer type = values.getAsInteger(MedicineContract.Medicine.COLUMN_MEDICINE_TYPE);
        if (type == null || !MedicineContract.Medicine.isValidType(type)) {
            throw new IllegalArgumentException("Medicine requires valid type");
        }

        // If the quantity is provided, check that it's greater than or equal to 0 kg
        Integer quantity = values.getAsInteger(MedicineContract.Medicine.COLUMN_MEDICINE_QUANTITY);
        if (quantity != null && quantity < 0) {
            throw new IllegalArgumentException("Medicine requires valid quantity");
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        long id = database.insert(MedicineContract.Medicine.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MEDICINE:
                return updateMedicine(uri, contentValues, selection, selectionArgs);
            case MEDICINE_ID:
                selection = MedicineContract.Medicine._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateMedicine(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Update medicines in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more medicine).
     * Return the number of rows that were successfully updated.
     */
    private int updateMedicine(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(MedicineContract.Medicine.COLUMN_MEDICINE_NAME)) {
            String name = values.getAsString(MedicineContract.Medicine.COLUMN_MEDICINE_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Medicine requires a name");
            }
        }

        if (values.containsKey(MedicineContract.Medicine.COLUMN_MEDICINE_TYPE)) {
            Integer typeMedicine = values.getAsInteger(MedicineContract.Medicine.COLUMN_MEDICINE_TYPE);
            if (typeMedicine == null || !MedicineContract.Medicine.isValidType(typeMedicine)) {
                throw new IllegalArgumentException("Medicine requires valid gender");
            }
        }

        if (values.containsKey(MedicineContract.Medicine.COLUMN_MEDICINE_QUANTITY)) {
            Integer weight = values.getAsInteger(MedicineContract.Medicine.COLUMN_MEDICINE_QUANTITY);
            if (weight != null && weight < 0) {
                throw new IllegalArgumentException("Medicine requires valid weight");
            }
        }

        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // Returns the number of database rows affected by the update statement
        int rowsUpdated = database.update(MedicineContract.Medicine.TABLE_NAME, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}
