package org.whichplan;

import static android.provider.CallLog.Calls.*;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class CallLogActivity extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_log_list);
        loadCallLog();
    }

	private void loadCallLog() {
        Cursor c = getContentResolver().query(
                        android.provider.CallLog.Calls.CONTENT_URI, 
                        null,  null,  null, 
                        android.provider.CallLog.Calls.DATE + " DESC");

        startManagingCursor(c);
        
        String[] from = new String[] {NUMBER,  DATE,  TYPE,  CACHED_NAME,  CACHED_NUMBER_LABEL,  CACHED_NUMBER_TYPE,  DURATION,  NEW};
        //String[] from = new String[] {NUMBER,  DATE};
        int[] to = new int[] {R.id.number, R.id.date, R.id.type, R.id.cached_name, R.id.cached_number_label, R.id.cached_number_type, R.id.duration, R.id.new_flag };
   
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,  R.layout.call_row,  c,  from,  to);
        this.setListAdapter(adapter);
		
	}
}