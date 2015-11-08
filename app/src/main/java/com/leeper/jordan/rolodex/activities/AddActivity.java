package com.leeper.jordan.rolodex.activities;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.leeper.jordan.rolodex.ContactsDialog;
import com.leeper.jordan.rolodex.R;
import com.leeper.jordan.rolodex.datasource.ContactsContract;

import org.w3c.dom.Text;

/**
 * Created by Jordan on 11/8/2015.
 */
public class AddActivity extends AppCompatActivity {

    private final String TAG = AddActivity.class.getSimpleName();
    private EditText mNameTextView, mEmailTextView, mPhoneTextView;
    private Button mButton;
    private ContentResolver mContentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit);

        mContentResolver = AddActivity.this.getContentResolver();

        mNameTextView = (EditText) findViewById(R.id.editContactName);
        mEmailTextView = (EditText) findViewById(R.id.editContactEmail);
        mPhoneTextView = (EditText) findViewById(R.id.editContactPhone);

        mButton = (Button) findViewById(R.id.saveContactButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    ContentValues values = new ContentValues();
                    values.put(ContactsContract.ContactsColumns.CONTACT_NAME, mNameTextView.getText().toString());
                    values.put(ContactsContract.ContactsColumns.CONTACT_EMAIL, mEmailTextView.getText().toString());
                    values.put(ContactsContract.ContactsColumns.CONTACT_PHONE, mPhoneTextView.getText().toString());
                    Uri returned = mContentResolver.insert(ContactsContract.URI_TABLE, values);
                    Log.d(TAG, "Record id: " + returned.toString());
                    Intent intent = new Intent(AddActivity.this, ContactsListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please ensure you have entered valid data.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private boolean isValid() {
        if(mNameTextView.getText().toString().length() == 0 || mPhoneTextView.getText().toString().length() == 0 || mEmailTextView.getText().toString().length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    private boolean someDataEntered() {
        if(mNameTextView.getText().toString().length() > 0 || mPhoneTextView.getText().toString().length() > 0 || mEmailTextView.getText().toString().length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        if(someDataEntered()) {
            ContactsDialog dialog = new ContactsDialog();
            Bundle args = new Bundle();
            args.putString(ContactsDialog.DIALOG_TYPE, ContactsDialog.CONFIRM_EXIT);
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "confirm-exit");
        } else {
            super.onBackPressed();
        }
    }
}
