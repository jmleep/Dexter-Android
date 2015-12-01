package com.leeper.jordan.rolodex.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.leeper.jordan.rolodex.R;
import com.leeper.jordan.rolodex.datasource.Contact;

/**
 * Created by jordan on 11/23/15.
 */
public class ContactDetailsActivity extends AppCompatActivity {

    private TextView name;
    private TextView phone;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        Contact contactToView = (Contact) getIntent().getExtras().get("contactToView");

        name = (TextView) findViewById(R.id.contact_details_name);
        phone = (TextView) findViewById(R.id.contact_details_phone);
        email = (TextView) findViewById(R.id.contact_details_email);

        name.setText(contactToView.getName());
        phone.setText(contactToView.getPhone());
        email.setText(contactToView.getEmail());
    }
}
