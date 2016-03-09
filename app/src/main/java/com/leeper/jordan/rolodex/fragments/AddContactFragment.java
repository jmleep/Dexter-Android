package com.leeper.jordan.rolodex.fragments;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
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
import com.leeper.jordan.rolodex.datasource.ContactsContract;
import com.leeper.jordan.rolodex.dialog.ContactsDialog;
import com.leeper.jordan.rolodex.validator.ContactDetailsValidator;

/**
 * Created by Jordan on 11/8/2015.
 */
public class AddContactFragment extends Fragment {
    private final String TAG = AddContactFragment.class.getSimpleName();
    private EditText mNameTextView, mEmailTextView, mPhoneTextView;
    private ContentResolver mContentResolver;

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

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                boolean check = false;
                if(keyCode == KeyEvent.KEYCODE_BACK) {
                    if(someDataEntered()) {
                        ContactsDialog dialog = new ContactsDialog();
                        Bundle args = new Bundle();
                        args.putString(ContactsDialog.DIALOG_TYPE, ContactsDialog.CONFIRM_EXIT);
                        dialog.setArguments(args);
                        dialog.show(getActivity().getSupportFragmentManager(), "confirm-exit");
                        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        check = true;
                    }
                }
                return check;
            }
        });
        return view;
    }

    private boolean someDataEntered() {
        if(mNameTextView.getText().toString().length() > 0 || mPhoneTextView.getText().toString().length() > 0 || mEmailTextView.getText().toString().length() > 0) {
            return true;
        } else {
            return false;
        }
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
                    Uri returned = mContentResolver.insert(ContactsContract.URI_TABLE, values);
                    Log.d(TAG, "Record id: " + returned.toString());
                    Intent intent = new Intent(getActivity(), ContactsListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Please ensure you have entered valid data.", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
