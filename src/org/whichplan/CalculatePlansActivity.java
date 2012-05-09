package org.whichplan;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.whichplan.call.Call;
import org.whichplan.call.CallLog;
import org.whichplan.call.CallRepository;
import org.whichplan.call.Calls;
import org.whichplan.plan.Plan;
import org.whichplan.plan.PlanAnalyser;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class CalculatePlansActivity extends ListActivity {

	private List<Plan> plans;
	public Date firstCallDate;
	public Date lastCallDate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plan_list);
		if(plans == null) {
			new CalculatePlansTask(this).execute();
		}
	}
	
	void showPlanList() {
		ListAdapter adapter = new ArrayAdapter<Plan>(this, android.R.layout.simple_list_item_1, plans);
        this.setListAdapter(adapter);
	}
	
	void showResult() {
		this.showSummary();
		this.showPlanList();		
	}
	
	void showSummary() {
		TextView summary = (TextView) this.findViewById(R.id.summary);
		summary.append(this.getString(R.string.from));
		summary.append(" ");
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		summary.append(dateFormat.format(firstCallDate));
		summary.append(" ");
		summary.append(this.getString(R.string.to));
		summary.append(" ");
		summary.append(dateFormat.format(lastCallDate));
	}

	class CalculatePlansTask extends AsyncTask<Void, Void, Void>  {

		private ProgressDialog dialog;
		private CalculatePlansActivity activity;
		
		public CalculatePlansTask(CalculatePlansActivity activity) {
			this.activity = activity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(CalculatePlansActivity.this);
			dialog.setMessage(getString(R.string.calculating_best_plan));
			dialog.show();
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dialog.dismiss();
			this.activity.showResult();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			Calls calls = new CallRepository(getContentResolver());
			CallLog callLog = calls.allOfLastMonth();
			List<Call> lastMonthCalls = callLog.getCalls();
			PlanAnalyser analyser = new PlanAnalyser(lastMonthCalls);
			plans = analyser.analysePlans();
			firstCallDate = callLog.getFirstCallDate();
			lastCallDate = callLog.getLastCallDate();
			return null;
		}
		
	}
}
