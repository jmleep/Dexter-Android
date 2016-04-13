package com.leeper.jordan.dexter.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leeper.jordan.dexter.R;
import com.leeper.jordan.dexter.datasource.Contact;

import java.util.List;

/**
 * Created by jordan on 12/3/15.
 */
public class ContactDetailsFragment extends Fragment {

    private TextView name;
    private TextView phone;
    private TextView email;
    private ImageButton textMessageButton;
    private ImageButton phoneCallButton;
    private ImageButton emailButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.contact_details, container, false);

        final Contact contactToView = (Contact) getArguments().get("contactToView");

        name = (TextView) view.findViewById(R.id.contact_details_name);
        phone = (TextView) view.findViewById(R.id.contact_details_phone);
        email = (TextView) view.findViewById(R.id.contact_details_email);

        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.contact_details_buttons);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        if(null != contactToView.getPhone()) {
            textMessageButton = new ImageButton(getActivity());
            textMessageButton.setBackground(null);
            textMessageButton.setImageResource(R.drawable.ic_chat_tinted);

            phoneCallButton = new ImageButton(getActivity());
            phoneCallButton.setBackground(null);
            phoneCallButton.setImageResource(R.drawable.ic_call_tinted);

            linearLayout.addView(phoneCallButton, params);
            linearLayout.addView(textMessageButton, params);
        }

        if(null != contactToView.getEmail()) {
            emailButton = new ImageButton(getActivity());
            emailButton.setBackground(null);
            emailButton.setImageResource(R.drawable.ic_email_tinted);

            linearLayout.addView(emailButton, params);
        }

        name.setText(contactToView.getName());
        phone.setText(contactToView.getPhone());
        email.setText(contactToView.getEmail());

        textMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent text = new Intent(Intent.ACTION_VIEW);
                text.setData(Uri.parse("sms:" + contactToView.getPhone()));

                if(userHasNecessaryApp(text)) {
                    startActivity(text);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Please be sure an application is installed that can handle this action",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        phoneCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:" + contactToView.getPhone()));

                if(userHasNecessaryApp(call)) {
                    startActivity(call);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Please be sure an application is installed that can handle this action",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + contactToView.getEmail()));

                if(userHasNecessaryApp(email)) {
                    startActivity(email);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Please be sure an application is installed that can handle this action",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    private boolean userHasNecessaryApp(Intent intent) {
        PackageManager packageManager = getContext().getPackageManager();
        List activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return activities.size() > 0;
    }

}
