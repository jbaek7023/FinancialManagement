package cs.android.ddtc.mvp.views;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import cs.android.ddtc.mvp.R;
import cs.android.ddtc.mvp.Iviews.ICreateView;
import cs.android.ddtc.mvp.model.JSONParser;
import cs.android.ddtc.mvp.presenters.CreatePresenter;

public class CreateActivity extends Activity implements ICreateView {
	protected static final int RESULT_SPEECH = 1;

	private ImageButton btnSpeak;
	private TextView txtText;

	private CreatePresenter myPresenter;
	private String uID;
	private NotificationManager nm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);
		txtText = (TextView) findViewById(R.id.acctEdit);

		btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

		btnSpeak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

				try {
					startActivityForResult(intent, RESULT_SPEECH);
					txtText.setText("");
				} catch (ActivityNotFoundException a) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Opps! Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT);
					t.show();
				}
			}
		});

		Intent intent = getIntent();
		uID = intent.getStringExtra("uID");
		myPresenter = new CreatePresenter(this, new JSONParser(), uID);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SPEECH: {
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				txtText.setText(text.get(0));
			}
			break;
		}

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create, menu);
		return true;
	}

	public void OnCreateClick(View v) {
		String ticker = "FinancialManagement";
		String title = "You just create new Account";
		String text = "Your new account name is" + " "
				+ getAcctField().getText().toString();
		if (getAcctField().getText().toString().equals("")) {
			Toast.makeText((Context) this, "Please enter account name first",
					Toast.LENGTH_SHORT).show();
		} else {
			String acct = getAcctField().getText().toString();
			myPresenter.onCreateClick(uID, acct, getAgreeField(), ticker,
					title, text);
		}
	}

	private EditText getAcctField() {
		return (EditText) findViewById(R.id.acctEdit);
	}

	/** check box should be handled in Presenter **/
	private boolean getAgreeField() {
		CheckBox check = (CheckBox) findViewById(R.id.agreeCheckbox);
		if (check.isChecked())
			return true;
		else
			return false;
	}

	public void OnCancelClick(View v) {
		myPresenter.onCancelClick(uID);
	}

	@Override
	public void advance(String uid) {
		Intent intent = new Intent(this, AccountActivity.class);
		Bundle extras = new Bundle();
		extras.putString("uid", uid);
		intent.putExtras(extras);
		startActivity(intent);

	}

	public void advanceToLogin(String ticker, String title, String text) {

		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		PendingIntent intent = PendingIntent.getActivity(CreateActivity.this,
				0, new Intent(CreateActivity.this, LoginActivity.class), 0);
		@SuppressWarnings("deprecation")
		Notification notification = new Notification(
				android.R.drawable.ic_input_add, ticker,
				System.currentTimeMillis());
		notification.setLatestEventInfo(CreateActivity.this, title, text,
				intent);
		nm.notify(1234, notification);
	}

}
