package com.leeper.jordan.rolodex.fragments;

import android.content.ContentResolver;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.leeper.jordan.rolodex.adapter.ContactsCustomAdapter;
import com.leeper.jordan.rolodex.datasource.Contact;
import com.leeper.jordan.rolodex.datasource.ContactsContract;
import com.leeper.jordan.rolodex.loaders.ContactsListLoader;

import java.util.List;

/**
 * Created by Jordan on 11/5/2015.
 */
public class ContactsListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<Contact>> {

    private static final String LOG_TAG = ContactsListFragment.class.getSimpleName();
    private ContactsCustomAdapter mAdapter;
    private static final int LOADER_ID = 1;
    private ContentResolver mContentResolver;
    private List<Contact> mContacts;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        mContentResolver = getActivity().getContentResolver();
        mAdapter = new ContactsCustomAdapter(getActivity(), getChildFragmentManager());
        setEmptyText("No Contacts");
        setListAdapter(mAdapter);
        setListShown(false);

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Contact>> onCreateLoader(int id, Bundle args) {
        mContentResolver = getActivity().getContentResolver();
        return new ContactsListLoader(getActivity(), ContactsContract.URI_TABLE, mContentResolver);
    }

    @Override
    public void onLoadFinished(Loader<List<Contact>> loader, List<Contact> contacts) {
        mAdapter.setData(contacts);
        mContacts = contacts;
        if(isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Contact>> loader) {
        mAdapter.setData(null);
    }
}
