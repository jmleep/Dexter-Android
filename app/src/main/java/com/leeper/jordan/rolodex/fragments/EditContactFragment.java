package com.leeper.jordan.rolodex.fragments;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.leeper.jordan.rolodex.R;
import com.leeper.jordan.rolodex.activities.ContactsListActivity;
import com.leeper.jordan.rolodex.datasource.Contact;
import com.leeper.jordan.rolodex.datasource.ContactsContract;
import com.leeper.jordan.rolodex.validator.ContactDetailsValidator;

/**
 * Created by jordan on 11/11/15.
 */
public class EditContactFragment extends Fragment {

    private final String TAG = EditContactFragment.class.getSimpleName();
    private EditText mNameTextView, mEmailTextView, mPhoneTextView;
    private ContentResolver mContentResolver;
    private String _id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = (View) inflater.inflate(R.layout.add_edit, container, false);

        mContentResolver = getActivity().getContentResolver();

        mNameTextView = (EditText) view.findViewById(R.id.editContactName);
        mEmailTextView = (EditText) view.findViewById(R.id.editContactEmail);
        mPhoneTextView = (EditText) view.findViewById(R.id.editContactPhone);

        Contact contactToEdit = (Contact) getArguments().getParcelable("contactToEdit");
        _id = String.valueOf(contactToEdit.getId());
        String name = contactToEdit.getName();
        String email = contactToEdit.getEmail();
        String phone = contactToEdit.getPhone();

        mNameTextView.setText(name);
        mEmailTextView.setText(email);
        mPhoneTextView.setText(phone);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.add_edit_contact, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.doneEditingButton:
                if (ContactDetailsValidator.validateContactDetails(mNameTextView.getText().toString(), mPhoneTextView.getText().toString(),
                        mEmailTextView.getText().toString())) {
                    ContentValues values = new ContentValues();
                    values.put(ContactsContract.ContactsColumns.CONTACT_NAME, mNameTextView.getText().toString());
                    values.put(ContactsContract.ContactsColumns.CONTACT_EMAIL, mEmailTextView.getText().toString());
                    values.put(ContactsContract.ContactsColumns.CONTACT_PHONE, mPhoneTextView.getText().toString());

                    Uri uri = ContactsContract.Contacts.buildContactUri(_id);
                    int recordsUpdated = mContentResolver.update(uri, values, null, null);
                    Log.d(TAG, "Records updated: " + recordsUpdated);
                    Intent intent = new Intent(getActivity(), ContactsListActivity.class);

                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Please ensure you have entered valid data.", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
