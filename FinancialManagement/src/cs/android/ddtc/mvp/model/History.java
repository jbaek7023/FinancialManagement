package cs.android.ddtc.mvp.model;

public class History {
	
	public String uid;
	public String acct;
	public String date;
	public String category;
	public double cost;
	public String strcost;
	
	public History(String u, String a, String d, String c1, double c2){
		uid = u;
		acct = a;
		date = d;
		category = c1;
		cost = c2;
	}
	
	public History(String d, String c1, String c2){
		date = d;
		category = c1;
		strcost = c2;
	}
	
	/**
	 * 
	 * @return the persons id
	 */
	public String getUid() { return uid; }
	/**
	 * 
	 * @return the accounts name
	 */
	public String getAcct() { return acct; }
	
	public String getCategory() { return category; }
	
	public double getCost() { return cost; }
	
	public String getDate() { return date; }
	
	public String getStrCost() { return strcost; }

}
