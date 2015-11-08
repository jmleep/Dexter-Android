package com.leeper.jordan.rolodex.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.leeper.jordan.rolodex.dialog.ContactsDialog;
import com.leeper.jordan.rolodex.R;
import com.leeper.jordan.rolodex.activities.EditActivity;
import com.leeper.jordan.rolodex.datasource.Contact;
import com.leeper.jordan.rolodex.datasource.ContactsContract;

import java.util.List;

/**
 * Created by Jordan on 11/5/2015.
 */
public class ContactsCustomAdapter extends ArrayAdapter<Contact> {
    private final String TAG = ContactsCustomAdapter.class.getSimpleName();
    private LayoutInflater mLayoutInflater;
    private static FragmentManager sFragmentManager;

    public ContactsCustomAdapter(Context context, FragmentManager fragmentManager) {
        super(context, android.R.layout.simple_list_item_2);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sFragmentManager = fragmentManager;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view;
        if(convertView == null) {
            view = mLayoutInflater.inflate(R.layout.custom_contact, parent, false);
        }else {
            view = convertView;
        }

        final Contact contact = getItem(position);
        final int _id = contact.getId();
        final String name = contact.getName();
        final String email = contact.getEmail();
        final String phone = contact.getPhone();

        ((TextView) view.findViewById(R.id.contact_name)).setText(name);
        ((TextView) view.findViewById(R.id.contact_email)).setText(email);
        ((TextView) view.findViewById(R.id.contact_phone)).setText(phone);

        ImageButton edit = (ImageButton) view.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditActivity.class);
                intent.putExtra(ContactsContract.ContactsColumns.CONTACT_ID, String.valueOf(_id));
                intent.putExtra(ContactsContract.ContactsColumns.CONTACT_NAME, String.valueOf(name));
                intent.putExtra(ContactsContract.ContactsColumns.CONTACT_EMAIL, String.valueOf(email));
                intent.putExtra(ContactsContract.ContactsColumns.CONTACT_PHONE, String.valueOf(phone));
                getContext().startActivity(intent);
            }
        });

        ImageButton delete = (ImageButton) view.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactsDialog dialog = new ContactsDialog();
                Bundle args = new Bundle();
                args.putString(ContactsDialog.DIALOG_TYPE, ContactsDialog.DELETE_RECORD);
                args.putString(ContactsContract.ContactsColumns.CONTACT_ID, String.valueOf(_id));
                args.putString(ContactsContract.ContactsColumns.CONTACT_NAME, name);
                dialog.setArguments(args);
                dialog.show(sFragmentManager, "delete-record");
            }
        });

        return view;
    }

    public void setData(List<Contact> contactList) {
        clear();
        if(contactList != null) {
            for(Contact contact: contactList) {
                add(contact);
            }
        }
    }
}
