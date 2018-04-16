package com.pum2018.pillreminder_java;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.pum2018.pillreminder_java.Data.MedicineContract;
import com.pum2018.pillreminder_java.Data.MedicineDBHelper;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    MedicineDBHelper medicineDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medicineDBHelper = new MedicineDBHelper(this);
        //SQLiteDatabase db = medicineDBHelper.getReadableDatabase();
    }

    /**
     *
     * @param view
     */
    public void onStoreClick(View view){
        //Toast.makeText(this,"You pressed Store",LENGTH_SHORT).show();
        Intent storeIntent = new Intent(this,StoreActivity.class) ;
        startActivity(storeIntent);
    }

    /**
     *
     * @param view
     */
    public void onSettingsClick(View view){
        //Toast.makeText(this,"You pressed Settings", LENGTH_SHORT).show();
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

    /**
     *
     * @param view
     */
    public void onReportClick(View view){
        //Toast.makeText(this,"You pressed Report", LENGTH_SHORT).show();
        Intent reportIntent = new Intent(this,ReportActivity.class);
        startActivity(reportIntent);
    }

    /**
     *
     * @param view
     */
    public void onScheduleClick(View view){
        //Toast.makeText(this,"You pressed Schedule", LENGTH_SHORT).show();
        Intent scheduleIntent = new Intent(this,ScheduleActivity.class);
        startActivity(scheduleIntent);
    }
}
