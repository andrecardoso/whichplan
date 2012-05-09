package org.whichplan.call;

import static android.provider.CallLog.Calls.CACHED_NUMBER_LABEL;
import static android.provider.CallLog.Calls.DATE;
import static android.provider.CallLog.Calls.DURATION;
import static android.provider.CallLog.Calls.NUMBER;
import static android.provider.CallLog.Calls.OUTGOING_TYPE;
import static android.provider.CallLog.Calls.TYPE;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.whichplan.contact.ContactUtil;

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
				
		String[] selectionArgs = {lastDay(), firstDay()};
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

				String operator = ContactUtil.getOperatorByNumber(getContentResolver(), call.getPhoneNumber());
				
				if (operator != null) {
					call.setOperator(operator);	
				}
				
				calls.add(call);
			}
		}
   
		return new CallLog(calls);
	}

	private String lastDay() {
		Calendar calendar = getLastMonth();
		int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return getDayInMillis(calendar, lastDay);
	}

	private String getDayInMillis(Calendar calendar, int day) {
		calendar.clear(Calendar.HOUR_OF_DAY);
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		long time = calendar.getTimeInMillis();
		return Long.toString(time);
	}

	private String firstDay() {
		Calendar calendar = getLastMonth();
		int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		return getDayInMillis(calendar, firstDay);
	}

	private Calendar getLastMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		return calendar;
	}

	public ContentResolver getContentResolver() {
		return contentResolver;
	}

}