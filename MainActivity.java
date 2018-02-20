package com.example.student.android_webapp;

import java.util.ArrayList;

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

public class MainActivity extends Activity {

    private static final Uri    K_URI  = ContactsContract.Contacts.CONTENT_URI;
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



    // private static final String MIMETYPE = ContactsContract.Data.MIMETYPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        android.webkit.WebView wv = new android.webkit.WebView(this);
        //JavaScriptGrensesnitt jg = new JavaScriptGrensesnitt();
        String html = getFullName("Kreutzz");
        wv.getSettings().setJavaScriptEnabled(true);
        wv.addJavascriptInterface(new WebAppInterface(this), "AndroidInterface");
        //wv.addJavascriptInterface(
        //        new JavaScriptGrensesnitt(this),
        //        "javaobjekt"
        //);
        wv.setWebChromeClient(new MyWebChromeClient());
        setContentView(wv);
        wv.loadUrl("file:///android_asset/index.html");
        //JavaScriptGrensesnitt.
    }

    public String getFullName(String nickname) {
        String html = "";

        Cursor k = getContentResolver().query(K_URI, null, null, null, null);
        if (k.getCount() > 0)
            while (k.moveToNext()) {

                String id = k.getString(k.getColumnIndex(ID));

                Cursor n = getContentResolver().query(N_URI, null, N_ID + " = " + id, null, null);
                while (n.moveToNext()) {
                    //html += n.getString(n.getColumnIndex(N_TYP)) + ": " + n.getString(n.getColumnIndex(N_DAT)) + "<br>";
                    if (n.getString(n.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME)).equals(nickname)) {
                        html += k.getString(k.getColumnIndex(NAVN)) + "<br>";
                    }
                }
                n.close();
            }
        k.close();

        return html;
    }
    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.d("LogTag", message);
            result.confirm();
            return true;
        }
    }
}