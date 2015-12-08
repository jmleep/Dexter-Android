package com.leeper.jordan.rolodex.activities;


import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.leeper.jordan.rolodex.R;
import com.leeper.jordan.rolodex.datasource.Contact;
import com.leeper.jordan.rolodex.dialog.ContactsDialog;
import com.leeper.jordan.rolodex.fragments.AddContactFragment;
import com.leeper.jordan.rolodex.fragments.ContactsRecyclerViewFragment;

public class ContactsListActivity extends AppCompatActivity implements ContactsRecyclerViewFragment.OnContactSelectedListener {

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private final static int LIST_LOADER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        if(savedInstanceState == null) {

            Bundle bundle = new Bundle();
            bundle.putInt("loaderId", LIST_LOADER);

            ContactsRecyclerViewFragment contactsRecyclerViewFragment = new ContactsRecyclerViewFragment();
            contactsRecyclerViewFragment.setArguments(bundle);

            fragmentManager.beginTransaction().add(R.id.content_frame, contactsRecyclerViewFragment).commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddContactFragment addContactFragment = new AddContactFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_frame, addContactFragment);
                transaction.addToBackStack("add-contact");
                transaction.commit();
            }
        });
    }

    @Override
    public void onContactSelected(Contact contact) {
        if(contact != null) {
            Intent intent = new Intent(this, ContactDetailsActivity.class);

            Bundle bundle = new Bundle();
            bundle.putParcelable("contactToView", contact);

            intent.putExtras(bundle);
            startActivity(intent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if(null != searchManager ) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(getApplicationContext(), SearchActivity.class)));
        }
        searchView.setIconifiedByDefault(false);

        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(Color.WHITE);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.deleteDatabase:
                ContactsDialog dialog = new ContactsDialog();
                Bundle args = new Bundle();
                args.putString(ContactsDialog.DIALOG_TYPE, ContactsDialog.DELETE_DATABASE);
                dialog.setArguments(args);
                dialog.show(getSupportFragmentManager(), "delete-database");
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
