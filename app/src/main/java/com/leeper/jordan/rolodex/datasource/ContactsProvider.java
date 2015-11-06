package com.leeper.jordan.rolodex.datasource;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Jordan on 11/5/2015.
 */
public class ContactsProvider extends ContentProvider {
    private ContactsDatabase mOpenHelper;

    private static String TAG = ContactsProvider.class.getSimpleName();
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    //Unique id for all contacts and for a single contact
    private static final int CONTACTS = 100;
    private static final int CONTACTS_ID = 101;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ContactsContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, "contacts", CONTACTS);
        matcher.addURI(authority, "contacts/*", CONTACTS_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new ContactsDatabase(getContext());
        return true;
    }

    private void deleteDatabase() {
        mOpenHelper.close();
        ContactsDatabase.deleteDatabase(getContext());
        mOpenHelper = new ContactsDatabase(getContext());
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CONTACTS:
                return ContactsContract.Contacts.CONTENT_TYPE;
            case CONTACTS_ID:
                return ContactsContract.Contacts.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final int match = sUriMatcher.match(uri);

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(ContactsDatabase.Tables.CONTACTS);

        switch (match) {
            case CONTACTS:
                //do nothing
                break;
            case CONTACTS_ID:
                String id = ContactsContract.Contacts.getContactId(uri);
                queryBuilder.appendWhere(BaseColumns._ID + "=" + id);
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }

        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.v(TAG, "insert(uri = " + uri + ", values = " + values.toString());
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case CONTACTS:
                long recordId = db.insertOrThrow(ContactsDatabase.Tables.CONTACTS, null, values);
                return ContactsContract.Contacts.buildContactUri(String.valueOf(recordId));
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.v(TAG, "update(uri = " + uri + ", values = " + values.toString());
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        String selectionCriteria = selection;
        switch (match) {
            case CONTACTS_ID:
                String id = ContactsContract.Contacts.getContactId(uri);
                selectionCriteria = BaseColumns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }

        return db.update(ContactsDatabase.Tables.CONTACTS, values, selectionCriteria, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.v(TAG, "delete(uri = " + uri);

        if(uri.equals(ContactsContract.BASE_CONTENT_URI)) {
            deleteDatabase();
            return 0;
        }

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case CONTACTS_ID:
                String id = ContactsContract.Contacts.getContactId(uri);
                String selectionCriteria = BaseColumns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                return db.delete(ContactsDatabase.Tables.CONTACTS, selectionCriteria, selectionArgs);
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }
}
