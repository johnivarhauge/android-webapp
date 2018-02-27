import java.util.ArrayList;
        import java.util.HashMap;
        import android.app.Application;
        import android.content.ContentResolver;
        import android.content.Context;
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

public class MainActivity extends Activity {

    private static final String CONTACT_ID = ContactsContract.Contacts._ID;
    private static final String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

    private static final String PHONE_NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
    private static final String PHONE_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;

    public static ArrayList<String> getAll(Context context) {
        ContentResolver cr = context.getContentResolver();

        Cursor pCur = cr.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{PHONE_NUMBER, PHONE_CONTACT_ID},
                null,
                null,
                null
        );
        if(pCur != null){
            if(pCur.getCount() > 0) {
                HashMap<Integer, ArrayList<String>> phones = new HashMap<>();
                while (pCur.moveToNext()) {
                    Integer contactId = pCur.getInt(pCur.getColumnIndex(PHONE_CONTACT_ID));
                    ArrayList<String> curPhones = new ArrayList<>();
                    if (phones.containsKey(contactId)) {
                        curPhones = phones.get(contactId);
                    }
                    curPhones.add(pCur.getString(pCur.getColumnIndex(PHONE_NUMBER)));
                    phones.put(contactId, curPhones);
                }
                Cursor cur = cr.query(
                        ContactsContract.Contacts.CONTENT_URI,
                        new String[]{CONTACT_ID, HAS_PHONE_NUMBER},
                        HAS_PHONE_NUMBER + " > 0",
                        null,null);
                if (cur != null) {
                    if (cur.getCount() > 0) {
                        ArrayList<String> contacts = new ArrayList<>();
                        while (cur.moveToNext()) {
                            int id = cur.getInt(cur.getColumnIndex(CONTACT_ID));
                            if(phones.containsKey(id)) {
                                contacts.addAll(phones.get(id));
                            }
                        }
                        return contacts;
                    }
                    cur.close();
                }
            }
            pCur.close();
        }
        return null;
    }