package com.leeper.jordan.rolodex.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Jordan on 10/26/2015.
 */
public class ContactDatabaseHelper extends SQLiteOpenHelper{
    SQLiteDatabase contactsDB = null;

    public ContactDatabaseHelper(Context context) {
        super(context, "Contacts.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        contactsDB.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static abstract class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "Contacts";
        public static final String CONTACT_ID = "entryid";
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";
        public static final String AREA_CODE = "areaCode";
        public static final String LOCAL_EXCHANGE = "localExchange";
        public static final String LINE_NUMBER = "lineNumber";
    }

    private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + ContactEntry.TABLE_NAME + "(" + ContactEntry.CONTACT_ID +
            " number(1), " + ContactEntry.FIRST_NAME + " varchar(30), " + ContactEntry.LAST_NAME + " varchar(30), " + ContactEntry.AREA_CODE +
            " number(3), " + ContactEntry.LOCAL_EXCHANGE + " number(3), " + ContactEntry.LINE_NUMBER + " number(3));";

}
