package com.leeper.jordan.dexter.datasource;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jordan on 10/26/2015.
 * Basic class for each contact.  Parcelable in order to move a list of them between fragment and activity.
 */
public class Contact implements Parcelable {

    private int _id;
    private String name;
    private String email;
    private String phone;

    public Contact(int _id, String name, String email, String phone) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Contact(Parcel in){
        String[] data = new String[4];

        in.readStringArray(data);
        this._id = Integer.parseInt(data[0]);
        this.name = data[1];
        this.email = data[2];
        this.phone = data[3];
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {String.valueOf(this._id), this.name, this.email, this.phone});
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