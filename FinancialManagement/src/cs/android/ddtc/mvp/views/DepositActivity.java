package cs.android.ddtc.mvp.views;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cs.android.ddtc.mvp.R;
import cs.android.ddtc.mvp.Iviews.IDepositView;
import cs.android.ddtc.mvp.model.JSONParser;
import cs.android.ddtc.mvp.presenters.DepositPresenter;

public class DepositActivity extends Activity implements IDepositView {

	private DepositPresenter myPresenter;
	public String uID;
	public String acct;
	public String date;
	public double latitude;
	public double longitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deposit);
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		uID = extras.getString("uid");
		acct = extras.getString("acctname");
		latitude = extras.getDouble("lat");
		longitude = extras.getDouble("long");
		
		myPresenter = new DepositPresenter(this, new JSONParser());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.deposit, menu);
		return true;
	}
	
	public void OnGoogleClick(View v){
		Intent intent = new Intent(this, GoogleMapActivity.class);
		Bundle extras = new Bundle();
		extras.putString("uid", uID);
		extras.putString("acctname", acct);
		intent.putExtras(extras);
		startActivity(intent);
	}

	public void OnClick(View v) {

		if ((getDateField().getText().toString().equals("") || getAmountField()
				.getText().toString().equals(""))) {
			Toast.makeText((Context) this, "Please select date and enter amount",
					Toast.LENGTH_SHORT).show();
		} else {

			Double amount = Double.parseDouble(getAmountField().getText()
					.toString());
			switch (v.getId()) {
			case R.id.savings:
				myPresenter.OnActionClick(uID, acct, date, "Savings", amount, latitude, longitude);
				break;

			case R.id.salary:
				myPresenter.OnActionClick(uID, acct, date, "Salary", amount, latitude, longitude);
				break;

			case R.id.misc:
				myPresenter.OnActionClick(uID, acct, date, "Misc", amount, latitude, longitude);
				break;

			case R.id.food:
				myPresenter.OnActionClick(uID, acct, date, "Food", -amount, latitude, longitude);
				break;

			case R.id.shopping:
				myPresenter.OnActionClick(uID, acct, date, "Shopping", -amount, latitude, longitude);
				break;

			case R.id.gas:
				myPresenter.OnActionClick(uID, acct, date, "Gas", -amount, latitude, longitude);
				break;

			case R.id.grocery:
				myPresenter.OnActionClick(uID, acct, date, "Grocery", -amount, latitude, longitude);
				break;

			case R.id.utility:
				myPresenter.OnActionClick(uID, acct, date, "Utility", -amount, latitude, longitude);
				break;

			default:
				break;
			}
		}
	}


	public void OnCalendarClick(View v) {
		myPresenter.OnCalendarClick();

	}

	private EditText getAmountField() {
		return (EditText) findViewById(R.id.depositamount);
	}

	private TextView getDateField() {
		return (TextView) findViewById(R.id.depositDate);
	}

	@Override
	public void advance(String uid) {
		Intent intent = new Intent(this, HistoryActivity.class);
		Bundle extras = new Bundle();
		extras.putString("uid", uID);
		extras.putString("acctname", acct);
		extras.putDouble("lat", latitude);
		extras.putDouble("long", longitude);
		intent.putExtras(extras);
		startActivity(intent);

	}

	@Override
	public void showDate() {

		final Calendar c = Calendar.getInstance();
		int myYear = c.get(Calendar.YEAR);
		int myMonth = c.get(Calendar.MONTH);
		int myDay = c.get(Calendar.DAY_OF_MONTH);

		Dialog dlgDate = new DatePickerDialog(this, myDateSetListener, myYear,
				myMonth, myDay);
		dlgDate.show();

	}

	private DatePickerDialog.OnDateSetListener myDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			date = String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth) + "/" + String.valueOf(year);
			getDateField().setText(date);
		}
	};

}
