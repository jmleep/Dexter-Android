package com.leeper.jordan.dexter.activities;

import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.leeper.jordan.dexter.R;
import com.leeper.jordan.dexter.datasource.Contact;
import com.leeper.jordan.dexter.fragments.ContactsRecyclerViewFragment;

/**
 * Created by Jordan on 11/8/2015. Returns contacts where name matches search.
 */
public class SearchActivity extends AppCompatActivity implements ContactsRecyclerViewFragment.OnContactSelectedListener {

    private static final String TAG = SearchActivity.class.getSimpleName();
    private static int SEARCH_LOADER = 2;
    private ContentResolver mContentResolver;
    private String matchText;
    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            Bundle bundle = new Bundle();
            bundle.putString("query", intent.getStringExtra(SearchManager.QUERY));
            bundle.putInt("loaderId", SEARCH_LOADER);

            ContactsRecyclerViewFragment contactsRecyclerViewFragment = new ContactsRecyclerViewFragment();
            contactsRecyclerViewFragment.setArguments(bundle);

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content_frame, contactsRecyclerViewFragment);
            transaction.addToBackStack("searchActivity");
            transaction.commit();
        }

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
}
