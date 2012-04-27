package org.whichplan;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

public class CallLogActivity extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_log_list);
        loadCallLog();
    }

	private void loadCallLog() {
		Calls calls = new CallRepository(this.getContentResolver());
		List<Call> lastMonthCalls = calls.allOfLastMonth().getCalls();
		ListAdapter adapter = new ArrayAdapter<Call>(this, android.R.layout.simple_list_item_1, lastMonthCalls);
        this.setListAdapter(adapter);
	}
}