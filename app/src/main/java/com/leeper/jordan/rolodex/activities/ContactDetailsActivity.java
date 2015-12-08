package com.leeper.jordan.rolodex.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.leeper.jordan.rolodex.R;
import com.leeper.jordan.rolodex.datasource.Contact;
import com.leeper.jordan.rolodex.fragments.ContactDetailsFragment;

/**
 * Created by jordan on 11/23/15.
 */
public class ContactDetailsActivity extends AppCompatActivity {

    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Contact contactToView = (Contact) getIntent().getExtras().get("contactToView");
        Bundle bundle = new Bundle();
        bundle.putParcelable("contactToView", contactToView);

        ContactDetailsFragment contactDetailsFragment = new ContactDetailsFragment();
        contactDetailsFragment.setArguments(bundle);

        fragmentManager.beginTransaction().add(R.id.content_frame, contactDetailsFragment).commit();

    }
}
