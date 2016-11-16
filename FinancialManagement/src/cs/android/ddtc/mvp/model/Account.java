package cs.android.ddtc.mvp.model;

/**
 * Represents a single Account in the model
 * Information Holder
 * 
 * @author Team DDTC
 *
 */
public class Account {
	/** the persons id */
	public String uid;
	/** the accounts name */
	public String acct;
	/** the accounts balance */
	private double balance;
	/** the account strBalance */
	public String strbalance;
	
	public Account(String u, String a, double b){
		uid = u;
		acct = a;
		balance = b;
//		strbalance = Double.toString(b);
	}
	
	  public Account(String a, String b) {
	    	this.acct = a;
	    	this.strbalance = b;

			
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
	/**
	 * 
	 * @return the accounts balance
	 */
	public double getBalance() { return balance; }
	
	/**
	 * 
	 * @return the accounts strbalance
	 */
	public String getStrBalance() { return strbalance; }
	

	

}
