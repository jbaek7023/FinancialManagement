package cs.android.ddtc.mvp.presenters;

import cs.android.ddtc.mvp.Iviews.IMainView;
/**
 * Main presenter. Should not know anything about an Android specific thing
 * 
 * @author Team DDTC
 *
 */
public class MainPresenter {
	/** the view to manipulate */
	private final IMainView myView;
	
	/**
	 * Make the presenter.
	 * @param view the view to use
	 */
	public MainPresenter(IMainView view){
		myView = view;
	}
	
	/**
	 * Handle a Login button click in the ui.
	 * Just advance to the next screen, but don't
	 * do anything to the model.
	 */
	public void onLoginClick(){
		myView.advanceToLogin();
	}
	
	/**
	 * Handle a Register button click in the ui.
	 * Just advance to the next screen, but don't
	 * do anything to the model.
	 */
	public void onRegisterClick(){
		myView.advanceToReg();
	}

}
