package cs.android.ddtc.mvp.Iviews;

import java.util.ArrayList;

import cs.android.ddtc.mvp.model.Account;

public interface IAccountView {
	
	void advanceToReport();
	void advanceToMain();
	void advanceToCreate();
	
	void setAccount(ArrayList<Account> a);

}
