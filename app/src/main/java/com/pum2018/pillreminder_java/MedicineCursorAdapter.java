package com.pum2018.pillreminder_java;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.pum2018.pillreminder_java.Data.MedicineContract;

/**
 * {@link MedicineCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of medicine data as its data source. This adapter knows
 * how to create list items for each row of medicine data in the {@link Cursor}.
 */
public class MedicineCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link MedicineCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public MedicineCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the medicine data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current medicine can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        String medQuantityCalculated;
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.summary);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        // Extract properties from cursor
        String medicineName = cursor.getString(cursor.getColumnIndex(MedicineContract.Medicine.COLUMN_MEDICINE_NAME));
        Integer medicineType = cursor.getInt(cursor.getColumnIndex(MedicineContract.Medicine.COLUMN_MEDICINE_TYPE));
        Integer medicineQuantity = cursor.getInt(cursor.getColumnIndex(MedicineContract.Medicine.COLUMN_MEDICINE_QUANTITY));
        // Populate fields with extracted properties
        switch(medicineType){
          case 1:
              medQuantityCalculated = "Aerosol";
              break;
          case 2:
              medQuantityCalculated = "Capsule";
              break;
          case 3:
              medQuantityCalculated = "Drops";
              break;
          case 4:
              medQuantityCalculated = "Globule";
              break;
          case 5:
              medQuantityCalculated = "Injection";
              break;
          case 6:
              medQuantityCalculated = "Insulin";
              break;
          case 7:
              medQuantityCalculated = "Plaster";
              break;
          case 8:
              medQuantityCalculated = "Sublingualtablet";
              break;
          case 9:
              medQuantityCalculated = "Suppository";
              break;
          case 10:
              medQuantityCalculated = "Syrup";
              break;
          case 11:
              medQuantityCalculated = "Tablet";
              break;
          default:
              medQuantityCalculated = "Undefined";
              break;
        }
        nameTextView.setText(medicineName);
        //summaryTextView.setText(String.valueOf(medicineType));
        summaryTextView.setText(String.valueOf(medQuantityCalculated));
        quantityTextView.setText(String.valueOf(medicineQuantity));
    }
}
