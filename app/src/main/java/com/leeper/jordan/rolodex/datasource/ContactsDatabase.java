package com.leeper.jordan.rolodex.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Jordan on 11/5/2015. Database creation and upgrade class.
 */
public class ContactsDatabase extends SQLiteOpenHelper {

    private static final String TAG = ContactsDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 2;
    private final Context mContext;

    interface Tables {
        String CONTACTS = "contacts";
    }

    public ContactsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tables.CONTACTS + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ContactsContract.ContactsColumns.CONTACT_NAME + " TEXT NOT NULL,"
                + ContactsContract.ContactsColumns.CONTACT_EMAIL + " TEXT NOT NULL,"
                + ContactsContract.ContactsColumns.CONTACT_PHONE + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int version = oldVersion;
        if(version == 1) {
            // Add some fields w/o deleting existing data
            version = 2;
        }
        if(version != DATABASE_VERSION) {
            db.execSQL("DROP TABLE IF EXISTS " + Tables.CONTACTS);
            onCreate(db);
        }
    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}
