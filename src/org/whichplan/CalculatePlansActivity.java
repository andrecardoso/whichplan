package org.whichplan;

import java.util.List;

import org.whichplan.call.Call;
import org.whichplan.call.CallRepository;
import org.whichplan.call.Calls;
import org.whichplan.plan.BestPlanFinder;
import org.whichplan.plan.Plan;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

public class CalculatePlansActivity extends ListActivity {

	private List<Plan> plans;
	
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
			this.activity.showPlanList();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			Calls calls = new CallRepository(getContentResolver());
			List<Call> lastMonthCalls = calls.allOfLastMonth().getCalls();
			BestPlanFinder finder = new BestPlanFinder(lastMonthCalls);
			plans = finder.calculatePlans();
			return null;
		}
		
	}
}
