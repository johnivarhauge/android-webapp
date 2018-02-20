package com.example.student.androidwebapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.webkit.JavascriptInterface;
import android.widget.Toast;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.RawContacts.Data;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;


public class WebAppInterface {
    Context mContext;
    String html = "";
    private static final Uri K_URI  = ContactsContract.Contacts.CONTENT_URI;
    private static final String ID   = ContactsContract.Contacts._ID;
    private static final String NAVN = ContactsContract.Contacts.DISPLAY_NAME;
    private static final String HPN  = ContactsContract.Contacts.HAS_PHONE_NUMBER;

    private static final Uri    TEL_URI  = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    private static final String TEL_ID   = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
    private static final String TEL_NUM  = ContactsContract.CommonDataKinds.Phone.NUMBER;
    private static final String TEL_TYP  = ContactsContract.CommonDataKinds.Phone.TYPE;

    private static final Uri    E_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
    private static final String E_ID  = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
    private static final String E_DAT = ContactsContract.CommonDataKinds.Email.DATA;
    private static final String E_TYP = ContactsContract.CommonDataKinds.Email.TYPE;

    private static final Uri    N_URI = ContactsContract.Data.CONTENT_URI;
    private static final String N_ID  = ContactsContract.CommonDataKinds.Nickname.CONTACT_ID;
    private static final String N_DAT = ContactsContract.CommonDataKinds.Nickname.DATA;
    private static final String N_TYP = ContactsContract.CommonDataKinds.Nickname.TYPE;

    // Instantiate the interface and set the context
    WebAppInterface(Context c) {
        mContext = c;
    }

    // Show a toast from the web page
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public int getAndroidVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    @JavascriptInterface
    public void showAndroidVersion(String versionName) {
        Toast.makeText(mContext, versionName, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public String getFullName(String nickname) {
        Cursor k = mContext.getApplicationContext().getContentResolver().query(K_URI, null, null, null, null);
        if (k.getCount() > 0)
            while (k.moveToNext()) {

                String id = k.getString(k.getColumnIndex(ID));

                Cursor n = mContext.getApplicationContext().getContentResolver().query(N_URI, null, N_ID + " = " + id, null, null);
                while (n.moveToNext()) {
                    //html += n.getString(n.getColumnIndex(N_TYP)) + ": " + n.getString(n.getColumnIndex(N_DAT)) + "<br>";
                    if (n.getString(n.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME)).equals(nickname)) {
                        html += k.getString(k.getColumnIndex(NAVN));
                    }
                }
                n.close();
            }
        k.close();

        return html;
    }
}
