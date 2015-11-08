package com.leeper.jordan.rolodex.activities;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.leeper.jordan.rolodex.R;
import com.leeper.jordan.rolodex.datasource.ContactsContract;

/**
 * Created by Jordan on 11/8/2015.
 */
public class EditActivity extends AppCompatActivity {

    private final String TAG = EditActivity.class.getSimpleName();
    private EditText mNameTextView, mEmailTextView, mPhoneTextView;
    private Button mButton;
    private ContentResolver mContentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit);

        mContentResolver = EditActivity.this.getContentResolver();

        mNameTextView = (EditText) findViewById(R.id.editContactName);
        mEmailTextView = (EditText) findViewById(R.id.editContactEmail);
        mPhoneTextView = (EditText) findViewById(R.id.editContactPhone);

        Intent intent = getIntent();
        final String _id = intent.getStringExtra(ContactsContract.ContactsColumns.CONTACT_ID);
        String name = intent.getStringExtra(ContactsContract.ContactsColumns.CONTACT_NAME);
        String email = intent.getStringExtra(ContactsContract.ContactsColumns.CONTACT_EMAIL);
        String phone = intent.getStringExtra(ContactsContract.ContactsColumns.CONTACT_PHONE);

        mNameTextView.setText(name);
        mEmailTextView.setText(email);
        mPhoneTextView.setText(phone);

        mButton = (Button) findViewById(R.id.saveContactButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(ContactsContract.ContactsColumns.CONTACT_NAME, mNameTextView.getText().toString());
                values.put(ContactsContract.ContactsColumns.CONTACT_EMAIL, mEmailTextView.getText().toString());
                values.put(ContactsContract.ContactsColumns.CONTACT_PHONE, mPhoneTextView.getText().toString());

                Uri uri = ContactsContract.Contacts.buildContactUri(_id);
                int recordsUpdated = mContentResolver.update(uri, values, null, null);
                Log.d(TAG, "Records updated: " + recordsUpdated);
                Intent intent = new Intent(EditActivity.this, ContactsListActivity.class);

                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
        }
        return true;
    }
}
