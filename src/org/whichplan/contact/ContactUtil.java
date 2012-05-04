package org.whichplan.contact;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;

public class ContactUtil {
	
	public static String getOperatorByNumber(ContentResolver contentResolver, String number) {
	    Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));

	    Cursor contactLookup = contentResolver.query(uri, new String[] {PhoneLookup.LABEL}, null, null, null);

	    try {
	        if (contactLookup != null && contactLookup.getCount() > 0) {
	        	while (contactLookup.moveToNext()) {
	        		String operator = contactLookup.getString(contactLookup.getColumnIndexOrThrow(PhoneLookup.LABEL));
	        		
	        		if (operator != null) {
	        			return operator;
	        		}
	        	}
	            
	        }
	    } catch (Exception e) {
	    	return null;
	    } finally {
	        if (contactLookup != null) {
	            contactLookup.close();
	        }
	    }

	    return null;
	}

}
