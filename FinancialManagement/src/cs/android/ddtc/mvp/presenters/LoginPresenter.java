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
import android.widget.Toast;
import cs.android.ddtc.mvp.Iviews.ILoginView;
import cs.android.ddtc.mvp.model.JSONParser;
import cs.android.ddtc.mvp.support.Constants;

public class LoginPresenter {
	/** the view to manipulate */
	private final ILoginView myView;
	private final JSONParser myModel;
	private ProgressDialog pDialog;
	
	/**
	 * Make the presenter.
	 * 
	 * @param view the view to use
	 * 
	 */
	public LoginPresenter(ILoginView view, JSONParser model) {
		myView = view;
		myModel = model;
	}
	
	/**
	 * Handle a login button click in the ui. Just advance to the next screen,
	 * but don't do anything to the model.
	 */
	public void onLoginClick(String uid, String pass) {
		
		new LoginUser(uid, pass).execute();
	}
	
	class LoginUser extends AsyncTask<String, String, String>{
		
		String ID, PW;

		public LoginUser(String uid, String pass) {
			ID = uid;
			PW = pass;
		}
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog((Context)myView);
			pDialog.setMessage("Login Process. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("ID", ID));
			params.add(new BasicNameValuePair("PASSWORD", PW));
			
			JSONObject json = myModel.makeHttpRequest(Constants.url_login, "GET", params);
			
			Log.d("Get User Information", json.toString());
			try{
				int success = json.getInt(Constants.TAG_SUCCESS);
				
				if(success == 1){
					JSONArray userObj = json.getJSONArray("product");
					JSONObject user = userObj.getJSONObject(0);
					
					Log.d("ID", user.getString(Constants.TAG_ID));
					Log.d("PW", user.getString(Constants.TAG_PASSWORD));
					
					myView.advanceToaccount(ID);
				}
				else{
					
				}
			}catch(JSONException e){
				e.printStackTrace();
			}
			return null;
		}
		
		protected void onPostExecute(String file_url) {

			pDialog.dismiss();
		}
		
	}
	
	protected void success() {
		Toast.makeText((Context) myView, "Login Success!",
				Toast.LENGTH_LONG).show();
	}
	
	protected void fail() {
		Toast.makeText((Context) myView, "Sorry, unrecognized username or password.",
				Toast.LENGTH_LONG).show();
	}

}
