package com.leeper.jordan.rolodex;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.leeper.jordan.rolodex.activities.ContactsListActivity;
import com.leeper.jordan.rolodex.datasource.ContactsContract;

import org.w3c.dom.Text;

import static android.app.AlertDialog.*;

/**
 * Created by Jordan on 11/8/2015.
 */
public class ContactsDialog extends DialogFragment {

    private LayoutInflater mLayoutInflater;
    public static final String DIALOG_TYPE = "command";
    public static final String DELETE_RECORD = "deleteRecord";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mLayoutInflater = getActivity().getLayoutInflater();
        final View view = mLayoutInflater.inflate(R.layout.contacts_dialog, null);
        String command = getArguments().getString(DIALOG_TYPE);
        if(command.equals(DELETE_RECORD)) {
            final int _id = getArguments().getInt(ContactsContract.ContactsColumns.CONTACT_ID);
            String name = getArguments().getString(ContactsContract.ContactsColumns.CONTACT_NAME);
            TextView popup = (TextView) view.findViewById(R.id.popup_message);
            popup.setText("Are you sure you want to delete " + name + " from your contacts?");
            builder.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ContentResolver contentResolver = getActivity().getContentResolver();
                    Uri uri = ContactsContract.Contacts.buildContactUri(String.valueOf(_id));
                    contentResolver.delete(uri, null, null);
                    Intent intent = new Intent(getActivity().getApplicationContext(), ContactsListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }

        return builder.create();

    }
}
