package cs.android.ddtc.mvp.presenters;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import cs.android.ddtc.mvp.Iviews.IDepositView;
import cs.android.ddtc.mvp.model.JSONParser;
import cs.android.ddtc.mvp.support.Constants;

public class DepositPresenter {

	private final IDepositView myView;
	public JSONParser myModel;
	private ProgressDialog pDialog;
	public double mybalance;

	public DepositPresenter(IDepositView view, JSONParser model) {
		myView = view;
		myModel = model;

	}

	public void OnActionClick(String uid, String acct, String date,
			String category, double cost, double latitude, double longitude) {
		new CreateNewHistory(uid, acct, date, category, cost, latitude, longitude).execute();
		new UpdateBalance(uid, acct, cost).execute();
	}
	
	

	class UpdateBalance extends AsyncTask<String, String, String> {
		String ID, ACCTNAME;
		double BALANCE;

		public UpdateBalance(String uID, String acct, double balance) {
			ID = uID;
			ACCTNAME = acct;
			BALANCE = balance;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog((Context) myView);
			pDialog.setMessage("Saving product ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("ID", ID));
			params.add(new BasicNameValuePair("ACCTNAME", ACCTNAME));
			params.add(new BasicNameValuePair("BALANCE", Double.toString(BALANCE)));

			JSONObject json = myModel.makeHttpRequest(Constants.url_update,
					"POST", params);

			try {
				int success = json.getInt(Constants.TAG_SUCCESS);
				if (success == 1) {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product uupdated
			pDialog.dismiss();
		}
	}

	class CreateNewHistory extends AsyncTask<String, String, String> {
		String ID, ACCTNAME, DATE, CATEGORY;
		double AMOUNT, LATITUDE, LONGITUDE;

		public CreateNewHistory(String uID, String acct, String date,
				String category, double cost, double latitude, double longitude) {
			ID = uID;
			ACCTNAME = acct;
			DATE = date;
			CATEGORY = category;
			AMOUNT = cost;
			LATITUDE = latitude;
			LONGITUDE = longitude;
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("ID", ID));
			params.add(new BasicNameValuePair("DATE", DATE));
			params.add(new BasicNameValuePair("CATEGORY", CATEGORY));
			params.add(new BasicNameValuePair("ACCTNAME", ACCTNAME));
			params.add(new BasicNameValuePair("AMOUNT", Double.toString(AMOUNT)));
			params.add(new BasicNameValuePair("LATITUDE", Double.toString(LATITUDE)));
			params.add(new BasicNameValuePair("LONGITUDE", Double.toString(LONGITUDE)));

			JSONObject json = myModel.makeHttpRequest(Constants.url_createhist,
					"POST", params);
			Log.d("Create History", json.toString());

			try {
				int success = json.getInt(Constants.TAG_SUCCESS);
				if (success == 1) {
					myView.advance(ID);
				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	//

	public void OnCalendarClick() {
		myView.showDate();
	}

	protected void success() {
		Toast.makeText((Context) myView, "Trancaction Accepted.",
				Toast.LENGTH_SHORT).show();
	}
}
