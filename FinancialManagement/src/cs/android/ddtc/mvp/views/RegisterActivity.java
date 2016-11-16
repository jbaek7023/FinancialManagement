package cs.android.ddtc.mvp.views;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import cs.android.ddtc.mvp.R;
import cs.android.ddtc.mvp.Iviews.IRegisterView;
import cs.android.ddtc.mvp.model.JSONParser;
import cs.android.ddtc.mvp.presenters.RegPresenter;

public class RegisterActivity extends Activity implements IRegisterView {
	
	/** The presenter for this activity */
	private RegPresenter myPresenter;
	private NotificationManager nm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		myPresenter = new RegPresenter(this, new JSONParser());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	
	/**
	 * Button handler for the login button
	 * 
	 * @param v not used
	 */
	public void onSignupClick(View v) {
		String uid = getUidField().getText().toString();
		String pass = getPassField().getText().toString();
		String confirm = getConfirmField().getText().toString();
		String name = getNameField().getText().toString();
		String email = getEmailField().getText().toString();
		String ticker = "FinancialManagement";
		String title = "Welcome!" + " " + uid;
		String text = "Thank you for sign up";
		myPresenter.onSignupClick(uid, pass, confirm, name, email, ticker, title, text);
	}
	
	private EditText getUidField()     {
        return (EditText) findViewById(R.id.registerID);
	}
	
	private EditText getPassField()     {
        return (EditText) findViewById(R.id.registerPW);
	}
	
	private EditText getConfirmField()     {
        return (EditText) findViewById(R.id.confirmPW);
	}
	
	private EditText getNameField()     {
        return (EditText) findViewById(R.id.registerName);
	}
	
	private EditText getEmailField()     {
        return (EditText) findViewById(R.id.registerEmail);
	}
	
	/**
	 * Button handler for the register button
	 * 
	 * @param v not used
	 */
	
	public void onCancelClick(View v){
		myPresenter.onCancelClick();
	}

	@Override
	public void advance() {
		//create an intent
		finish();
	}

	@Override
	public void fail() {
		// TODO Auto-generated method stub
		Toast.makeText((Context) this, "Sorry, userID is alrady exist.",
				Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void success() {
		// TODO Auto-generated method stub
		Toast.makeText((Context) this, "Your account has been created!",
				Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void failpw() {
		// TODO Auto-generated method stub
		Toast.makeText((Context) this, "Password must match",
				Toast.LENGTH_SHORT).show();
		
	}
	
	public void advanceToLogin(String ticker, String title, String text) {
		
		nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
		PendingIntent intent = PendingIntent.getActivity(
				RegisterActivity.this, 0, 
				new Intent(RegisterActivity.this, LoginActivity.class), 0);
		@SuppressWarnings("deprecation")
		Notification notification = new Notification(android.R.drawable.ic_input_add, ticker, System.currentTimeMillis());
		notification.setLatestEventInfo(RegisterActivity.this, 
				title, text, intent);
		nm.notify(1234, notification);
	}

}
