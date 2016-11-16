package cs.android.ddtc.mvp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import cs.android.ddtc.mvp.R;
import cs.android.ddtc.mvp.Iviews.IReportView;
import cs.android.ddtc.mvp.presenters.ReportPresenter;

public class ReportActivity extends Activity implements IReportView {
	
	private ReportPresenter myPresenter;

	private String uID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		myPresenter = new ReportPresenter(this);
		Intent intent = getIntent();
    	uID = intent.getStringExtra("uID");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.report, menu);
		return true;
	}
	
	public void OnSpendingClick(View v){
		myPresenter.onSpendingClick();
	}
	
	public void OnCashClick(View v){
		myPresenter.onCashClick();
	}
	
	public void OnIncomeClick(View v){
		myPresenter.onIncomeClick();
	}
	
	public void OnXClick(View v){
		myPresenter.onXClick();
	}
	
	

	@Override
	public void advanceToSpending() {
		// create an intent (a request for phone OS to do something)
		Intent intent = new Intent(this, SpendingActivity.class);
		intent.putExtra("uID", uID);
		startActivity(intent);

		
	}

	@Override
	public void advanceToCash() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, CashflowActivity.class);
		intent.putExtra("uID", uID);
		startActivity(intent);
		
	}

	@Override
	public void advanceToIncome() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, IncomeActivity.class);
		intent.putExtra("uID", uID);
		startActivity(intent);
		
	}

	@Override
	public void advance() {
		Intent intent = new Intent(this, AccountActivity.class);
		Bundle extras = new Bundle();
		extras.putString("uid", uID);
		intent.putExtras(extras);
		startActivity(intent);
		
	}

}
