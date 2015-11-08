package com.leeper.jordan.rolodex.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.leeper.jordan.rolodex.dialog.ContactsDialog;
import com.leeper.jordan.rolodex.R;
import com.leeper.jordan.rolodex.fragments.ContactsListFragment;

public class ContactsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        if(savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            ContactsListFragment contactsListFragment = new ContactsListFragment();
            fragmentManager.beginTransaction().add(R.id.content_frame, contactsListFragment).commit();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //ContactListFragment contactListFragment = new ContactListFragment();
        //contactListFragment.setArguments(bundle);
        //getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, contactListFragment).commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.addRecord:
                Intent intent = new Intent(ContactsListActivity.this, AddActivity.class);
                startActivity(intent);
                break;
            case R.id.deleteDatabase:
                ContactsDialog dialog = new ContactsDialog();
                Bundle args = new Bundle();
                args.putString(ContactsDialog.DIALOG_TYPE, ContactsDialog.DELETE_DATABASE);
                dialog.setArguments(args);
                dialog.show(getSupportFragmentManager(), "delete-database");
                break;
            case R.id.searchRecords:
                Intent searchIntent = new Intent(ContactsListActivity.this, SearchActivity.class);
                startActivity(searchIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
