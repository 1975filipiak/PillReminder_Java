package com.pum2018.pillreminder_java;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
    }

    public void addMedicineToStore(View view){
        Intent addMedicineToStoreIntent = new Intent(StoreActivity.this,AddMedicine.class);
        startActivity(addMedicineToStoreIntent);
    }

    public void checkMedicineInStore(View view){
        Intent checkMedicineToStoreIntent = new Intent(StoreActivity.this,CheckStoreActivity.class);
        startActivity(checkMedicineToStoreIntent);
    }

}
