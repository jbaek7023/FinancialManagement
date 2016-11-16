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
import android.widget.TextView;
import cs.android.ddtc.mvp.R;
import cs.android.ddtc.mvp.Iviews.IHistView;
import cs.android.ddtc.mvp.model.History;
import cs.android.ddtc.mvp.model.JSONParser;
import cs.android.ddtc.mvp.presenters.HistPresenter;
import cs.android.ddtc.mvp.support.HistListAdapter;

public class HistoryActivity extends Activity implements IHistView{
	
	private HistPresenter myPresenter;
	private String uID;
	private String acctName;
	private ArrayList<History> hists;
	private HistListAdapter adapt;
	private double latitude;
	private double longitude;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		uID = extras.getString("uid");
		acctName = extras.getString("acctname");
		latitude = extras.getDouble("lat");
		longitude = extras.getDouble("long");
		
		myPresenter = new HistPresenter(this, new JSONParser(), uID, acctName);
		
		getTextViewField().setText(acctName);
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.history, menu);
		return true;
	}
	
	private ListView getListViewField(){
		return (ListView) findViewById(R.id.histList);
	}
	
	private OnItemClickListener listClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			Intent intent = new Intent(getBaseContext(), LocationActivity.class);
			Bundle extras = new Bundle();
			extras.putString("uid", uID);
			extras.putString("acctname", acctName);
			extras.putDouble("lat", latitude);
			extras.putDouble("long", longitude);
			Log.d("lat", Double.toString(latitude));
			Log.d("log", Double.toString(longitude));
			intent.putExtras(extras);
			startActivity(intent);
			// TODO Auto-generated method stub
			
		}
		
	};
	
	private TextView getTextViewField(){
		return (TextView) findViewById(R.id.HistAcctView);
	}
	
	public void OnXClick(View v){
		myPresenter.onXClick();
	}
	
	public void OnPlusClick(View v){
		myPresenter.onPlusClick();
	}

	@Override
	public void advanceToAdd() {
		Intent intent = new Intent(this, DepositActivity.class);
		Bundle extras = new Bundle();
		extras.putString("uid", uID);
		extras.putString("acctname", acctName);
		intent.putExtras(extras);
		startActivity(intent);
		
	}

	@Override
	public void advance() {
		Intent intent = new Intent(this, AccountActivity.class);
		Bundle extras = new Bundle();
		extras.putString("uid", uID);
		extras.putString("acctname", acctName);
		intent.putExtras(extras);
		startActivity(intent);
		
	}

	@Override
	public void setHistory(ArrayList<History> h) {
		hists = h;
		adapt = new HistListAdapter(this, hists);
		getListViewField().setAdapter(adapt);
		getListViewField().setOnItemClickListener(listClickListener);

		
	}

}
