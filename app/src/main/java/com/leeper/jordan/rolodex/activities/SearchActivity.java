package com.leeper.jordan.rolodex.activities;

import android.content.ContentResolver;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.leeper.jordan.rolodex.R;
import com.leeper.jordan.rolodex.adapter.ContactsCustomAdapter;
import com.leeper.jordan.rolodex.datasource.Contact;
import com.leeper.jordan.rolodex.datasource.ContactsContract;
import com.leeper.jordan.rolodex.loaders.ContactsSearchListLoader;

import java.util.List;

/**
 * Created by Jordan on 11/8/2015.
 */
public class SearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Contact>> {

    private static final String TAG = SearchActivity.class.getSimpleName();
    private ContactsCustomAdapter contactsCustomAdapter;
    private static int LOADER_ID = 2;
    private ContentResolver mContentResolver;
    private List<Contact> contactsRetrieved;
    private ListView listView;
    private EditText mSearchEditText;
    private Button mSearchButton;
    private String matchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        listView = (ListView) findViewById(R.id.searchResultsList);
        mSearchEditText = (EditText) findViewById(R.id.searchName);
        mSearchButton = (Button) findViewById(R.id.searchButton);
        mContentResolver = getContentResolver();
        contactsCustomAdapter = new ContactsCustomAdapter(SearchActivity.this, getSupportFragmentManager());
        listView.setAdapter(contactsCustomAdapter);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchText = mSearchEditText.getText().toString();
                getSupportLoaderManager().initLoader(LOADER_ID++, null, SearchActivity.this);
            }
        });
    }

    @Override
    public Loader<List<Contact>> onCreateLoader(int id, Bundle args) {
        return new ContactsSearchListLoader(SearchActivity.this, ContactsContract.URI_TABLE, this.mContentResolver, matchText);
    }

    @Override
    public void onLoadFinished(Loader<List<Contact>> loader, List<Contact> data) {
        contactsCustomAdapter.setData(data);
        this.contactsRetrieved = data;
    }

    @Override
    public void onLoaderReset(Loader<List<Contact>> loader) {
        contactsCustomAdapter.setData(null);
    }
}
