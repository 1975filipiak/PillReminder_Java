package com.pum2018.pillreminder_java;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pum2018.pillreminder_java.Data.MedicineContract;

public class CheckStoreActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int Medicine_Loader = 0;

    MedicineCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_store);


        ListView MedicineListView = (ListView) findViewById(R.id.list_view_medicine);
        //View emptyView = findViewById(R.id.empty_view);
        //MedicineListView.edicineListView.setEmptyView(emptyView);

        MedicineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CheckStoreActivity.this,AddMedicine.class);
                Uri currentMedicineUri = ContentUris.withAppendedId(MedicineContract.Medicine.CONTENT_URI,id);
                intent.setData(currentMedicineUri);
                startActivity(intent);
            }
        });


        mCursorAdapter = new MedicineCursorAdapter(this,null);
        MedicineListView.setAdapter(mCursorAdapter);

        getLoaderManager().initLoader(Medicine_Loader,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                MedicineContract.Medicine._ID,
                MedicineContract.Medicine.COLUMN_MEDICINE_NAME,
                MedicineContract.Medicine.COLUMN_MEDICINE_TYPE,
                MedicineContract.Medicine.COLUMN_MEDICINE_QUANTITY
        };

        return new CursorLoader(this,
                MedicineContract.Medicine.CONTENT_URI,   // The content URI of the words table
                projection,             // The columns to return for each row
                null,                   // Selection criteria
                null,                   // Selection criteria
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
