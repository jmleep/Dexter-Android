package com.leeper.jordan.rolodex.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.leeper.jordan.rolodex.R;
import com.leeper.jordan.rolodex.datasource.Contact;
import com.leeper.jordan.rolodex.datasource.ContactDatabaseHelper;
import com.leeper.jordan.rolodex.fragments.AddContactFragment;
import com.leeper.jordan.rolodex.fragments.ContactListFragment;

import java.util.ArrayList;
import java.util.List;

public class ContactsListActivity extends AppCompatActivity {

    private ContactDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<Contact> contacts =  dbHelper.getContacts(getApplicationContext());

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("contacts", contacts);
        // However, if we're being restored from a previous state,
        // then we don't need to do anything and should return or else
        // we could end up with overlapping fragments.

        // Create a new Fragment to be placed in the activity layout
        ContactListFragment contactListFragment = new ContactListFragment();

        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        contactListFragment.setArguments(bundle);

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, contactListFragment).commit();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddContactFragment addContactFragment = new AddContactFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, addContactFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                Log.i("tag", "Got into onclick");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
