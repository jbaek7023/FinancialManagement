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
import cs.android.ddtc.mvp.Iviews.IRegisterView;
import cs.android.ddtc.mvp.model.JSONParser;
import cs.android.ddtc.mvp.support.Constants;

public class RegPresenter {
	/** the view to manipulate */
	private final IRegisterView myView;
	public JSONParser myModel;

	// private final DbOpenHelper myModel;

	/**
	 * Make the presenter.
	 * 
	 * @param view
	 *            the view to use
	 */
	public RegPresenter(IRegisterView view, JSONParser model) {
		myView = view;
		myModel = model;
	}

	/**
	 * Handle a Sign-up button click in the ui. Just advance to the next screen,
	 * but don't do anything to the model.
	 */
	public void onSignupClick(String uID, String pw, String confirm,
			String name, String email, String ticker, String title, String text) {

		if (pw.equals(confirm)) {
			new RegisterNewUser(uID, pw, name, email).execute();
			myView.advanceToLogin(ticker, title, text);
			myView.success();

		} else {
			myView.failpw();
		}
	}

	class RegisterNewUser extends AsyncTask<String, String, String> {

		String ID, PW, NAME, EMAIL;

		public RegisterNewUser(String uID, String pw, String name, String email) {
			// TODO Auto-generated constructor stub
			ID = uID;
			PW = pw;
			NAME = name;
			EMAIL = email;

		}

		@Override
		protected String doInBackground(String... arg0) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("ID", ID));
			params.add(new BasicNameValuePair("PASSWORD", PW));
			params.add(new BasicNameValuePair("NAME", NAME));
			params.add(new BasicNameValuePair("EMAIL", EMAIL));

			JSONObject json = myModel.makeHttpRequest(Constants.url_register,
					"POST", params);

			Log.d("Create Response", json.toString());

			try {
				int success = json.getInt(Constants.TAG_SUCCESS);
				if (success == 1) {

					myView.advance();

				} else {
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

	}

	/**
	 * Handle a Cancel button click in the ui. Just advance to the next screen,
	 * but don't do anything to the model.
	 */
	public void onCancelClick() {
		myView.advance();
	}
}
