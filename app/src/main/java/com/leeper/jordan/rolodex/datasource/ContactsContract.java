package com.leeper.jordan.rolodex.datasource;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Jordan on 11/5/2015. Define contract for interacting with the Contacts Database.
 */
public class ContactsContract {
    public interface ContactsColumns {
        String CONTACT_ID = "_id";
        String CONTACT_NAME = "contact_name";
        String CONTACT_EMAIL = "contact_email";
        String CONTACT_PHONE = "contact_phone";
    }

    public static final String CONTENT_AUTHORITY = "com.leeper.jordan.rolodex.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_CONTACTS = "contacts";
    public static final Uri URI_TABLE = Uri.parse(BASE_CONTENT_URI.toString() + "/" + PATH_CONTACTS);

    public static final String[] TOP_LEVEL_PATHS = {
            PATH_CONTACTS
    };

    public static class Contacts implements ContactsColumns, BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_CONTACTS).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + ".contacts";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + ".contacts";

        public static Uri buildContactUri(String contactId) {
            return CONTENT_URI.buildUpon().appendEncodedPath(contactId).build();
        }

        public static String getContactId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }
}
