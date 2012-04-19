package org.whichplan;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

public class CalculatePlansActivity extends Activity {

	private Plan bestPlan;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.call_log_list);
		if(bestPlan == null) {
			new CalculatePlansTask().execute();
		}
	}
	
	class CalculatePlansTask extends AsyncTask<Void, Void, Void>  {

		private ProgressDialog dialog;
		
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
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			Calls calls = new CallRepository(getContentResolver());
			List<Call> lastMonthCalls = calls.allOfLastMonth().getCalls();
			OiCartao oiCartao = new OiCartao();
			oiCartao.calculate(new CallLog(lastMonthCalls));
			bestPlan = oiCartao;
			return null;
		}
		
	}
}
