package com.pum2018.pillreminder_java;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.pum2018.pillreminder_java.Data.MedicineContract;

public class AddMedicine extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{


    public static final int EXISTING_MEDICINE_LOADER = 0;

    Uri mCurrentMedicineUri;

    RadioGroup rg1, rg2;
    EditText editQuantity, editMedicine;
    int chkId1 = 0, chkId2 = 0;
    int realCheck;

    private boolean mMedicineHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mMedicineHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Intent intent = getIntent();
        mCurrentMedicineUri = intent.getData();

        if (mCurrentMedicineUri == null){
            ImageView removeButton = findViewById(R.id.removeFromStore);
            removeButton.setVisibility(View.INVISIBLE);
            ImageView addButton = findViewById(R.id.addToStore);
            addButton.setVisibility(View.VISIBLE);
        } else {
            getLoaderManager().initLoader(EXISTING_MEDICINE_LOADER, null, this);
            Toast.makeText(this,"Opened for modification/deletion",Toast.LENGTH_SHORT).show();
            ImageView removeButton = findViewById(R.id.removeFromStore);
            removeButton.setVisibility(View.VISIBLE);
            ImageView addButton = findViewById(R.id.addToStore);
            addButton.setVisibility(View.VISIBLE);

        }

        rg1 = (RadioGroup) findViewById(R.id.radio_group_left);
        rg2 = (RadioGroup) findViewById(R.id.radio_group_right);
        editQuantity = (EditText) findViewById(R.id.edit_quantity);
        editMedicine = (EditText) findViewById(R.id.medicine_edit_text_view);

        rg1.setOnTouchListener(mTouchListener);
        rg2.setOnTouchListener(mTouchListener);
        editMedicine.setOnTouchListener(mTouchListener);
        editQuantity.setOnTouchListener(mTouchListener);

        rg1.clearCheck();
        rg2.clearCheck();
        rg1.setOnCheckedChangeListener(listener1);
        rg2.setOnCheckedChangeListener(listener2);
    }

    private RadioGroup.OnCheckedChangeListener listener1 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != 0) {
                rg2.setOnCheckedChangeListener(null);
                chkId1 = rg1.getCheckedRadioButtonId();
                rg2.clearCheck();
                rg2.setOnCheckedChangeListener(listener2);

            }
        }
    };

    private RadioGroup.OnCheckedChangeListener listener2 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != 0) {
                rg1.setOnCheckedChangeListener(null);
                chkId2 = rg2.getCheckedRadioButtonId();
                rg1.clearCheck();
                rg1.setOnCheckedChangeListener(listener1);
            }
        }
    };

    public void addMedicineToStore(View view){
        String quantity, medicineName;
        quantity = editQuantity.getText().toString();
        medicineName = editMedicine.getText().toString();
        if (chkId1 != 0 || chkId2 != 0 && (!(medicineName.matches(""))) && (!(quantity.matches("")))) {
            realCheck = chkId1 == 0 ? chkId2 : chkId1;
            int checkedRg1, checkedRg2 = 0;
            Log.i("You pressed", ": " + realCheck);
            Toast.makeText(this,"\tMedicine Name : "+ editMedicine.getText().toString()
                    + "\n\tMedicine Type : " + realCheck
                    + "\n\tMedicine Quantity : "+editQuantity.getText().toString(),Toast.LENGTH_LONG).show();
            saveMedicine();
            checkedRg1 = rg1.getCheckedRadioButtonId();
            checkedRg2 = rg2.getCheckedRadioButtonId();
            if (checkedRg1 != 0) {
                rg1.clearCheck();
            }
            ;
            if (checkedRg2 != 0) {
                rg2.clearCheck();
            }
            editQuantity.setText("");
            editMedicine.setText("");
            chkId1 = 0;
            chkId2 = 0;
        } else if (medicineName.matches("")) {
            Toast.makeText(this,"Please provide Medicine Name",Toast.LENGTH_SHORT).show();
        } else if (quantity.matches("")){
            Toast.makeText(this,"Please provide Quantity of Medicine",Toast.LENGTH_SHORT).show();
        } else if (chkId1 == 0 && chkId2 == 0){
            Toast.makeText(this,"Please select proper Type of Medicine",Toast.LENGTH_SHORT).show();
        }
    }

    public void saveMedicineToStore(View view) {
        Log.i("ADD Medicine","Saving medicines to store");
        Toast.makeText(this,"Saving medicine to store",Toast.LENGTH_LONG).show();
    }


    private void saveMedicine(){
        String medicineName = editMedicine.getText().toString().trim();
        String medicineQuantity = editQuantity.getText().toString().trim();
        int medicineType = realCheck;

        ContentValues values = new ContentValues();
        values.put(MedicineContract.Medicine.COLUMN_MEDICINE_NAME, medicineName);
        values.put(MedicineContract.Medicine.COLUMN_MEDICINE_QUANTITY, medicineQuantity);
        values.put(MedicineContract.Medicine.COLUMN_MEDICINE_TYPE, medicineType);

            Uri newUri = getContentResolver().insert(MedicineContract.Medicine.CONTENT_URI, values);

            if (newUri == null) {
                Toast.makeText(this, getString(R.string.editor_insert_medicine_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_insert_medicine_successful),
                        Toast.LENGTH_SHORT).show();
           }
    }

    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                MedicineContract.Medicine._ID,
                MedicineContract.Medicine.COLUMN_MEDICINE_NAME,
                MedicineContract.Medicine.COLUMN_MEDICINE_QUANTITY,
                MedicineContract.Medicine.COLUMN_MEDICINE_TYPE};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentMedicineUri,         // Query the content URI for the current medicine
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        if (cursor.moveToFirst()) {
            // Find the columns of medicine attributes that we're interested in
            int nameColumnIndex = cursor.getColumnIndex(MedicineContract.Medicine.COLUMN_MEDICINE_NAME);
            int quantityColumnIndex = cursor.getColumnIndex(MedicineContract.Medicine.COLUMN_MEDICINE_QUANTITY);
            int typeColumnIndex = cursor.getColumnIndex(MedicineContract.Medicine.COLUMN_MEDICINE_TYPE);

            // Extract out the value from the Cursor for the given column index
            String name = cursor.getString(nameColumnIndex);
            String quantity = cursor.getString(quantityColumnIndex);
            int type = cursor.getInt(typeColumnIndex);

            // Update the views on the screen with the values from the database
            editMedicine.setText(name);
            editQuantity.setText(quantity);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
