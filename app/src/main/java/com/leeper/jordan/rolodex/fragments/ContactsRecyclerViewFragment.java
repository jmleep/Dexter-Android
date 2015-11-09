package com.leeper.jordan.rolodex.fragments;


import android.content.ContentResolver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leeper.jordan.rolodex.R;
import com.leeper.jordan.rolodex.adapter.ContactsCustomAdapter;
import com.leeper.jordan.rolodex.datasource.Contact;
import com.leeper.jordan.rolodex.datasource.ContactsContract;
import com.leeper.jordan.rolodex.loaders.ContactsListLoader;

import java.util.List;

/**
 * Created by Jordan on 11/8/2015. Recycler view fragment for displaying list of contacts.
 */
public class ContactsRecyclerViewFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Contact>>{
    private ContentResolver mContentResolver;
    private ContactsCustomAdapter mAdapter;
    private List<Contact> mContacts;
    private static final int LOADER_ID = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        mAdapter = new ContactsCustomAdapter(getContext(), getFragmentManager(), mContacts);

        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getLoaderManager().initLoader(LOADER_ID, null, this);
        return recyclerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public Loader<List<Contact>> onCreateLoader(int id, Bundle args) {
        mContentResolver = getActivity().getContentResolver();
        return new ContactsListLoader(getActivity(), ContactsContract.URI_TABLE, mContentResolver);
    }

    @Override
    public void onLoadFinished(Loader<List<Contact>> loader, List<Contact> contacts) {
        mContacts = contacts;
        mAdapter.setContacts(contacts);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Contact>> loader) {

    }
}
