package com.pum2018.pillreminder_java;

import android.annotation.SuppressLint;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.pum2018.pillreminder_java.Data.MedicineContract;

public class AddMedicine extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{


    public static final int EXISTING_MEDICINE_LOADER = 0;

    Uri mCurrentMedicineUri;

    RadioGroup rg1, rg2;
    EditText editQuantity, editMedicine;
    int chkId1, chkId2 = 0;
    int realCheck = 0;

    private boolean mMedicineHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mMedicineHasChanged = true;
            return false;
        }
    };

    @SuppressLint("ClickableViewAccessibility")
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
        Toast.makeText(this,"AddMedicine just created",Toast.LENGTH_LONG).show();
        Log.i("chkId1", ": " + chkId1);
        Log.i("chkId2", ": " + chkId2);
        Log.i("realCheck", ": " + realCheck);
        Log.i("realCheck", ": " + rg1.getCheckedRadioButtonId());
        Log.i("realCheck", ": " + rg2.getCheckedRadioButtonId());
    }

    private RadioGroup.OnCheckedChangeListener listener1 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != 0) {
                rg2.setOnCheckedChangeListener(null);
                chkId1 = rg1.indexOfChild(findViewById(rg1.getCheckedRadioButtonId())) + 1;
                Log.i("chkId1", ": " + chkId1);
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
                chkId2 = rg1.getChildCount() + rg2.indexOfChild(findViewById(rg2.getCheckedRadioButtonId())) +1;
                Log.i("chkId2", ": " + chkId2);
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
        if (mCurrentMedicineUri == null) {
            Uri newUri = getContentResolver().insert(MedicineContract.Medicine.CONTENT_URI, values);

            if (newUri == null) {
                Toast.makeText(this, getString(R.string.editor_insert_medicine_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_insert_medicine_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            int rowsAffected = getContentResolver().update(mCurrentMedicineUri,values,null,null);

            if (rowsAffected == 0) {
                Toast.makeText(this, getString(R.string.editor_insert_medicine_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_insert_medicine_successful),
                        Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

    public void deleteMedicine(View view) {
        if (mCurrentMedicineUri != null) {
            int rowsDeleted = getContentResolver().delete(mCurrentMedicineUri, null, null);
            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.editor_delete_medicine_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_delete_medicine_successful),
                        Toast.LENGTH_SHORT).show();
            }
            finish();
        }}

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
        RadioButton radioButton;
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
            rg1.clearCheck();
            rg2.clearCheck();
            switch(type){
                case 1 :
                    radioButton = (RadioButton) findViewById(R.id.aerosol);
                    radioButton.setChecked(true);
                    break;
                case 2 :
                    radioButton = (RadioButton) findViewById(R.id.capsule);
                    radioButton.setChecked(true);
                    break;
                case 3 :
                    radioButton = (RadioButton) findViewById(R.id.drops);
                    radioButton.setChecked(true);
                    break;
                case 4 :
                    radioButton = (RadioButton) findViewById(R.id.globule);
                    radioButton.setChecked(true);
                    break;
                case 5 :
                    radioButton = (RadioButton) findViewById(R.id.injection);
                    radioButton.setChecked(true);
                    break;
                case 6 :
                    radioButton = (RadioButton) findViewById(R.id.insulin);
                    radioButton.setChecked(true);
                    break;
                case 7 :
                    radioButton = (RadioButton) findViewById(R.id.plaster);
                    radioButton.setChecked(true);
                    break;
                case 8 :
                    radioButton = (RadioButton) findViewById(R.id.sublingualtablet);
                    radioButton.setChecked(true);
                    break;
                case 9 :
                    radioButton = (RadioButton) findViewById(R.id.suppository);
                    radioButton.setChecked(true);
                    break;
                case 10 :
                    radioButton = (RadioButton) findViewById(R.id.syrup);
                    radioButton.setChecked(true);
                    break;
                case 11 :
                    radioButton = (RadioButton) findViewById(R.id.tablet);
                    radioButton.setChecked(true);
                    break;
            }

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
