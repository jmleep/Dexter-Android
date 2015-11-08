package com.leeper.jordan.rolodex.loaders;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.leeper.jordan.rolodex.datasource.Contact;
import com.leeper.jordan.rolodex.datasource.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan on 11/5/2015. Load data from ContactsProvider
 */
public class ContactsSearchListLoader extends AsyncTaskLoader<List<Contact>> {
    private static final String LOG_TAG = ContactsSearchListLoader.class.getSimpleName();
    private List<Contact> mContacts;
    private ContentResolver mContentResolver;
    private Cursor mCursor;
    private String mFilterText;

    public ContactsSearchListLoader(Context context, Uri uri, ContentResolver contentResolver, String filterText) {
        super(context);
        mContentResolver = contentResolver;
        this.mFilterText = filterText;
    }

    @Override
    public List<Contact> loadInBackground() {
        String[] projection = {BaseColumns._ID, ContactsContract.ContactsColumns.CONTACT_NAME, ContactsContract.ContactsColumns.CONTACT_EMAIL, ContactsContract.ContactsColumns.CONTACT_PHONE };
        List<Contact> entries = new ArrayList<Contact>();

        String selection = ContactsContract.ContactsColumns.CONTACT_NAME + " LIKE '" + mFilterText + "%'";
        mCursor = mContentResolver.query(ContactsContract.URI_TABLE, projection, selection, null, null);
        if(mCursor != null) {
            if(mCursor.moveToFirst()) {
                do {
                    int _id = mCursor.getInt(mCursor.getColumnIndex(BaseColumns._ID));
                    String name = mCursor.getString(mCursor.getColumnIndex(ContactsContract.ContactsColumns.CONTACT_NAME));
                    String email = mCursor.getString(mCursor.getColumnIndex(ContactsContract.ContactsColumns.CONTACT_EMAIL));
                    String phone = mCursor.getString(mCursor.getColumnIndex(ContactsContract.ContactsColumns.CONTACT_PHONE));

                    Contact contact = new Contact(_id, name, email, phone);
                    entries.add(contact);
                }while (mCursor.moveToNext());
            }
        }

        return entries;
    }

    @Override
    public void deliverResult(List<Contact> data) {
        if(isReset()) {
            if (data != null) {
                mCursor.close();
            }
        }

        List<Contact> oldContactList = mContacts;
        if(mContacts == null || mContacts.size() == 0) {
            Log.d(LOG_TAG, "**** No Data returned");
        }

        mContacts = data;
        if (isStarted()) {
            super.deliverResult(data);
        }

        if(oldContactList != null && oldContactList != data) {
            mCursor.close();
        }
    }

    @Override
    protected void onStartLoading() {
        if(mContacts != null) {
            deliverResult(mContacts);
        }

        if(takeContentChanged() || mContacts == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();
        if(mCursor != null) {
            mCursor.close();
        }

        mContacts = null;
    }

    @Override
    public void onCanceled(List<Contact> data) {
        super.onCanceled(data);
        if(mCursor != null) {
            mCursor.close();
        }
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }
}
