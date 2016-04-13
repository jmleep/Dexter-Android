package com.leeper.jordan.dexter.fragments;


import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leeper.jordan.dexter.R;
import com.leeper.jordan.dexter.adapter.ContactsCustomAdapter;
import com.leeper.jordan.dexter.datasource.Contact;
import com.leeper.jordan.dexter.datasource.ContactsContract;
import com.leeper.jordan.dexter.listeners.RecyclerItemClickListener;
import com.leeper.jordan.dexter.loaders.ContactsListLoader;
import com.leeper.jordan.dexter.loaders.ContactsSearchListLoader;

import java.util.List;

/**
 * Created by Jordan on 11/8/2015. Recycler view fragment for displaying list of contacts.
 */
public class ContactsRecyclerViewFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Contact>>{
    private ContentResolver mContentResolver;
    private ContactsCustomAdapter mAdapter;
    private List<Contact> mContacts;
    private int mLoaderId;
    private OnContactSelectedListener mCallback;
    private String mMatchText;
    private static final String TAG = ContactsRecyclerViewFragment.class.getSimpleName();
    private static final int LIST_LOADER = 1;
    private static final int SEARCH_LOADER = 2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLoaderId = (int) getArguments().get("loaderId");
        if(mLoaderId == SEARCH_LOADER) {
            mMatchText = (String) getArguments().get("query");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        mAdapter = new ContactsCustomAdapter(getContext(), getFragmentManager(), mContacts);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mCallback.onContactSelected(mContacts.get(position));
            }
        }));

        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getLoaderManager().initLoader(mLoaderId, null, this);
        return recyclerView;
    }

    public interface OnContactSelectedListener {
        public void onContactSelected(Contact contact);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnContactSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " + OnContactSelectedListener.class.getSimpleName());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public Loader<List<Contact>> onCreateLoader(int id, Bundle args) {
        mContentResolver = getActivity().getContentResolver();

        if (mLoaderId == LIST_LOADER) {
            return new ContactsListLoader(getActivity(), ContactsContract.URI_TABLE, mContentResolver);
        } else if(mLoaderId == SEARCH_LOADER) {
            return new ContactsSearchListLoader(getActivity(), ContactsContract.URI_TABLE, this.mContentResolver, mMatchText);
        } else {
            Log.e(TAG, "Invalid loader was passed");
            return null;
        }

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
