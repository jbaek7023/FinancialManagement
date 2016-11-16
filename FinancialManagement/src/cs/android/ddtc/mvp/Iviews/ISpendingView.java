package cs.android.ddtc.mvp.Iviews;

public interface ISpendingView {
	
	void advance();
	
	void advanceToGraph(double f, double gro, double s, double gas, double u, double t);
	
	void advanceToUpdate(double f, double gro, double s, double gas, double u, double t);
	
	void showStartDate();
	
	void showEndDate();

}

