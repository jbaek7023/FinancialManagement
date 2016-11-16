package cs.android.ddtc.mvp.Iviews;

/**
 * Interface to the welcome screen view.
 * 
 * It only holds a transition to another view 
 * 
 * @author Team DDTC
 *
 */
public interface IMainView {
	
	void advanceToLogin();
	void advanceToReg();

}
