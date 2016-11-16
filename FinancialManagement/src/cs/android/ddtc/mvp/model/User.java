package cs.android.ddtc.mvp.model;

/**
 * Represents a single User in the model
 * Information Holder
 * 
 * @author Team DDTC
 *
 */
public class User {
	/** the persons id */
	private String uid;
	/** the persons password */
	private String pw;
	/** the persons name */
	private String name;
	/** the persons email */
	private String email;
	
	/**
	 * Makes a new person
	 * @param u the id
	 * @param p the pw
	 * @param n the name
	 * @param e the email
	 */
	public User(String u, String p, String n) {
		uid = u;
		pw = p;
		name = n;
	}
	
	/**
	 * 
	 * @return the persons name
	 */
	public String getName() { return name; }
	/**
	 * 
	 * @return the persons id
	 */
	public String getUid() { return uid; }
	/**
	 * 
	 * @return the persons email
	 */
	public String getEmail() { return email; }
	
	/**
	 * 
	 * @return the persons password
	 */
	public String getPw() { return pw; }
	

}
