package cs.android.ddtc.mvp.presenters;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import cs.android.ddtc.mvp.Iviews.ICreateView;
import cs.android.ddtc.mvp.model.JSONParser;
import cs.android.ddtc.mvp.support.Constants;

public class CreatePresenter {
	
	private final ICreateView myView;
	public JSONParser myModel;
	
	/**
	 * Make the presenter.
	 * @param view the view to use
	 */
	public CreatePresenter(ICreateView view, JSONParser model, String uid){
		myView = view;
		myModel = model;
	}
	
	public void onCreateClick(String uID, String acct, boolean checked, String ticker, String title, String text){
		if(checked == true){
			new CreateNewAcct(uID, acct).execute();
			myView.advanceToLogin(ticker, title, text);
			
		}
		else{
			fail();
		}
	}
	
	class CreateNewAcct extends AsyncTask<String, String, String>{
		String ID, ACCTNAME;
		double BALANCE;
		
		public CreateNewAcct(String uID, String acct){
			ID = uID;
			ACCTNAME = acct;
			BALANCE = 0;
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("ID", ID));
			params.add(new BasicNameValuePair("ACCTNAME", ACCTNAME));
			params.add(new BasicNameValuePair("BALANCE", Double.toString(BALANCE)));
			
			JSONObject json = myModel.makeHttpRequest(Constants.url_creatacct, "POST", params);
			
			Log.d("Create Response", json.toString());
			
			try{
				int success = json.getInt(Constants.TAG_SUCCESS);
				if(success == 1){
					myView.advance(ID);
					
				}
				else{
					
				}
			}catch(JSONException e){
				e.printStackTrace();
			}
			return null;
		}
	}


	protected void fail() {
		Toast.makeText((Context) myView, "Please check the agreement first",
				Toast.LENGTH_SHORT).show();
	}

	
	/**
	 * Handle a Cancel button click in the ui.
	 * Just advance to the next screen, but don't
	 * do anything to the model.
	 */
	public void onCancelClick(String uid){
		myView.advance(uid);
	}
	
	

}
