package cs.android.ddtc.mvp.Iviews;

/**
 * Interface to the login view.
 * 
 * It only holds a transition to another view 
 * 
 * @author Team DDTC
 *
 */
public interface ILoginView {
	
	void advanceToaccount(String uid);
	void advanceTomain();

}
