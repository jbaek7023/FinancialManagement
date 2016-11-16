package cs.android.ddtc.mvp.views;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cs.android.ddtc.mvp.R;
import cs.android.ddtc.mvp.Iviews.IAccountView;
import cs.android.ddtc.mvp.model.Account;
import cs.android.ddtc.mvp.model.JSONParser;
import cs.android.ddtc.mvp.presenters.AccountPresenter;
import cs.android.ddtc.mvp.support.AcctListAdapter;

public class AccountActivity extends Activity implements IAccountView {

	private AccountPresenter myPresenter;
	private String uID;
	private String acctName;
	private ArrayList<Account> accts;
	private AcctListAdapter adapt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
		
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		uID = extras.getString("uid");
		acctName = extras.getString("acctname");
		
		myPresenter = new AccountPresenter(this, new JSONParser(), uID);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account, menu);
		return true;
	}

	private ListView getListViewField() {
		return (ListView) findViewById(R.id.acct_list);
	}

	private OnItemClickListener listClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			Intent intent = new Intent(getBaseContext(), HistoryActivity.class);
			Account select = (Account) adapt.getItem(position);
			Bundle extras = new Bundle();
			extras.putString("uid", uID);
			extras.putString("acctname", select.getAcct());
			intent.putExtras(extras);
			startActivity(intent);

		}

	};

	public void OnCreateClick(View v) {
		myPresenter.onCreateClick();
	}

	public void OnReportClick(View v) {
		myPresenter.onReportClick();
	}

	public void OnDoorClick(View v) {
		myPresenter.onMainClick();
	}

	@Override
	public void advanceToReport() {
		// create an intent (a request for phone OS to do something)
		Intent intent = new Intent(this, ReportActivity.class);
		intent.putExtra("uID", uID);
		startActivity(intent);

	}

	@Override
	public void advanceToMain() {
		// create an intent (a request for phone OS to do something)
		Intent intent = new Intent(this, MainActivity.class);
		// now pass that to the phone OS and ask to start it
		startActivity(intent);

	}

	@Override
	public void advanceToCreate() {
		// create an intent (a request for phone OS to do something)
		Intent intent = new Intent(this, CreateActivity.class);
		intent.putExtra("uID", uID);
		// now pass that to the phone OS and ask to start it
		startActivity(intent);

	}

	@Override
	public void setAccount(ArrayList<Account> a) {
		accts = a;
		adapt = new AcctListAdapter(this, accts);
		getListViewField().setAdapter(adapt);
		getListViewField().setOnItemClickListener(listClickListener);

	}

}
