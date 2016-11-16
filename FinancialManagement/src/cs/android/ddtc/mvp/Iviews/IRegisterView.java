package cs.android.ddtc.mvp.Iviews;

/**
 * Interface to the register screen view.
 * 
 * It only holds a transition to another view 
 * 
 * @author Team DDTC
 *
 */
public interface IRegisterView {
	
	void advance();
	void fail();
	void success();
	void failpw();
	void advanceToLogin(String ticker, String title, String text);

}
