package cs.android.ddtc.mvp.presenters;

import cs.android.ddtc.mvp.Iviews.IReportView;

public class ReportPresenter {
	
	private final IReportView myView;
	
	public ReportPresenter(IReportView view){
		myView = view;
	}
	
	public void onSpendingClick(){
		myView.advanceToSpending();
	}
	
	public void onCashClick(){
		myView.advanceToCash();
	}
	
	public void onIncomeClick(){
		myView.advanceToIncome();
	}
	
	public void onXClick(){
		myView.advance();
	}

}
