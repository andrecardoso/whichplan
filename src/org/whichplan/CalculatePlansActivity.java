package org.whichplan;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CalculatePlansActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button calculatePlansButton = (Button) findViewById(R.id.button_calculate_plans);
		calculatePlansButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new CalculatePlansTask().execute(null);
			}
		});
	}
	
	class CalculatePlansTask extends AsyncTask<Void, Void, Void>  {

		private ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(CalculatePlansActivity.this);
			dialog.setTitle("Calculating...");
			dialog.setMessage("Calculating best plan...");
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
			double calculate = oiCartao.calculate(new CallLog(lastMonthCalls));
			return null;
		}
		
	}
}
