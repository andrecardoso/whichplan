package org.whichplan.call;

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
	public CallLog allOfLastMonth() {
		List<Call> calls = new ArrayList<Call>();
		
		String[] from = new String[] {NUMBER,  DATE, CACHED_NUMBER_LABEL, DURATION};
				
		String[] selectionArgs = {today(), lastMonth()};
		Cursor c = getContentResolver().query(
                        android.provider.CallLog.Calls.CONTENT_URI, 
                        from, 
                        DATE + " <= ? and " + DATE +" >= ? and " + TYPE + " = " + OUTGOING_TYPE,
                        selectionArgs, 
                        android.provider.CallLog.Calls.DATE + " DESC");
		
		if(c != null) {
			int numberIndex = c.getColumnIndex(NUMBER);
			int dateIndex = c.getColumnIndex(DATE);
			int numberLabelIndex = c.getColumnIndex(CACHED_NUMBER_LABEL);
			int durationIndex = c.getColumnIndex(DURATION);
			while(c.moveToNext()) {
				Call call = new Call(new Date(c.getLong(dateIndex)), 
						c.getInt(durationIndex), 
						c.getString(numberIndex), 
						c.getString(numberLabelIndex));
				calls.add(call);
			}
		}
   
		return new CallLog(calls);
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