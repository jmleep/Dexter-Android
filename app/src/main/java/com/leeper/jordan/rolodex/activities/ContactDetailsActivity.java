package com.leeper.jordan.rolodex.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.leeper.jordan.rolodex.R;
import com.leeper.jordan.rolodex.datasource.Contact;
import com.leeper.jordan.rolodex.datasource.ContactsContract;
import com.leeper.jordan.rolodex.dialog.ContactsDialog;
import com.leeper.jordan.rolodex.fragments.ContactDetailsFragment;
import com.leeper.jordan.rolodex.fragments.EditContactFragment;

/**
 * Created by jordan on 11/23/15.
 */
public class ContactDetailsActivity extends AppCompatActivity {

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private Contact contactToView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contactToView = (Contact) getIntent().getExtras().get("contactToView");
        Bundle bundle = new Bundle();
        bundle.putParcelable("contactToView", contactToView);

        ContactDetailsFragment contactDetailsFragment = new ContactDetailsFragment();
        contactDetailsFragment.setArguments(bundle);

        fragmentManager.beginTransaction().add(R.id.content_frame, contactDetailsFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.contact_details, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.deleteContactButton:
                ContactsDialog dialog = new ContactsDialog();
                Bundle contactToDelete = new Bundle();
                contactToDelete.putString(ContactsDialog.DIALOG_TYPE, ContactsDialog.DELETE_RECORD);
                contactToDelete.putString(ContactsContract.ContactsColumns.CONTACT_ID, String.valueOf(contactToView.getId()));
                contactToDelete.putString(ContactsContract.ContactsColumns.CONTACT_NAME, contactToView.getName());

                dialog.setArguments(contactToDelete);
                dialog.show(getSupportFragmentManager(), "delete-record");
                break;
            case R.id.editContactButton:
                Bundle contactToEdit = new Bundle();
                contactToEdit.putParcelable("contactToEdit", contactToView);

                EditContactFragment editContactFragment = new EditContactFragment();
                editContactFragment.setArguments(contactToEdit);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_frame, editContactFragment);
                transaction.addToBackStack("edit-contact");
                transaction.commit();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
