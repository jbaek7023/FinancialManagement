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
import cs.android.ddtc.mvp.Iviews.ICashflowView;
import cs.android.ddtc.mvp.model.JSONParser;
import cs.android.ddtc.mvp.support.Constants;

public class CashflowPresenter {
	
	private final ICashflowView myView;
	private final JSONParser myModel;
	private ProgressDialog pDialog;
	JSONArray totals = null;
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	
	public CashflowPresenter(ICashflowView view, JSONParser model){
		myView = view;
		myModel = model;
	}
	
	public void onXClick(){
		myView.advance();
	}
	
	public void onStartClick(){
		myView.showStartDate();
	}
	
	public void onEndClick(){
		myView.showEndDate();
	}
	
	public void onUpdateClick(String uid, String start, String end) throws ParseException{
	
		new UpdateCashFlow(uid, start, end).execute();
	}
	
	class UpdateCashFlow extends AsyncTask<String, String, String>{
		String ID, START, END;
		double totalincome, totalexpense, cashflow;
		
		public UpdateCashFlow(String uid, String start, String end){
			ID = uid;
			START= start;
			END = end;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog((Context) myView);
			pDialog.setMessage("Calculating cashflow. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("ID", ID));
			
			JSONObject json = myModel.makeHttpRequest(Constants.url_cashflow, "GET", params);
			
			try{
				int success = json.getInt(Constants.TAG_SUCCESS);
				if(success == 1){
					totals = json.getJSONArray("product");
					
					for(int i = 0; i < totals.length(); i ++){
						JSONObject c = totals.getJSONObject(i);
						Date startDateVar = sdf.parse(START);
						Date endDateVar = sdf.parse(END);

						startDateVar = getYesterDate(startDateVar);
						endDateVar = getTomorrowDate(endDateVar);
						Date timeDateVar = sdf.parse(c
								.getString(Constants.TAG_DATE));
						if (timeDateVar.after(startDateVar) && timeDateVar.before(endDateVar) && c.getDouble(Constants.TAG_AMOUNT) > 0) {
							totalincome += c.getDouble(Constants.TAG_AMOUNT);
						}
						else if(timeDateVar.after(startDateVar) && timeDateVar.before(endDateVar) && c.getDouble(Constants.TAG_AMOUNT) < 0){
							totalexpense += c.getDouble(Constants.TAG_AMOUNT);
						}
			
					}
				
					
				}
				else{
					
				}
			}catch(JSONException e){
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			cashflow = totalincome - totalexpense;
			myView.advanceToUpdate(totalincome, totalexpense, cashflow);
		}
		
		
	}
	
	public void onGraphClick(String uid, String start, String end) throws ParseException{
		
		new UpdateGraph(uid, start, end).execute();

	}
	
	class UpdateGraph extends AsyncTask<String, String, String>{
		String ID, START, END;
		double totalincome, totalexpense, cashflow;
		
		public UpdateGraph(String uid, String start, String end){
			ID = uid;
			START= start;
			END = end;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog((Context) myView);
			pDialog.setMessage("Calculating cashflow. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("ID", ID));
			
			JSONObject json = myModel.makeHttpRequest(Constants.url_cashflow, "GET", params);
			
			try{
				int success = json.getInt(Constants.TAG_SUCCESS);
				if(success == 1){
					totals = json.getJSONArray("product");
					
					for(int i = 0; i < totals.length(); i ++){
						JSONObject c = totals.getJSONObject(i);
						Date startDateVar = sdf.parse(START);
						Date endDateVar = sdf.parse(END);

						startDateVar = getYesterDate(startDateVar);
						endDateVar = getTomorrowDate(endDateVar);
						Date timeDateVar = sdf.parse(c
								.getString(Constants.TAG_DATE));
						if (timeDateVar.after(startDateVar) && timeDateVar.before(endDateVar) && c.getDouble(Constants.TAG_AMOUNT) > 0) {
							totalincome += c.getDouble(Constants.TAG_AMOUNT);
						}
						else if(timeDateVar.after(startDateVar) && timeDateVar.before(endDateVar) && c.getDouble(Constants.TAG_AMOUNT) < 0){
							totalexpense += c.getDouble(Constants.TAG_AMOUNT);
						}
			
					}
				
					
				}
				else{
					
				}
			}catch(JSONException e){
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			cashflow = totalincome - totalexpense;
			myView.advanceToGraph(totalincome, totalexpense);
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
