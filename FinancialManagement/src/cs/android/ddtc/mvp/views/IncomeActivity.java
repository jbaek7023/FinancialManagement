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
import cs.android.ddtc.mvp.Iviews.IIncomeView;
import cs.android.ddtc.mvp.model.JSONParser;
import cs.android.ddtc.mvp.presenters.IncomePresenter;

public class IncomeActivity extends Activity implements IIncomeView {

	private IncomePresenter myPresenter;

	private String uID;
	String date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_income);
		myPresenter = new IncomePresenter(this, new JSONParser());
		Intent intent = getIntent();
		uID = intent.getStringExtra("uID");

		getUidViewField().setText(uID);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.income, menu);
		return true;
	}

	public void OnXClick(View v) {
		myPresenter.onXClick();
	}
	
	public void OnStartClick(View v){
		myPresenter.onStartClick();
	}
	
	public void OnEndClick(View v){
		myPresenter.onEndClick();
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
		} else {
			myPresenter.onGraphClick(uID, getStartDateField().getText().toString(),
					getEndDateField().getText().toString());
		}

	}

	private TextView getUidViewField() {
		return (TextView) findViewById(R.id.incomereportuser);
	}

	private TextView getSavingsViewField() {
		return (TextView) findViewById(R.id.incomesavingsvalue);
	}

	private TextView getSalaryViewField() {
		return (TextView) findViewById(R.id.incomesalaryvalue);
	}

	private TextView getMiscViewField() {
		return (TextView) findViewById(R.id.incomemiscvalue);
	}

	private TextView getTotalViewField() {
		return (TextView) findViewById(R.id.incometotalvalue);
	}

	private TextView getStartDateField() {
		return (TextView) findViewById(R.id.incomestartingdate);
	}

	private TextView getEndDateField() {
		return (TextView) findViewById(R.id.incomeendingdate);
	}


	@Override
	public void advance() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, ReportActivity.class);
		intent.putExtra("uID", uID);
		startActivity(intent);

	}

	@Override
	public void advanceToGraph(double sa, double s, double m, double t) {

		Intent intent = new Intent(this, PieGraphActivity.class);
		Bundle extras = new Bundle();
		extras.putString("uid", uID);
		extras.putDouble("savings", sa);
		extras.putDouble("salary", s);
		extras.putDouble("misc", m);
		extras.putDouble("total", t);
		extras.putInt("btnId", R.id.incomechart);
		intent.putExtras(extras);
		startActivity(intent);

	}

	@Override
	public void advanceToUpdate(double sa, double s, double m, double t) {

		getSavingsViewField().setText(String.valueOf(sa));
		getSalaryViewField().setText(String.valueOf(s));
		getMiscViewField().setText(String.valueOf(m));
		getTotalViewField().setText(String.valueOf(t));

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