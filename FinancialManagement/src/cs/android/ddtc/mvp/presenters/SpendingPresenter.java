package cs.android.ddtc.mvp.presenters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import cs.android.ddtc.mvp.Iviews.ISpendingView;
import cs.android.ddtc.mvp.model.JSONParser;
import cs.android.ddtc.mvp.support.Constants;

public class SpendingPresenter {

	private final ISpendingView myView;
	private final JSONParser myModel;
	private ProgressDialog pDialog;
	JSONArray totals = null;
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	public SpendingPresenter(ISpendingView view, JSONParser model) {
		myView = view;
		myModel = model;

	}

	public void onXClick() {
		myView.advance();
	}

	public void onStartClick() {
		myView.showStartDate();
	}

	public void onEndClick() {
		myView.showEndDate();
	}

	public void onUpdateClick(String uid, String start, String end)
			throws ParseException, JSONException {

		UpdateExpense up = (UpdateExpense) new UpdateExpense(uid, start, end)
				.execute();
		Log.d("total", Double.toString(up.totalFood));
	}

	class UpdateExpense extends AsyncTask<String, String, String> {
		String ID, START, END;
		double totalFood, totalGas, totalShop, totalUtil, totalGro, total;

		public UpdateExpense(String uid, String start, String end)
				throws ParseException {
			ID = uid;
			START = start;
			END = end;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog((Context) myView);
			pDialog.setMessage("Calculating expense. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("ID", ID));
			params.add(new BasicNameValuePair("CATEGORY", "Food"));

			JSONObject json = myModel.makeHttpRequest(Constants.url_translist,
					"GET", params);

			try {
				int success = json.getInt(Constants.TAG_SUCCESS);
				if (success == 1) {
					totals = json.getJSONArray("product");

					for (int i = 0; i < totals.length(); i++) {
						JSONObject c = totals.getJSONObject(i);
						Date startDateVar = sdf.parse(START);
						Date endDateVar = sdf.parse(END);

						startDateVar = getYesterDate(startDateVar);
						endDateVar = getTomorrowDate(endDateVar);
						Date timeDateVar = sdf.parse(c
								.getString(Constants.TAG_DATE));
						if (timeDateVar.after(startDateVar)
								&& timeDateVar.before(endDateVar)) {
							totalFood += c.getDouble(Constants.TAG_AMOUNT);
						}
					}
				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("ID", ID));
			params1.add(new BasicNameValuePair("CATEGORY", "Shopping"));

			JSONObject json1 = myModel.makeHttpRequest(Constants.url_translist,
					"GET", params1);

			try {
				int success = json.getInt(Constants.TAG_SUCCESS);
				if (success == 1) {
					totals = json1.getJSONArray("product");

					for (int i = 0; i < totals.length(); i++) {
						JSONObject c = totals.getJSONObject(i);
						
						Date startDateVar = sdf.parse(START);
						Date endDateVar = sdf.parse(END);

						startDateVar = getYesterDate(startDateVar);
						endDateVar = getTomorrowDate(endDateVar);
						Date timeDateVar = sdf.parse(c
								.getString(Constants.TAG_DATE));
						if (timeDateVar.after(startDateVar)
								&& timeDateVar.before(endDateVar)) {
							totalShop += c.getDouble(Constants.TAG_AMOUNT);
						}
						
					}
				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<NameValuePair> params2 = new ArrayList<NameValuePair>();
			params2.add(new BasicNameValuePair("ID", ID));
			params2.add(new BasicNameValuePair("CATEGORY", "Grocery"));

			JSONObject json2 = myModel.makeHttpRequest(Constants.url_translist,
					"GET", params2);

			try {
				int success = json2.getInt(Constants.TAG_SUCCESS);
				if (success == 1) {
					totals = json2.getJSONArray("product");

					for (int i = 0; i < totals.length(); i++) {
						JSONObject c = totals.getJSONObject(i);
						Date startDateVar = sdf.parse(START);
						Date endDateVar = sdf.parse(END);

						startDateVar = getYesterDate(startDateVar);
						endDateVar = getTomorrowDate(endDateVar);
						Date timeDateVar = sdf.parse(c
								.getString(Constants.TAG_DATE));
						if (timeDateVar.after(startDateVar)
								&& timeDateVar.before(endDateVar)) {
							totalGro += c.getDouble(Constants.TAG_AMOUNT);
						}
					}
				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<NameValuePair> params3 = new ArrayList<NameValuePair>();
			params3.add(new BasicNameValuePair("ID", ID));
			params3.add(new BasicNameValuePair("CATEGORY", "Gas"));

			JSONObject json3 = myModel.makeHttpRequest(Constants.url_translist,
					"GET", params3);

			try {
				int success = json3.getInt(Constants.TAG_SUCCESS);
				if (success == 1) {
					totals = json3.getJSONArray("product");

					for (int i = 0; i < totals.length(); i++) {
						JSONObject c = totals.getJSONObject(i);
						Date startDateVar = sdf.parse(START);
						Date endDateVar = sdf.parse(END);

						startDateVar = getYesterDate(startDateVar);
						endDateVar = getTomorrowDate(endDateVar);
						Date timeDateVar = sdf.parse(c
								.getString(Constants.TAG_DATE));
						if (timeDateVar.after(startDateVar)
								&& timeDateVar.before(endDateVar)) {
							totalGas += c.getDouble(Constants.TAG_AMOUNT);
						}
					}
				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<NameValuePair> params4 = new ArrayList<NameValuePair>();
			params4.add(new BasicNameValuePair("ID", ID));
			params4.add(new BasicNameValuePair("CATEGORY", "Utility"));

			JSONObject json4 = myModel.makeHttpRequest(Constants.url_translist,
					"GET", params4);

			try {
				int success = json4.getInt(Constants.TAG_SUCCESS);
				if (success == 1) {
					totals = json4.getJSONArray("product");

					for (int i = 0; i < totals.length(); i++) {
						JSONObject c = totals.getJSONObject(i);
						Date startDateVar = sdf.parse(START);
						Date endDateVar = sdf.parse(END);

						startDateVar = getYesterDate(startDateVar);
						endDateVar = getTomorrowDate(endDateVar);
						Date timeDateVar = sdf.parse(c
								.getString(Constants.TAG_DATE));
						if (timeDateVar.after(startDateVar)
								&& timeDateVar.before(endDateVar)) {
							totalUtil += c.getDouble(Constants.TAG_AMOUNT);
						}
					}
				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			total = totalFood + totalGro + totalShop + totalGas + totalUtil;
			myView.advanceToUpdate(totalFood, totalGro, totalShop, totalGas,
					totalUtil, total);
		}
	}

	public void onGraphClick(String uID, String string, String string2) {
		// TODO Auto-generated method stub
		new UpdateGraph(uID, string, string2).execute();
	}

	class UpdateGraph extends AsyncTask<String, String, String> {
		String ID, START, END;
		double totalFood, totalGas, totalShop, totalUtil, totalGro, total;

		public UpdateGraph(String uid, String start, String end) {
			ID = uid;
			START = start;
			END = end;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog((Context) myView);
			pDialog.setMessage("Calculating expense. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("ID", ID));
			params.add(new BasicNameValuePair("CATEGORY", "Food"));

			JSONObject json = myModel.makeHttpRequest(Constants.url_translist,
					"GET", params);

			try {
				int success = json.getInt(Constants.TAG_SUCCESS);
				if (success == 1) {
					totals = json.getJSONArray("product");

					for (int i = 0; i < totals.length(); i++) {
						JSONObject c = totals.getJSONObject(i);
						Date startDateVar = sdf.parse(START);
						Date endDateVar = sdf.parse(END);

						startDateVar = getYesterDate(startDateVar);
						endDateVar = getTomorrowDate(endDateVar);
						Date timeDateVar = sdf.parse(c
								.getString(Constants.TAG_DATE));
						if (timeDateVar.after(startDateVar)
								&& timeDateVar.before(endDateVar)) {
							totalFood += c.getDouble(Constants.TAG_AMOUNT);
						}
					}
				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("ID", ID));
			params1.add(new BasicNameValuePair("CATEGORY", "Shopping"));

			JSONObject json1 = myModel.makeHttpRequest(Constants.url_translist,
					"GET", params1);

			try {
				int success = json.getInt(Constants.TAG_SUCCESS);
				if (success == 1) {
					totals = json1.getJSONArray("product");

					for (int i = 0; i < totals.length(); i++) {
						JSONObject c = totals.getJSONObject(i);
						Date startDateVar = sdf.parse(START);
						Date endDateVar = sdf.parse(END);

						startDateVar = getYesterDate(startDateVar);
						endDateVar = getTomorrowDate(endDateVar);
						Date timeDateVar = sdf.parse(c
								.getString(Constants.TAG_DATE));
						if (timeDateVar.after(startDateVar)
								&& timeDateVar.before(endDateVar)) {
							totalShop += c.getDouble(Constants.TAG_AMOUNT);
						}
					}
				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<NameValuePair> params2 = new ArrayList<NameValuePair>();
			params2.add(new BasicNameValuePair("ID", ID));
			params2.add(new BasicNameValuePair("CATEGORY", "Grocery"));

			JSONObject json2 = myModel.makeHttpRequest(Constants.url_translist,
					"GET", params2);

			try {
				int success = json2.getInt(Constants.TAG_SUCCESS);
				if (success == 1) {
					totals = json2.getJSONArray("product");

					for (int i = 0; i < totals.length(); i++) {
						JSONObject c = totals.getJSONObject(i);
						Date startDateVar = sdf.parse(START);
						Date endDateVar = sdf.parse(END);

						startDateVar = getYesterDate(startDateVar);
						endDateVar = getTomorrowDate(endDateVar);
						Date timeDateVar = sdf.parse(c
								.getString(Constants.TAG_DATE));
						if (timeDateVar.after(startDateVar)
								&& timeDateVar.before(endDateVar)) {
							totalGro += c.getDouble(Constants.TAG_AMOUNT);
						}
					}
				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<NameValuePair> params3 = new ArrayList<NameValuePair>();
			params3.add(new BasicNameValuePair("ID", ID));
			params3.add(new BasicNameValuePair("CATEGORY", "Gas"));

			JSONObject json3 = myModel.makeHttpRequest(Constants.url_translist,
					"GET", params3);

			try {
				int success = json3.getInt(Constants.TAG_SUCCESS);
				if (success == 1) {
					totals = json3.getJSONArray("product");

					for (int i = 0; i < totals.length(); i++) {
						JSONObject c = totals.getJSONObject(i);
						Date startDateVar = sdf.parse(START);
						Date endDateVar = sdf.parse(END);

						startDateVar = getYesterDate(startDateVar);
						endDateVar = getTomorrowDate(endDateVar);
						Date timeDateVar = sdf.parse(c
								.getString(Constants.TAG_DATE));
						if (timeDateVar.after(startDateVar)
								&& timeDateVar.before(endDateVar)) {
							totalGas += c.getDouble(Constants.TAG_AMOUNT);
						}

					}
				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<NameValuePair> params4 = new ArrayList<NameValuePair>();
			params4.add(new BasicNameValuePair("ID", ID));
			params4.add(new BasicNameValuePair("CATEGORY", "Utility"));

			JSONObject json4 = myModel.makeHttpRequest(Constants.url_translist,
					"GET", params4);

			try {
				int success = json4.getInt(Constants.TAG_SUCCESS);
				if (success == 1) {
					totals = json4.getJSONArray("product");

					for (int i = 0; i < totals.length(); i++) {
						JSONObject c = totals.getJSONObject(i);
						Date startDateVar = sdf.parse(START);
						Date endDateVar = sdf.parse(END);

						startDateVar = getYesterDate(startDateVar);
						endDateVar = getTomorrowDate(endDateVar);
						Date timeDateVar = sdf.parse(c
								.getString(Constants.TAG_DATE));
						if (timeDateVar.after(startDateVar)
								&& timeDateVar.before(endDateVar)) {
							totalUtil += c.getDouble(Constants.TAG_AMOUNT);
						}
					}
				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			total = totalFood + totalGro + totalShop + totalGas + totalUtil;
			myView.advanceToGraph(totalFood, totalGro, totalShop, totalGas,
					totalUtil, total);
		}
	}

	private Calendar DateToCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	private Date getYesterDate(Date date) throws ParseException {
		Calendar cal = DateToCalendar(date);
		cal.add(Calendar.DATE, -1);
		String stringType = sdf.format(cal.getTime());
		Date output = sdf.parse(stringType);
		return output;
	}

	private Date getTomorrowDate(Date date) throws ParseException {
		Calendar cal = DateToCalendar(date);
		cal.add(Calendar.DATE, 1);
		String stringType = sdf.format(cal.getTime());
		Date output = sdf.parse(stringType);
		return output;
	}

}
