package cs.android.ddtc.mvp.presenters;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import cs.android.ddtc.mvp.Iviews.IHistView;
import cs.android.ddtc.mvp.model.History;
import cs.android.ddtc.mvp.model.JSONParser;
import cs.android.ddtc.mvp.support.Constants;

public class HistPresenter {

	private final IHistView myView;
	private final JSONParser myModel;
	private ArrayList<History> histlist;
	private ProgressDialog pDialog;
	JSONArray hists = null;

	public HistPresenter(IHistView view, JSONParser model, String uid,
			String acct) {
		myView = view;
		myModel = model;
		new LoadAllHistory(uid, acct).execute();

	}

	class LoadAllHistory extends AsyncTask<String, String, String> {
		String ID, ACCTNAME;

		public LoadAllHistory(String uid, String acct) {
			ID = uid;
			ACCTNAME = acct;
			histlist = new ArrayList<History>();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog((Context) myView);
			pDialog.setMessage("Loading account history. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("ID", ID));
			params.add(new BasicNameValuePair("ACCTNAME", ACCTNAME));
			JSONObject json = myModel.makeHttpRequest(Constants.url_histlist,
					"GET", params);

			Log.d("All hists", json.toString());

			try {
				int success = json.getInt(Constants.TAG_SUCCESS);
				if (success == 1) {
					hists = json.getJSONArray("product");

					for (int i = 0; i < hists.length(); i++) {
						JSONObject c = hists.getJSONObject(i);
						Log.d("date", c.getString(Constants.TAG_DATE));
						Log.d("date", c.getString(Constants.TAG_CATEGORY));
						Log.d("date", c.getString(Constants.TAG_AMOUNT));
						histlist.add(new History(c
								.getString(Constants.TAG_DATE), c
								.getString(Constants.TAG_CATEGORY), c
								.getString(Constants.TAG_AMOUNT)));
					}
				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			myView.setHistory(histlist);
		}
	}

	public void onXClick() {
		myView.advance();
	}

	public void onPlusClick() {
		myView.advanceToAdd();
	}

}
