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
import cs.android.ddtc.mvp.Iviews.IAccountView;
import cs.android.ddtc.mvp.model.Account;
import cs.android.ddtc.mvp.model.JSONParser;
import cs.android.ddtc.mvp.support.Constants;

public class AccountPresenter {
	
	private final IAccountView myView;
	private final JSONParser myModel;
	private ArrayList<Account> acctList;
	private ProgressDialog pDialog;
	JSONArray accts = null;
	
	
	
	public AccountPresenter(IAccountView view, JSONParser model, String uid){
		myView = view;
		myModel = model;
		new LoadAllAccounts(uid).execute();
		
	}
	
	class LoadAllAccounts extends AsyncTask<String, String, String>{
		
		String ID;
		
		public LoadAllAccounts(String uid){
			ID = uid;
			acctList = new ArrayList<Account>();
		}
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog((Context)myView);
			pDialog.setMessage("Loading accounts. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("ID", ID));
			JSONObject json = myModel.makeHttpRequest(Constants.url_acctlist, "GET", params);
			
			Log.d("All accts", json.toString());
			
			try{
				int success = json.getInt(Constants.TAG_SUCCESS);
				if(success == 1){
					accts = json.getJSONArray("product");
					
					for(int i = 0; i < accts.length(); i ++){
						JSONObject c = accts.getJSONObject(i);
						Log.d("acct", c.getString(Constants.TAG_ACCTNAME));
						Log.d("bal", c.getString(Constants.TAG_BALANCE));
						acctList.add(new Account(c.getString(Constants.TAG_ACCTNAME), c.getString(Constants.TAG_BALANCE)));
						
						
					}
				}
				else{
					
				}
			}catch(JSONException e){
				e.printStackTrace();
			}
			return null;
		}
		
		protected void onPostExecute(String file_url){
			pDialog.dismiss();
			myView.setAccount(acctList);
		}
		
	}
	
	/**
	 * Handle a Create button click in the ui.
	 * Just advance to the next screen, but don't
	 * do anything to the model.
	 */
	public void onCreateClick(){
		myView.advanceToCreate();
	}
	
	/**
	 * Handle a Report button click in the ui.
	 * Just advance to the next screen, but don't
	 * do anything to the model.
	 */
	public void onReportClick(){
		myView.advanceToReport();
	}
	
	/**
	 * Handle a Main button click in the ui.
	 * Just advance to the next screen, but don't
	 * do anything to the model.
	 */
	public void onMainClick(){
		myView.advanceToMain();
	}
	

}
