package com.leeper.jordan.rolodex.datasource;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan on 10/26/2015.
 * Basic class for each contact.  Parcelable in order to move a list of them between fragment and activity.
 */
public class Contact implements Parcelable {
    private String firstName;
    private String lastName;

    public Contact(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Contact(Parcel in){
        String[] data = new String[3];

        in.readStringArray(data);
        this.firstName = data[0];
        this.lastName = data[1];
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.firstName,
                this.lastName});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}