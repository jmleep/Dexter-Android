package com.leeper.jordan.rolodex.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.leeper.jordan.rolodex.R;
import com.leeper.jordan.rolodex.datasource.Contact;
import com.leeper.jordan.rolodex.datasource.ContactsContract;
import com.leeper.jordan.rolodex.dialog.ContactsDialog;

import java.util.List;

/**
 * Created by Jordan on 11/5/2015. Custom adapter for binding views to custom items.
 */
public class ContactsCustomAdapter extends RecyclerView.Adapter<ContactsCustomAdapter.ContactViewHolder> {
    private final String TAG = ContactsCustomAdapter.class.getSimpleName();
    private LayoutInflater mLayoutInflater;
    private static FragmentManager sFragmentManager;
    private List<Contact> mContacts;
    private Context mContext;

    public ContactsCustomAdapter(Context context, FragmentManager fragmentManager, List<Contact> contacts) {

        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mContacts = contacts;
        sFragmentManager = fragmentManager;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected int v_id;
        protected TextView vName;
        protected TextView vEmail;
        protected TextView vPhone;
        protected ImageButton edit;
        protected ImageButton delete;

        public ContactViewHolder(View v) {
            super(v);
            vName = (TextView) v.findViewById(R.id.contact_name);
            vEmail = (TextView) v.findViewById(R.id.contact_email);
            vPhone = (TextView) v.findViewById(R.id.contact_phone);
            edit = (ImageButton) v.findViewById(R.id.edit);
            delete = (ImageButton) v.findViewById(R.id.delete);
        }
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contactView = mLayoutInflater.inflate(R.layout.custom_contact, parent, false);
        return new ContactViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        final Contact contact = mContacts.get(position);
        final int _id = contact.getId();

        holder.v_id = _id;
        holder.vName.setText(contact.getName());
        holder.vEmail.setText(contact.getEmail());
        holder.vPhone.setText(contact.getPhone());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactsDialog dialog = new ContactsDialog();
                Bundle args = new Bundle();
                args.putString(ContactsDialog.DIALOG_TYPE, ContactsDialog.DELETE_RECORD);
                args.putString(ContactsContract.ContactsColumns.CONTACT_ID, String.valueOf(_id));
                args.putString(ContactsContract.ContactsColumns.CONTACT_NAME, contact.getName());
                dialog.setArguments(args);
                dialog.show(sFragmentManager, "delete-record");
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mContacts == null) {
            return 0;
        } else {
            return mContacts.size();
        }
    }

    public void setContacts(List<Contact> contacts) {
        mContacts = contacts;
    }


}
