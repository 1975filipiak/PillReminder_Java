package com.pum2018.pillreminder_java.Data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class MedicineContract {
    private MedicineContract(){};

    public static final String CONTENT_AUTHORITY = "com.pum2018.pillreminder_java";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MEDICINE = "Medicines";

    public static final class Medicine implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MEDICINE);


        public static final String TABLE_NAME = "Medicines";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_MEDICINE_NAME = "name";
        public static final String COLUMN_MEDICINE_TYPE = "type";
        public static final String COLUMN_MEDICINE_QUANTITY = "quantity";

        public static final int TYPE_AEROSOL = 1;
        public static final int TYPE_CAPSULE = 2;
        public static final int TYPE_DROPS = 3;
        public static final int TYPE_GLOBULE = 4;
        public static final int TYPE_INJECTION = 5;
        public static final int TYPE_INSULIN = 6;
        public static final int TYPE_PLASTER = 7;
        public static final int TYPE_SUBLINGUALTABLET = 8;
        public static final int TYPE_SUPPOSITORY = 9;
        public static final int TYPE_SYRUP = 10;
        public static final int TYPE_TABLET = 11;


        public static boolean isValidType(int type) {
            if (type == TYPE_AEROSOL || type == TYPE_CAPSULE || type == TYPE_DROPS ||
                    type == TYPE_GLOBULE || type == TYPE_INJECTION || type == TYPE_INSULIN ||
                    type == TYPE_PLASTER || type == TYPE_SUBLINGUALTABLET || type == TYPE_SUPPOSITORY ||
                    type == TYPE_SYRUP || type == TYPE_TABLET) {
                return true;
            }
            return false;
        }
    }



}
