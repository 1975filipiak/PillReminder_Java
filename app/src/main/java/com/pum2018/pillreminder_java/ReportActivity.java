package com.pum2018.pillreminder_java;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.pum2018.pillreminder_java.Data.MedicineContract;

public class ReportActivity extends AppCompatActivity {


    public static final int EXISTING_MEDICINE_LOADER = 0;

    Uri mCurrentMedicineUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
    }


    public void saveDataInReportTakings(View view){
        //TODO : Read data from GUI and put in "" below
        String rtt_key_date = "2018-04-25";
        String rtt_key_medicine_name = "Aspirin";
        String rtt_key_planned_time = "2018-04-30";
        String rtt_key_taking_time = "2018-04-30";

        ContentValues values = new ContentValues();
        values.put(MedicineContract.ReportTaking.COLUMN_RTT_KEY_DATE, rtt_key_date);
        values.put(MedicineContract.ReportTaking.COLUMN_RTT_KEY_MEDICINE_NAME, rtt_key_medicine_name);
        values.put(MedicineContract.ReportTaking.COLUMN_RTT_KEY_PLANNED_TIME, rtt_key_planned_time);
        values.put(MedicineContract.ReportTaking.COLUMN_RTT_KEY_TAKING_TIME,rtt_key_taking_time);

        if (mCurrentMedicineUri == null) {
            Uri newUri = getContentResolver().insert(MedicineContract.ReportTaking.CONTENT_URI, values);

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
}
