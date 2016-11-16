package cs.android.ddtc.mvp.views;

import java.text.ParseException;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import cs.android.ddtc.mvp.R;
import cs.android.ddtc.mvp.Iviews.ICashflowView;
import cs.android.ddtc.mvp.model.JSONParser;
import cs.android.ddtc.mvp.presenters.CashflowPresenter;

public class CashflowActivity extends Activity implements ICashflowView {
	
	private CashflowPresenter myPresenter;
	
	private String uID;
	
	String date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cashflow);
		myPresenter = new CashflowPresenter(this, new JSONParser());
		Intent intent = getIntent();
		uID = intent.getStringExtra("uID");

		getUidViewField().setText(uID);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cashflow, menu);
		return true;
	}
	
	public void OnXClick(View v) {
		myPresenter.onXClick();
	}
	
	public void OnUpdateClick(View v) throws ParseException {
		if (getStartDateField().getText().toString().equals("") || getEndDateField().getText().toString().equals("")) {
			Toast.makeText((Context) this, "Please select date range first!",
					Toast.LENGTH_SHORT).show();
		} else {
			myPresenter.onUpdateClick(uID, getStartDateField().getText().toString(),
					getEndDateField().getText().toString());
		}

	}
	
	public void OnGraphClick(View v) throws ParseException {
		if (getStartDateField().getText().toString().equals("") || getEndDateField().getText().toString().equals("")) {
			Toast.makeText((Context) this, "Please select date range first!",
					Toast.LENGTH_SHORT).show();
		} else{
			myPresenter.onGraphClick(uID,  getStartDateField().getText().toString(),
					getEndDateField().getText().toString());
		}
	}
	
	public void OnStartClick(View v){
		myPresenter.onStartClick();
	}
	
	public void OnEndClick(View v){
		myPresenter.onEndClick();
	}
	
	private TextView getUidViewField(){
		return (TextView) findViewById(R.id.cashreportuser);
	}
	
	private TextView getIncomeViewField(){
		return (TextView) findViewById(R.id.cashincomeval);
	}
	
	private TextView getExpenseViewField(){
		return (TextView) findViewById(R.id.cashexpenseval);
	}
	
	private TextView getFlowViewField(){
		return (TextView) findViewById(R.id.cashTotalval);
	}
	
	private TextView getStartDateField(){
		return (TextView)findViewById(R.id.cashstartingdate);
	}
	
	private TextView getEndDateField() {
		return (TextView) findViewById(R.id.cashendingdate);
	}

	@Override
	public void advance() {
		Intent intent = new Intent(this, ReportActivity.class);
		intent.putExtra("uID", uID);
		startActivity(intent);
		
	}

	@Override
	public void advanceToGraph(double i, double e) {
		Intent intent = new Intent(this, PieGraphActivity.class);
		Bundle extras = new Bundle();
		extras.putString("uid", uID);
		extras.putDouble("income", i);
		extras.putDouble("expense", e);
		extras.putInt("btnId", R.id.cashchart);
		intent.putExtras(extras);
		startActivity(intent);
		
	}

	@Override
	public void advanceToUpdate(double i, double e, double t) {
		getIncomeViewField().setText(String.valueOf(i));
		getExpenseViewField().setText(String.valueOf(e));
		getFlowViewField().setText(String.valueOf(t));
		
	}

	@Override
	public void showStartDate() {
		final Calendar c = Calendar.getInstance();
		int myYear = c.get(Calendar.YEAR);
		int myMonth = c.get(Calendar.MONTH);
		int myDay = c.get(Calendar.DAY_OF_MONTH);

		Dialog dlgDate = new DatePickerDialog(this, myStartDateSetListener, myYear,
				myMonth, myDay);
		dlgDate.show();
		
	}
	
	private DatePickerDialog.OnDateSetListener myStartDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			date = String.valueOf(monthOfYear + 1) + "/"
					+ String.valueOf(dayOfMonth) + "/" + String.valueOf(year);
			getStartDateField().setText(date);
		}
	};

	@Override
	public void showEndDate() {
		final Calendar c = Calendar.getInstance();
		int myYear = c.get(Calendar.YEAR);
		int myMonth = c.get(Calendar.MONTH);
		int myDay = c.get(Calendar.DAY_OF_MONTH);

		Dialog dlgDate = new DatePickerDialog(this, myEndDateSetListener, myYear,
				myMonth, myDay);
		dlgDate.show();
		
	}
	private DatePickerDialog.OnDateSetListener myEndDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			date = String.valueOf(monthOfYear + 1) + "/"
					+ String.valueOf(dayOfMonth) + "/" + String.valueOf(year);
			getEndDateField().setText(date);
		}
	};
}
