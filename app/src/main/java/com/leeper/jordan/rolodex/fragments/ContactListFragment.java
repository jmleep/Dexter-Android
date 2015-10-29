package com.leeper.jordan.rolodex.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leeper.jordan.rolodex.R;
import com.leeper.jordan.rolodex.adapters.ContactsRecyclerViewAdapter;
import com.leeper.jordan.rolodex.datasource.Contact;

/**
 * Created by jordan on 10/29/15.
 */
public class ContactListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        ContactsRecyclerViewAdapter adapter = new ContactsRecyclerViewAdapter(Contact.createContactsList(30));

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return recyclerView;
    }
}
