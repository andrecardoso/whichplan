package org.whichplan;

import static android.provider.CallLog.Calls.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.ContentResolver;
import android.database.Cursor;

public class CallRepository implements Calls {

	private ContentResolver contentResolver;

	public CallRepository(ContentResolver contentResolver) {
		this.contentResolver = contentResolver;
	}

	@Override
	public List<Call> allOfLastMonth() {
		List<Call> calls = new ArrayList<Call>();
		
		String[] from = new String[] {NUMBER,  DATE,  TYPE,  CACHED_NAME,  CACHED_NUMBER_LABEL,  CACHED_NUMBER_TYPE,  DURATION};
				
		String[] selectionArgs = {today(), lastMonth()};
		Cursor c = getContentResolver().query(
                        android.provider.CallLog.Calls.CONTENT_URI, 
                        from, 
                        DATE + " <= ? and " + DATE +" >= ?",
                        selectionArgs, 
                        android.provider.CallLog.Calls.DATE + " DESC");
		
		
		if(c != null) {
			int numberIndex = c.getColumnIndex(NUMBER);
			int dateIndex = c.getColumnIndex(DATE);
			int nameIndex = c.getColumnIndex(CACHED_NAME);
			int numberLabelIndex = c.getColumnIndex(CACHED_NUMBER_LABEL);
			int numberTypeIndex = c.getColumnIndex(CACHED_NUMBER_TYPE);
			int durationIndex = c.getColumnIndex(DURATION);
			int typeIndex = c.getColumnIndex(TYPE);
			while(c.moveToNext()) {
				Call call = new Call(c.getString(nameIndex), 
						new Date(c.getLong(dateIndex)), 
						c.getInt(durationIndex), 
						c.getString(numberIndex), 
						c.getString(numberLabelIndex), 
						CallType.values()[c.getInt(typeIndex)-1], 
						c.getInt(numberTypeIndex));
				calls.add(call);
			}
		}
   
		return calls;
	}

	private String lastMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		long time = calendar.getTimeInMillis();
		return Long.toString(time);
	}

	private String today() {
		long time = new Date().getTime();
		return Long.toString(time);
	}

	public ContentResolver getContentResolver() {
		return contentResolver;
	}
	
	

}
